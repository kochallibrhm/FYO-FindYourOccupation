package com.example.fyo.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fyo.MainActivity
import com.example.fyo.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.nav_header_main.*

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private var user = FirebaseAuth.getInstance().currentUser
    private var db = FirebaseDatabase.getInstance()
    private var myRef = db.reference
    private lateinit var mActivity : FragmentActivity





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_settings, container, false)  // inflating view
        mActivity = requireActivity()


        // Initiation for change username part
        var newUsernameEt = view.findViewById<EditText>(R.id.sttngsNewUserNameEditText)
        var usernameChangeButton = view.findViewById<Button>(R.id.sttngsChangeUsernameButton)

        // Initiation for change email part
        var newEmailEt = view.findViewById<EditText>(R.id.sttngsNewEmailEditText)
        var emailChangePassword = view.findViewById<EditText>(R.id.sttngsEmailPasswordEt)
        var emailChangeButton = view.findViewById<Button>(R.id.sttngsChangemailButton)

        // Initiation for change password part
        var passwordChangeButton = view.findViewById<Button>(R.id.sttngsChangePasswordButton)
        var passwordEt = view.findViewById<EditText>(R.id.sttngsChangePasswordEt)
        var newPasswordEt = view.findViewById<EditText>(R.id.sttngsNewPasswordEt)
        var repeatNewPassword = view.findViewById<EditText>(R.id.sttngsRepeatNewPasswordEt)


        // Changing email
        emailChangeButton.setOnClickListener {

            // Checking if fields are empty
            if(newEmailEt.text.toString().isNotEmpty() && emailChangePassword.text.toString().isNotEmpty()){

                //Re-authentication with credential
                var credential = EmailAuthProvider.getCredential(user!!.email.toString(), emailChangePassword.text.toString())
                user!!.reauthenticate(credential).addOnCompleteListener { task ->

                    if(task.isSuccessful){

                        // İf password is true than Update email
                        user?.updateEmail(newEmailEt.text.toString())?.addOnCompleteListener { task ->

                            if(task.isSuccessful){

                                // Sending verification mail to new mail address
                                user!!.sendEmailVerification().addOnSuccessListener {
                                    Toast.makeText(mActivity, "Update completed SUCCESSFULLY!\nCheck your mailbox for verify new email!", Toast.LENGTH_LONG).show()

                                    // Writing new data to FirebaseDatabase
                                    myRef.child("users")
                                        .child(user?.uid.toString())
                                        .child("email").setValue(newEmailEt.text.toString())

                                }.addOnFailureListener {
                                    Toast.makeText(mActivity, "Oopss! There was a problem sending verification mail!", Toast.LENGTH_LONG).show()
                                }
                            }
                            else{
                                Toast.makeText(mActivity, "FAIL!! New email has badly format!", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    else{
                        Toast.makeText(mActivity, "FAIL!! The password is invalid!", Toast.LENGTH_LONG).show()
                    }
                }

            }
            else{
                Toast.makeText(mActivity, "Please fill empty fields!", Toast.LENGTH_LONG).show()
            }
        }

        // Changing Username
        usernameChangeButton.setOnClickListener {

            if(newUsernameEt.text.toString().isNotEmpty()) {
                var newUsername = newUsernameEt.text.toString()

                // Updating username in FirebaseDatabase
                myRef.child("users")
                    .child(user?.uid.toString())
                    .child("username").setValue(newUsername).addOnSuccessListener {
                        Toast.makeText(mActivity, "Username update is SUCCESS!", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener {
                        Toast.makeText(mActivity, "FAIL! Username update has been failed!", Toast.LENGTH_LONG).show()
                    }

            }
            else{
                Toast.makeText(mActivity, "Please fill empty fields!", Toast.LENGTH_LONG).show()
            }
        }

        // Changing Password
        passwordChangeButton.setOnClickListener {

            // Checking for empty fields
            if(passwordEt.text.toString().isNotEmpty() && newPasswordEt.text.toString().isNotEmpty() && repeatNewPassword.text.toString().isNotEmpty()){

                // Checking if new passwords are match
                if (newPasswordEt.text.toString() == repeatNewPassword.text.toString()){

                    // Re-authentication with credential
                    var credentialChangePassword = EmailAuthProvider.getCredential(user!!.email.toString(), passwordEt.text.toString())
                    user!!.reauthenticate(credentialChangePassword).addOnCompleteListener { task ->
                        if (task.isSuccessful){

                            user?.updatePassword(newPasswordEt.text.toString())?.addOnCompleteListener { task ->
                                if (task.isSuccessful){
                                    Toast.makeText(mActivity, "Password update SUCCESS!", Toast.LENGTH_LONG).show()
                                }
                                else{
                                    Toast.makeText(mActivity, "FAIL! New password has badly format!", Toast.LENGTH_LONG).show()
                                }
                            }
                        }

                        else{
                            Toast.makeText(mActivity, "FAIL! The password is invalid!", Toast.LENGTH_LONG).show()
                        }
                    }

                }

                else{
                    Toast.makeText(mActivity, "Passwords does not match!", Toast.LENGTH_LONG).show()
                }
            }

            else{
                Toast.makeText(mActivity, "Please fill empty fields!", Toast.LENGTH_LONG).show()
            }
        }

        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)



        //val textView: TextView = root.findViewById(R.id.text_tools)
        settingsViewModel.text.observe(this, Observer {
            //textView.text = it

        })
        return view
    }

}