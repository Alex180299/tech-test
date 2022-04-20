package com.tech.test.infrastructure.client

import com.tech.test.domain.CityResponse

interface IWeatherClient {
    fun getTemperatureByCityName(cityName: String): CityResponse
}