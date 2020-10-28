package com.example.fyo

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.fyo.ui.login.LoginActivity
import com.example.fyo.ui.login.afterTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register_page.*

class RegisterPage : AppCompatActivity() {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth




    // Initialize FireBase Database reference
    private val database = FirebaseDatabase.getInstance()
    var myRef = database.reference

    // Initialize FireBase FireStore reference
    private val db = Firebase.firestore





    private val registerForm = MutableLiveData<RegisterFormState>()



        override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)


        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // Initialize FireBase Database referance




            registerForm.observe(this, Observer {
                val registerState = it ?: return@Observer

                // disable login button unless both username / password is valid
                rgSingup.isEnabled = registerState.isDataValid

                // Setting errors to edittexts
                if (registerState.usernameError != null) {
                    rgchooseNameEText.error = getString(registerState.usernameError)

                }
                if(registerState.rgEmailError != null){
                    rgEmailEtext.error = getString(registerState.rgEmailError)

                }
                if (registerState.passwordError != null) {
                    rgPasswordEtext.error = getString(registerState.passwordError)
                }
                if(registerState.repeatpasswordError != null){
                    rgRepeatPasswordEtext.error = getString(registerState.repeatpasswordError)
                }
            })

            // Checking validations after texts changed
            rgchooseNameEText.afterTextChanged { registerDataChanged(
                rgchooseNameEText.text.toString(),
                rgEmailEtext.text.toString(),
                rgPasswordEtext.text.toString(),
                rgRepeatPasswordEtext.text.toString()
                )
            }

            rgEmailEtext.afterTextChanged { registerDataChanged(
                rgchooseNameEText.text.toString(),
                rgEmailEtext.text.toString(),
                rgPasswordEtext.text.toString(),
                rgRepeatPasswordEtext.text.toString()
                )
            }

            rgPasswordEtext.afterTextChanged { registerDataChanged(
                rgchooseNameEText.text.toString(),
                rgEmailEtext.text.toString(),
                rgPasswordEtext.text.toString(),
                rgRepeatPasswordEtext.text.toString()
                )
            }

            rgRepeatPasswordEtext.afterTextChanged { registerDataChanged(
                rgchooseNameEText.text.toString(),
                rgEmailEtext.text.toString(),
                rgPasswordEtext.text.toString(),
                rgRepeatPasswordEtext.text.toString()
                )
            }

            // when sign up button clicked checking the password match and signing up with newRegistration func.
            rgSingup.setOnClickListener {

            // Checks the fields is empty.
            if(rgPasswordEtext.text.isNotEmpty() && rgPasswordEtext.text.isNotEmpty() && rgRepeatPasswordEtext.text.isNotEmpty() && rgchooseNameEText.text.isNotEmpty()){

                // Controls the passwords are same.
                if(rgPasswordEtext.text.toString().equals(rgRepeatPasswordEtext.text.toString())){

                    //Calling the new registration function with email and password parameters.
                    newRegistration(rgEmailEtext.text.toString(), rgPasswordEtext.text.toString())

                }

                else{
                    Toast.makeText(this, "ERROR: Passwords do not match!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val backToLogin: TextView = findViewById(R.id.rgbackToLogin)
        backToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
    private fun registerDataChanged(username: String, email: String, password: String, repassword: String) {
        if (!isUserNameValid(username)) {
            registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        } else if (!isEmailValid(email)) {
            registerForm.value = RegisterFormState(rgEmailError = R.string.invalid_email)
        } else if (!isPasswordValid(password)) {
            registerForm.value = RegisterFormState(passwordError = R.string.invalid_password)
        } else if (!isPasswordValid(repassword)) {
            registerForm.value = RegisterFormState(repeatpasswordError = R.string.invalid_password)
        } else {
            registerForm.value = RegisterFormState(isDataValid = true)
        }
    }
    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean{
       return username.isNotEmpty()
    }
    // A placeholder e mail validation check
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    // A function that make registration with mail and password
    private fun newRegistration(email: String, password: String) {
        // show progress bar until registration done
        progressBarShow()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // if Sign up success display a message to the user.


                    //val user = auth.currentUser
                    //updateUI(user)
                    verifyEmail()

                    writeNewUser()  // Calling the func that writes a new user.


                    auth.signOut()  // Signing out the user after the registration.
                } else {
                    // If sign up fails, display a message to the user.


                    Toast.makeText(baseContext, "Oopss! Registration Failed! \n"+ task.exception?.message , Toast.LENGTH_LONG).show()
                    //updateUI(null)
                }

                // hide progress bar after the registration transaction.
                progressBarHide()


            }
    }
    private fun writeNewUser(){
        // Creating a new user(User class instance) with register form data
        val newUser = User()
        newUser.username = rgchooseNameEText.text.toString()
        newUser.email = rgEmailEtext.text.toString()
        newUser.profilePicture = ""

        myRef.child("users")
            .child(auth.currentUser?.uid.toString())
            .setValue(newUser).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this, "Registration is Successful!", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(baseContext, "Oopss! Registration Failed! \n"+ task.exception?.message  , Toast.LENGTH_LONG).show()
                }
            }

        /*db.collection("users")
            .document(auth.currentUser?.uid.toString())
            .set(newUser).addOnFailureListener { e ->
                Toast.makeText(baseContext, "Oopss!! \n"+ e.message , Toast.LENGTH_LONG).show()
            }*/
    }
    // Making progress bar visible
    private fun progressBarShow(){
        rgprogressBar.visibility = View.VISIBLE
    }
    // Making progress bar invisible
    private fun progressBarHide(){
        rgprogressBar.visibility = View.INVISIBLE
    }

    private fun verifyEmail(){
        val user = auth.currentUser
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Please check your mailbox for verify email.", Toast.LENGTH_LONG).show()
            } else{
                Toast.makeText(this, "Oopss! There was a problem sending verification mail!", Toast.LENGTH_LONG).show()
            }
        }
    }

}
