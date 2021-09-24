package com.kotlintest.food.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlintest.food.R
import com.kotlintest.food.adapters.FiltersAdapter
import com.kotlintest.food.adapters.RestaurantsAdapter
import com.kotlintest.food.helpers.CustomProgressDialog
import com.kotlintest.food.models.FilterItemModel
import com.kotlintest.food.models.Model
import com.kotlintest.food.util.ViewModelFactory
import com.kotlintest.food.viewmodel.RestaurantsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var _filtersAdapter: FiltersAdapter
    private lateinit var _restaurantsAdapter: RestaurantsAdapter
    private val _progressDialog = CustomProgressDialog()
    lateinit var restaurantsViewModel: RestaurantsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        restaurantsViewModel = ViewModelProvider(this, ViewModelFactory()).get(RestaurantsViewModel::class.java)

        //create filter adapter
        _filtersAdapter = FiltersAdapter(this, ArrayList())
        rvFiltersList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false)
        rvFiltersList.adapter = _filtersAdapter
        _filtersAdapter.setOnClickListener(object: FiltersAdapter.ClickListener{
            override fun onClick(pos: Int, model: FilterItemModel, view: View) {
                if (_filtersAdapter.index != pos) {
                    _filtersAdapter.index = pos
                    val restaurantName = "${model.label} Restaurant"
                    tvRestaurantType.text = restaurantName
                    restaurantsViewModel.sortList(model.id)
                    _restaurantsAdapter.switchList(restaurantsViewModel.getSortedList())
                } else {
                    tvRestaurantType.text = resources.getText(R.string.all_restaurants_text)
                    _filtersAdapter.index = -1
                    fetchRestaurants()
                }
                _filtersAdapter.notifyDataSetChanged()

            }
        })

        //create restaurant adapter
        _restaurantsAdapter = RestaurantsAdapter(this, ArrayList())
        rvRestaurantsList.layoutManager = LinearLayoutManager(this)
        rvRestaurantsList.adapter = _restaurantsAdapter
        fetchFiltersAndRestaurants()

        refreshApp()
    }

    private fun refreshApp(){
        srLayout.setOnRefreshListener {
            tvRestaurantType.text = resources.getText(R.string.all_restaurants_text)
            _filtersAdapter.index = -1
            _filtersAdapter.notifyDataSetChanged()
            fetchRestaurants()
            srLayout.isRefreshing = false
        }
    }

    //get data from server
    private fun fetchFiltersAndRestaurants(){
        _progressDialog.show(this, resources.getText(R.string.loading_message_text))
        restaurantsViewModel.getRestaurants()!!.observe(this, androidx.lifecycle.Observer {
            displayFiltersAndRestaurants(it)
        })
    }

    //show data
    private fun displayFiltersAndRestaurants(data: Model){
        _progressDialog.dialog.dismiss()
        restaurantsViewModel.addData(data)
        _filtersAdapter.switchList(data.filters)
        _restaurantsAdapter.switchList(data.restaurants)
    }

    //get all restaurants list from view model
    fun fetchRestaurants(){
        _restaurantsAdapter.switchList(restaurantsViewModel.getAllRestaurants())
    }

}