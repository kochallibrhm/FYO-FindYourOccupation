package com.example.fyo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    private val _text = MutableLiveData<String>().apply {
        auth = FirebaseAuth.getInstance()
        value = "This is home Fragment\n" + auth.currentUser?.email.toString()
    }
    val text: LiveData<String> = _text
}