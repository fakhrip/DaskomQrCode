package com.faitechno.www.daskomqrcode;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IUsersApi {
 
    @GET("/api/change_status/{nim}")
    Call<Praktikan> changeStatus(@Path("nim") String nim);

    @GET("/api/all_praktikan/{nim}")
    Call<Praktikan> getPraktikanWithNim(@Path("nim") String nim);

    @GET("/api/all_praktikan")
    Call<List<Praktikan>> getAllPraktikan();
}