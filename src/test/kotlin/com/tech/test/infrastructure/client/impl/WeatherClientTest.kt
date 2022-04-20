package com.tech.test.infrastructure.client.impl

import com.tech.test.domain.CityResponse
import com.tech.test.domain.Main
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.web.client.RestTemplate

internal class WeatherClientTest {
    @Test
    fun whenGetTemperatureFromMexico_ThenGetResponseFromWeatherAPI_ReturnCityResponse() {
        val restTemplateMock: RestTemplate = Mockito.mock(RestTemplate::class.java)
        Mockito.`when`(
            restTemplateMock
                .getForObject(
                    "https://api.openweathermap.org/data/2.5/weather?q=Mexico&units=metric&appid=apiKey",
                    CityResponse::class.java
                )
        )
            .thenReturn(
                CityResponse(
                    main = Main(
                        temp = 26.84,
                        tempMin = 24.12,
                        tempMax = 30.71,
                    )
                )
            )

        val weatherClient = WeatherClient(restTemplateMock, "apiKey")
        val cityResponse = weatherClient.getTemperatureByCityName("Mexico")

        Assertions.assertEquals(26.84, cityResponse.main.temp)
        Assertions.assertEquals(24.12, cityResponse.main.tempMin)
        Assertions.assertEquals(30.71, cityResponse.main.tempMax)
    }

    @Test
    fun whenGetTemperatureFromColombia_ThenGetResponseFromWeatherAPI_ReturnCityResponse() {
        val restTemplateMock: RestTemplate = Mockito.mock(RestTemplate::class.java)
        Mockito.`when`(
            restTemplateMock
                .getForObject(
                    "https://api.openweathermap.org/data/2.5/weather?q=Colombia&units=metric&appid=apiKey",
                    CityResponse::class.java
                )
        )
            .thenReturn(
                CityResponse(
                    main = Main(
                        temp = 26.84,
                        tempMin = 24.12,
                        tempMax = 30.71,
                    )
                )
            )

        val weatherClient = WeatherClient(restTemplateMock, "apiKey")
        val cityResponse = weatherClient.getTemperatureByCityName("Colombia")

        Assertions.assertEquals(26.84, cityResponse.main.temp)
        Assertions.assertEquals(24.12, cityResponse.main.tempMin)
        Assertions.assertEquals(30.71, cityResponse.main.tempMax)
    }
}