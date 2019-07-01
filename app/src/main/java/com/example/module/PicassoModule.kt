package com.example.module

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module(includes = [ContextModule::class, NetworkModule::class])
class PicassoModule {

    @Provides
    fun getPicasso(context: Context, okHttp3Downloader: OkHttp3Downloader) =
        Picasso.Builder(context).downloader(okHttp3Downloader).build()

    @Provides
    fun getOkHttpDownloader(okHttpClient: OkHttpClient):OkHttp3Downloader = OkHttp3Downloader(okHttpClient)
}