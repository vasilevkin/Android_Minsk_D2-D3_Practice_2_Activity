package com.vasilevkin.multiactivityapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private var selectedSetting = SaveLocation.SHARED_PREF

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        selectedSetting = TasksRepository.getInstance(this).getSaveLocation()

        when (selectedSetting) {
            SaveLocation.SHARED_PREF -> radio_button_1_shared_pref.isChecked = true
            SaveLocation.EXTERNAL -> radio_button_2_external.isChecked = true
            SaveLocation.INTERNAL -> radio_button_3_internal.isChecked = true
            SaveLocation.SQL -> radio_button_4_sql.isChecked = true
        }

        saveto_setting_radiogroup.setOnCheckedChangeListener { group, isChecked ->

            var selected: SaveLocation = when (isChecked) {
                R.id.radio_button_1_shared_pref -> SaveLocation.SHARED_PREF
                R.id.radio_button_2_external -> SaveLocation.EXTERNAL
                R.id.radio_button_3_internal -> SaveLocation.INTERNAL
                R.id.radio_button_4_sql -> SaveLocation.SQL
                else -> SaveLocation.SHARED_PREF
            }

            TasksRepository.getInstance(this).setSaveLocation(selected)
        }
    }
}