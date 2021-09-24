package com.kotlintest.food.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlintest.food.R
import com.kotlintest.food.activities.DetailsActivity
import com.kotlintest.food.models.RestaurantItemModel
import kotlinx.android.synthetic.main.restaurant_item_row.view.*

class RestaurantsAdapter (
    private val _context: Context,
    private val _restaurantsList : ArrayList<RestaurantItemModel>,
): RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.restaurant_item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = _restaurantsList[position]
        holder.itemView.animation =
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.recycler_anim)
        holder.itemView.setOnClickListener {
            val intent = Intent(_context, DetailsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
            intent.putExtra("restaurantId", restaurant.id.toString())
            _context.applicationContext.startActivity(intent)
        }
        holder.itemView.apply {
            Glide.with(context)
                    .load(restaurant.image)
                    .into(ivRestaurant)
            tvRestaurantTitle.text = restaurant.title
            tvRestaurantSubtitle.text = restaurant.subtitle
        }
    }

    override fun getItemCount(): Int {
        return _restaurantsList.size
    }

    fun switchList(list: ArrayList<RestaurantItemModel>){
        _restaurantsList.clear()
        _restaurantsList.addAll(list)
        notifyDataSetChanged()
    }
}