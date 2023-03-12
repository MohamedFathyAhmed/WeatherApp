package com.example.android.architecture.blueprints.todoapp.data.source.local


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherapp.model.Current
import com.example.weatherapp.model.WeatherDataBase
import com.example.weatherapp.model.Welcome
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {
      private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private lateinit var database: WeatherDataBase

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    val task = Welcome(lat = 1.0, hourly = emptyList(), current = Current(), daily = emptyList(), alerts = emptyList(), timezone = "egypt", isFav = 0)
    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            WeatherDataBase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertTaskAndGetById() = testScope.runBlockingTest {
        // GIVEN - insert a task

        database.getFavWeatherDao().insertCurrentWeather(task)

        // WHEN - Get the task by id from the database
        database.getFavWeatherDao().getCurrentWeather().collect{
            // THEN - The loaded data contains the expected values
            assertThat(it , notNullValue())
            assertThat(it.timezone, `is`(task.timezone))
        }
    }

    @Test
    fun updateTaskAndGetById() = runBlockingTest {
        // When inserting a task
        val originalTask = task
        database.getFavWeatherDao().insertCurrentWeather(originalTask)

        // When the task is updated
        val updatedTask = Welcome(lat = 1.0, hourly = emptyList(), current = Current(), daily = emptyList(), alerts = emptyList(), timezone = "ismailia", isFav = 0)
        database.getFavWeatherDao().updateCurrentWeather(updatedTask)

        // THEN - The loaded data contains the expected values
        database.getFavWeatherDao().getCurrentWeather().collect{
            assertThat(it.timezone, `is`("ismailia"))
        }

    }
}