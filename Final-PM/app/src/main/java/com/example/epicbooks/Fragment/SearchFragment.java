package com.example.epicbooks.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.epicbooks.Activity.LoginActivity;
import com.example.epicbooks.Adapter.BookAdapter;
import com.example.epicbooks.Api.BookApiService;
import com.example.epicbooks.Api.RetrofitClient;
import com.example.epicbooks.Model.BookInfo;
import com.example.epicbooks.Model.BookResponse;
import com.example.epicbooks.Model.Item;
import com.example.epicbooks.Model.VolumeInfo;
import com.example.epicbooks.R;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private ArrayList<BookInfo> bookInfoArrayList;
    private ProgressBar progressBar;
    private EditText searchEdt;
    private ImageButton searchBtn;
    private ImageView logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        sharedPreferences = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }

        progressBar = view.findViewById(R.id.idLoadingPB);
        searchEdt = view.findViewById(R.id.idEdtSearchBooks);
        searchBtn = view.findViewById(R.id.idBtnSearch);

        searchBtn.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            String query = searchEdt.getText().toString().trim();
            if (query.isEmpty()) {
                searchEdt.setError("Please enter search query");
                progressBar.setVisibility(View.GONE);
                return;
            }
            getBooksInfo(query);
        });

        logout = view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(v -> {
            saveLoginStatus(false);
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }

    private void saveLoginStatus(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

    private void getBooksInfo(String query) {
        bookInfoArrayList = new ArrayList<>();

        // membuat instance retrofit
        Retrofit retrofit = RetrofitClient.getClient("https://www.googleapis.com/books/v1/");
        BookApiService apiService = retrofit.create(BookApiService.class);

        // panggil API dengan endpoint getBooks
        Call<BookResponse> call = apiService.getBooks(query);

        // tangani respons dari API.
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookResponse> call, @NonNull Response<BookResponse> response) {
                progressBar.setVisibility(View.GONE);
                // kalau sukses, memproses hasil pencarian dan menambahkan buku ke arraylist
                if (response.isSuccessful() && response.body() != null) {
                    BookResponse bookResponse = response.body();
                    if (bookResponse.getItems() != null) {
                        List<Item> items = bookResponse.getItems();
                        for (Item item : items) {
                            VolumeInfo volumeInfo = item.getVolumeInfo();
                            if (volumeInfo != null && volumeInfo.getTitle() != null) {
                                String title = volumeInfo.getTitle().toLowerCase();
                                if (title.contains(query.toLowerCase())) {
                                    BookInfo bookInfo = new BookInfo(
                                            volumeInfo.getTitle(),
                                            volumeInfo.getSubtitle(),
                                            volumeInfo.getAuthors() != null ? new ArrayList<>(volumeInfo.getAuthors()) : new ArrayList<>(),
                                            volumeInfo.getPublisher(),
                                            volumeInfo.getPublishedDate(),
                                            volumeInfo.getDescription(),
                                            volumeInfo.getPageCount(),
                                            volumeInfo.getImageLinks() != null ? volumeInfo.getImageLinks().getThumbnail() : null,
                                            volumeInfo.getPreviewLink(),
                                            volumeInfo.getInfoLink()
                                    );
                                    bookInfoArrayList.add(bookInfo);
                                }
                            }
                        }
                    }

                    if (bookInfoArrayList.isEmpty()) {
                        Toast.makeText(requireContext(), "No books found", Toast.LENGTH_SHORT).show();
                    }

                    // kalau pencarian berhasil, hasil pencarian buku ditampilkan dalam RecyclerView
                    BookAdapter adapter = new BookAdapter(bookInfoArrayList, requireContext());
                    RecyclerView mRecyclerView = getView().findViewById(R.id.idRVBooks);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false));
                    mRecyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(requireContext(), "Failed to get data from server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView iv_home = view.findViewById(R.id.IV_Home);
        ImageView iv_search = view.findViewById(R.id.IV_Search);
        ImageView iv_profile = view.findViewById(R.id.IV_Profile);

        iv_home.setOnClickListener(v -> {
            HomeFragment homeFragment = new HomeFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, homeFragment)
                    .addToBackStack(null)
                    .commit();
        });

        iv_search.setOnClickListener(v -> {
            SearchFragment searchFragment = new SearchFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, searchFragment)
                    .addToBackStack(null)
                    .commit();
        });

        iv_profile.setOnClickListener(v -> {
            UserFragment userFragment = new UserFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, userFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}
