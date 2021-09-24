package com.kotlintest.food.network.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kotlintest.food.network.interfaces.ApiInterface
import com.kotlintest.food.network.RetrofitInstance
import com.kotlintest.food.models.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestaurantsRepository {

    val restaurants = MutableLiveData<Model>()

    fun getServicesApiCall(): MutableLiveData<Model> {

        //init api
        val retrofit = RetrofitInstance.instance
        val jsonApi = retrofit.create(ApiInterface::class.java).getRestaurants()

        jsonApi.enqueue(object: Callback<Model> {
            override fun onFailure(call: Call<Model>, t: Throwable) {
                Log.v("RESTAURANTS ERROR : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<Model>,
                response: Response<Model>
            ) {
                Log.v("RESTAURANTS SUCCESS : ", "Fetching Data")
                val data = response.body()
                restaurants.value = data
            }
        })

        return restaurants
    }
}