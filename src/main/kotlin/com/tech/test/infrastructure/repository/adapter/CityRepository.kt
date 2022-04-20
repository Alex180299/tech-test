package com.tech.test.infrastructure.repository.adapter

import com.tech.test.domain.City
import com.tech.test.infrastructure.repository.ICityRepository
import com.tech.test.infrastructure.repository.dao.CityDao
import com.tech.test.infrastructure.repository.mapper.CityMapper
import com.tech.test.infrastructure.repository.model.CityEntity
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.lang.Exception

class CityRepository(private val cityDao: CityDao, private val cityMapper: CityMapper) : ICityRepository {
    override fun save(city: City): City {
        val cityEntity = cityMapper.toEntity(city)

        try {
            val cityFound = getEntity(city.name)
            cityEntity.id = cityFound.id
        } catch (e: Exception) {
            cityEntity.id = 0
        }

        return cityDao.save(cityEntity).let { cityMapper.toDto(it) }
    }

    private fun getEntity(cityName: String): CityEntity {
        return cityDao.findByNameEquals(cityName)
    }

    override fun get(cityName: String): City {
        return getEntity(cityName).let { cityMapper.toDto(it) }
    }

    override fun getLastTenValues(): List<City> {
        return cityDao.findAll(
            PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"))
        ).get().map {
            cityMapper.toDto(it)
        }.toList()
    }
}