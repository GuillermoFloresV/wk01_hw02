package com.example.wk01_hw02;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("posts")
    Call<List<Post>> getPosts();
    @GET("users")
    Call<List<User>> getUsers();
}
