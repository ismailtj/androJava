package models;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiService {

    @POST("temps")
    Call<Temp> createTemp(@Body Temp temp);
}
