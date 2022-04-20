package com.tech.test.infrastructure.controller.presenter

import com.tech.test.domain.City
import org.springframework.http.ResponseEntity

interface ICityPresenter {
    fun getWeatherResponseByCity(city: City): ResponseEntity<Any>
    fun getWeatherResponseNotFound(): ResponseEntity<Any>
    fun getLastTenWeatherResultsResponseByCities(cities: List<City>): ResponseEntity<Any>
}