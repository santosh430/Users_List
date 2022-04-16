package com.example.userslistmvvm.presentation.ui.fav

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userslistmvvm.R
import com.example.userslistmvvm.data.dbhelper.User
import com.example.userslistmvvm.data.repo.UserRepository
import com.example.userslistmvvm.UserApplication
import com.example.userslistmvvm.data.dbhelper.UserDataBase
import com.example.userslistmvvm.presentation.viewmodel.MainViewModelFactory
import com.example.userslistmvvm.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_favourite_user.*
import kotlinx.android.synthetic.main.fragment_favourite_user.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class FavouriteUser : Fragment() {


    lateinit var mUserViewModel:UserViewModel
    lateinit var repository:UserRepository
    lateinit var dataBase: UserDataBase
    var list = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_favourite_user, container, false)


        view.recyclerViewFavUserList.layoutManager=LinearLayoutManager(context)
        val adapter=FavUserFragmentAdapter()
        view.recyclerViewFavUserList.adapter=adapter


        dataBase=(requireContext()?.applicationContext as UserApplication).database
        repository=(requireContext()?.applicationContext as UserApplication).userRepository
        mUserViewModel= ViewModelProvider(this, MainViewModelFactory(repository,dataBase)).get(UserViewModel::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            mUserViewModel.getFavUserData()
        }

        mUserViewModel.favUserList.observe(viewLifecycleOwner){
            adapter.updateUserList(it)
        }
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.iv_toHome.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_FavUserFragment_to_HomeFragment)
        }
    }
}

