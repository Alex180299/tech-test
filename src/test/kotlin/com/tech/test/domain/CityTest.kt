package com.tech.test.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CityTest {
    @Test
    fun whenCreateCity_ThenGetName_ReturnCityName() {
        val city = City(name = "Ciudad de Mexico")
        assertEquals("Ciudad de Mexico", city.name)
    }

    @Test
    fun whenCreateCity_ThenSetTemperatureValuesAndGetThem_ReturnTemperatureValues() {
        val city = City(name = "Ciudad de Mexico")
        city.temperature = 26.84
        city.temperatureMin = 24.12
        city.temperatureMax = 30.71

        assertEquals(26.84, city.temperature)
        assertEquals(24.12, city.temperatureMin)
        assertEquals(30.71, city.temperatureMax)
    }
}