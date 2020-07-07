package com.dhimas.githubsuserfinder.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.service.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener,
    View.OnClickListener {
    private lateinit var alarmReceiver: AlarmReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        alarmReceiver = AlarmReceiver()

        switch_reminder.isChecked = alarmReceiver.isAlarmSet(this)
        switch_reminder.setOnCheckedChangeListener(this)

        setupUI()
    }

    private fun setupUI(){
        tv_language_title.setOnClickListener(this)
        tv_language.setOnClickListener(this)
    }

    private fun changeLanguage() {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        changeLanguage()
    }

    override fun onCheckedChanged(p0: CompoundButton?, isOn: Boolean) {
        if (isOn) {
            alarmReceiver.setAlarm(this)

            val message = getString(R.string.reminder_activated)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        } else {
            alarmReceiver.cancelAlarm(this)

            val message = getString(R.string.reminder_canceled)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}