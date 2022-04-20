package com.tech.test.domain

data class Main(
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double
)

data class CityResponse(
    val main: Main
)

data class City(
    val name: String = "",
    var temperature: Double = 0.0,
    var temperatureMin: Double = 0.0,
    var temperatureMax: Double = 0.0
)
