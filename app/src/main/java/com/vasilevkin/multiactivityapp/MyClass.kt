package com.vasilevkin.multiactivityapp

import android.content.Context

abstract class MyClass {

    companion object {

        private lateinit var context: Context

        fun setContext(con: Context) {
            context = con
        }

        fun getContext(): Context {
            return context
        }
    }
}