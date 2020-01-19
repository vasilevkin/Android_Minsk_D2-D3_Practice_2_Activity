package com.vasilevkin.multiactivityapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_task.*

class NewTaskActivity : AppCompatActivity() {

//    companion object {
//        const val EXTRA_TITLE = "title"
//        const val EXTRA_DESCRIPTION = "url"
//
//        fun newIntent(context: Context, task: Task): Intent {
//            val detailIntent = Intent(context, NewTaskActivity::class.java)
//
//            detailIntent.putExtra(EXTRA_TITLE, task.title)
//            detailIntent.putExtra(EXTRA_DESCRIPTION, task.description)
//
//            return detailIntent
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)

        val bundle = intent.getBundleExtra("Bundle")
        val objectTask = bundle.getParcelable<Task>("key")

        new_task_title.setText(objectTask?.title)
        new_task_description.setText(objectTask?.description)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_new_task_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_save -> {
//                TasksRepository.addNewTask(Task())
//                val intent = Intent(this, SettingsActivity::class.java)
//                startActivity(intent)

                TasksRepository.getInstance(this).addNewTask(
                    Task(
                        new_task_title.text.toString(),
                        new_task_description.text.toString(),
                        false
                    )
                )
                Toast.makeText(
                    applicationContext,
                    "Add new task ${new_task_title.text} - ${new_task_description.text}",
                    Toast.LENGTH_SHORT
                )
                    .show()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
