package com.dhimas.githubsuserfinder.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.dhimas.githubsuserfinder.R
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val ID_REMINDER = 131
    }

    override fun onReceive(context: Context, intent: Intent) {
        showAlarmNotification(context)
    }

    private fun showAlarmNotification(context: Context){
        val CHANNEL_ID = "REMINDER_1"
        val CHANNEL_NAME = "Reminder Channel"
        val title = "Github User Finder"
        val message = "Find other user again!"
        val vibrationPattern = longArrayOf(1000, 1000)

        val notificationManager= context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_apartment_24)
            .setContentTitle(title)
            .setContentText(message)
            .setVibrate(vibrationPattern)
            .setSound(alarmSound)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = vibrationPattern

            builder.setChannelId(CHANNEL_ID)

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(ID_REMINDER, builder.build())
    }

    fun setAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra("REMINDERZ", "INI ADLAH PESAN")

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 20)
        calendar.set(Calendar.MINUTE, 49)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REMINDER, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent)

        Log.d("Info", "Alarm active")
    }

    fun cancelAlarm(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REMINDER, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)

        Log.d("Info", "Alarm canceled")
    }

    fun isAlarmSet(context: Context): Boolean{
        val intent = Intent(context, AlarmReceiver::class.java)

        return PendingIntent.getBroadcast(context, ID_REMINDER, intent,
            PendingIntent.FLAG_NO_CREATE) != null
    }
}