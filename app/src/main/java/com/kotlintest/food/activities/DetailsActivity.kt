package com.kotlintest.food.activities

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlintest.food.R
import com.kotlintest.food.adapters.MenuItemsAdapter
import com.kotlintest.food.models.SelectedRestaurantModel
import com.kotlintest.food.util.ViewModelFactory
import com.kotlintest.food.viewmodel.RestaurantMenuViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.details_activity.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var _adapter: MenuItemsAdapter
    private lateinit var _sortItemsArrayAdapter: ArrayAdapter<String>
    private var _restaurantId: String? = null
    private var _recyclerView: RecyclerView? = null
    private var _sortItems: MutableList<String> = arrayListOf()

    lateinit var restaurantMenuViewModel: RestaurantMenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)

        val bundle :Bundle ?= intent.extras
        if (bundle != null){
            _restaurantId = bundle.getString("restaurantId") // 1
        }

        restaurantMenuViewModel = ViewModelProvider(this, ViewModelFactory()).get(RestaurantMenuViewModel::class.java)

        //create menu adapter
        _adapter = MenuItemsAdapter(this@DetailsActivity, ArrayList())
        _recyclerView = findViewById(R.id.rvMenuItems)
        _recyclerView?.layoutManager = GridLayoutManager(this@DetailsActivity, 2)
        _recyclerView?.adapter = _adapter

        _adapter.setOnClickListener(object : MenuItemsAdapter.ClickListener {
            override fun onClick(pos: Int, view: View) {
                Toast.makeText(this@DetailsActivity, restaurantMenuViewModel.getMenuItemsList()[pos].title, Toast.LENGTH_SHORT).show()
            }
        })

        fetchMenuItems(_restaurantId!!)

        //create sort dropdown
        _sortItems = resources.getStringArray(R.array.sortItems).toMutableList()
        _sortItemsArrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, _sortItems)
        atSortMenuItems.setAdapter(_sortItemsArrayAdapter)
        atSortMenuItems.setOnItemClickListener { parent, _, position, _ ->
            val select = parent.getItemAtPosition(position)
            when{
                select.equals("Ascending") -> {
                    restaurantMenuViewModel.sortMenuList(true)
                    _adapter.switchList(restaurantMenuViewModel.getMenuItemsList())
                }
                select.equals("Descending") -> {
                    restaurantMenuViewModel.sortMenuList(false)
                    _adapter.switchList(restaurantMenuViewModel.getMenuItemsList())
                }
            }
        }
        refreshApp()
    }

    private fun refreshApp(){
        srMenuLayout.setOnRefreshListener {
            atSortMenuItems.setText(resources.getText(R.string.choose_text), false)
            fetchMenuItems(_restaurantId!!)
            srMenuLayout.isRefreshing = false
        }
    }

    //get menus list from server by restaurant id
    private fun fetchMenuItems(restaurantId: String){
        restaurantMenuViewModel.getRestaurantMenu(restaurantId)!!.observe(this, androidx.lifecycle.Observer {
            displayMenuItems(it)
        })
    }

    //show menus list
    private fun displayMenuItems(data: SelectedRestaurantModel){
        tvMenuItemTitle.text = data.title
        restaurantMenuViewModel.displayMenuItems(data)
        _adapter.switchList(data.menu)
    }

}