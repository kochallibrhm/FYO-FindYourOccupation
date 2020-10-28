package com.example.fyo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyo.ui.TasksListClass
import kotlinx.android.synthetic.main.activity_bio_task_list.*

class BioTaskListActivity : AppCompatActivity() {

    // Initializing all task array list
    private var allTasks = ArrayList<TasksListClass>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bio_task_list)

        //Initializing bioGlobal
        var bioGlobal = Globals

        // Initializing adapter and layout manager
        var myAdapter = BioRcAdapter(allTasks, this)
        bioRc.adapter = myAdapter
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        bioRc.layoutManager = layoutManager

        // calling functions that prepares data and adapter
        prepareBioData(bioGlobal)
        updateUi(myAdapter)

    }

    private fun prepareBioData(bioGlobal: Globals): ArrayList<TasksListClass> {
        for (i in bioGlobal.bioTasks){
            var elementToBeAdded = TasksListClass(i, R.drawable.virus, R.id.toggleButtonFavorite, R.id.toggleButtonCompleted)
            allTasks.add(elementToBeAdded)
        }
        return allTasks
    }
    private fun updateUi(myAdapter: BioRcAdapter) {
        myAdapter.notifyDataSetChanged()
        bioRc.invalidate()
    }

}
