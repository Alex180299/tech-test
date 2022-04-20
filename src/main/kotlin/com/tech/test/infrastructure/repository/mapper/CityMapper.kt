package com.tech.test.infrastructure.repository.mapper

import com.tech.test.domain.City
import com.tech.test.infrastructure.repository.model.CityEntity

class CityMapper {
    fun toEntity(city: City): CityEntity {
        return CityEntity(
            id = 0,
            name = city.name,
            temperature = city.temperature,
            temperatureMax = city.temperatureMax,
            temperatureMin = city.temperatureMin
        )
    }

    fun toDto(cityEntity: CityEntity): City {
        return City(
            name = cityEntity.name,
            temperature = cityEntity.temperature,
            temperatureMax = cityEntity.temperatureMax,
            temperatureMin = cityEntity.temperatureMin
        )
    }

}