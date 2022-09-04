package com.example.esteveshopfullytest.model.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAdapter {

    val apiClient: RetrofitService = Retrofit.Builder()
        .baseUrl("https://run.mocky.io/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitService::class.java)
}