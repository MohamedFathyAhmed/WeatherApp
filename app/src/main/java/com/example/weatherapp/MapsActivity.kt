package com.example.weatherapp

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.location.LocationServices
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.internal.ContextUtils.getActivity
import java.io.IOException
import java.util.*


class MapsActivity() : FragmentActivity(), OnMapReadyCallback{



    private lateinit var mMap: GoogleMap
    var currentMarker:Marker?=null
    var fusedLocationProviderClient: FusedLocationProviderClient?=null

    private lateinit var editor: SharedPreferences.Editor



    var latitude:Double=0.0
    var longtude:Double=0.0
    var selectedAddress=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val sharedPreference =  getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
        sharedPreference.edit().putFloat(CONST.AlertLat, latitude.toFloat()).putFloat(CONST.AlertLong,longtude.toFloat()).putString(CONST.AlertCityName,selectedAddress).commit()

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        editor =  sharedPreference.edit()
        latitude = sharedPreference.getString(CONST.GpsLat, "30")!!.toDouble()
        longtude = sharedPreference.getString(CONST.GpsLong, "40")!!.toDouble()
        fetchLocation()
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation(){

            if(latitude != 0.0 && longtude != 0.0){
                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            1000->if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                fetchLocation()
            }
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val latLon=LatLng(latitude,longtude)
        drawMarker(latLon)
        mMap.setOnMapClickListener {
            currentMarker?.remove()
            drawMarker(it)
        }

        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener{
            override fun onMarkerDrag(p0: Marker) {
            }

            override fun onMarkerDragEnd(p0: Marker) {
                if(currentMarker!=null){
                    currentMarker?.remove()
                }
                val newLatLon=LatLng(p0.position.latitude,p0.position.longitude)
                drawMarker(newLatLon)
                Log.i("TAG","lat= ${p0.position.latitude} \n lon= ${p0.position.longitude}")
            }

            override fun onMarkerDragStart(p0: Marker) {

            }
        })
    }

    fun drawMarker(latLon:LatLng){
        val sharedPreference =  getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
        val language =  sharedPreference.getString(CONST.lang,CONST.Enum_language.en.toString())!!

        val markerOption=MarkerOptions().position(latLon).title("i need this location")
            .snippet(getAddress(latLon.latitude,latLon.longitude,language,this)).draggable(true)
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLon))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLon,15f))
        currentMarker=mMap.addMarker(markerOption)
        currentMarker?.showInfoWindow()
        latitude=latLon.latitude
        longtude=latLon.longitude
        selectedAddress=getAddress(latitude,longtude,language,this)
    }




    fun searchLocation(view: View) {
        val locationSearch = findViewById<EditText>(R.id.editText)
        val location = locationSearch.text.toString()
        var addressList: List<Address>? = null

        if (location.isEmpty()) {
            Toast.makeText(applicationContext,"provide location",Toast.LENGTH_SHORT).show()
        }
        else{

            val geoCoder = Geocoder(this)
            try {
                addressList = geoCoder.getFromLocationName(location, 1)
                if(!addressList.isNullOrEmpty()){
                    val address = addressList[0]
                    val latLng = LatLng(address.latitude, address.longitude)
                    drawMarker(latLng)
                }else{
                    Toast.makeText(applicationContext,"not found",Toast.LENGTH_SHORT).show()
                }

            } catch (e: IOException) {
                e.printStackTrace()

            }

        }
    }
  fun setLocation(view: View) {
      val sharedPreference =  getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
      sharedPreference.edit().putFloat(CONST.AlertLat, latitude.toFloat()).putFloat(CONST.AlertLong,longtude.toFloat()).putString(CONST.AlertCityName,selectedAddress).commit()
     finish()
   }
}