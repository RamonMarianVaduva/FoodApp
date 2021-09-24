package com.kotlintest.food.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlintest.food.models.FilterItemModel
import com.kotlintest.food.models.Model
import com.kotlintest.food.models.RestaurantItemModel
import com.kotlintest.food.network.repositories.RestaurantsRepository

class RestaurantsViewModel: ViewModel() {

    private var _restaurantsLiveData: MutableLiveData<Model>? = null
    private var _filtersList: ArrayList<FilterItemModel> = ArrayList()
    private var _restaurantsList: ArrayList<RestaurantItemModel> = ArrayList()
    private var _sortedList: ArrayList<RestaurantItemModel> = ArrayList()

    fun getRestaurants() : LiveData<Model>? {
        _restaurantsLiveData = RestaurantsRepository.getServicesApiCall()
        return _restaurantsLiveData
    }

    fun addData(data: Model){
        _filtersList.addAll(data.filters)
        _restaurantsList.addAll(data.restaurants)
    }

    fun sortList(selectedOption: String){
        _sortedList.clear()
        for(element in _restaurantsList){
            for(el in element.filters) {
                if (el.contains(selectedOption)) {
                    _sortedList.addAll(listOf(element))
                }
            }
        }
    }

    fun getSortedList(): ArrayList<RestaurantItemModel> {
        return _sortedList
    }

    fun getAllRestaurants(): ArrayList<RestaurantItemModel> {
        return _restaurantsList
    }
}