package com.example.weatherapp.data.source

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weatherapp.model.*
import com.example.weatherapp.view.ui.fav.FavDataViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavouriteViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private var favList: MutableList<Welcome> = mutableListOf<Welcome>(
        Welcome(lat = 1.0, hourly = emptyList(), current = Current(), daily = emptyList(), alerts = emptyList(), timezone = "egypt", isFav = 0)
        ,    Welcome(lat = 2.0, hourly = emptyList(), current = Current(), daily = emptyList(), alerts = emptyList(), timezone = "ismailia", isFav = 0)
        , Welcome(lat = 3.0, hourly = emptyList(), current = Current(), daily = emptyList(), alerts = emptyList(), timezone = "alex", isFav = 0),
        Welcome(lat = 4.0, hourly = emptyList(), current = Current(), daily = emptyList(), alerts = emptyList(), timezone = "masr", isFav = 0))

//    private var alertList: MutableList<MyAlert> = mutableListOf<MyAlert>(
//        MyAlert(12323,13652,1245,13.65,12.21,"")
//        ,MyAlert(2323,13652,1245,13.65,12.21,"")
//        ,MyAlert(1223,13652,1245,13.65,12.21,"")
//        ,MyAlert(1232,13652,1245,13.65,12.21,"")
//    )



    private val task3 = Welcome(lat = 3.0, hourly = null, current = null, daily = null, alerts = null)
    private val localTasks = favList
    private val remoteTasks = task3

    lateinit var appContext: Context
    private lateinit var tasksRemoteDataSource: FakeDataSource
    private lateinit var tasksLocalDataSource: FakeDataSourceDB

    // Class under test
    private lateinit var Repository: Repositary

    private lateinit var favouriteViewModel: FavDataViewModel


    @Before
    fun setUp() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        tasksRemoteDataSource = FakeDataSource(remoteTasks)
        tasksLocalDataSource = FakeDataSourceDB(localTasks)
        // Get a reference to the class under test
        Repository = Repositary.getInstance(tasksRemoteDataSource, tasksLocalDataSource, appContext)
        favouriteViewModel = FavDataViewModel(Repository)


    }

    @After
    fun tearDown() {
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getFavWeather_getItems_sameFakeData() = runBlockingTest {
        //When
        favouriteViewModel.getFavsWeatherDB()
        var data:List<Welcome> = emptyList()
        val result = favouriteViewModel.flowData.first()

        when (result) {
            is ApiStateList.Loading -> {

            }
            is ApiStateList.Success -> {

                data = result.data
            }
            is ApiStateList.Failure -> {

            }
        }
        //Then
        MatcherAssert.assertThat(data.size,`is`(4) )
    }

    @Test
    fun insertFav_item_checkSize() = runTest {
        //When
        favouriteViewModel.insertFavWeatherDB( Welcome(lat = 5.0, hourly = emptyList(), current = Current(), daily = emptyList(), alerts = emptyList(), timezone = "alex", isFav = 0))
        var data:List<Welcome> = emptyList()
        favouriteViewModel.getFavsWeatherDB()
        val result = favouriteViewModel.flowData.first()
        when (result) {
            is ApiStateList.Loading -> {

            }
            is ApiStateList.Success -> {

                data = result.data
            }
            is ApiStateList.Failure -> {

            }
        }

        //Then
        MatcherAssert.assertThat(data.size, `is`(5))
    }

    @Test
    fun deleteFav_deleteItem_checkSize() = runBlockingTest{
        //When
        favouriteViewModel.deleteFavWeatherDB(favList[0])
        var data:List<Welcome> = emptyList()
        favouriteViewModel.getFavsWeatherDB()
        val result = favouriteViewModel.flowData.first()
        val msg:String
        when (result) {
            is ApiStateList.Loading -> {

            }
            is ApiStateList.Success -> {

                data = result.data
            }
            is ApiStateList.Failure -> {
                msg= result.msg.toString()
            }
        }

        //Then
        assertThat(data.size, `is`(3))
    }
}