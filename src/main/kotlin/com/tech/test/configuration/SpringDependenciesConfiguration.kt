package com.tech.test.configuration

import com.tech.test.infrastructure.client.impl.WeatherClient
import com.tech.test.infrastructure.controller.presenter.CityPresenter
import com.tech.test.infrastructure.repository.adapter.CityRepository
import com.tech.test.infrastructure.repository.dao.CityDao
import com.tech.test.infrastructure.repository.mapper.CityMapper
import com.tech.test.use_case.impl.CityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
class SpringDependenciesConfiguration(@Autowired private val cityDao: CityDao) {
    @Value("\${app.api_key}")
    private lateinit var apiKey: String

    @Bean
    fun restTemplate(): RestTemplate =
        RestTemplateBuilder().setConnectTimeout(Duration.ofMillis(10000)).build()

    @Bean
    fun weatherClient() = WeatherClient(restTemplate = restTemplate(), apiKey = apiKey)

    @Bean
    fun cityRepository() = CityRepository(cityDao = cityDao, cityMapper = CityMapper())

    @Bean
    fun cityService() = CityService(weatherClient = weatherClient(), cityRepository = cityRepository())

    @Bean
    fun cityPresenter() = CityPresenter()
}