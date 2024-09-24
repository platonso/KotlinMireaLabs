package com.platon.kotlinmirealabs.DI

import com.platon.kotlinmirealabs.api.NewsApi
import com.platon.kotlinmirealabs.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {

    single {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // URL базы данных
            .addConverterFactory(GsonConverterFactory.create())
            .client(get()) // Инъекция OkHttpClient
            .build()
    }

    single {
        get<Retrofit>().create(NewsApi::class.java)
    }
}
