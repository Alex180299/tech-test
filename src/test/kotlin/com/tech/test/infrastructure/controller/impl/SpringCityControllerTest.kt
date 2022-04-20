package com.tech.test.infrastructure.controller.impl

import com.tech.test.domain.City
import com.tech.test.infrastructure.controller.configuration.UrlMapping
import com.tech.test.infrastructure.controller.presenter.CityPresenter
import com.tech.test.infrastructure.controller.presenter.ICityPresenter
import com.tech.test.use_case.ICityService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.client.HttpClientErrorException.NotFound

@WebMvcTest(value = [SpringCityController::class])
internal class SpringCityControllerTest(
    @Autowired private val mockMvc: MockMvc
) {
    @MockBean
    private lateinit var cityPresenter: ICityPresenter

    @MockBean
    private lateinit var cityService: ICityService

    private val cityPresenterImpl = CityPresenter()

    @Test
    fun whenGetWeatherFromCityByEndpoint_ThenServiceGetsCityWeather_ReturnCityWeatherJson() {
        val city = City(
            name = "Mexico",
            temperature = 26.84,
            temperatureMax = 30.71,
            temperatureMin = 24.12
        )
        Mockito.`when`(cityService.getCityWeatherAndSaveDataToRepository("Mexico")).thenReturn(city)

        Mockito.`when`(cityPresenter.getWeatherResponseByCity(city))
            .thenReturn(cityPresenterImpl.getWeatherResponseByCity(city))

        val cityName = "Mexico"
        mockMvc.perform(get("${UrlMapping.CITY}/weather/$cityName"))
            .andDo(print()).andExpect(status().isOk)
            .andExpect(content().string("{\"name\":\"Mexico\",\"temperature\":26.84,\"temperatureMin\":24.12,\"temperatureMax\":30.71}"))

        Mockito.verify(cityService, Mockito.times(1)).getCityWeatherAndSaveDataToRepository("Mexico")
        Mockito.verify(cityPresenter, Mockito.times(1)).getWeatherResponseByCity(city)
    }

    @Test
    fun whenGetLastTenWeatherValues_ThenServiceGetsLastTenWeatherValues_ReturnCitiesJsonList() {
        val cities = listOf(
            City(
                name = "Mexico",
                temperature = 26.84,
                temperatureMax = 30.71,
                temperatureMin = 24.12
            ),
            City(
                name = "Peru",
                temperature = 28.12,
                temperatureMax = 34.15,
                temperatureMin = 23.14
            ),
            City(
                name = "Colombia",
                temperature = 30.3,
                temperatureMax = 33.6,
                temperatureMin = 28.9
            ),
            City(
                name = "Israel",
                temperature = 33.12,
                temperatureMax = 35.8,
                temperatureMin = 31.12
            ),
            City(
                name = "Brasil",
                temperature = 35.13,
                temperatureMax = 37.1,
                temperatureMin = 33.9
            ),
            City(
                name = "Argentina",
                temperature = 38.2,
                temperatureMax = 39.14,
                temperatureMin = 34.1
            ),
            City(
                name = "Bolivia",
                temperature = 10.05,
                temperatureMax = 12.71,
                temperatureMin = 8.31
            ),
            City(
                name = "Canada",
                temperature = 11.02,
                temperatureMax = 12.55,
                temperatureMin = 9.78
            ),
            City(
                name = "Guatemala",
                temperature = 21.91,
                temperatureMax = 21.91,
                temperatureMin = 18.62
            ),
            City(
                name = "Inglaterra",
                temperature = 7.18,
                temperatureMax = 8.12,
                temperatureMin = 5.75
            ),
        )
        Mockito.`when`(cityService.getLastTenWeatherValues()).thenReturn(cities)

        Mockito.`when`(cityPresenter.getLastTenWeatherResultsResponseByCities(cities))
            .thenReturn(cityPresenterImpl.getLastTenWeatherResultsResponseByCities(cities))

        mockMvc.perform(get(UrlMapping.CITY))
            .andDo(print()).andExpect(status().isOk)
            .andExpect(content().string("[{\"name\":\"Mexico\",\"temperature\":26.84,\"temperatureMin\":24.12,\"temperatureMax\":30.71},{\"name\":\"Peru\",\"temperature\":28.12,\"temperatureMin\":23.14,\"temperatureMax\":34.15},{\"name\":\"Colombia\",\"temperature\":30.3,\"temperatureMin\":28.9,\"temperatureMax\":33.6},{\"name\":\"Israel\",\"temperature\":33.12,\"temperatureMin\":31.12,\"temperatureMax\":35.8},{\"name\":\"Brasil\",\"temperature\":35.13,\"temperatureMin\":33.9,\"temperatureMax\":37.1},{\"name\":\"Argentina\",\"temperature\":38.2,\"temperatureMin\":34.1,\"temperatureMax\":39.14},{\"name\":\"Bolivia\",\"temperature\":10.05,\"temperatureMin\":8.31,\"temperatureMax\":12.71},{\"name\":\"Canada\",\"temperature\":11.02,\"temperatureMin\":9.78,\"temperatureMax\":12.55},{\"name\":\"Guatemala\",\"temperature\":21.91,\"temperatureMin\":18.62,\"temperatureMax\":21.91},{\"name\":\"Inglaterra\",\"temperature\":7.18,\"temperatureMin\":5.75,\"temperatureMax\":8.12}]"))

        Mockito.verify(cityService, Mockito.times(1)).getLastTenWeatherValues()
        Mockito.verify(cityPresenter, Mockito.times(1)).getLastTenWeatherResultsResponseByCities(cities)
    }

    @Test
    fun whenGetWeatherFromCityByEndpoint_ThenServiceNotFound_ReturnNotFoundStatus() {
        Mockito.`when`(cityService.getCityWeatherAndSaveDataToRepository("Mexico"))
            .thenThrow(NotFound::class.java)

        Mockito.`when`(cityPresenter.getWeatherResponseNotFound())
            .thenReturn(cityPresenterImpl.getWeatherResponseNotFound())

        val cityName = "Mexico"
        mockMvc.perform(get("${UrlMapping.CITY}/weather/$cityName"))
            .andDo(print()).andExpect(status().isNotFound)
            .andExpect(content().string(""))

        Mockito.verify(cityService, Mockito.times(1)).getCityWeatherAndSaveDataToRepository("Mexico")
        Mockito.verify(cityPresenter, Mockito.times(1)).getWeatherResponseNotFound()
    }
}
