package com.kotlintest.food.models

data class Model (
        val restaurants: ArrayList<RestaurantItemModel>,
        val filters: ArrayList<FilterItemModel>
)