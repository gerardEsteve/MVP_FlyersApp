package com.example.esteveshopfullytest.model.network

import com.example.esteveshopfullytest.model.Flyer
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {

    @GET("/v3/94da1ce3-3d3f-414c-8857-da813df3bb05") // TODO cambiar
    suspend fun getAllFlyers(): Response<ResponseFlyerModel>

}