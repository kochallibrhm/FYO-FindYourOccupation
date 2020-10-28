package com.example.fyo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyo.ui.TasksListClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_computer_science_task_list.*
import kotlin.collections.ArrayList

class ComputerScienceTaskListActivity : AppCompatActivity() {
    // Initializing the array list
    private var allTasks = ArrayList<TasksListClass>()
    // Creating a task list array
    /*var taskList = ArrayList<String>()

    var auth = FirebaseAuth.getInstance().currentUser
    // Initializing the database
    var db = FirebaseDatabase.getInstance().reference */


    // Querying the database fpr get the tasks
    //var query = db.child("occupations").child("1").child("tasks")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_computer_science_task_list)

        var csGlobal = Globals


        // Initializing Adapter and Layout Manager
        var myAdapter = ComputerScienceRcAdapter(allTasks, this)
        computerScienceRc.adapter = myAdapter
        var csLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        computerScienceRc.layoutManager = csLayoutManager

        prepareCsData(csGlobal)
        updateUi(myAdapter)


        Log.e("Tasks10", csGlobal.csTasks.toString())




    }
    private fun prepareCsData(csGlobal: Globals): ArrayList<TasksListClass>{
        // Preparing data to ArrayAdapter
        for (i in csGlobal.csTasks){
            var elementToBeAdded = TasksListClass(i, R.drawable.computer, R.id.toggleButtonFavorite, R.id.toggleButtonCompleted)
            allTasks.add(elementToBeAdded)
        }
        //Log.e("Tasks6", taskList[2])

        return allTasks
    }
    private  fun updateUi(myAdapter: ComputerScienceRcAdapter){
        myAdapter.notifyDataSetChanged()
        computerScienceRc.invalidate()
    }
}
