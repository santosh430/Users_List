package com.example.userslistmvvm.presentation.ui.adduser

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.userslistmvvm.R
import com.example.userslistmvvm.data.repo.*
import com.example.userslistmvvm.data.dbhelper.User
import com.example.userslistmvvm.UserApplication
import com.example.userslistmvvm.data.dbhelper.UserDataBase
import com.example.userslistmvvm.presentation.viewmodel.MainViewModelFactory
import com.example.userslistmvvm.presentation.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add_user.*

class AddUserFragment : Fragment(),View.OnClickListener {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var repository:UserRepository
    private lateinit var dataBase: UserDataBase

    private var list= mutableListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate ","AddUserFragment Calling ")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("onCreateView ","AddUserFragment Calling ")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("onViewCreated ","AddUserFragment Calling")
        dataBase = (requireContext().applicationContext as UserApplication).database
        repository  = (requireContext().applicationContext as UserApplication).userRepository
        mUserViewModel = ViewModelProvider(this, MainViewModelFactory(repository,dataBase )).get(UserViewModel::class.java)
        mUserViewModel.user.observe(viewLifecycleOwner){
            list.addAll(it)
        }

        btn_addUser.setOnClickListener(this)
        iv_toFavUser.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_addUser->{
                insertDataToDatabase()
            }
            R.id.iv_toFavUser->{
                Navigation.findNavController(requireView()).navigate(R.id.action_AddUserFragment_to_FavUserFragment)
            }
        }
    }

    private fun insertDataToDatabase() {

        val firstName = et_name.text.toString()
        val email = et_email.text.toString()
        val phoneNumber = et_phoneNo.text.toString()

        if (emailCheck(email)) {     //for unique email verification

            if (inputCheck(firstName, email, phoneNumber)) {

                // if user is marked fav
                val favourite: Int = isCheckBoxClicked()
                // Create User Object
                val user =
                    User(name = firstName, email = email, phoneNo = phoneNumber, fav = favourite)
                // Add Data to Database

                mUserViewModel.addUser(user)

                Toast.makeText(requireContext(), "User Successfully added!", Toast.LENGTH_LONG)
                    .show()

                // Navigate to Favourite Users Fragment
                Navigation.findNavController(requireView()).navigate(R.id.action_AddUserFragment_to_FavUserFragment)

            } else {
                Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG)
                    .show()
            }
        }
        else
            Toast.makeText(requireContext(), "User is already added with the same email", Toast.LENGTH_SHORT).show()
    }

    private fun emailCheck(email: String): Boolean {
        for(i in list){
            if(i.email==email){        //email is not unique
                return false
            }
        }
        return true
    }

    private fun isCheckBoxClicked(): Int {
        val checked:Boolean=checkbox.isChecked
        return if(checked){
            1
        } else
            0
    }

    private fun inputCheck(firstName: String, email: String, phone: String): Boolean{

        Log.d("inputCheck","${TextUtils.isEmpty(firstName)} ${TextUtils.isEmpty(email)} ${TextUtils.isEmpty(phone)}")
        return !(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)|| phone.length!=10)  // for checking null and empty values
    }
}
