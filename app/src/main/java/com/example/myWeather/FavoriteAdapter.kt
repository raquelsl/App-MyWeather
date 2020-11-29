package com.example.myWeather

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myWeather.model.CityDataBase
import kotlinx.android.synthetic.main.favorite_item.view.*

class FavoriteAdapter(val list: List<CityDataBase>?) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val cityNametext: TextView = itemView.cityNametext
        private val cityCodetext: TextView = itemView.cityCodetext

        fun bindView(cityDataBase: CityDataBase){
            cityNametext.text = cityDataBase.cityName
            cityCodetext.text = cityDataBase.id.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context)
                                .inflate(R.layout.favorite_item, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        when (holder) {
            is FavoriteAdapter.FavoriteViewHolder -> {
                if(position < (list?.size ?: 0)) {
                    val element = list?.get(position)
                    if (element != null){
                        holder.bindView(element)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class itemDecoration(val size: Int) : RecyclerView.ItemDecoration(){

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0 ){
                    top = size
                }
                right = size
                left = size
                bottom = size
            }
        }
    }

}