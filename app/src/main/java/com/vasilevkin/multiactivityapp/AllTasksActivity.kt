package com.vasilevkin.multiactivityapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AllTasksActivity : AppCompatActivity(), AllTasksRecyclerViewAdapter.ItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: AllTasksRecyclerViewAdapter

    private lateinit var tasks: ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_tasks)

        // set up the RecyclerView
        recyclerView = findViewById(R.id.all_tasks_recyclerview)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        tasks = TasksRepository.getInstance(this).getAllTasks()
    }

    override fun onResume() {
        super.onResume()

        adapter = AllTasksRecyclerViewAdapter(this, tasks)
        adapter.setClickListener(this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(
            this,
            "You clicked " + adapter.getItem(position) + " on row number " + position,
            Toast.LENGTH_SHORT
        ).show()

        val intent = Intent(this, NewTaskActivity::class.java)
//        intent.putExtra("Task_object", adapter.getItem(position))
        startActivity(intent)
    }
}