package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class ActivityReified : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reified)

        //Normal Call
        startActivity(applicationContext, MainActivity::class.java)

        //Using reified
        startActivity<MainActivity>(applicationContext)
    }

    // Normal Approach
    fun <T : Activity> Activity.startActivity(context: Context, clazz: Class<T>) {
        startActivity(Intent(context, clazz))
    }

    //Reified Approach
    inline fun <reified T : Activity> Activity.startActivity(context: Context) {
        startActivity(Intent(context, T::class.java))
    }

    /*fun <T> Bundle.getDataOrNull() : T? {

    }*/


}
