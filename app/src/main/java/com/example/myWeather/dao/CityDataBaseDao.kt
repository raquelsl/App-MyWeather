package com.example.myWeather.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myWeather.model.CityDataBase

@Dao
interface CityDataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(cityDataBase: CityDataBase)

    @Query("SELECT * FROM  CityDataBase")
    fun getAllCityDataBase() : List<CityDataBase>
}