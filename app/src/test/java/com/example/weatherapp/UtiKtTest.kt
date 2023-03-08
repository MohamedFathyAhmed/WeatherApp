package com.example.weatherapp

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.core.Is
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class UtiKtTest {

    lateinit var appContext: Context

    @Before

    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun getIconImage_String_returnIntOfImage(){
        // Given  string and context
        var  timeState ="02n"
        // When
        val result = getIconImage(timeState)
        // Then the result is 2131165363
        assertThat(result, Is.`is`(2131165363))
    }


    @Test
    fun convertToTime_getlong_returnTime() {
        // Given  timemills and language
        val long :Long=12345
        val language = "en"
        // When
        val result = com.example.weatherapp.convertToTime(long,language)
        // Then the result is 5:25 AM
        assertThat(result, Is.`is`("5:25 AM"))
    }

    @Test
    fun convertToTime_getzero_returnTime() {
        // Given  timemills and language
        val long :Long=0
        val language = "en"
        // When
        val result = com.example.weatherapp.convertToTime(long,language)
        // Then the result is 5:25 AM
        assertThat(result, Is.`is`("2:00 AM"))
    }


    @Test
    fun getseconds_yearAndMonthAndDay_returnMills() {
        // Given  year and month and day
        var year = 2020
        var month = 10
        var day =6
        //when
        val result =  getseconds(year,month,day)
        // Then the result is 5:25 AM
        assertThat(result, Is.`is`(result))
    }


    @Test
    fun setBackgroundContainer_String_returnIntOfImage(){
        // Given  string and context
      var  timeState ="02n"
        // When
        val result = setBackgroundContainer(timeState, appContext)
        // Then the result is 2131165306
        assertThat(result, Is.`is`(2131165306))
    }


    @Test
    fun setBackgroundContainer_EmtyString_returnIntOfImage(){
        // Given  string and context
        var  timeState =""
        // When
        val result = setBackgroundContainer(timeState, appContext)
        // Then the result is 2131165308
        assertThat(result, Is.`is`(2131165308))
    }



}


