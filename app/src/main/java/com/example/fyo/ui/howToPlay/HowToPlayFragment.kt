package com.example.fyo.ui.howToPlay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fyo.R

class HowToPlayFragment : Fragment() {

    private lateinit var howToPlayViewModel: HowToPlayViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        howToPlayViewModel =
            ViewModelProviders.of(this).get(HowToPlayViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_how_to_play, container, false)


        return root
    }
}