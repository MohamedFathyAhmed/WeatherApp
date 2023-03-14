package com.example.weatherapp.view.ui.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.weatherapp.CONST
import com.example.weatherapp.R
import com.example.weatherapp.model.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NotificationsWorker(private var appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {
    var weatherDataBase = WeatherDataBase.getInstance(appContext)
    var room = LocalDataSource.getInstance(weatherDataBase,appContext)
    var _repo = Repositary.getInstance(API.retrofitService, room, appContext)

    companion object {
        const val CHANNEL_ID = "channelID"
    }

    override suspend fun doWork(): Result {
        val sharedPreference =
            appContext.getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
        val startDate = inputData.getLong("startDate", 0)
        val endDate = inputData.getLong("endDate", 0)
        val lat = inputData.getString("lat")
        val lon =  inputData.getString("lon")
        val address=inputData.getString("address")
        val alertString = inputData.getString("alert")
      var alert=  Gson().fromJson(alertString, MyAlert::class.java)
        val currentTime = System.currentTimeMillis().div(1000)
      if(currentTime >= startDate && currentTime <= endDate) {
            val responseModel = _repo.getCurrentWeatherApiForWorker(lat, lon)
            var desc:String= responseModel.alerts?.get(0)?.description?:appContext.getString(R.string.there_is_no_alarm_yet)
           // var desc: String = responseModel.current?.weather?.get(0)?.description ?: ""
            if (desc == "") desc = appContext.getString(R.string.there_is_no_alarm_yet)
            if (sharedPreference.getString(
                    CONST.alert,
                    CONST.Enum_alert.notification.toString()
                ) == CONST.Enum_alert.notification.toString()
            ) {
                Notification(responseModel.timezone, " $desc")
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    AlertWindow(appContext, desc, address.toString()).onCreate()
                }
            }
            return Result.success()
        }else
            if(currentTime>endDate){
                var weatherDataBase = WeatherDataBase.getInstance(appContext)
                var room = LocalDataSource.getInstance(weatherDataBase,appContext)
                GlobalScope.launch(Dispatchers.Main) {
                    room.deleteAlertDataBase(alert)
                }
                WorkManager.getInstance(appContext).cancelAllWorkByTag(startDate.toString()+endDate.toString())
            return Result.success()
        }else{
            return Result.failure()
        }
    }



    private fun Notification(title:String,desc:String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel_name"
            val descriptionText = "channel_description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =applicationContext
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_pressure)
            .setContentTitle(title)
            .setContentText(desc)
            .setSound(alarmSound)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(applicationContext)) {
            if (ActivityCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }
    }
}


