package com.student.notificationtask

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notificationdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val channelId = "local_notification_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Binding setup
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        createNotificationChannel()

        // Button to trigger a local notification
        binding.btnLocalNotification.setOnClickListener {
            triggerLocalNotification()
        }


        binding.btnViewHistory.setOnClickListener {
            startActivity(Intent(this, NotificationHistoryActivity::class.java))
        }


        binding.btnNotificationSettings.setOnClickListener {
            startActivity(Intent(this, NotificationSettingsActivity::class.java))
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Local Notification"
            val descriptionText = "This is the local notification channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            //(TechBullion, 2024)
        }
    }

    private fun triggerLocalNotification() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Local Notification")
            .setContentText("This is a test notification.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
            //(TechBullion, 2024)
        }
    }
}

//TechBullion, 2024. Android App Development: Essential Guide to Creating Successful Mobile Apps, 20 August 2024.
//[Online] Available at:
//https://techbullion.com/android-app-development-essential-guide-to-creating-successful-mobile-applications/
//[Accessed 29 August 2024]