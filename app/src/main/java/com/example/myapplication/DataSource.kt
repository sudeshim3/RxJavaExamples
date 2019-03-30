package com.example.myapplication

import com.example.myapplication.models.User

object DataSource {

    fun createUserList(): ArrayList<User> {
        var users: ArrayList<User> = arrayListOf()
        users.add(User("Ralph", "Technician"))
        users.add(User("Janiyah Kelly", "Zoologist"))
        users.add(User("Sherlyn Lozano", "Plumber"))
        users.add(User("Erika Simmons", "Anthropologist"))
        users.add(User("Laura", "Dentist"))
        users.add(User("Isabella", "Compliance Officer"))
        users.add(User("Rogelio", "Pharmacist"))
        users.add(User("Harold", "Actor"))
        return users
    }
}