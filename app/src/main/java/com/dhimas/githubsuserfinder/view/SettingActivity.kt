package com.dhimas.githubsuserfinder.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.service.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private lateinit var alarmReceiver: AlarmReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        alarmReceiver = AlarmReceiver()

        switch_reminder.isChecked = alarmReceiver.isAlarmSet(this)
        switch_reminder.setOnCheckedChangeListener(this)
    }

    fun changeLanguage(view: View) {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(intent)
    }

    override fun onCheckedChanged(p0: CompoundButton?, isOn: Boolean) {
        if(isOn){
            alarmReceiver.setAlarm(this)
            Toast.makeText(this, "Reminder at 09:00 AM activated", Toast.LENGTH_LONG).show()
        }else{
            alarmReceiver.cancelAlarm(this)
            Toast.makeText(this, "Reminder at 09:00 AM canceled", Toast.LENGTH_LONG).show()
        }
    }
}