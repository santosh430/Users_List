package com.example.userslistmvvm.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userslistmvvm.data.dbhelper.UserDataBase
import com.example.userslistmvvm.data.repo.UserRepository

class MainViewModelFactory(private val repository: UserRepository,private val database:UserDataBase) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repository, application = Application(),database) as T
    }
}