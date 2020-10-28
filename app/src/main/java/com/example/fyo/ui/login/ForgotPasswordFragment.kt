package com.example.fyo.ui.login

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

import com.example.fyo.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_forgot_password.*


class ForgotPasswordFragment : DialogFragment() {

    lateinit var emailEditText: EditText
    lateinit var mContext: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_forgot_password, container, false)

        emailEditText = view.findViewById(R.id.fgEmailEText)
        mContext = activity!!

        var fgContinueButton = view.findViewById<Button>(R.id.fgContinueButton)

        fgContinueButton.setOnClickListener {
            if (emailEditText.text.isEmpty()){
                Toast.makeText(mContext, "Please fill empty fields!", Toast.LENGTH_LONG).show()
            }
            else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(emailEditText.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                mContext,
                                "Password recovery mail has been sent.\nPlease check your mail box.",
                                Toast.LENGTH_LONG
                            ).show()
                            dialog?.dismiss()
                        } else {
                            Toast.makeText(
                                mContext,
                                "ERROR!\n" + task.exception?.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
        return view
    }


}
