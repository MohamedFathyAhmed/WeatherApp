package com.example.weatherapp.view.ui


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMapsBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment() {
    private lateinit var binding: FragmentMapsBinding
    private val callback = OnMapReadyCallback { googleMap ->


        googleMap.setOnMapClickListener {
            var lat: Float = 0f
            var long: Float = 0f
            val marker = MarkerOptions().apply {
                position(it)
                lat = it.latitude.toFloat()
                long = it.longitude.toFloat()
                title((lat).plus(long).toString())
                googleMap.clear()
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        it, 10f
                    )
                )
            }
            googleMap.addMarker(marker)
            changeSaveCondition(false)

            binding.btnSave.setOnClickListener {
                handleSaveClickable(lat, long)
            }
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun changeSaveCondition(visible: Boolean) {
        if (visible) {
            binding.btnSave.visibility = View.INVISIBLE
            binding.btnSave.isClickable = false
        } else {
            binding.btnSave.visibility = View.VISIBLE
            binding.btnSave.isClickable = true
        }
    }

    private fun handleSaveClickable(lat: Float, lon: Float) {

        Log.i("test", "$lat + $lon: ")

        changeSaveCondition(true)
    }
}


