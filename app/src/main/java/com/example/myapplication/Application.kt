package com.example.myapplication

import android.app.Application
import android.content.Context
import com.example.RetrofitExample.Api
import com.example.module.ContextModule
import com.example.module.GithubServiceModule
import com.example.module.NetworkModule
import com.example.module.PicassoModule
import com.google.gson.GsonBuilder
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File

class SampleApp: Application() {

    override fun onCreate() {
        super.onCreate()

        val component = DaggerApplicationComponent.builder()
            .contextModule(ContextModule(this))
            /*.githubServiceModule(GithubServiceModule())
            .networkModule(NetworkModule())
            .picassoModule(PicassoModule())*/
            .build()

        /*Timber.plant(Timber.DebugTree())
        //context
        val context: Context = this

        //NETWORK


        val cacheFile = File(context.cacheDir,"okhttp_cache")
        cacheFile.mkdirs()

        val cache = Cache(cacheFile, 10 * 1024 * 1024) //10mb cache*/

        /*val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .build()*/

        /*val picasso = Picasso.Builder(context)
            .downloader(OkHttp3Downloader(okHttpClient))
            .build()*/

        // CLIENT.
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(DateTime::class.java, DateTimeConverter())
        val gson = gsonBuilder.create()

        /*val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl("https://url")
            .build()*/

//        val apiInstance = retrofit.create(Api::class.java)







    }
}