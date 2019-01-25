package com.faitechno.www.daskomqrcode;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IUsersApi {
 
    @GET("/api/change_status/{nim}")
    Call<Praktikan> changeStatus(@Path("nim") String nim);
    
}