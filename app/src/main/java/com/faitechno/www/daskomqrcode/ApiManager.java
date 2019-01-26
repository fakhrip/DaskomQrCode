package com.faitechno.www.daskomqrcode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ApiManager {

    private static IUsersApi service;
    private static ApiManager apiManager;

    private ApiManager(String ipaddr) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+ipaddr+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(IUsersApi.class);
    }

    static ApiManager getInstance(String ipaddr) {
        if (apiManager == null) {
            apiManager = new ApiManager(ipaddr);
        }
        return apiManager;
    }

    void changePraktikanStatus(String nim, Callback<Praktikan> callback) {
        Call<Praktikan> praktikanCall = service.changeStatus(nim);
        praktikanCall.enqueue(callback);
    }

    void getPraktikanData(String nim, Callback<Praktikan> callback){
        Call<Praktikan> praktikanCall = service.getPraktikanWithNim(nim);
        praktikanCall.enqueue(callback);
    }

    void getAllPraktikanData(Callback<List<Praktikan>> praktikanList){
        Call<List<Praktikan>> call = service.getAllPraktikan();
        call.enqueue(praktikanList);
    }
}