
package com.example.android.architecture.blueprints.todoapp.data.source

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry

import com.example.weatherapp.MainCoroutineRule
import com.example.weatherapp.model.Repositary

import com.example.weatherapp.model.Welcome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Unit tests for the implementation of the in-memory repository with cache.
 */
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class DefaultTasksRepositoryTest {

    lateinit var appContext: Context

    private val task1 = Welcome(lat = 1.0, hourly = null, current = null, daily = null, alerts = null)
    private val task2 = Welcome(lat = 2.0, hourly = null, current = null, daily = null, alerts = null)
    private val task3 = Welcome(lat = 3.0, hourly = null, current = null, daily = null, alerts = null)
    private val localTasks = listOf(task1, task2)
    private val remoteTasks = task3
    private val newTasks = listOf(task3)
    private lateinit var tasksRemoteDataSource: FakeDataSource
    private lateinit var tasksLocalDataSource: FakeDataSourceDB

    // Class under test
    private lateinit var tasksRepository: Repositary

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()



    @Before
    fun createRepository() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        tasksRemoteDataSource = FakeDataSource(remoteTasks)
        tasksLocalDataSource = FakeDataSourceDB(localTasks.toMutableList())
        // Get a reference to the class under test
        tasksRepository = Repositary.getInstance(tasksRemoteDataSource, tasksLocalDataSource, appContext)
    }

    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource() = mainCoroutineRule.runBlockingTest {
        // When tasks are requested from the tasks repository
       tasksRepository.getCurrentWeatherApi("","").collect{
           val tasks =it
           // Then tasks are loaded from the remote data source
           assertThat(tasks, IsEqual(remoteTasks))
        }
    }

}

