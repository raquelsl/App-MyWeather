package com.example.myWeather.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityDataBase (
    @PrimaryKey val id: Long,
    val cityName: String
)