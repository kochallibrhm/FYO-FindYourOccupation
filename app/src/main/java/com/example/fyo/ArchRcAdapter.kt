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

class ArchRcAdapter(allTasks: ArrayList<TasksListClass>, private val mContext: Context): RecyclerView.Adapter<ArchRcAdapter.ArchViewHolder>(){

    var taskListArray = allTasks

    inner class ArchViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var taskListItem = itemView as CardView

        var archOccIcon = taskListItem.OccIconImgListItem
        var archTaskText = taskListItem.taskTvListItem
        var archFavButton = taskListItem.toggleButtonFavorite
        var archCompButton = taskListItem.toggleButtonCompleted
    }

    // ARCH ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var taskListItem = inflater.inflate(R.layout.task_list_items, parent, false)
        return ArchViewHolder(taskListItem)
        Log.e("Arch_VIEW_HOLDER", "CALISTI")
    }

    override fun getItemCount(): Int {
        return taskListArray.size
        Log.e("Arch_COUNT", "CALISTI")
    }

    override fun onBindViewHolder(holder: ArchRcAdapter.ArchViewHolder, position: Int) {
        // Setting resources to the views
        holder.archOccIcon.setImageResource(taskListArray[position].occIcon)
        holder.archTaskText.text = taskListArray[position].task
    }


}