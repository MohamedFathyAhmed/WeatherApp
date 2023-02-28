package com.example.weatherapp.view.ui.fav

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.CONST
import com.example.weatherapp.R
import com.example.weatherapp.adapter.FavAdapter
import com.example.weatherapp.databinding.FragmentDashboardBinding
import com.example.weatherapp.databinding.FragmentFavBinding
import com.example.weatherapp.model.ApiState
import com.example.weatherapp.model.ApiStateList
import com.example.weatherapp.model.Welcome
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
        viewModel = ViewModelProvider(this, FavViewModelFactory(requireContext())).get(FavDataViewModel::class.java)

        return _binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavsWeatherDB()
        lifecycleScope.launch() {
            viewModel.flowData.collectLatest { result ->
                when (result) {
                    is ApiStateList.Loading ->
                    {
                        Toast. makeText ( requireContext(),  " loading", Toast.LENGTH_SHORT) .show ()
                    }

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

            val action =FavFragmentDirections.actionNavigationFavToMapsFragment()
            Navigation.findNavController(requireView()).navigate(action)
            _binding.daysRecyclerView.adapter?.notifyDataSetChanged()
        }



    }

    override fun deleteTask(welcome: Welcome, position: Int) {
        viewModel.deleteFavWeatherDB(welcome)
    }


    override fun selectTask(welcome: Welcome) {

        val action =FavFragmentDirections.actionNavigationFavToNavigationHome(false,welcome)

        Navigation.findNavController(requireView()).navigate(action)
    }


}