package com.example.userslistmvvm.presentation.viewmodel

import android.app.Application
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.userslistmvvm.data.dbhelper.User
import com.example.userslistmvvm.data.dbhelper.UserDataBase
import com.example.userslistmvvm.data.repo.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.logging.Handler

class UserViewModel(
    private var repository: UserRepository,
    application: Application,
    private var database: UserDataBase
)
    : AndroidViewModel(
    application
) {


    init {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getUser()
        }
    }

    val list = ArrayList<User>()
    val  favUserList= MutableLiveData<ArrayList<User>>()

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
             repository.getUser()
        }
    }

    fun getFavUserData(){

        val  favUser = database.userDao().readData()
        Log.d("Repo ","UserList $favUser")

        favUser.forEach {
            if (it!!.fav == 1){
                if (it != null) {
                    list.add(it)
                }
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            favUserList.value=list
        }

    }

    val favUserData: LiveData<ArrayList<User>>
        get()=repository.favUsers


    val user : LiveData<List<User>>
        get() = repository.users

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }

    }

    fun updateFavUser(email:String,choice:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateFavUser(email,choice)
        }
    }
}