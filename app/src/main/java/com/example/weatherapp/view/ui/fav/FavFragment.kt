package com.example.weatherapp.view.ui.fav

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapter.FavAdapter
import com.example.weatherapp.databinding.FragmentFavBinding
import com.example.weatherapp.view.ui.MapsFragment


class FavFragment : Fragment() {
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

            _binding.daysRecyclerView.adapter= FavAdapter(it){

            }
            _binding.daysRecyclerView.apply {
                adapter = _binding.daysRecyclerView.adapter
                layoutManager = LinearLayoutManager(requireContext())
                    .apply { orientation = RecyclerView.VERTICAL }
            }

        }
        _binding.addFab.setOnClickListener{

            val action =FavFragmentDirections.actionNavigationFavToMapsFragment()
            Navigation.findNavController(requireView()).navigate(action)
//open map
//startActivity(Intent(requireContext(),MapsFragment::class.java))

        }



    }


}