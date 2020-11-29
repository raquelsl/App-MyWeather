package com.example.myWeather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myWeather.FavoriteAdapter
import com.example.myWeather.R
import com.example.myWeather.dataBase.WeatherDataBase
import kotlinx.android.synthetic.main.favorite_item.*
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_search.*

class FavoritesFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val favoritesContainer =
            inflater.inflate(R.layout.fragment_favorites, container, false)

        return favoritesContainer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFavorites()
    }

    private fun loadFavorites() {

        val db = context?.let { WeatherDataBase.getInstance(it) }

        val list = db?.cityDataBaseDao()?.getAllCityDataBase()

        rvFavorite.adapter = FavoriteAdapter(list)
        rvFavorite.layoutManager = LinearLayoutManager(context)
        rvFavorite.addItemDecoration(FavoriteAdapter.itemDecoration(25))
    }
}