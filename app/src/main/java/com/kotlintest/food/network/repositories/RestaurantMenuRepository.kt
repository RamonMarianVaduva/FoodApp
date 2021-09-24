package com.kotlintest.food.network.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.kotlintest.food.network.interfaces.ApiInterface
import com.kotlintest.food.network.RetrofitInstance
import com.kotlintest.food.models.SelectedRestaurantModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestaurantMenuRepository {

    val restaurantMenu = MutableLiveData<SelectedRestaurantModel>()

    fun getServicesApiCall(id: String): MutableLiveData<SelectedRestaurantModel> {

        //init api
        val retrofit = RetrofitInstance.instance
        val jsonApi = retrofit.create(ApiInterface::class.java).getRestaurantMenu(id)

        jsonApi.enqueue(object: Callback<SelectedRestaurantModel> {
            override fun onFailure(call: Call<SelectedRestaurantModel>, t: Throwable) {
                Log.v("MENU ERROR : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<SelectedRestaurantModel>,
                response: Response<SelectedRestaurantModel>
            ) {
                Log.v("MENU SUCCESS : ", "Fetching Data")
                val data = response.body()
                restaurantMenu.value = data
            }
        })

        return restaurantMenu
    }
}