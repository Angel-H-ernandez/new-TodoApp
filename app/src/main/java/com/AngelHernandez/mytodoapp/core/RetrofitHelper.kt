package com.AngelHernandez.mytodoapp.core

import com.AngelHernandez.mytodoapp.data.network.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        // Configura el interceptor de logging
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Registra el cuerpo de la solicitud y respuesta
        }

        val httpClient = OkHttpClient.Builder().apply {
            // Agrega el interceptor de logging
            addInterceptor(loggingInterceptor)

            // Agrega el interceptor personalizado para los headers
            addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", UserService.AUTHORIZATION)
                    .addHeader("apikey", UserService.API_KEY)
                    .addHeader("Range", "0") // Reemplaza "0" con el valor necesario
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Prefer", "return=representation")
                    .build()
                chain.proceed(request)
            }
        }.build()

        return Retrofit.Builder()
            .baseUrl(UserService.BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}






