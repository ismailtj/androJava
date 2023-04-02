package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8025/";
    private static Gson gson = new GsonBuilder().setLenient().create();
    private static OkHttpClient httpClient = new OkHttpClient.Builder().build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}