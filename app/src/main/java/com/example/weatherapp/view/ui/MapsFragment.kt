package com.example.weatherapp.view.ui


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.CONST
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMapsBinding
import com.example.weatherapp.view.HomeActivity
import com.example.weatherapp.view.ui.fav.FavDataViewModel
import com.example.weatherapp.view.ui.fav.FavFragment
import com.example.weatherapp.view.ui.fav.FavViewModelFactory
import com.example.weatherapp.view.ui.home.HomeDataViewModel
import com.example.weatherapp.view.ui.home.HomeViewModelFactory

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
     var comeFromHome:Boolean =  MapsFragmentArgs.fromBundle(requireArguments()).comeFromHome ?: true

if(comeFromHome){
    val sharedPreference =  requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
    sharedPreference.edit().putFloat(CONST.MapLat,lat).putFloat(CONST.MapLong,lon).commit()
    startActivity(Intent(requireContext(), HomeActivity::class.java))

}else{
    lateinit var honeViewModel: HomeDataViewModel
    honeViewModel = ViewModelProvider(this, HomeViewModelFactory(requireContext())).get(
        HomeDataViewModel::class.java)
    honeViewModel.getCurrentWeatherApi(lat.toString(),lon.toString())
    honeViewModel.welcome.observe(viewLifecycleOwner) {
        lateinit var viewModel: FavDataViewModel
        viewModel = ViewModelProvider(this, FavViewModelFactory(requireContext())).get(
            FavDataViewModel::class.java
        )
        viewModel.insertFavWeatherDB(it)
        startActivity(Intent(requireContext(), HomeActivity::class.java))
    }

    //startActivity(Intent(requireContext(), FavFragment::class.java))
}
        changeSaveCondition(true)
    }

}


