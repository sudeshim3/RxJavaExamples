package com.example.myapplication

import com.example.RetrofitExample.Api
import com.example.module.ContextModule
import com.example.module.GithubServiceModule
import com.example.module.NetworkModule
import com.example.module.PicassoModule
import com.squareup.picasso.Picasso
import dagger.Component

@Component(modules = [GithubServiceModule::class, NetworkModule::class, ContextModule::class, PicassoModule::class])
interface ApplicationComponent {

    fun getPicassos(): Picasso
    fun getApiService(): Api
}