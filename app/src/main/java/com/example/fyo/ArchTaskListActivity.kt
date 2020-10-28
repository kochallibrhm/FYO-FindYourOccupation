package com.example.fyo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyo.ui.TasksListClass
import kotlinx.android.synthetic.main.activity_arch_task_list.*

class ArchTaskListActivity : AppCompatActivity() {

    // Initializing all task array list
    private var allTasks = ArrayList<TasksListClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arch_task_list)

        var archGlobal = Globals

        // Initializing Array adapter and Layout Manager
        var myAdapter = ArchRcAdapter(allTasks, this)
        archRc.adapter = myAdapter
        var archLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        archRc.layoutManager = archLayoutManager
        // Preparing Data
        prepareArchData(archGlobal)
        updateUi(myAdapter)

    }

    private fun prepareArchData(archGlobal: Globals): ArrayList<TasksListClass> {
        // Preparing data to array adapter
        for (i in archGlobal.arcTasks){
            var elementToBeAdded = TasksListClass(i, R.drawable.arch, R.id.toggleButtonFavorite, R.id.toggleButtonCompleted)
            allTasks.add(elementToBeAdded)
        }

        return allTasks
    }

    private fun updateUi(myAdapter: ArchRcAdapter){
        myAdapter.notifyDataSetChanged()
        archRc.invalidate()
    }
}
