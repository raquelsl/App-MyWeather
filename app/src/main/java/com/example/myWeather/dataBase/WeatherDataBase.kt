package com.example.myWeather.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myWeather.dao.CityDataBaseDao
import com.example.myWeather.model.CityDataBase

@Database(entities = arrayOf(CityDataBase::class), version = 1)
abstract class WeatherDataBase: RoomDatabase() {

    abstract fun cityDataBaseDao(): CityDataBaseDao

    companion object{
        private var INSTANCE: WeatherDataBase? = null

        fun getInstance(context: Context): WeatherDataBase? {

            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                            WeatherDataBase::class.java, "MyWeather.db")
                            .allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }
}