package com.vasilevkin.multiactivityapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

class NewTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_new_task_menu, menu)
        return true
    }
}
