package com.tech.test.infrastructure.controller.impl

import com.tech.test.infrastructure.controller.configuration.UrlMapping
import com.tech.test.infrastructure.controller.presenter.ICityPresenter
import com.tech.test.use_case.ICityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException.NotFound
import java.lang.NullPointerException

@RestController
@RequestMapping(value = [UrlMapping.CITY])
class SpringCityController(
    @Autowired private val cityPresenter: ICityPresenter,
    @Autowired private val cityService: ICityService
) {
    @GetMapping("/weather/{cityName}")
    fun getWeatherByCityName(@PathVariable cityName: String): ResponseEntity<Any> {
        return try {
            val city = cityService.getCityWeatherAndSaveDataToRepository(cityName = cityName)
            cityPresenter.getWeatherResponseByCity(city)
        } catch (notFound: NotFound) {
            cityPresenter.getWeatherResponseNotFound()
        } catch (nullPointer: NullPointerException) {
            cityPresenter.getWeatherResponseNotFound()
        }
    }

    @GetMapping
    fun getLastTenWeatherResults(): ResponseEntity<Any> {
        val cities = cityService.getLastTenWeatherValues()
        return cityPresenter.getLastTenWeatherResultsResponseByCities(cities)
    }
}
