package com.example.weatherapp.view.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.weatherapp.CONST
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSettingBinding

import com.example.weatherapp.view.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*


class SettingFragment : Fragment() {
    lateinit var navBar: BottomNavigationView
    private lateinit var _binding: FragmentSettingBinding
    private lateinit var languageRadioButton: RadioButton
    private lateinit var locationRadioButton: RadioButton
    private lateinit var tempRadioButton: RadioButton
    private lateinit var alertRadioButton: RadioButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val sharedPreference =
            requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        _binding.ConstraintLayoutBackground.setBackgroundResource(
            sharedPreference.getInt(
                CONST.Background,
                R.drawable.gradient
            )
        )
        initUI()
        return _binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        navBar.visibility = View.VISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //hideen bar
        initUI()
        navBar = requireActivity().findViewById(R.id.nav_view)
        navBar.visibility = View.GONE

        val sharedPreference =
            requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)

        _binding.closeButton.setOnClickListener {
            startActivity(Intent(requireContext(), HomeActivity::class.java))
        }


        //todo///////////////////////////////
        _binding.alertRadioGroup
            .setOnCheckedChangeListener { group, checkedId ->
                alertRadioButton = view.findViewById<View>(checkedId) as RadioButton
                when (alertRadioButton.text) {
                    getString(R.string.notification) -> {
                        sharedPreference.edit()
                            .putString(CONST.alert, CONST.Enum_alert.notification.toString())
                            .commit()
                    }
                    getString(R.string.alarm) -> {
                        sharedPreference.edit()
                            .putString(CONST.alert, CONST.Enum_alert.alarm.toString()).commit()
                    }
                }

            }

        _binding.languageRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            languageRadioButton = view.findViewById<View>(checkedId) as RadioButton
            when (languageRadioButton.text) {
                getString(R.string.arabic) -> {
                    sharedPreference.edit().putString(CONST.lang, CONST.Enum_language.ar.toString())
                        .commit()
                    setLan("ar")

                }
                getString(R.string.english) -> {
                    sharedPreference.edit().putString(CONST.lang, CONST.Enum_language.en.toString())
                        .commit()
                    setLan("en")
                }
            }

        }



        _binding.locationRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            locationRadioButton = view.findViewById<View>(checkedId) as RadioButton

            when (locationRadioButton.text.toString()) {

                getString(R.string.map) -> {
                    sharedPreference.edit()
                        .putString(CONST.LOCATION, CONST.Enum_LOCATION.map.toString()).commit()
                    val action =
                        SettingFragmentDirections.actionNavigationDashboardToMapsFragment("home")
                    Navigation.findNavController(requireView()).navigate(action)

                }
                getString(R.string.gps) -> {

                    sharedPreference.edit()
                        .putString(CONST.LOCATION, CONST.Enum_LOCATION.gps.toString()).commit()

                }
            }
        }

        _binding.tempRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            tempRadioButton = view.findViewById<View>(checkedId) as RadioButton
            when (tempRadioButton.text.toString()) {
                getString(R.string.celsius) -> {
                    sharedPreference.edit()
                        .putString(CONST.units, CONST.Enum_units.metric.toString()).commit()
                }
                getString(R.string.fehrenheit) -> {
                    sharedPreference.edit()
                        .putString(CONST.units, CONST.Enum_units.imperial.toString()).commit()
                }
                getString(R.string.kelvin) -> {
                    sharedPreference.edit()
                        .putString(CONST.units, CONST.Enum_units.standard.toString()).commit()
                }
            }
        }
    }

    private fun setLan(language: String) {
        val metric = resources.displayMetrics
        val configuration = resources.configuration
        configuration.locale = Locale(language)
        Locale.setDefault(Locale(language))

        configuration.setLayoutDirection(Locale(language))
        // update configuration
        resources.updateConfiguration(configuration, metric)
        // notify configuration
        onConfigurationChanged(configuration)

    }

    fun initUI() {
        val sharedPreference =
            requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
        var lang = sharedPreference.getString(CONST.lang, CONST.Enum_language.en.toString())
        var LOCATION =
            sharedPreference.getString(CONST.LOCATION, CONST.Enum_LOCATION.gps.toString())
        var alert =
            sharedPreference.getString(CONST.alert, CONST.Enum_alert.notification.toString())
        var units = sharedPreference.getString(CONST.units, CONST.Enum_units.metric.toString())
        if (lang == CONST.Enum_language.en.toString()) {
            _binding.languageRadioGroup.check(_binding.englishRadioButton.id)
        } else {
            _binding.languageRadioGroup.check(_binding.arabicRadioButton.id)
        }

        if (LOCATION == CONST.Enum_LOCATION.gps.toString()) {
            _binding.locationRadioGroup.check(_binding.gpsRadioButton.id)
        } else {
            _binding.locationRadioGroup.check(_binding.mapRadioButton.id)
        }

        if (alert == CONST.Enum_alert.notification.toString()) {
            _binding.alertRadioGroup.check(_binding.notificationRadioButton.id)
        } else {
            _binding.languageRadioGroup.check(_binding.alertRadioButton.id)
        }

        if (units == CONST.Enum_units.metric.toString()) {
            _binding.tempRadioGroup.check(_binding.celsiusRadioButton.id)
        } else if (units == CONST.Enum_units.standard.toString()) {
            _binding.tempRadioGroup.check(_binding.kelvinRadioButton.id)
        } else {
            _binding.tempRadioGroup.check(_binding.fehrenheitRadioButton.id)
        }


    }
}