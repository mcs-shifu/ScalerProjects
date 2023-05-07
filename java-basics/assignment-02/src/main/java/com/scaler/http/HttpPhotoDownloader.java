package com.scaler.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;

public class HttpPhotoDownloader {
    private OkHttpClient okHttpClient;

    public HttpPhotoDownloader() {
        okHttpClient = new OkHttpClient();
    }

    public String getPhoto (String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            return okHttpClient.newCall(request).execute().body().string();
        } catch (Exception e) {
            // Should probably be writing error messages to System.err not System.out
            System.out.println("Exception: " + e.getMessage() + " while getting photo from url: " + url);
            return null;
        }
    }

}
