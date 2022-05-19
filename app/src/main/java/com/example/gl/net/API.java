package com.example.gl.net;

import com.example.gl.data.PointCloud;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    @GET("getCloude")
    Call<PointCloud> getPointCloude();
}
