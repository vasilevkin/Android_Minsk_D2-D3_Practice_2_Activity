package com.vasilevkin.multiactivityapp


import android.app.Application
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.R.attr.key
import android.preference.PreferenceManager
import android.util.Log


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

class TasksRepository private constructor() {

    private val prefs : SharedPreferences
    lateinit var saveTo : SaveLocation
    private val PREFS_FILENAME = "com.vasilevkin.practice2.prefs"
    val SAVE_SETTING = "com.vasilevkin.practice2.savelocation"
    val SAVE_TASKS_LIST = "com.vasilevkin.practice2.taskslist"
    private var tasksList: ArrayList<Task> = ArrayList()

    init {
        prefs = MyClass.getContext().getSharedPreferences(PREFS_FILENAME, 0)
        saveTo = SaveLocation.fromInteger(prefs.getInt(SAVE_SETTING, 0))
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

    fun addNewTask(task: Task) {
        tasksList.add(task)

        saveAllTasks()
    }

    private fun saveAllTasks() {

        val gson = Gson()
        val json = gson.toJson(tasksList)

        with (prefs.edit()) {
            putString(SAVE_TASKS_LIST, json)
            commit()
        }


//        with (prefs.edit()) {
//            putInt()
//            putInt(getString(R.string.saved_high_score_key), newHighScore)
//            commit()
//        }
    }

    fun setSaveLocation(location: SaveLocation) {


//        when (location) {
//            SaveLocation.SHARED_PREF -> {
//                with (prefs.edit()) {
//                    putString(SAVE_SETTING, SaveLocation.SHARED_PREF.toString())
//                    putInt(getString(R.string.saved_high_score_key), newHighScore)
//                    commit()
//                }
//            }
//        }

    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TasksRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: TasksRepository().also { instance = it }
            }
    }
}