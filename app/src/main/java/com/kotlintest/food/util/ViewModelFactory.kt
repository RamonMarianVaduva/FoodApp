package com.kotlintest.food.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlintest.food.viewmodel.RestaurantMenuViewModel
import com.kotlintest.food.viewmodel.RestaurantsViewModel

class ViewModelFactory constructor(): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RestaurantsViewModel::class.java) -> {
                RestaurantsViewModel() as T
            }
            modelClass.isAssignableFrom(RestaurantMenuViewModel::class.java) -> {
                RestaurantMenuViewModel() as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}