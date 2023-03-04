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
import androidx.work.*
import com.example.weatherapp.R
import com.example.weatherapp.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NotificationsWorker(private var appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {
    private  var _repo: Repositary = Repositary.getInstance(appContext)

    companion object{
        const val CHANNEL_ID = "channelID"
    }

    override suspend fun doWork(): Result {
//        val data =  inputData.getString("MyAlert")
//        var MyAlert = Gson().fromJson(data, MyAlert::class.java)
        var responseModel: Welcome
        responseModel = _repo.getCurrentWeatherApiForWorker("55","40")
       // var desc:String= responseModel.alerts?.get(0)?.description?:"no alert"
        var desc:String= responseModel.current.weather[0].description
        if(desc=="")desc="no alert"
        if(true){
            GlobalScope.launch (Dispatchers.Main){
                AlertWindow(appContext,desc,"alert").onCreate()
            }
        }else{
            Notification(responseModel.timezone," $desc")
        }

        return Result.success()
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
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(1, builder.build())
        }
    }
}


