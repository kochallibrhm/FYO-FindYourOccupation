package com.example.fyo.ui.myGoals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fyo.R

class MyGoalsFragment : Fragment() {

    private lateinit var myGoalsViewModel: MyGoalsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myGoalsViewModel =
            ViewModelProviders.of(this).get(MyGoalsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mygoals, container, false)
        val textView: TextView = root.findViewById(R.id.text_myGoals)
        myGoalsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}