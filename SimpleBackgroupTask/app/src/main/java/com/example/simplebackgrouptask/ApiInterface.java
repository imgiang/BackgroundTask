package com.example.simplebackgrouptask;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int id);

    String token = "113de560946cf253f1a1fd89d55f700fb3239247a3ab04982ba447f502863238";
    @POST("users?access-token=" + token)
    Call<User> addUser(@Body User user);

    @PUT("users/{id}?access-token=" + token)
    Call<User> editUser(@Path("id") int id,@Body User user);

    @DELETE("users/{id}?access-token=" + token)
    Call deleteUser(@Path("id") int id);
}
