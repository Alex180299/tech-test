package com.tech.test.infrastructure.repository.adapter

import com.tech.test.domain.City
import com.tech.test.infrastructure.repository.dao.CityDao
import com.tech.test.infrastructure.repository.mapper.CityMapper
import com.tech.test.infrastructure.repository.model.CityEntity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class CityRepositoryTest {
    @Autowired
    private lateinit var cityDao: CityDao

    @Test
    fun whenSaveCityData_ThenSaveToH2Database_ReturnCity() {
        val cityMapper = CityMapper()
        val cityRepository = CityRepository(cityDao = cityDao, cityMapper = cityMapper)
        val cityResult = cityRepository.save(
            City(
                name = "Mexico",
                temperatureMin = 24.12,
                temperatureMax = 30.71,
                temperature = 26.84
            )
        )

        assertEquals(26.84, cityResult.temperature)
        assertEquals(24.12, cityResult.temperatureMin)
        assertEquals(30.71, cityResult.temperatureMax)

        val cityFound = cityDao.findByNameEquals("Mexico")
        assertEquals(26.84, cityFound.temperature)
        assertEquals(24.12, cityFound.temperatureMin)
        assertEquals(30.71, cityFound.temperatureMax)
    }

    @Test
    fun whenSaveExistentCityData_ThenReplaceCityInDB_ReturnCity() {
        cityDao.save(
            CityEntity(
                id = 1,
                name = "Mexico",
                temperatureMin = 24.12,
                temperatureMax = 30.71,
                temperature = 26.84
            )
        )

        val cityMapper = CityMapper()
        val cityRepository = CityRepository(cityDao = cityDao, cityMapper = cityMapper)
        val cityResult = cityRepository.save(
            City(
                name = "Mexico",
                temperatureMin = 0.0,
                temperatureMax = 0.0,
                temperature = 0.0
            )
        )

        assertEquals(0.0, cityResult.temperature)
        assertEquals(0.0, cityResult.temperatureMin)
        assertEquals(0.0, cityResult.temperatureMax)

        val cityFound = cityDao.findByNameEquals("Mexico")
        assertEquals(0.0, cityFound.temperature)
        assertEquals(0.0, cityFound.temperatureMin)
        assertEquals(0.0, cityFound.temperatureMax)
    }

    @Test
    fun whenGetCityData_ThenGetCityDataFromDB_ReturnCity() {
        cityDao.save(
            CityEntity(
                id = 1,
                name = "Mexico",
                temperatureMin = 24.12,
                temperatureMax = 30.71,
                temperature = 26.84
            )
        )

        val cityMapper = CityMapper()
        val cityRepository = CityRepository(cityDao = cityDao, cityMapper = cityMapper)
        val cityResult = cityRepository.get("Mexico")

        assertEquals(26.84, cityResult.temperature)
        assertEquals(24.12, cityResult.temperatureMin)
        assertEquals(30.71, cityResult.temperatureMax)
    }

    @Test
    fun whenGetLastTenCitiesData_ThenGetCitiesDataFromDB_ReturnCities() {
        val cities = listOf(
            CityEntity(
                id = 0,
                name = "Mexico",
                temperature = 26.84,
                temperatureMax = 30.71,
                temperatureMin = 24.12
            ),
            CityEntity(
                id = 0,
                name = "Peru",
                temperature = 28.12,
                temperatureMax = 34.15,
                temperatureMin = 23.14
            ),
            CityEntity(
                id = 0,
                name = "Colombia",
                temperature = 30.3,
                temperatureMax = 33.6,
                temperatureMin = 28.9
            ),
            CityEntity(
                id = 0,
                name = "Israel",
                temperature = 33.12,
                temperatureMax = 35.8,
                temperatureMin = 31.12
            ),
            CityEntity(
                id = 0,
                name = "Brasil",
                temperature = 35.13,
                temperatureMax = 37.1,
                temperatureMin = 33.9
            ),
            CityEntity(
                id = 0,
                name = "Argentina",
                temperature = 38.2,
                temperatureMax = 39.14,
                temperatureMin = 34.1
            ),
            CityEntity(
                id = 0,
                name = "Bolivia",
                temperature = 10.05,
                temperatureMax = 12.71,
                temperatureMin = 8.31
            ),
            CityEntity(
                id = 0,
                name = "Canada",
                temperature = 11.02,
                temperatureMax = 12.55,
                temperatureMin = 9.78
            ),
            CityEntity(
                id = 0,
                name = "Guatemala",
                temperature = 21.91,
                temperatureMax = 21.91,
                temperatureMin = 18.62
            ),
            CityEntity(
                id = 0,
                name = "Inglaterra",
                temperature = 7.18,
                temperatureMax = 8.12,
                temperatureMin = 5.75
            ),
            CityEntity(
                id = 0,
                name = "Nueva Zelanda",
                temperature = 10.12,
                temperatureMax = 12.2,
                temperatureMin = 9.92
            ),
        )
        for (city in cities) {
            cityDao.save(city)
        }

        val cityMapper = CityMapper()
        val cityRepository = CityRepository(cityDao = cityDao, cityMapper = cityMapper)
        val citiesResult = cityRepository.getLastTenValues()

        assertEquals(10, citiesResult.size)
    }
}