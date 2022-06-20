package com.example.embarques.services;

import com.example.embarques.models.users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface usersApi {

    @POST("auth")
    Call<List<users>> VerificarLogin(@Body users _user);
}
