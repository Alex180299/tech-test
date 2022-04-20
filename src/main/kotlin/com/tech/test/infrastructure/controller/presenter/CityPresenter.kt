package com.tech.test.infrastructure.controller.presenter

import com.tech.test.domain.City
import org.springframework.http.ResponseEntity

class CityPresenter : ICityPresenter {
    override fun getWeatherResponseByCity(city: City): ResponseEntity<Any> =
        ResponseEntity.ok(city)

    override fun getWeatherResponseNotFound(): ResponseEntity<Any> =
        ResponseEntity.notFound().build()

    override fun getLastTenWeatherResultsResponseByCities(cities: List<City>): ResponseEntity<Any> =
        ResponseEntity.ok(cities)
}