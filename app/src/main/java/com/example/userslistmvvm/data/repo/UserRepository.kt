package com.example.userslistmvvm.data.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.userslistmvvm.data.dbhelper.User
import com.example.userslistmvvm.data.dbhelper.UserDataBase

class UserRepository(private val userDatabase: UserDataBase,
                     private val applicationContext:Context,
                      ) {

   private val readAllData = MutableLiveData<List<User>>()
    val  favUserList= MutableLiveData<ArrayList<User>>()

    val users : LiveData<List<User>>
        get() = readAllData

    val favUsers:LiveData<ArrayList<User>>
        get()= favUserList

    val list = ArrayList<User>()


//    fun getFavUser(){
//        var pos=0
//        val  favUser = userDatabase.userDao().readData()
//        Log.d("Repo ","UserList $favUser")
//        favUser.forEach {
//            if (it.fav == 1){
//                list.add(it)
//                favUserList.value?.add(it)
//            }
//           Log.d("TAG in foreach",list.toString())
//        }
//        Log.d("Repo ","favUserList ${favUserList.value}")
//    }



    suspend fun addUser(user: User){
        userDatabase.userDao().addUser(user)
    }

    fun getUser() {
            val users = userDatabase.userDao().readData()
        Log.d("tagtagtag", users.toString())
            readAllData.postValue(users as List<User>?)
        }

    suspend fun deleteUser(user: User){
        userDatabase.userDao().deleteUser(user)
    }

    suspend fun updateFavUser(email: String,choice:Int){
            userDatabase.userDao().updateFavUser(email,choice)
    }


}