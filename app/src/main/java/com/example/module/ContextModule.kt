package com.example.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
public class ContextModule(val context: Context) {

@Provides
fun context(): Context = context
}