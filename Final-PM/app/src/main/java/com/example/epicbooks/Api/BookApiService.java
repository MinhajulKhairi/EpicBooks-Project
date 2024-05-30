package com.example.epicbooks.Api;

import com.example.epicbooks.Model.BookResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookApiService {
    @GET("volumes")
    Call<BookResponse> getBooks(@Query("q") String query);
}
