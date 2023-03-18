package com.example.weatherapp.view.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.RadioButton
import android.widget.Toast
import com.example.weatherapp.CONST

import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.isConnected
import com.example.weatherapp.view.HomeActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var locationRadioButton: RadioButton
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val sharedPreference = getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
        if (sharedPreference.getBoolean("firstTimeMain",true)) {
            sharedPreference.edit().putString(CONST.lang,Locale.getDefault().language).commit()
            sharedPreference.getString(CONST.lang,"en")?.let { setLan(it) }
            sharedPreference.edit().putBoolean("firstTimeMain",false).commit()
            binding.locationRadioGroup.setOnCheckedChangeListener { group, checkedId ->
                locationRadioButton = findViewById<View>(checkedId) as RadioButton
                Toast.makeText(this, locationRadioButton.text, Toast.LENGTH_SHORT).show()

                //connect to network
if(isConnected(this)) {
    when (locationRadioButton.text.toString()) {
        getString(R.string.map) -> {

            val sharedPreference =
                getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
            sharedPreference.edit().putString(
                CONST.LOCATION,
                CONST.Enum_LOCATION.map.toString()
            ).commit()

            binding.cardView.visibility = GONE
            binding.fragmentContainerMap.visibility = VISIBLE
        }
        getString(R.string.gps) -> {
            val sharedPreference =
                getSharedPreferences("getSharedPreferences", Context.MODE_PRIVATE)
            sharedPreference.edit().putString(CONST.LOCATION, CONST.Enum_LOCATION.gps.toString())
                .commit()
            sharedPreference.edit().putBoolean("test", false).commit()
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}else{
    //todo
    Toast.makeText(this,R.string.you_are_offline,Toast.LENGTH_SHORT).show()
}
            }
        }else{
            sharedPreference.getString(CONST.lang,Locale.getDefault().language)?.let { setLan(it) }
            startActivity(Intent(this, HomeActivity::class.java))
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

}







