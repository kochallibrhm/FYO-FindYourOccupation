package com.example.fyo.ui.myProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fyo.R

class MyProfileFragment : Fragment() {

    private lateinit var myProfileViewModel: MyProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myProfileViewModel =
            ViewModelProviders.of(this).get(MyProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_myprofile, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        myProfileViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}