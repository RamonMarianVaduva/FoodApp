package com.kotlintest.food.network.interfaces

import com.kotlintest.food.models.Model
import com.kotlintest.food.models.SelectedRestaurantModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
//
//    @get:GET("restaurants.json")
//    val restaurants: Observable<Model>

    @GET("restaurants.json")
    fun getRestaurants(): Call<Model>

    @GET("{id}/restaurant.json")
    fun getRestaurantMenu(@Path("id") id: String): Call<SelectedRestaurantModel>
//    fun menuItem(@Path("id") id: String): Observable<SelectedRestaurantModel>
}