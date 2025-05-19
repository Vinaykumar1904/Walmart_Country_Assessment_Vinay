package com.vinay.walmartassessment.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    val retrofit by lazy{

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
        }.build()
        Retrofit.Builder().apply{
            baseUrl("https://gist.githubusercontent.com/peymano-wmt/")
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()
    }
}