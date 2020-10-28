package com.example.fyo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyo.ui.TasksListClass
import kotlinx.android.synthetic.main.task_list_items.view.*

class ComputerScienceRcAdapter(allTasks: ArrayList<TasksListClass>, private val mContext: Context): RecyclerView.Adapter<ComputerScienceRcAdapter.ComputerScienceViewHolder>(){

    // Initializing array list
    var tasksListArray = allTasks

    inner class ComputerScienceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        // Initializing the views on list items
        var taskListItem = itemView as CardView

        var csOccIcon = taskListItem.OccIconImgListItem!!
        var csTaskText = taskListItem.taskTvListItem!!
        var csToggleButtonFav = taskListItem.toggleButtonFavorite!!
        var csToggleButtonComp = taskListItem.toggleButtonCompleted!!
    }

    override fun getItemCount(): Int {
        // Returning array size
        return tasksListArray.size
        Log.e("COUNT", "CALISTI")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComputerScienceViewHolder {
        // inflating the list item from task_list_items
        var inflater = LayoutInflater.from(parent.context)
        var taskListItem = inflater.inflate(R.layout.task_list_items, parent, false)
        return ComputerScienceViewHolder(taskListItem)
        Log.e("HOLDER", "CALISTI")
    }



    override fun onBindViewHolder(holder: ComputerScienceViewHolder, position: Int) {
        holder.csOccIcon.setImageResource(tasksListArray[position].occIcon)  // setting occupation icon to the image view
        holder.csTaskText.text = tasksListArray[position].task  // setting task to task text view

        // setting buttons to fav and comp buttons on Card view


        Log.e("BİND", "CALISTI")

    }
}