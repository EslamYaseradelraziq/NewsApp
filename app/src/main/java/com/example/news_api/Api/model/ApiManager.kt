package com.example.news_api.Api.model

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {

    companion object {
        private var retrofit: Retrofit? = null

        @Synchronized
        fun getInstance(): Retrofit {
            if (retrofit == null) {
                val loggingInterceptor = HttpLoggingInterceptor(
                    HttpLoggingInterceptor.Logger {
                        Log.e("Api", it)
                    }
                )
                // this interceptor help to find errors on api response
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl("https://newsapi.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)

                    .build()
            }
            return retrofit!!

        }

        fun getApis(): WebServices {
            return getInstance().create(WebServices::class.java)
        }
    }
}