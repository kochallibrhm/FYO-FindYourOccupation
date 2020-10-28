package com.example.fyo

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.occupation_list_items.view.*

class OccupationsRcAdapter(allOccupations: ArrayList<OccListClass>, private val mContext: Context): RecyclerView.Adapter<OccupationsRcAdapter.OccupationViewHolder>() {

    var occupations = allOccupations
   //private lateinit var mActivity: NewAchievementActivity
    //private lateinit var mContext: Context


    override fun getItemCount(): Int {
        return occupations.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OccupationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        var occListItem = inflater.inflate(R.layout.occupation_list_items, parent, false)
        return OccupationViewHolder(occListItem)
    }

    inner class OccupationViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        // Initializing the views on list items
        var occListItem = itemView as CardView

        var occName = occListItem.occupationNameTv!!
        var occIcon = occListItem.occupationSymbolImg!!
    }

    // Binding data with design views
    override fun onBindViewHolder(holder: OccupationViewHolder, position: Int) {
        holder.occName.text = occupations[position].occName
        holder.occIcon.setImageResource(occupations[position].occIcon)

        // List Item setOnclick Listener for go to task list activity
        holder.itemView.setOnClickListener {

            // Going to Cs Activity
            if (occupations[position].occName == "Computer Science"){

                // Intent for go to the CsActivity
                var intent = Intent(mContext, ComputerScienceTaskListActivity::class.java)
                mContext.startActivity(intent)

                Toast.makeText(mContext, "Computer Science", Toast.LENGTH_SHORT).show()
            }

            // Going to Cs Activity
            if (occupations[position].occName == "Architecture"){

                // Intent for go to the ArchActivity
                var intent = Intent(mContext, ArchTaskListActivity::class.java)
                mContext.startActivity(intent)

                Toast.makeText(mContext, "Architecture", Toast.LENGTH_SHORT).show()
            }

            // Going to Cs Activity
            if (occupations[position].occName == "Biology & Genetics"){

                // Intent for go to the biology Activity
                var intent = Intent(mContext, BioTaskListActivity::class.java)
                mContext.startActivity(intent)

                Toast.makeText(mContext, "Biology & Genetics", Toast.LENGTH_SHORT).show()
            }


        }
    }
}
