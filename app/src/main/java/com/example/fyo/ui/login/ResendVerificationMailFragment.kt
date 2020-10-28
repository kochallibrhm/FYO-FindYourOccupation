package com.example.fyo.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.example.fyo.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth


class ResendVerificationMailFragment : DialogFragment() {

    lateinit var emailEtext: EditText
    lateinit var passwordEText: EditText
    lateinit var mContext: FragmentActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_resend_verification_mail, container, false)

        emailEtext = view.findViewById(R.id.resendVerifMailText)
        passwordEText = view.findViewById(R.id.resendVerifPasswordText)
        mContext = activity!!

        // finding cancel button with id and setting on click listener
        var btnCancel = view.findViewById<Button>(R.id.resendVerifCancelButton)
        btnCancel.setOnClickListener {
            dialog?.dismiss()
        }
        // finding ok button with id and setting on click listener
        var btnOk = view.findViewById<Button>(R.id.resendVerifOkButton)
        btnOk.setOnClickListener {

            if (emailEtext.text.toString().isNotEmpty() && passwordEText.text.toString().isNotEmpty()){

                signInAndResendVerificationMail(emailEtext.text.toString(), passwordEText.text.toString())
            }
            else{
                Toast.makeText(mContext, "Please fill empty fields!", Toast.LENGTH_LONG).show()
            }

        }



        return view
    }

    private fun signInAndResendVerificationMail(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email,password)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    resendVerificationMail()
                    dialog?.dismiss()
                }
                else{
                    Toast.makeText(mContext, "Oopss! There was a problem sending verification mail11111!", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun resendVerificationMail() {
        var user = FirebaseAuth.getInstance().currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(mContext, "Please check your mailbox for verify email.", Toast.LENGTH_LONG).show()
            } else{
                Toast.makeText(mContext, "Oopss! There was a problem sending verification mail!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
