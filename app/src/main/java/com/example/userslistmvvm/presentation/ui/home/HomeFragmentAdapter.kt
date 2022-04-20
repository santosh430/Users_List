package com.example.userslistmvvm.presentation.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.userslistmvvm.R
import com.example.userslistmvvm.data.dbhelper.User
import kotlinx.android.synthetic.main.adapter_home.view.*
import kotlinx.android.synthetic.main.fav_user_adapter.view.tv_emailContent
import kotlinx.android.synthetic.main.fav_user_adapter.view.tv_nameContent
import kotlinx.android.synthetic.main.fav_user_adapter.view.tv_phoneContent

class HomeFragmentAdapter(var homeFragment: HomeFragment) :RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder>() {

    var list= mutableListOf<User>()
    var value:Int=0

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return (ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_home, parent, false)
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_nameContent.text = list[position].name
        holder.itemView.tv_emailContent.text =list[position].email
        holder.itemView.tv_phoneContent.text=list[position].phoneNo

        holder.itemView.iv_delete.setOnClickListener {
            notifyDataSetChanged()
            Log.d("delete","Home Fragmentadapter ${list[position]}")
            homeFragment.delete(list,position)     //calling interface method
            notifyDataSetChanged()
        }

        holder.itemView.iv_hollowStarHome.setOnClickListener {
            addFav(holder,position)
            Log.d("Star"," hollow Click Listener Working ${list[position]}")
            Toast.makeText(homeFragment.context, "User added as Favourite", Toast.LENGTH_SHORT).show()
            notifyDataSetChanged()
        }

        holder.itemView.iv_fillStarHome.setOnClickListener {
            removeFav(holder,position)
            Log.d("Star"," fill Star Click Listener Working")

        }

    }

    private fun addFav(holder: ViewHolder, position: Int) {
        holder.itemView.iv_hollowStarHome.isInvisible=true
        holder.itemView.iv_fillStarHome.isVisible=true
        value=1
        homeFragment.update(list,list[position].email,value)
        notifyItemChanged(position)
        notifyDataSetChanged()
    }

    private fun removeFav(holder: ViewHolder, position: Int) {
        holder.itemView.iv_hollowStarHome.isVisible=true
        holder.itemView.iv_fillStarHome.isInvisible=true
        value=0
        homeFragment.update(list, list[position].email, value)
        notifyItemChanged(position)
        notifyDataSetChanged()
    }

    fun updateUserList(list1:MutableList<User>){
        list.clear()
        list=list1
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
       return list.size
    }

}