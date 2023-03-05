package com.example.weatherapp.view.ui.notifications

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eram.weather.adapter.AlertAdapter
import com.example.weatherapp.databinding.FragmentNotificationsBinding
import com.example.weatherapp.model.ApiStateAlert
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.CONST
import com.example.weatherapp.R
import com.example.weatherapp.view.HomeActivity
import com.example.weatherapp.view.ui.home.HomeFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class NotificationsFragment : Fragment() {
    lateinit var viewModel: NotificationsViewModel
    private lateinit var _binding: FragmentNotificationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, NotificationsViewModelFactory(requireContext())).get(
            NotificationsViewModel::class.java)
        checkOverlayPermission()
        return _binding.root
    }

    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(requireContext())) {
                val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
                alertDialogBuilder.setTitle(getString(R.string.weatherAlerts))
                    .setMessage(getString(R.string.features))
                    .setPositiveButton(getString(R.string.ok)) { dialog: DialogInterface, i: Int ->
                        var myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                        startActivity(myIntent)
                        dialog.dismiss()
                    }.setNegativeButton(
                        getString(R.string.cancel)
                    ) { dialog: DialogInterface, i: Int ->
                        dialog.dismiss()
                        startActivity(Intent(requireContext(),HomeActivity::class.java))
                    }.show()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAlertsDB()
        lifecycleScope.launch() {
            viewModel.flowData.collectLatest { result ->
                when (result) {
                    is ApiStateAlert.Loading -> {
                  }

                    is ApiStateAlert.Success -> {
                        val sharedPreference = requireActivity().getSharedPreferences(
                            "getSharedPreferences",
                            Context.MODE_PRIVATE
                        )

                        _binding.daysRecyclerView.adapter =
                            AlertAdapter(result.data, requireContext()) {

                                viewModel.deleteAlertDB(it)
                                Toast.makeText(
                                    requireContext(),
                                    getString(R.string.succ_deleted),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        _binding.daysRecyclerView.apply {
                            adapter = _binding.daysRecyclerView.adapter
                            layoutManager = LinearLayoutManager(requireContext())
                                .apply { orientation = RecyclerView.VERTICAL }
                            setBackgroundResource(
                                sharedPreference.getInt(
                                    CONST.Background,
                                    R.drawable.gradient
                                )
                            )

                        }
                    }
                    is ApiStateAlert.Failure -> {
                        Toast.makeText(requireContext(), "Check ${result.msg}", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

            }
        }

        _binding.addFab.setOnClickListener {
            checkOverlayPermission()
            SelectTimeAlert().show(
                requireActivity().supportFragmentManager,
                "Frag")
        }
  }







}
