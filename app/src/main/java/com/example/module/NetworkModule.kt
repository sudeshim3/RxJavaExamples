package com.example.module

import android.content.Context
import com.example.RetrofitExample.Api
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File

@Module
class NetworkModule {

    @Provides
    fun loggingInterceptor() = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Timber.i(it) })

    @Provides
    fun getCache(cacheFile: File) = Cache(cacheFile, 10 * 1024 * 1024)

    @Provides
    fun getCacheFile(context:Context): File = File(context.cacheDir,"okhttp_cache")

    @Provides
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache) =  OkHttpClient.Builder()
                                            .addInterceptor(loggingInterceptor)
                                            .cache(cache)
                                            .build()

}