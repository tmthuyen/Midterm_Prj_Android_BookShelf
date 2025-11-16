package com.example.myapplication.data.remote;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/";
    private static final String API_KEY = "AIzaSyAxZ4RAJqMZwECJPPB5tH1miel9-ER9Vvs";
    private static Retrofit retrofit;
    private ApiClient(){}
    public static ApiServer getApiService(){
        if(retrofit == null){
            OkHttpClient client = new OkHttpClient.Builder().
                    addInterceptor(chain -> {
                        HttpUrl newUrl = chain.request().url()
                                .newBuilder()
                                .addQueryParameter("key", API_KEY)
                                .build();
                        Request newRequest = chain.request()
                                .newBuilder()
                                .url(newUrl)
                                .build();

                        return chain.proceed(newRequest);
                    }).build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiServer.class);
    }


}
