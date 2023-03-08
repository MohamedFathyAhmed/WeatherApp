package com.example.weatherapp.view.ui.notifications

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.weatherapp.*
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSelectTimeAlertBinding
import com.example.weatherapp.model.*
import com.example.weatherapp.view.ui.map.MapsActivity
import java.util.*
import java.util.concurrent.TimeUnit

class SelectTimeAlert : DialogFragment() {
    override fun onResume() {
        super.onResume()
        val sharedPreference =  requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
   var AlertCityName= sharedPreference.getString(CONST.AlertCityName,"default")
        _binding.btnCity.text=AlertCityName
    }

    lateinit var myAlert :MyAlert
    var language :String=""
   lateinit var viewModel: NotificationsViewModel
    private lateinit var _binding: FragmentSelectTimeAlertBinding
    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setBackgroundDrawableResource(R.drawable.round_corner)
        val windowlp = dialog!!.window!!.attributes
        windowlp.gravity = Gravity.BOTTOM
        dialog!!.window!!.attributes = windowlp
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        _binding = FragmentSelectTimeAlertBinding.inflate(inflater, container, false)
        var weatherDataBase = WeatherDataBase.getInstance(requireContext())
        var room = LocalDataSource.getInstance(weatherDataBase,requireContext())
        var repo = Repositary.getInstance(API.retrofitService, room, requireContext())
        viewModel = ViewModelProvider(
            this,
            NotificationsViewModelFactory(repo)
        )[NotificationsViewModel::class.java]
        return _binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val current = Calendar.getInstance()
        val sharedPreference =  requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
         language =  sharedPreference.getString(CONST.lang,"en") !!
        setFirstUi(current.timeInMillis)
        _binding.ConstraintLayout.setBackgroundResource(sharedPreference.getInt(CONST.Background,R.drawable.gradient))
        _binding.closeButton.setOnClickListener{
            dialog!!.dismiss()
        }
        _binding.btnCity.setOnClickListener{
          startActivity(Intent(requireContext(), MapsActivity::class.java))
        }
        _binding.btnTime.setOnClickListener{
            val listener: (TimePicker?, Int, Int) -> Unit =
                { timePicker: TimePicker?, hour: Int, minute: Int ->
                    var time = (TimeUnit.MINUTES.toSeconds(minute.toLong()) + TimeUnit.HOURS.toSeconds(hour.toLong()))
                    time = time.minus(3600L * 2)
                    _binding.btnTime.text = convertToTime(time, language)
                    myAlert.Time=time
                }
            val timePickerDialog = TimePickerDialog(requireContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, listener, current.get(Calendar.HOUR_OF_DAY), current.get(Calendar.MINUTE), false)
            timePickerDialog.setTitle("time")
            timePickerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            timePickerDialog.show()
        }
        _binding.btnFrom.setOnClickListener{
            val myDateListener =
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                  if (view.isShown) {


                        _binding.btnFrom.text = convertToDate(getseconds(year,month+1,day),language)
                        myAlert.startDay=getseconds(year,month+1,day)
                    }
                }
            val datePickerDialog = DatePickerDialog(
                requireContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myDateListener, current[Calendar.YEAR],current[Calendar.MONTH],current [Calendar.DAY_OF_MONTH]
            )
            val title = "Choose date"
            datePickerDialog.setTitle(title)
            datePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            datePickerDialog.show()
        }
        _binding.btnTo.setOnClickListener{
            val myDateListener =
                DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    if (view.isShown) {
                        _binding.btnTo.text = convertToDate(getseconds(year,month+1,day),language)
                        myAlert.endDay=getseconds(year,month+1,day)
                    }
                }
            val datePickerDialog = DatePickerDialog(
                requireContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myDateListener, current[Calendar.YEAR],current[Calendar.MONTH],current [Calendar.DAY_OF_MONTH]
            )
            val title = "Choose date"
            datePickerDialog.setTitle(title)
            datePickerDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            datePickerDialog.show()
        }
        _binding.btnSaveAlert.setOnClickListener{
            val sharedPreference =  requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
            myAlert.lat =sharedPreference.getFloat(CONST.AlertLat, 0.0F).toDouble()
            myAlert.lon=sharedPreference.getFloat(CONST.AlertLong, 0.0F).toDouble()
            myAlert.AlertCityName = sharedPreference.getString(CONST.AlertCityName,"default").toString()
            viewModel.insertAlertDB(myAlert)
            setWorker(myAlert)
            dialog!!.dismiss()
        }
    }

     fun setFirstUi(current: Long) {
        val current = current.div(1000L)
        val timeNow = convertToTime(current, language)
        val dateplus = (86400L) + current
        val dateAfter = convertToDate((dateplus), language)
        val dateNow = convertToDate(current, language)
        _binding.btnTo.text = dateAfter
        _binding.btnFrom.text = dateNow
        _binding.btnTime.text=timeNow
        myAlert = MyAlert(current,current,dateplus,null,0.0,0.0,"")
    }

     fun setWorker(myAlert:MyAlert) {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.timeInMillis.div(1000)
        val targetTime = myAlert.Time
        val initialDelay = ((currentTime - targetTime)/60/60/60/60)-100
        println( convertToTime(currentTime,"en"))
        println( convertToTime(myAlert.Time,"en"))
        println(initialDelay )

        val data = Data.Builder()
        data.putString("lat", myAlert.lat.toString())
        data.putString("lon", myAlert.lon.toString())
        data.putString("address", myAlert.AlertCityName)
        data.putLong("startDate", myAlert.startDay)
        data.putLong("endDate", myAlert.endDay)
        val workRequest = PeriodicWorkRequestBuilder<NotificationsWorker>(1,TimeUnit.DAYS)
            .setInitialDelay(initialDelay, TimeUnit.SECONDS)
            .addTag(myAlert.id.toString())
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .setInputData(data.build())
            .build()

        WorkManager.getInstance(requireContext()).enqueue(workRequest)

    }
}