package com.example.weatherapp.view.ui.notifications

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.*
import com.example.weatherapp.databinding.FragmentSelectTimeAlertBinding
import com.example.weatherapp.model.Alert
import java.util.*
import java.util.concurrent.TimeUnit

class SelectTimeAlert : DialogFragment() {
    lateinit var alert :Alert
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
        viewModel = ViewModelProvider(this, NotificationsViewModelFactory(requireContext())).get(
            NotificationsViewModel::class.java)
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
        _binding.btnTime.setOnClickListener{
            val listener: (TimePicker?, Int, Int) -> Unit =
                { timePicker: TimePicker?, hour: Int, minute: Int ->
                    var time = (TimeUnit.MINUTES.toSeconds(minute.toLong()) + TimeUnit.HOURS.toSeconds(hour.toLong()))
                    time = time.minus(3600L * 2)
                    _binding.btnTime.text = convertToTime(time, language)
                    alert.Time=time
                }
            val timePickerDialog = TimePickerDialog(requireContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, listener, current.get(Calendar.HOUR_OF_DAY), current.get(Calendar.MINUTE), false)
            timePickerDialog.setTitle("time")
            timePickerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            timePickerDialog.show()
        }
        _binding.btnFrom.setOnClickListener{
            val myDateListener =
                DatePickerDialog.OnDateSetListener { view, year, month, day ->

                    Log.i("time", "onViewCreated: ${getseconds(year,month,day)}")
                    if (view.isShown) {


                        _binding.btnFrom.text = convertToDate(getseconds(year,month+1,day),language)
                        alert.startDay=getseconds(year,month+1,day)
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
                        alert.endDay=getseconds(year,month+1,day)
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
            viewModel.insertAlertDB(alert)
            dialog!!.dismiss()
        }
    }
    private fun setFirstUi(current: Long) {
        val current = current.div(1000L)
        val timeNow = convertToTime(current, language)
        val dateplus = (86400L) + current
        val dateAfter = convertToDate((dateplus), language)
        val dateNow = convertToDate(current, language)
        _binding.btnTo.text = dateAfter
        _binding.btnFrom.text = dateNow
        _binding.btnTime.text=timeNow
        alert = Alert(current,current,dateplus,null)
    }
}