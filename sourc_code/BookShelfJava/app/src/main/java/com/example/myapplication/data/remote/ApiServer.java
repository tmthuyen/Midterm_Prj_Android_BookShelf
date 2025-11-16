package com.example.myapplication.data.remote;

import com.example.myapplication.model.VolumeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServer {
    @GET("volumes")
    Call<VolumeResponse> searchBooks(
            @Query("q") String query,
            @Query("filter") String filter,
            @Query("download") String download,
            @Query("maxResults") Integer maxResults
            );
}
