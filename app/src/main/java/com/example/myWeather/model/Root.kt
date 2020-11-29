package com.example.myWeather.model

data class Root (
        val message: String,
        val cod:     String,
        val count:   Long,
        val list:    List<Element>
)

data class Element(
        val id:      Long,
        val name:    String,
        val date:    Long,
        val weather: List<Weather>
)

data class Weather(
        val id:     Long,
        val main:   String,
        val description: String,
        val icon:   String
)
