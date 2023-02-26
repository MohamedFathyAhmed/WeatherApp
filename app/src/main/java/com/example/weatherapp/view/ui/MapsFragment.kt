package com.example.weatherapp.view.ui


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherapp.CONST
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMapsBinding
import com.example.weatherapp.view.HomeActivity
import com.example.weatherapp.view.ui.fav.FavDataViewModel
import com.example.weatherapp.view.ui.fav.FavFragment
import com.example.weatherapp.view.ui.fav.FavFragmentDirections
import com.example.weatherapp.view.ui.fav.FavViewModelFactory
import com.example.weatherapp.view.ui.home.HomeDataViewModel
import com.example.weatherapp.view.ui.home.HomeViewModelFactory
import com.google.android.gms.common.api.Status

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class MapsFragment : Fragment() {
    var lat: Float = 0f
    var long: Float = 0f
    private lateinit var binding: FragmentMapsBinding
    lateinit var mMap : GoogleMap
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        googleMap.setOnMapClickListener {

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
        val apiKey = getString(R.string.api_key)
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), apiKey)
        }
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.PHOTO_METADATAS,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS
            )
        )
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                place.latLng?.let {
                    mMap.addMarker(
                        MarkerOptions()
                            .position(it)
                            .title(place.name)
                    )
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 10f))
//                    handleSaveClickable(it.latitude.toFloat(), it.longitude.toFloat())
                    lat = it.latitude.toFloat()
                    long = it.longitude.toFloat()
                    changeSaveCondition(false)


                }
            }
            override fun onError(status: Status) {
                Toast.makeText(requireContext(), status.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        binding.btnSave.setOnClickListener {
            handleSaveClickable(lat, long)
        }
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
        viewModel.getFavsWeatherDB()
        val action =MapsFragmentDirections.actionMapsFragmentToNavigationFav()
        Navigation.findNavController(requireView()).navigate(action)

    }


}
        changeSaveCondition(true)
    }

}


