package com.scaler.json;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface JSONPlaceHolderAPI {
    @GET("/photos")
    Call<List<PhotoEntity>> getPhotos();
}
