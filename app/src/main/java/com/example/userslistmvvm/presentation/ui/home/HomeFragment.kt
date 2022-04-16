package com.example.userslistmvvm.presentation.ui.home

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userslistmvvm.R
import com.example.userslistmvvm.data.dbhelper.User
import com.example.userslistmvvm.data.repo.UserRepository
import com.example.userslistmvvm.UserApplication
import com.example.userslistmvvm.data.dbhelper.UserDataBase
import com.example.userslistmvvm.presentation.viewmodel.MainViewModelFactory
import com.example.userslistmvvm.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(),ItemAdapterListener {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var repository: UserRepository
    private lateinit var dataBase: UserDataBase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =inflater.inflate(R.layout.fragment_home, container, false)

        //setting recyclerview
        view.recyclerViewUserList.layoutManager=LinearLayoutManager(context)
        val adapter = HomeFragmentAdapter(this)
        view.recyclerViewUserList.adapter=adapter

        //initializing viewmodel
        dataBase= (requireContext().applicationContext as UserApplication).database
        repository  = (requireContext().applicationContext as UserApplication).userRepository
        mUserViewModel = ViewModelProvider(this, MainViewModelFactory(repository,dataBase)).get(UserViewModel::class.java)


        mUserViewModel.user.observe(viewLifecycleOwner) {

            adapter.updateUserList(it as MutableList<User>)

        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        view.floatingActionbtn_toAddUser.setOnClickListener {
            view.findNavController().navigate(R.id.homeFragment_to_addUserFragment)
        }
    }


    override fun delete(list: MutableList<User>, position: Int?) {
        Log.d("TAG","Position $position")

        mUserViewModel.deleteUser(list[position!!])
        recyclerViewUserList.adapter?.notifyDataSetChanged()
        mUserViewModel.getUser()
//        Log.d("TAG delete","${list[0]}")
        recyclerViewUserList.adapter?.notifyDataSetChanged()
        Toast.makeText(context, "User Deleted", Toast.LENGTH_SHORT).show()

    }

    override fun update(list: MutableList<User>, email: String?, choice: Int?) {
        Log.d("TAG","Email: $email")

           mUserViewModel.updateFavUser(email!!,choice!!)
        Log.d("TAG update","${list[0]}")

        recyclerViewUserList.adapter?.notifyDataSetChanged()

    }
}