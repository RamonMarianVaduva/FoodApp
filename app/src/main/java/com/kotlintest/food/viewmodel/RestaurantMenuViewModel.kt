package com.kotlintest.food.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kotlintest.food.models.MenuItemModel
import com.kotlintest.food.models.SelectedRestaurantModel
import com.kotlintest.food.network.repositories.RestaurantMenuRepository

class RestaurantMenuViewModel: ViewModel() {

    private var _restaurantMenuLiveData: MutableLiveData<SelectedRestaurantModel>? = null
    private var _menuItemsList: ArrayList<MenuItemModel> = ArrayList()


    fun getRestaurantMenu(id: String) : LiveData<SelectedRestaurantModel>? {
        _restaurantMenuLiveData = RestaurantMenuRepository.getServicesApiCall(id)
        return _restaurantMenuLiveData
    }

    fun displayMenuItems(data: SelectedRestaurantModel){
        _menuItemsList.clear()
        _menuItemsList.addAll(data.menu)
    }

    fun getMenuItemsList(): ArrayList<MenuItemModel>{
        return _menuItemsList
    }

    fun sortMenuList(asc: Boolean){
        if(asc){
            _menuItemsList.sortBy { it.price }
        } else {
            _menuItemsList.sortByDescending { it.price }
        }
        Log.e("err", "sort")
    }
}