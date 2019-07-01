package com.example.myapplication

import com.example.myapplication.models.User

interface UserManager {
    fun getUser(): User
    fun setName(name:String)
    fun setAge(age:Int)
}