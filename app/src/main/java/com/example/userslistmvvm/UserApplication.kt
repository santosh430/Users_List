package com.example.userslistmvvm

import android.app.Application
import com.example.userslistmvvm.data.dbhelper.UserDataBase
import com.example.userslistmvvm.data.repo.UserRepository

class UserApplication: Application() {

    lateinit var userRepository: UserRepository
    lateinit var database: UserDataBase
    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize(){
        database = UserDataBase.getDataBase(applicationContext)
        userRepository = UserRepository(database, applicationContext)

//        userRepository = UserRepository(userService, database, applicationContext)
    }
}