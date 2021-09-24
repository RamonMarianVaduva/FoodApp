package com.kotlintest.food.models

data class RestaurantItemModel (
    val id: Int,
    val title: String,
    val subtitle: String,
    val image: String,
    val filters: ArrayList<String>
)