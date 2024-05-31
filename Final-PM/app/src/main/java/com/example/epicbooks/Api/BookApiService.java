package com.example.epicbooks.Api;

import com.example.epicbooks.Model.BookResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookApiService {
    @GET("volumes") // untuk mencari dan mengambil daftar buku
    Call<BookResponse> getBooks(@Query("q") String query); // untuk melakukan pencarian
}
