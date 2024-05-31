package com.example.epicbooks.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class HomeFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerView;
    private ArrayList<BookInfo> bookInfoArrayList;
    private BookAdapter adapter;
    private ProgressBar progressBar;
    private ImageView logout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = requireContext().getSharedPreferences("user_pref", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (!isLoggedIn) {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }

        mRecyclerView = view.findViewById(R.id.idRVBooks);
        progressBar = view.findViewById(R.id.idLoadingPB);
        bookInfoArrayList = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookAdapter(bookInfoArrayList, getContext());
        mRecyclerView.setAdapter(adapter);


        // memberikan Data Default programming
        getBooksInfo("programming");

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
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = RetrofitClient.getClient("https://www.googleapis.com/books/v1/");
        BookApiService apiService = retrofit.create(BookApiService.class);

        Call<BookResponse> call = apiService.getBooks(query);
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookResponse> call, @NonNull Response<BookResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> items = response.body().getItems();
                    for (Item item : items) {
                        VolumeInfo volumeInfo = item.getVolumeInfo();
                        BookInfo bookInfo = new BookInfo(
                                volumeInfo.getTitle(),
                                volumeInfo.getSubtitle(),
                                new ArrayList<>(volumeInfo.getAuthors()),
                                volumeInfo.getPublisher(),
                                volumeInfo.getPublishedDate(),
                                volumeInfo.getDescription(),
                                volumeInfo.getPageCount(),
                                volumeInfo.getImageLinks() != null ? volumeInfo.getImageLinks().getThumbnail() : null,
                                volumeInfo.getPreviewLink(),
                                volumeInfo.getInfoLink()
//                                volumeInfo.getBuyLink()


                        );
                        bookInfoArrayList.add(bookInfo);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show();
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
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, homeFragment)
                    .addToBackStack(null)
                    .commit();
        });

        iv_search.setOnClickListener(v -> {
            SearchFragment searchFragment = new SearchFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, searchFragment)
                    .addToBackStack(null)
                    .commit();
        });

        iv_profile.setOnClickListener(v -> {
            UserFragment userFragment = new UserFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_container, userFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

}
