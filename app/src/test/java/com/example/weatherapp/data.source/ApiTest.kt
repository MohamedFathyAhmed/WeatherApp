package com.example.weatherapp.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.CONST
import com.example.weatherapp.model.API
import com.example.weatherapp.model.SimpleService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ApiTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var apiObj :SimpleService


    @Before
    fun setUp() {
        apiObj = API.retrofitService
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getRoot_requestKey_Authorized() = runBlocking{
        //Given
        val longitude = 30.6210725
        val latitude = 32.2687095


        //When
        val response= apiObj.getCurrentWeather(
            latitude.toString()
            , longitude.toString()
            , CONST.appid
            , CONST.Enum_units.metric.toString()
            ,  CONST.Enum_language.en.toString()
        )
        //Then
        MatcherAssert.assertThat(response.timezone, `is`("Etc/GMT-2"))
        MatcherAssert.assertThat(response.lat, `is`(32.2687))



    }



    @Test
    fun getRoot_requestNoKey_unAuthorized() = runBlocking{
        //Given
        val longitude = 30.6210725
        val latitude = 32.2687095


        //When
        val response= apiObj.getCurrentWeather(
            latitude.toString()
            , longitude.toString()
            , CONST.Enum_units.metric.toString()
            ,  CONST.Enum_language.en.toString()
            , appId = ""
        )
        //Then
        MatcherAssert.assertThat(response, IsNull.nullValue())

    }
}