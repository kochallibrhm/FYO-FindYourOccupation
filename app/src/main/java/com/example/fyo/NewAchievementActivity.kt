package com.example.fyo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_achievement.*

class NewAchievementActivity : AppCompatActivity() {
    // Initializing the array list
    var allOccupations = ArrayList<OccListClass>()
    //var db = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_achievement)


        // Back button click listener which is on the actionbar.
        toolbarNewAchievement.setNavigationOnClickListener {
            intent = Intent(this, MainActivity::class.java )
            startActivity(intent)
        }

        // Setting the data to allOccupations list
        prepareDataSource()

        // Initializing the adapter
        var myAdapter = OccupationsRcAdapter(allOccupations, this)
        occsRecyclerView.adapter = myAdapter
        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        occsRecyclerView.layoutManager = layoutManager


    }

    private fun prepareDataSource(): ArrayList<OccListClass>{
        var allIcons = arrayOf(R.drawable.computer, R.drawable.arch, R.drawable.virus)
        var allNames = arrayOf("Computer Science", "Architecture", "Biology & Genetics")

        // creating allOccupations array
        for (i in allIcons.indices){


            // creating i. array element
            var occToBeAdded = OccListClass( allNames[i], allIcons[i])

            allOccupations.add(occToBeAdded)

        }
        return allOccupations
    }
}
