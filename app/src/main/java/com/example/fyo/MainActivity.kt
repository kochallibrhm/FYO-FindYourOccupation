package com.example.fyo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.example.fyo.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var myAuthStateListener: FirebaseAuth.AuthStateListener
    // Initializing user
    private var user = FirebaseAuth.getInstance().currentUser
    // Initializing the database
    private var db = FirebaseDatabase.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initializing Globals for tasks array lists
        var csGlobal = Globals
        var arcGlobal = Globals
        var bioGlobal = Globals

        initMyAuthStateListener()

        // Getting username for navigation drawer profile part
        updateUsername()


        // fab button onclick listener. To go to new achievement activity
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val intentToNewAchievement = Intent(this, NewAchievementActivity::class.java)
            startActivity(intentToNewAchievement)
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_my_profile, R.id.nav_mygoals,
                R.id.nav_settings, R.id.nav_how_to_play
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Querying the database fpr get the tasks before show them
        //Taking tasks for Computer Science
        var queryCs = db.child("occupations").child("1").child("tasks")

        queryCs.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("Error", p0.message)
            }


            override fun onDataChange(p0: DataSnapshot) {
                // First clearing the old list for adding actual new data
                csGlobal.csTasks.clear()
                for (i in p0.children){
                    csGlobal.csTasks.add(i.value.toString())
                }
                Log.e("csTask", csGlobal.csTasks.toString())
            }

        })

        // Taking tasks for ARCHITECTURE
        var queryArch = db.child("occupations").child("2").child("tasks")

        queryArch.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("Error", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                // First clearing the old list for adding actual new data
                csGlobal.arcTasks.clear()
                for (i in p0.children){
                    arcGlobal.arcTasks.add(i.value.toString())
                }
                Log.e("arcTask", arcGlobal.arcTasks.toString())
            }

        })

        // Taking tasks for BIOLOGY AND GENETICS
        var queryBio = db.child("occupations").child("3").child("tasks")

        queryBio.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("Error", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                // First clearing the old list for adding actual new data
                csGlobal.bioTasks.clear()
                for (i in p0.children){
                    bioGlobal.bioTasks.add(i.value.toString())
                }
                Log.e("BioTasks", bioGlobal.bioTasks.toString())
            }

        })



    }

    private fun updateUsername() {
        // Setting the username to the nav header
        // Querying the database for get the user data
        var queryUserName = db.child("users").child(user?.uid.toString()).child("username")

        queryUserName.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("Error", p0.message)
            }


            override fun onDataChange(p0: DataSnapshot) {
                // Setting the username to the nav header
                navmain_profileName_text.text = p0.value.toString()
            }

        })
    }

    private fun initMyAuthStateListener() {
        myAuthStateListener = object : FirebaseAuth.AuthStateListener{
            override fun onAuthStateChanged(p0: FirebaseAuth) {

                var user = p0.currentUser

                if (user != null){  // if user not null. Do nothing for now

                }
                // if user is null so user is logged out. Then go to the login activity
                else{
                    var intent = Intent(this@MainActivity, LoginActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.action_log_out -> {
                logOut()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        checkTheUser()
    }

    private fun checkTheUser() {
        var user = FirebaseAuth.getInstance().currentUser
        // if user is null go to the main activity.
        if (user == null){
            var intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }
    }

    // onStart and OnStop methods for Auth State Listener
    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().addAuthStateListener(myAuthStateListener)
    }

    override fun onStop() {
        super.onStop()
        if(myAuthStateListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(myAuthStateListener)
        }
    }

}
