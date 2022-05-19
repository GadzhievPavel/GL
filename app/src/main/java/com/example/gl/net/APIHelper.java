package com.example.gl.net;

import androidx.annotation.NonNull;

import com.example.gl.data.PointCloud;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHelper {
    private Retrofit retrofit;
    private String address;
    private API api;
    public APIHelper(String address){
        this.address = address;
        retrofit = new Retrofit.Builder().baseUrl(address).addConverterFactory(GsonConverterFactory.create()).build();
        api = retrofit.create(API.class);
    }

    public PointCloud getPointClouds(){
        Call<PointCloud> req = api.getPointCloude();
        final PointCloud[] pc = new PointCloud[1];
        req.enqueue(new Callback<PointCloud>() {
            @Override
            public void onResponse(Call<PointCloud> call, Response<PointCloud> response) {
                pc[0] = response.body();
            }

            @Override
            public void onFailure(@NonNull Call<PointCloud> call, Throwable t) {

            }
        });
        return pc[0];
    }
}
