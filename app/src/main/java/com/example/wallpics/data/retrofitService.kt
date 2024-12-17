package com.example.wallpics.data

import com.example.wallpics.constans.constantes
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitService{
    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(constantes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WebService::class.java)
    }
}