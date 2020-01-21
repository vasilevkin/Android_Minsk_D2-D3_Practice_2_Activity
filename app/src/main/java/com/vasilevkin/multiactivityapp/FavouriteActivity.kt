package com.vasilevkin.multiactivityapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class FavouriteActivity : AppCompatActivity(), AllTasksRecyclerViewAdapter.ItemClickListener,
    AllTasksRecyclerViewAdapter.DetailsButtonClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: AllTasksRecyclerViewAdapter

    private lateinit var tasks: ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        // set up the RecyclerView
        recyclerView = findViewById(R.id.favourite_tasks_recyclerview)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

    }

    override fun onResume() {
        super.onResume()

        tasks = TasksRepository.getInstance(this).getFavouriteTasksOnly()
        adapter = AllTasksRecyclerViewAdapter(this, tasks)
        adapter.setClickListener(this)
        adapter.setDetailsClickListener(this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(view: View, position: Int) {
        onChangeTaskClicked(position)
    }

    override fun onDetailsClick(context: Context, position: Int) {

        Toast.makeText(
            context,
            "onClickDetailsImageButton clicked at $position position",
            Toast.LENGTH_SHORT
        ).show()

        showCustomDialog(position)
    }

    private fun showCustomDialog(position: Int) {
        this?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Action")
                .setItems(
                    R.array.favouriteActionsArray
                ) { dialog, which ->
                    // The 'which' argument contains the index position
                    // of the selected item
                    when (which) {
                        0 -> onChangeTaskClicked(position)
                        1 -> onDeleteTaskClicked(position)
                        2 -> onRemoveFromFavouritesClicked(position)
                    }
                }
            builder.create()
        }.show()
    }

    private fun onChangeTaskClicked(position: Int) {
        val name = object{}.javaClass.enclosingMethod?.name
        Log.d("maap", "$name")

        Toast.makeText(
            this,
            "You clicked " + adapter.getItem(position) + " on row number " + position,
            Toast.LENGTH_SHORT
        ).show()

        val selectedTask = adapter.getItem(position)
        val intent = Intent(this, NewTaskActivity::class.java)
        val bundle = Bundle()
        val parcel = Task(selectedTask.title, selectedTask.description, selectedTask.isFavourite)

        bundle.putParcelable("key", parcel)
        intent.putExtra("Bundle", bundle)
        intent.putExtra("TaskPosition", position)
        startActivity(intent)
    }

    private fun onDeleteTaskClicked(position: Int) {
        val name = object{}.javaClass.enclosingMethod?.name
        Log.d("maap", "$name")

        TasksRepository.getInstance(this).deleteTaskAtPosition(position)
        adapter.notifyDataSetChanged()
    }

    private fun onRemoveFromFavouritesClicked(position: Int) {
        val name = object{}.javaClass.enclosingMethod?.name
        Log.d("maap", "$name")

        TasksRepository.getInstance(this).setTaskFavourite(position, false)
        adapter.notifyDataSetChanged()
    }

}