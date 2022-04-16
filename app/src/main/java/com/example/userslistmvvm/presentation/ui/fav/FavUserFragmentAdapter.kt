package com.example.userslistmvvm.presentation.ui.fav

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.userslistmvvm.R
import com.example.userslistmvvm.data.dbhelper.User
import kotlinx.android.synthetic.main.fav_user_adapter.view.*
import java.util.ArrayList

class FavUserFragmentAdapter() : RecyclerView.Adapter<FavUserFragmentAdapter.ViewHolder>() {

    var userArrayList = ArrayList<User>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return (ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.fav_user_adapter, parent, false)
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.tv_nameContent.text = userArrayList[position].name
        holder.itemView.tv_emailContent.text =userArrayList[position].email
        holder.itemView.tv_phoneContent.text=userArrayList[position].phoneNo
    }

    override fun getItemCount(): Int {
        Log.d("TAG FavUserAdapter","List size ${userArrayList.size}")
        return userArrayList.size
    }

    fun updateUserList(userArrayList1: ArrayList<User>) {
        userArrayList.clear()
        userArrayList = userArrayList1
        notifyDataSetChanged()
    }
}