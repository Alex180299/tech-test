package com.tech.test.infrastructure.client.impl

import com.tech.test.domain.CityResponse
import com.tech.test.domain.Main
import com.tech.test.infrastructure.client.IWeatherClient
import org.springframework.web.client.RestTemplate

class WeatherClient(private val restTemplate: RestTemplate, private val apiKey: String) : IWeatherClient {
    override fun getTemperatureByCityName(cityName: String): CityResponse {
        return restTemplate.getForObject(
            "https://api.openweathermap.org/data/2.5/weather?q=$cityName&units=metric&appid=$apiKey",
            CityResponse::class.java
        ) ?: CityResponse(
            main = Main(
                temp = 0.0,
                tempMin = 0.0,
                tempMax = 0.0,
            )
        )
    }
}