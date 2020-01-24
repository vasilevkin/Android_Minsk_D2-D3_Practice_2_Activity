package com.vasilevkin.multiactivityapp


import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


enum class SaveLocation {
    SHARED_PREF,
    EXTERNAL,
    INTERNAL,
    SQL;

    companion object {
        fun fromInteger(x: Int): SaveLocation {
            when (x) {
                0 -> return SHARED_PREF
                1 -> return EXTERNAL
                2 -> return INTERNAL
                3 -> return SQL
            }
            return SHARED_PREF
        }
    }
}

interface Storage {
    fun saveAllTasks()
    fun getAllTasks()
}

class TasksRepository private constructor(context: Context) {

    private val prefs: SharedPreferences
    private val PREFS_FILENAME = "com.vasilevkin.practice2.prefs"
    val SAVE_LOCATION_SETTING = "com.vasilevkin.practice2.savelocation"
    val SAVE_TASKS_LIST = "com.vasilevkin.practice2.taskslist"
    private var tasksList: ArrayList<Task> = ArrayList()

    init {
        prefs = context.getSharedPreferences(PREFS_FILENAME, 0)
    }

    fun getAllTasks(): ArrayList<Task> {
        val gson = Gson()
        val json = prefs.getString(SAVE_TASKS_LIST, "")
        if ((json == null) or (json == "")) {
            return ArrayList<Task>()
        }
//        val obj = gson.fromJson<ArrayList<Task>>(json, ::class.java!!)

        val type = object : TypeToken<ArrayList<Task>>() {}.type
        tasksList = gson.fromJson(json, type)

//        tasksList.add(Task("First", "description 1", 1))
//        tasksList.add(Task("Second", "description 2",2))
//
        Log.d("maap", "tasksList = $tasksList")

        return tasksList
    }

    fun getFavouriteTasksOnly(): ArrayList<Task> {
        val gson = Gson()
        val json = prefs.getString(SAVE_TASKS_LIST, "")
        if ((json == null) or (json == "")) {
            return ArrayList<Task>()
        }
//        val obj = gson.fromJson<ArrayList<Task>>(json, ::class.java!!)

        val type = object : TypeToken<ArrayList<Task>>() {}.type
        tasksList = gson.fromJson(json, type)

//        tasksList.add(Task("First", "description 1", 1))
//        tasksList.add(Task("Second", "description 2",2))
//
        Log.d("maap", "tasksList = $tasksList")

        val favouriteTasksList = ArrayList<Task>()

        tasksList.forEach { task ->
            if (task.isFavourite) {
                favouriteTasksList.add(task)
            }
        }

        return favouriteTasksList
    }

    fun addNewTask(task: Task) {
        tasksList.add(task)

        saveAllTasks()
    }

    fun changeTaskAt(position: Int, newTask: Task) {
        tasksList.set(position, newTask)

        saveAllTasks()
    }

    fun deleteTaskAtPosition(position: Int) {
        tasksList.removeAt(position)
        saveAllTasks()
    }

    fun setTaskFavourite(position: Int, favourite: Boolean) {
        var task = tasksList[position]
        task.isFavourite = favourite

        Log.d("maap", "task = $task")

        tasksList.set(position, task)
        saveAllTasks()
    }

    private fun saveAllTasks() {

        val gson = Gson()
        val json = gson.toJson(tasksList)

        with(prefs.edit()) {
            putString(SAVE_TASKS_LIST, json)
            commit()
        }


//        with (prefs.edit()) {
//            putInt()
//            putInt(getString(R.string.saved_high_score_key), newHighScore)
//            commit()
//        }
    }

    fun getSaveLocation() : SaveLocation {
        return SaveLocation.fromInteger(prefs.getInt(SAVE_LOCATION_SETTING, 0))
    }

    fun setSaveLocation(location: SaveLocation) {
        with(prefs.edit()) {
            putInt(SAVE_LOCATION_SETTING, location.ordinal)
            commit()
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: TasksRepository? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: TasksRepository(context).also { instance = it }
            }
    }
}