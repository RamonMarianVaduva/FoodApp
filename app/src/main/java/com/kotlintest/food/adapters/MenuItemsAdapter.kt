package com.kotlintest.food.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlintest.food.R
import com.kotlintest.food.models.MenuItemModel
import kotlinx.android.synthetic.main.menu_item_row.view.*

class MenuItemsAdapter(
    private val _context: Context,
    private val _menuItems: ArrayList<MenuItemModel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(_menuItems[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(_context)
        return ViewHolder(layoutInflater.inflate(R.layout.menu_item_row, parent, false))
    }

    override fun getItemCount(): Int {
        return _menuItems.size
    }

    lateinit var mClickListener: ClickListener

    fun setOnClickListener(aClickListener: ClickListener){
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(pos: Int, view: View)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(model: MenuItemModel){
            itemView.animation =
                    AnimationUtils.loadAnimation(itemView.context, R.anim.menu_recycler_anim)
            itemView.tvMenuTitle.text = model.title
            val priceString = "$ " + model.price.toString()
            itemView.tvMenuPrice.text = priceString
            Glide.with(itemView.context)
                .load(model.image)
                .into(itemView.ivMenuItem)
        }

        override fun onClick(v: View?) {
            mClickListener.onClick(adapterPosition, itemView)
        }
    }

    fun switchList(list: ArrayList<MenuItemModel>){
        _menuItems.clear()
        _menuItems.addAll(list)
        notifyDataSetChanged()
    }
}