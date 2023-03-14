//package com.example.weatherapp.data.source
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.weatherapp.model.API
//import com.example.weatherapp.model.SimpleService
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.runBlocking
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//class ApiTest {
//
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//
//    lateinit var apiObj :SimpleService
//
//
//    @Before
//    fun setUp() {
//        apiObj = API.retrofitService
//    }
//
//    @After
//    fun tearDown() {
//    }
//
//    @Test
//    fun getRoot_requestKey_Authorized() = runBlocking{
//        //Given
//        val longitude = 30.6210725
//        val latitude = 32.2687095
//
//
//        //When
//        val response= apiObj.getRoot(
//            latitude
//            , longitude
//            , apiKey
//            , unit
//            , language
//        )
//        //Then
//        MatcherAssert.assertThat(response.code(),is (200))
//        MatcherAssert.assertThat(response.body(), notNullValue())
//
//
//    }
//
//    fun getRoot_requestNoKey_unAuthorized() = runBlocking{
//        //Given
//        val longitude = 30.6210725
//        val latitude = 32.2687095
//        val language="en"
//        val unit = "metric"
//        val apiKey = ""
//
//        //When
//        val response= apiObj.getRoot(
//            latitude
//            , longitude
//            , apiKey
//            , unit
//            , language
//        )
//
//        //Then
//        MatcherAssert.assertThat(response.code(),is (401))
//        MatcherAssert.assertThat(response.body(), IsNull.nullValue())
//
//
//    }
//}