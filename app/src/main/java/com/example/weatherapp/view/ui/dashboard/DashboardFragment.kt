
    package com.example.weatherapp.view.ui.dashboard

    import android.content.Context
    import android.content.Intent
    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.RadioButton
    import androidx.fragment.app.Fragment
    import androidx.lifecycle.ViewModelProvider
    import com.example.weatherapp.CONST
    import com.example.weatherapp.R
    import com.example.weatherapp.databinding.FragmentDashboardBinding
    import com.example.weatherapp.view.HomeActivity
    import com.google.android.material.bottomnavigation.BottomNavigationView




    class DashboardFragment : Fragment() {
        lateinit var navBar: BottomNavigationView
        private lateinit var _binding: FragmentDashboardBinding
        private lateinit var languageRadioButton: RadioButton
        private lateinit var locationRadioButton: RadioButton
        private lateinit var tempRadioButton: RadioButton

       fun initUI (){

        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            val SettingFragmentViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
            _binding = FragmentDashboardBinding.inflate(inflater, container, false)
            initUI()
            return _binding.root
        }


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            //hideen bar
            navBar = requireActivity().findViewById(R.id.nav_view)
            navBar.setVisibility(View.GONE);

             val sharedPreference =  requireActivity().getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)

            _binding.closeButton.setOnClickListener{
                startActivity(Intent(requireContext(), HomeActivity::class.java))
            }



            _binding.languageRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                languageRadioButton  = view.findViewById<View>(checkedId) as RadioButton
                when(languageRadioButton.text){
                    getString(R.string.arabic) -> {
                         sharedPreference.edit().putString(CONST.lang,CONST.Enum_language.ar.toString()).commit()
                    }
                    getString(R.string.english)->{
                              sharedPreference.edit().putString(CONST.lang,CONST.Enum_language.en.toString()).commit()
                    }
                }

            }



            _binding.locationRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                locationRadioButton = view.findViewById<View>(checkedId) as RadioButton

                when(locationRadioButton.text.toString()){

                    getString(R.string.map) -> {
                               sharedPreference.edit().putString(CONST.LOCATION,CONST.Enum_LOCATION.map.toString()).commit()


                    }
                    getString(R.string.gps)->{

                             sharedPreference.edit().putString(CONST.LOCATION,CONST.Enum_LOCATION.gps.toString()).commit()

                    }
                }
            }

            _binding.tempRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                tempRadioButton = view.findViewById<View>(checkedId) as RadioButton
                when (tempRadioButton.text.toString()) {
                    getString(R.string.celsius) -> {
                        sharedPreference.edit().putString(CONST.units,CONST.Enum_units.metric.toString()).commit()
                    }
                    getString(R.string.fehrenheit) -> {
                        sharedPreference.edit().putString(CONST.units,CONST.Enum_units.imperial .toString()).commit()
                    }
                    getString(R.string.kelvin) -> {
                        sharedPreference.edit().putString(CONST.units,CONST.Enum_units.standard.toString()).commit()
                    }
                }
            }
        }
    }