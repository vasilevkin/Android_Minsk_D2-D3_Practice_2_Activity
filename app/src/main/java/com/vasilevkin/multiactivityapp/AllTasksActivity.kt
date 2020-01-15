package com.vasilevkin.multiactivityapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AllTasksActivity : AppCompatActivity(), AllTasksRecyclerViewAdapter.ItemClickListener {

    internal lateinit var adapter: AllTasksRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tasks)

        // data to populate the RecyclerView with
        val tasks = ArrayList<Task>()
        tasks.add(Task("Horse", "new1", false))
        tasks.add(Task("Cow", "new2", false))
        tasks.add(Task("Pig", "desc3", false))

        // set up the RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.all_tasks_recyclerview)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(layoutManager)
        adapter = AllTasksRecyclerViewAdapter(this, tasks)
        adapter.setClickListener(this)
        recyclerView.setAdapter(adapter)

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.getOrientation()
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(
            this,
            "You clicked " + adapter.getItem(position) + " on row number " + position,
            Toast.LENGTH_SHORT
        ).show()
    }
}