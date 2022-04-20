package com.tech.test.infrastructure.repository

import com.tech.test.domain.City

interface ICityRepository {
    fun save(city: City): City
    fun get(cityName: String): City
    fun getLastTenValues(): List<City>
}