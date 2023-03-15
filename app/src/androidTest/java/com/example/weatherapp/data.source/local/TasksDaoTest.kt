package com.example.weatherapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherapp.model.Current
import com.example.weatherapp.model.WeatherDAO
import com.example.weatherapp.model.WeatherDataBase
import com.example.weatherapp.model.Welcome
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
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
@SmallTest
class WeatherDAOTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WeatherDataBase
    private lateinit var weatherDAO: WeatherDAO

    @Before
    fun setUp() {

        //initialize database
        db= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDataBase::class.java
        ).allowMainThreadQueries().build()
        weatherDAO= db.getFavWeatherDao()
    }

    @After
    fun tearDown() {
        //close database
        db.close()
    }

        @Test
    fun insertweatherAndGetweather() = runBlockingTest{
        // GIVEN - insert a task
    val task = Welcome(lat = 1.0, hourly = emptyList(), current = Current(), daily = emptyList(), alerts = emptyList(), timezone = "egypt", isFav = 0)

        db.getFavWeatherDao().insertCurrentWeather(task)

        // WHEN - Get the weather by id from the database
        val result = db.getFavWeatherDao().getCurrentWeather().first()
        // THEN - The loaded data contains the expected values

        assertThat(result , notNullValue())
        assertThat(result.timezone, `is`(task.timezone))
    }
    @Test
    fun updateweatherAndGetweather() = runBlockingTest {
        // When inserting a weather
        val weather = Welcome(lat = 1.0, hourly = emptyList(), current = Current(), daily = emptyList(), alerts = emptyList(), timezone = "egypt", isFav = 0)

        val originalTask = weather
        db.getFavWeatherDao().insertCurrentWeather(originalTask)

        // When the weather is updated
        val updatedTask = Welcome(lat = 2.0, hourly = emptyList(), current = Current(), daily = emptyList(), alerts = emptyList(), timezone = "egypt", isFav = 0)
        db.getFavWeatherDao().updateCurrentWeather(updatedTask)

        // THEN - The loaded data contains the expected values
        var res =db.getFavWeatherDao().getCurrentWeather().first()
            assertThat(res.lat, `is`( 2.0))
    }

}