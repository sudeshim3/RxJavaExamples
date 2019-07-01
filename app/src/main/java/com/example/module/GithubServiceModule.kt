package com.example.module

import com.example.RetrofitExample.Api
import com.example.myapplication.DateTimeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class GithubServiceModule {

    @Provides
    fun githubService(retrofit: Retrofit): Api {
       return retrofit.create(Api::class.java)
    }
    @Provides
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(DateTime::class.java, DateTimeConverter())
        return  gsonBuilder.create()
    }


    @Provides
    fun retrofit(okhttpClient: OkHttpClient, gson: Gson):Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okhttpClient)
            .baseUrl("http://www.google.com")
            .build()
    }
}