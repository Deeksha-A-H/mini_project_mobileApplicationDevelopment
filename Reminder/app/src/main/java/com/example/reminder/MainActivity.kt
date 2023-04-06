package com.example.reminder

import android.app.*
import android.app.Notification.AUDIO_ATTRIBUTES_DEFAULT
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.reminder.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val da = binding.userDate
        val today = Calendar.getInstance()
        val now = today.timeInMillis
        da.minDate = now

        createNotificationChannel()
        binding.submitButton.setOnClickListener { scheduleNotification() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Notify Channel"
        val desc = "A description of the Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelID, name, importance).apply {
                lightColor = Color.RED
                enableLights(true)
                enableVibration(true)
            }

            channel.description = desc
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification() {
        val intent= Intent(applicationContext,Notification::class.java)
        val title = binding.userTitle.text.toString()
        intent.putExtra(titleExtra,title)

        val pendingIntent=PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time=getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time,title)
    }

    private fun showAlert(time: Long, title: String) {
       val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Task Notification")
            .setMessage(
                "Task: " + title+ "\nCome on let's do :)" +
                        "\nAt: "+dateFormat.format(date)+" "+timeFormat.format(date)
            )
            .setPositiveButton("Ok"){_,_->Toast.makeText(this,"Task is set", Toast.LENGTH_LONG).show()}
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTime(): Long {

       val minute = binding.userTime.minute
        val hour = binding.userTime.hour
        val day = binding.userDate.dayOfMonth
        val month = binding.userDate.month
        val year = binding.userDate.year
        val calendar = java.util.Calendar.getInstance()
        calendar.set(year,month,day,hour,minute)
        return calendar.timeInMillis
    }

    fun exit(v: View?)
    {
        finish()
        Toast.makeText(this,"CANCELED",Toast.LENGTH_LONG).show()
    }
}