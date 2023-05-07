package com.scaler.json;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JSONPlaceHolderClient {
    private JSONPlaceHolderAPI api;

    public JSONPlaceHolderClient (String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        api = retrofit.create(JSONPlaceHolderAPI.class);
    }

    public JSONPlaceHolderAPI getApi() {
        return api;
    }
}
