package com.tech.test.domain

import com.fasterxml.jackson.annotation.JsonAlias

data class Main(
    val temp: Double,
    @JsonAlias("temp_min")
    val tempMin: Double,
    @JsonAlias("temp_max")
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
