package com.example.weatherapp.view.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eram.weather.adapter.DaysAdapter
import com.eram.weather.adapter.TimesAdapter
import com.example.weatherapp.*
import com.example.weatherapp.R
import com.example.weatherapp.adapter.ConditionAdapter
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.model.*
import com.example.weatherapp.view.ui.fav.FavDataViewModel
import com.example.weatherapp.view.ui.fav.FavViewModelFactory
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

const val PERMISSION_ID = 44
class HomeFragment : Fragment() {
    lateinit var navBar: BottomNavigationView
    lateinit var homeViewModel: HomeDataViewModel
    lateinit var favViewModel: FavDataViewModel
    var latitude: Double =0.0
    var longitude: Double =0.0
    private lateinit var _binding: FragmentHomeBinding
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val binding get() = _binding


  /*============================================================================================================*/

    override fun onDestroy() {
        super.onDestroy()
        navBar.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPreference =  requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this, HomeViewModelFactory(requireContext())).get(HomeDataViewModel::class.java)
        favViewModel = ViewModelProvider(this, FavViewModelFactory(requireContext())).get(FavDataViewModel::class.java)
        //network
        if (isConnected(requireContext())) {
            //from home
        if( HomeFragmentArgs.fromBundle(requireArguments()).comeFromHome) {
            var location = sharedPreference.getString(CONST.LOCATION, "gps")
            // from gps
                if (location.equals("gps")) {
                    //gps
                    getLastLocation()

                } else {
                    //map


                    homeViewModel.getCurrentWeatherApi(
                        sharedPreference.getFloat(CONST.MapLat, 0f).toString(),
                        sharedPreference.getFloat(CONST.MapLong, 0f).toString()
                    )
                    lifecycleScope.launch() {
                        homeViewModel.data.collectLatest { result ->
                            when (result) {
                                is ApiState.Loading ->
                                {
                                    Toast. makeText ( requireContext(),  " loading",Toast.LENGTH_SHORT) .show ()
                                }

                                is ApiState.Success -> {
                                    initUi(result.data)
                                  //  homeViewModel.insertCurrentWeatherDB(result.data)
                                    Toast. makeText ( requireContext(),  "get connection",Toast.LENGTH_SHORT) .show ()
                                }
                                is ApiState.Failure->{
                                    Toast. makeText ( requireContext(),  "Check ${result.msg}",Toast.LENGTH_SHORT) .show ()
                                }

                            }

                        }
                    }
                }

//from fav
            } else {

                navBar = requireActivity().findViewById(R.id.nav_view)
                navBar.setVisibility(View.GONE);
                HomeFragmentArgs.fromBundle(requireArguments()).favWeather?.let {
                    homeViewModel.getCurrentWeatherApi(it.lat.toString(),it.lon.toString())

                    lifecycleScope.launch() {
                        homeViewModel.data.collectLatest { result ->
                            when (result) {
                                is ApiState.Loading ->
                                {
                                    Toast. makeText ( requireContext(),  " loading",Toast.LENGTH_SHORT) .show ()
                                }

                                is ApiState.Success -> {
                                    initUi(result.data)
                                    favViewModel.updateFavWeatherDB(result.data)
                                    Toast. makeText ( requireContext(),  "get connection",Toast.LENGTH_SHORT) .show ()
                                }
                                is ApiState.Failure->{
                                    Toast. makeText ( requireContext(),  "Check ${result.msg}",Toast.LENGTH_SHORT) .show ()
                                }

                            }

                        }
                    }
                }
            }
        }//offline
        else{
            //from home
            if( HomeFragmentArgs.fromBundle(requireArguments()).comeFromHome) {
homeViewModel.getCurrentWeatherDB()
                // TODO: get current from data base
                lifecycleScope.launch() {
                    homeViewModel.data.collectLatest { result ->
                        when (result) {
                            is ApiState.Loading ->
                            {
                                Toast. makeText ( requireContext(),  " loading",Toast.LENGTH_SHORT) .show ()
                            }
                            is ApiState.Success -> {
                                initUi(result.data)
                                favViewModel.updateFavWeatherDB(result.data)
                            }
                            is ApiState.Failure->{
                                Toast. makeText ( requireContext(),  "Check ${result.msg}",Toast.LENGTH_SHORT) .show ()
                            }

                        }

                    }
                }
            //from fav
            } else {
                navBar = requireActivity().findViewById(R.id.nav_view)
                navBar.setVisibility(View.GONE);
                HomeFragmentArgs.fromBundle(requireArguments()).favWeather?.let { initUi(it) }
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

        binding.recyViewConditionDescription.adapter= ConditionAdapter(init_rv_des(it.current))
        init_rv_des(it.current)
        binding.recyViewConditionDescription.apply {
            adapter = binding.recyViewConditionDescription.adapter
            layoutManager = GridLayoutManager(requireContext(),3)
                .apply { orientation = RecyclerView.VERTICAL }
        }

       _binding.imgIcon.setImageResource(getIconImage(it.current.weather[0].icon))

        _binding.txtDegree.text = "${it.current.temp}°"
        if (it.daily[0].temp.min != it.daily[0].temp.max)
            _binding.txtDegreeRange.text = "${it.daily[0].temp.min}°/${it.daily.get(0).temp.max}°"
        else
            _binding.txtDegreeRange.text = ""



        _binding.city.text=  getAddress(it.lat,it.lon,"ar" ,requireContext())
        _binding.txtDegree.text= "${it.current.temp}°"

        _binding.container.setBackgroundResource(setBackgroundContainer(it.current.weather[0].icon,requireContext()))
        _binding.dayState.text=it.current.weather[0].description
    }


    /*============================================================================================================*/

    /*============================================================================================================*/
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
            val mLastLocation: Location? = locationResult.lastLocation
            if (mLastLocation != null) {
                latitude  = mLastLocation. latitude
                longitude = mLastLocation. longitude
            }else{
                val sharedPreference =  requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
                latitude  =  sharedPreference.getFloat(CONST.MapLat, 0f).toDouble()
                longitude =  sharedPreference.getFloat(CONST.MapLong, 0f).toDouble()
            }

            homeViewModel.getCurrentWeatherApi(latitude.toString(),longitude.toString())

            lifecycleScope.launch() {
                homeViewModel.data.collectLatest { result ->
                    when (result) {
                        is ApiState.Loading ->
                        {
                            Toast. makeText ( requireContext(),  " loading",Toast.LENGTH_SHORT) .show ()
                        }
                        is ApiState.Success -> {
                            initUi(result.data)
                         //   homeViewModel.insertCurrentWeatherDB(result.data)
                            favViewModel.updateFavWeatherDB(result.data)
                       }
                        is ApiState.Failure->{
                            Toast. makeText ( requireContext(),  "Check ${result.msg}",Toast.LENGTH_SHORT) .show ()
                        }

                    }

                }
            }
            mFusedLocationClient.removeLocationUpdates(this)
        }

    }


    /*============================================================================================================*/
   fun init_rv_des (current:Current)  = listOf<Condition>(
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
                convertToTime(current.sunrise!!.toLong(), "en"),
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