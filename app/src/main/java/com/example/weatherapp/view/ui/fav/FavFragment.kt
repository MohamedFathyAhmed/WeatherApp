package com.example.weatherapp.view.ui.fav

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.CONST
import com.example.weatherapp.R
import com.example.weatherapp.adapter.FavAdapter
import com.example.weatherapp.databinding.FragmentFavBinding
import com.example.weatherapp.isConnected
import com.example.weatherapp.model.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FavFragment : Fragment(),FavInterface {
    lateinit var viewModel: FavDataViewModel
    private lateinit var _binding: FragmentFavBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavBinding.inflate(inflater, container, false)
        var weatherDataBase = WeatherDataBase.getInstance(requireContext())
        var room = LocalDataSource.getInstance(weatherDataBase,requireContext())
        var repo = Repositary.getInstance(API.retrofitService, room, requireContext())
        viewModel =
            ViewModelProvider(this, FavViewModelFactory(repo)).get(FavDataViewModel::class.java)

        return _binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!isConnected(requireContext())){
            Toast.makeText(requireContext(),R.string.you_are_offline,Toast.LENGTH_SHORT).show()
            _binding.addFab.visibility=GONE
        }

        viewModel.getFavsWeatherDB()
        lifecycleScope.launch() {
            viewModel.flowData.collectLatest { result ->
                when (result) {
                    is ApiStateList.Loading -> {}
                    is ApiStateList.Success -> {
                        val sharedPreference =  requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
                        _binding.daysRecyclerView.adapter= FavAdapter(requireContext(),result.data,this@FavFragment)
                        _binding.daysRecyclerView.apply {
                            adapter = _binding.daysRecyclerView.adapter
                            layoutManager = LinearLayoutManager(requireContext())
                                .apply { orientation = RecyclerView.VERTICAL }
                            setBackgroundResource(sharedPreference.getInt(CONST.Background, R.drawable.gradient))
                        }
                    }
                    is ApiStateList.Failure->{
                        Toast. makeText ( requireContext(),  "Check ${result.msg}", Toast.LENGTH_SHORT) .show ()
                    }

                }

            }
        }


        _binding.addFab.setOnClickListener{

            val action =FavFragmentDirections.actionNavigationFavToMapsFragment("fav")
            Navigation.findNavController(requireView()).navigate(action)
        }



    }

    override fun deleteTask(welcome: Welcome, position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.AWTD)
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { dialog, id ->
                viewModel.deleteFavWeatherDB(welcome)
            }
            .setNegativeButton(R.string.no) { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()

    }


    override fun selectTask(welcome: Welcome) {
        val action =FavFragmentDirections.actionNavigationFavToNavigationHome(false,welcome)
        Navigation.findNavController(requireView()).navigate(action)
    }


}