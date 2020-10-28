package com.example.fyo.ui.login

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.fyo.MainActivity

import com.example.fyo.R
import com.example.fyo.RegisterPage
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var myAuthStateListener: FirebaseAuth.AuthStateListener
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        initMyAuthStateListener()

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val createAccount = findViewById<Button>(R.id.createNewAccount)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        // Create account button lambda function
        createAccount.setOnClickListener {
            val intentToRegisterPage = Intent(this, RegisterPage::class.java)
            startActivity(intentToRegisterPage)
        }

        forgot_password_text.setOnClickListener {
            var showDialogForgotPassword = ForgotPasswordFragment()
            showDialogForgotPassword.show(supportFragmentManager, "showDialogForgotPassword")
        }

        verificationMailEText.setOnClickListener {
            var showDialog = ResendVerificationMailFragment()
            showDialog.show(supportFragmentManager, "showDialog")

        }

        val loading = findViewById<ProgressBar>(R.id.loginProgressBar)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)


        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        /*loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            //loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })*/

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            /*setOnEditorActionListener { _, actionId, _ ->
                 when (actionId) {
                     EditorInfo.IME_ACTION_DONE ->
                         loginViewModel.login(
                             username.text.toString(),
                             password.text.toString()
                         )
                 }
                 false
             }*/

            login.setOnClickListener {
                //showing progress bar without a function hand write function unlike the register page
                loading.visibility = View.VISIBLE

                auth.signInWithEmailAndPassword(username.text.toString(), password.text.toString())
                    .addOnCompleteListener { task ->
                        val user = auth.currentUser

                        // if login successful display a welcome message and go to the main activity.
                        if (task.isSuccessful) {
                            if (user != null) {  // check is user null
                                if (user.isEmailVerified) {  // check email verified

                                    Toast.makeText(this@LoginActivity, "Welcome to FYO!", Toast.LENGTH_SHORT).show()

                                    /*val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)  // Go to the main activity --> Not necessary because AuthStateChanged triggered.*/

                                    //finish()  // Complete and destroy login activity once successful
                                }
                                //if email not verified display a message and sign out
                                else {
                                    Toast.makeText(this@LoginActivity, "Please verify your email!", Toast.LENGTH_LONG).show()
                                    auth.signOut()
                                }
                            }
                        }

                        // if login failed display a message to user about exception
                        else {
                            Toast.makeText(this@LoginActivity, task.exception?.message, Toast.LENGTH_LONG).show()
                        }


                        /*else{
                            Toast.makeText(this@LoginActivity, "There is no user with this email and password", Toast.LENGTH_LONG).show()
                        }*/


                        //hiding the progress bar with same way
                        loading.visibility = View.INVISIBLE
                    }


            }

        }


    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

    private fun initMyAuthStateListener(){
        myAuthStateListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {
                var user = p0.currentUser
                if(user != null){
                    if (user.isEmailVerified){
                        loginProgressBar.visibility = View.VISIBLE
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(myAuthStateListener)
    }

    override fun onStop() {
        super.onStop()
        FirebaseAuth.getInstance().removeAuthStateListener(myAuthStateListener)
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
