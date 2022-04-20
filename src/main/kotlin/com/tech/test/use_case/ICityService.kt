package com.tech.test.use_case

import com.tech.test.domain.City

interface ICityService {
    fun getTemperatureByCityName(cityName: String): City
    fun saveTemperatureCity(city: City): City
    fun getCityWeatherAndSaveDataToRepository(cityName: String): City
    fun getLastTenWeatherValues(): List<City>
}