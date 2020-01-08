package com.vasilevkin.multiactivityapp

import android.app.TabActivity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TabHost
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

//    class MainActivity : TabActivity() {

//    val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)




//        val tabHost = findViewById<TabHost>(android.R.id.tabhost)
//        if (tabHost != null) {
//            addTab(
//                tabHost,
//                getString(R.string.all_tasks),
//                getString(R.string.all_tasks),
//                AllTasksActivity::class.java
//            )
//            addTab(
//                tabHost,
//                getString(R.string.favourite),
//                getString(R.string.favourite),
//                FavouriteActivity::class.java
//            )
//
//            tabHost.currentTab = 0
//            tabHost.setOnTabChangedListener { tabId ->
//                Toast.makeText(
//                    applicationContext,
//                    tabId,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(applicationContext, "Settings menu is selected", Toast.LENGTH_SHORT)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_tasks -> {
                Toast.makeText(this, "Tasks clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_settings -> {
                Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun addTab(tabHost: TabHost, name: String, indicator: String, className: Class<*>) {
        val tabSpec = tabHost.newTabSpec(name)
        tabSpec.setIndicator(indicator)
        val intent = Intent(this, className)
        tabSpec.setContent(intent)
        tabHost.addTab(tabSpec)
    }

    fun addNewTask(view: View) {
        val intent = Intent(this, NewTaskActivity::class.java)
        startActivity(intent)
    }
}