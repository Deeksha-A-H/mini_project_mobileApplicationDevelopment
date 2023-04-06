package com.example.reminder

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Notification.DEFAULT_VIBRATE
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_VIBRATE

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
class Notification:BroadcastReceiver()
{


    @SuppressLint("WrongConstant")
    override fun onReceive(context: Context, intent: Intent) {

      var i = Intent(context,MainActivity::class.java)
         var pi = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_CANCEL_CURRENT)

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_clock)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200,400,300, 200,400))
            .setLights(Color.WHITE,1000,15000)
            .setContentIntent(pi)
            .setAutoCancel(true)
            //.setSound(Uri.parse("${ContentResolver.SCHEME_ANDROID_RESOURCE}://${context.packageName}/${R.raw.nice_alarm}"))
            .build()



        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var mp=MediaPlayer.create(context,R.raw.nice_alarm)
        mp.start()
        manager.notify(notificationID,notification)
    }
}