package com.example.RetrofitExample

import com.example.myapplication.models.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {

    @GET("users/{id}")
    fun user(@Path("id") userId: String): Deferred<Response<List<User>>>

    @POST("users/{id}")
    fun update(@Path("id") userId: String, @Body update: String):Deferred<Response<Unit>>
}