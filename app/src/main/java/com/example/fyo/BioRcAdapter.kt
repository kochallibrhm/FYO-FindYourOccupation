package com.example.fyo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyo.ui.TasksListClass
import kotlinx.android.synthetic.main.task_list_items.view.*
import java.util.zip.Inflater

class BioRcAdapter(allTasks: ArrayList<TasksListClass>, private val mContext : Context): RecyclerView.Adapter<BioRcAdapter.BioViewHolder>(){
    var bioTaskListArray = allTasks

    inner class BioViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var taskListItem = itemView as CardView

        var bioOccIcon = taskListItem.OccIconImgListItem
        var bioTask = taskListItem.taskTvListItem
        var bioFavButton = taskListItem.toggleButtonFavorite
        var bioCompButton = taskListItem.toggleButtonCompleted
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BioViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var taskListItem = inflater.inflate(R.layout.task_list_items, parent, false)
        return BioViewHolder(taskListItem)
    }

    override fun getItemCount(): Int {
        return bioTaskListArray.size
    }

    override fun onBindViewHolder(holder: BioRcAdapter.BioViewHolder, position: Int) {
        // Setting resources to the views
        holder.bioOccIcon.setImageResource(bioTaskListArray[position].occIcon)
        holder.bioTask.text = bioTaskListArray[position].task


    }

}