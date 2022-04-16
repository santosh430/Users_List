package com.example.userslistmvvm.presentation.ui.home

import com.example.userslistmvvm.data.dbhelper.User

interface ItemAdapterListener {

    fun delete(list: MutableList<User>, position: Int?)   //to delete users
    fun update(list: MutableList<User>, email: String?, choice: Int?)  //to update fav users
}