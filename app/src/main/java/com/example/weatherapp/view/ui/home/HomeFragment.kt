package com.example.weatherapp.view.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.eram.weather.adapter.DaysAdapter
import com.eram.weather.adapter.TimesAdapter
import com.example.weatherapp.R

import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.getWeatherIcon
import com.example.weatherapp.model.TimeState
import com.example.weatherapp.model.Weather

class HomeFragment : Fragment() {
    lateinit var viewModel: HomeDataViewModel
    private lateinit var _binding: FragmentHomeBinding
  //  private lateinit var daysAdapter: DaysAdapter
    //private lateinit var timesAdapter: TimesAdapter
    private val binding get() = _binding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, DataViewModelFactory(requireContext())).get(HomeDataViewModel::class.java)

        viewModel.welcome.observe(viewLifecycleOwner) {

            binding.rvDays.adapter= DaysAdapter(it.daily,requireContext()){
                //to do
            }
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
            //get current
            Log.i("it", it.toString())
        }



        return binding.root
    }

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

    private fun setWeatherIcon(weather: Weather) {
    //   _binding.imgIcon.setImageResource(getWeatherIcon(weather.state, currentTimeState!!))
    }

}