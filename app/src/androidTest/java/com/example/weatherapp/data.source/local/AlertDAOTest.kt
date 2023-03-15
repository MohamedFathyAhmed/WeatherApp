package com.example.weatherapp.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherapp.model.AlertDAO
import com.example.weatherapp.model.MyAlert
import com.example.weatherapp.model.WeatherDataBase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
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
class AlertDAOTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: WeatherDataBase
    private lateinit var alertDao: AlertDAO

    @Before
    fun setUp() {

        //initialize database
        db= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            WeatherDataBase::class.java
        ).allowMainThreadQueries().build()
        alertDao= db.getAlertDao()
    }

    @After
    fun tearDown() {

        //close database
        db.close()

    }

    @Test
    fun allAlerts() = runBlockingTest{

        val data = MyAlert(12323,13652,1245,13.65,12.21,"")
        val data1 = MyAlert(2323,13652,1245,13.65,12.21,"")
        val data2 = MyAlert(1223,13652,1245,13.65,12.21,"")
        val data3 = MyAlert(1232,13652,1245,13.65,12.21,"")

        alertDao.insertAlert(data)
        alertDao.insertAlert(data1)
        alertDao.insertAlert(data2)
        alertDao.insertAlert(data3)

        //When
        val result = alertDao.getAlerts().first()

        //Then
        MatcherAssert.assertThat(result.size, CoreMatchers.`is`(4))
    }

    @Test
    fun insertFavourite_insertOneItem_returnTheItem() = runBlockingTest {
        //Given
        val data = MyAlert(12323,13652,1245,13.65,12.21,"")

        //When
        alertDao.insertAlert(data)

        //Then
        val result= alertDao.getAlerts().first()
        MatcherAssert.assertThat(result[0], IsNull.notNullValue())

    }

    @Test
    fun insertAlert() {
    }

    @Test
    fun deleteAlert() {
    }
}