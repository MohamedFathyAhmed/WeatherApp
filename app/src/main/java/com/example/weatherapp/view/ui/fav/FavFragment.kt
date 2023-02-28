package com.example.weatherapp.view.ui.fav

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.CONST
import com.example.weatherapp.R
import com.example.weatherapp.adapter.FavAdapter
import com.example.weatherapp.databinding.FragmentDashboardBinding
import com.example.weatherapp.databinding.FragmentFavBinding
import com.example.weatherapp.model.Welcome


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavsWeatherDB()
        viewModel.favs.observe(viewLifecycleOwner) {

            val sharedPreference =  requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)

            _binding.daysRecyclerView.adapter= FavAdapter(requireContext(),it,this)
            _binding.daysRecyclerView.apply {
                adapter = _binding.daysRecyclerView.adapter
                layoutManager = LinearLayoutManager(requireContext())
                    .apply { orientation = RecyclerView.VERTICAL }
                setBackgroundResource(sharedPreference.getInt(CONST.Background, R.drawable.gradient))

            }

        }
        _binding.addFab.setOnClickListener{

            val action =FavFragmentDirections.actionNavigationFavToMapsFragment()
            Navigation.findNavController(requireView()).navigate(action)

        }



    }

    @SuppressLint("NotifyDataSetChanged")
    override fun deleteTask(welcome: Welcome, position: Int) {
        viewModel.deleteFavWeatherDB(welcome)
        viewModel.getFavsWeatherDB()
        _binding.daysRecyclerView.adapter?.notifyDataSetChanged()

    }


    override fun selectTask(welcome: Welcome) {

        val action =FavFragmentDirections.actionNavigationFavToNavigationHome(false,welcome)

        Navigation.findNavController(requireView()).navigate(action)
    }


}