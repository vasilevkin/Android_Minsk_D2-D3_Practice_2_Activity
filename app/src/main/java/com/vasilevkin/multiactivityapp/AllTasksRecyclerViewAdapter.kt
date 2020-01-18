package com.vasilevkin.multiactivityapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class AllTasksRecyclerViewAdapter// data is passed into the constructor
internal constructor(context: Context, private val data: List<Task>) :
    RecyclerView.Adapter<AllTasksRecyclerViewAdapter.ViewHolder>() {
    private val inflater: LayoutInflater
    private var clickListener: ItemClickListener? = null
    private val con = context

    init {
        this.inflater = LayoutInflater.from(context)
    }

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.task_list_item, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val taskTitle = data[position].title
        val taskDescription = data[position].description

        holder.titleTextView.text = taskTitle
        holder.descriptionTextView.text = taskDescription
        holder.detailsImageButton.setOnClickListener(onClickDetailsImageButton(con, position))
    }

    // total number of rows
    override fun getItemCount(): Int {
        return data.size
    }

    private fun onClickDetailsImageButton(context: Context, position: Int): View.OnClickListener {
        return View.OnClickListener { v ->
            Toast.makeText(
                context,
                "onClickDetailsImageButton clicked at $position position",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        internal var titleTextView: TextView
        internal var descriptionTextView: TextView
        internal var detailsImageButton: ImageButton

        init {
            titleTextView = itemView.findViewById(R.id.task_list_title)
            descriptionTextView = itemView.findViewById(R.id.task_list_description)
            detailsImageButton = itemView.findViewById(R.id.task_list_details_button)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (clickListener != null) clickListener!!.onItemClick(view, adapterPosition)
        }
    }

    // convenience method for getting data at click position
    internal fun getItem(id: Int): Task {
        return data[id]
    }

    // allows clicks events to be caught
    internal fun setClickListener(itemClickListener: ItemClickListener) {
        this.clickListener = itemClickListener
    }

    // parent activity will implement this method to respond to click events
    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}