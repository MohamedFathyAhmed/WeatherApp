package com.example.weatherapp.view.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eram.weather.adapter.DaysAdapter
import com.eram.weather.adapter.TimesAdapter
import com.example.weatherapp.CONST
import com.example.weatherapp.R
import com.example.weatherapp.convertToTime
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.getWeatherIcon
import com.example.weatherapp.model.*
import com.google.android.gms.location.*
import android.content.Context.LOCATION_SERVICE as ContextLOCATION_SERVICE

const val PERMISSION_ID = 44
class HomeFragment : Fragment() {

    lateinit var viewModel: HomeDataViewModel
    var latitude: Double =0.0
    var longitude: Double =0.0
    private lateinit var _binding: FragmentHomeBinding
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val binding get() = _binding


  /*============================================================================================================*/



    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPreference =  requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, HomeViewModelFactory(requireContext())).get(HomeDataViewModel::class.java)

       var location =  sharedPreference.getString(CONST.LOCATION,"gps")
        if(location.equals("gps")){
          getLastLocation()
       }else{
           //map
            viewModel.getCurrentWeatherApi(sharedPreference.getFloat(CONST.MapLat,0f).toString(),sharedPreference.getFloat(CONST.MapLong,0f).toString())
          viewModel.welcome.observe(viewLifecycleOwner) {
              //needtodelete
              println(it)
                initUi(it)
            }
        }

        return binding.root
    }

    /*============================================================================================================*/
    private fun initUi(it:Welcome) {
        binding.rvDays.adapter= DaysAdapter(it.daily,requireContext()){}
        binding.rvDays.apply {
            adapter = binding.rvDays.adapter
            layoutManager = LinearLayoutManager(requireContext())
                .apply { orientation = RecyclerView.VERTICAL }
        }

        binding.rvTimes.adapter= TimesAdapter(requireContext(),it.hourly)
        binding.rvTimes.apply {
            adapter = binding.rvTimes.adapter
            layoutManager = LinearLayoutManager(requireContext())
                .apply { orientation = RecyclerView.HORIZONTAL }
        }

        var iconUrl= "https://openweathermap.org/img/wn/${it.current.weather[0].icon}@2x.png"
        Glide.with(requireContext()).load(iconUrl)
            .apply(
                RequestOptions().override(400, 300).placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
            ).into(  _binding.imgIcon)

        _binding.txtDegree.text = "${it.current.temp}°"
        if (it.daily[0].temp.min != it.daily[0].temp.max)
            _binding.txtDegreeRange.text = "${it.daily[0].temp.min}°/${it.daily.get(0).temp.max}°"
        else
            _binding.txtDegreeRange.text = ""
        // setWeatherIcon(it)
        _binding.city.text= it.timezone
        _binding.txtDegree.text= "${it.current.temp}°"

    }


    /*============================================================================================================*/
    private fun setBackgroundContainer(timeState: TimeState?) {
     _binding.container.setBackgroundResource(
            when (timeState) {
                TimeState.Dawn ->
                    R.drawable.background_dawn
                TimeState.Night ->
                    R.drawable.background_night
                TimeState.Morning ->
                    R.drawable.background_morning
                TimeState.Evening ->
                    R.drawable.background_evening
                TimeState.Noon ->
                    R.drawable.background_noon
                else ->
                    R.drawable.background_noon
            }
        )
    }
    /*============================================================================================================*/
    private fun setWeatherIcon(weather: Weather) {
    //   _binding.imgIcon.setImageResource(getWeatherIcon(weather.state, currentTimeState!!))
    }
    /*========================================GPS====================================================================*/
    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf<String>(
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )

    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
        if (requestCode == PERMISSION_ID) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                requestNewLocationData()

            }else{
                val intent = Intent (Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity (intent)

            }
        } else {
            requestPermissions()

        }
    }

    fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }




    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {

        val mLocationRequest = LocationRequest()
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(0)
         mLocationRequest.setFastestInterval(0);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            latitude  = mLastLocation. latitude
            longitude = mLastLocation. longitude
            Log.i("test","hello")
            viewModel.getCurrentWeatherApi(latitude.toString(),longitude.toString())
            viewModel.welcome.observe(viewLifecycleOwner) {
                //needtodelete
                println(it)
                initUi(it)
            }
            mFusedLocationClient.removeLocationUpdates(this)
        }

    }


    /*============================================================================================================*/
   fun init_rv (current:Current)  = listOf<Condition>(
            Condition(
                R.drawable.ic_pressure,
                ("${current.pressure} ${getString(R.string.Pascal)}"),
                getString(
                    R.string.Pressure
                )
            ),
            Condition(
                R.drawable.ic_humidity,
                ("${current.humidity} %") as String,
                getString(
                    R.string.Humidity
                )
            ),
            Condition(
                R.drawable.ic_cloudy,
                ("${current.clouds} "),
                getString(
                    R.string.Cloud
                )
            ),
            Condition(
                R.drawable.ic_sunrise,
                convertToTime(current.sunrise!!.toLong(), requireContext()),
                getString(
                    R.string.Sun_Rise
                )
            ),
            Condition(
                R.drawable.ic_visibility,
                ("${current.visibility}"),
                getString(
                    R.string.Visibility
                )
            ),
            Condition(
                R.drawable.ic_windspeed,
                ("${current.wind_speed} ${R.string.MiliPerHour} "),
                getString(
                    R.string.WindSpeed
                )
            )

        )
    /*============================================================================================================*/
}