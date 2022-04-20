package com.tech.test.use_case.impl

import com.tech.test.domain.City
import com.tech.test.domain.CityResponse
import com.tech.test.domain.Main
import com.tech.test.infrastructure.client.IWeatherClient
import com.tech.test.infrastructure.repository.ICityRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.client.HttpClientErrorException.NotFound
import org.springframework.web.client.HttpClientErrorException.TooManyRequests

internal class CityServiceTest {
    @Test
    fun whenGetTemperatureByCityName_ThenGetTemperatureData_ReturnCityWithTemperatureData() {
        val cityRepositoryMock: ICityRepository = Mockito.mock(ICityRepository::class.java)

        val weatherClientMock: IWeatherClient = Mockito.mock(IWeatherClient::class.java)
        Mockito.`when`(weatherClientMock.getTemperatureByCityName("Mexico")).thenReturn(
            CityResponse(
                main = Main(
                    temp = 26.84,
                    tempMin = 24.12,
                    tempMax = 30.71,
                )
            )
        )

        val cityService = CityService(
            weatherClient = weatherClientMock,
            cityRepository = cityRepositoryMock
        )
        val cityResult = cityService.getTemperatureByCityName("Mexico")

        assertEquals(26.84, cityResult.temperature)
        assertEquals(24.12, cityResult.temperatureMin)
        assertEquals(30.71, cityResult.temperatureMax)
    }

    @Test
    fun whenGetTemperatureByCityName_ThenWeatherClientNotFoundData_AndThrowsAnNotFoundError() {
        val cityRepositoryMock: ICityRepository = Mockito.mock(ICityRepository::class.java)

        val weatherClientMock: IWeatherClient = Mockito.mock(IWeatherClient::class.java)
        Mockito.`when`(weatherClientMock.getTemperatureByCityName("Mexico"))
            .thenThrow(NotFound::class.java)

        val cityService = CityService(
            weatherClient = weatherClientMock,
            cityRepository = cityRepositoryMock
        )
        assertThrows(NotFound::class.java) {
            cityService.getTemperatureByCityName("Mexico")
        }
    }

    @Test
    fun whenGetTemperatureByCityName_ThenWeatherClientGetsAnError_AndThrowsAnError() {
        val cityRepositoryMock: ICityRepository = Mockito.mock(ICityRepository::class.java)

        val weatherClientMock: IWeatherClient = Mockito.mock(IWeatherClient::class.java)
        Mockito.`when`(weatherClientMock.getTemperatureByCityName("Mexico"))
            .thenThrow(TooManyRequests::class.java)

        val cityService = CityService(
            weatherClient = weatherClientMock,
            cityRepository = cityRepositoryMock
        )
        assertThrows(TooManyRequests::class.java) {
            cityService.getTemperatureByCityName("Mexico")
        }
    }

    @Test
    fun whenSaveCityTemperature_ThenSaveCityTemperatureInfoToRepository_ReturnCity() {
        val weatherClientMock: IWeatherClient = Mockito.mock(IWeatherClient::class.java)

        val cityArg = City(
            name = "Mexico",
            temperature = 26.84,
            temperatureMax = 30.71,
            temperatureMin = 24.12
        )
        val cityRepositoryMock: ICityRepository = Mockito.mock(ICityRepository::class.java)
        Mockito.`when`(cityRepositoryMock.save(cityArg)).thenReturn(cityArg)
        Mockito.`when`(cityRepositoryMock.get(cityArg.name)).thenReturn(null)

        val cityService = CityService(
            weatherClient = weatherClientMock,
            cityRepository = cityRepositoryMock
        )
        val cityResult = cityService.saveTemperatureCity(cityArg)

        Mockito.verify(cityRepositoryMock, Mockito.times(1)).save(cityArg)
        Mockito.verify(cityRepositoryMock, Mockito.times(1)).get(cityArg.name)

        assertEquals(26.84, cityResult.temperature)
        assertEquals(24.12, cityResult.temperatureMin)
        assertEquals(30.71, cityResult.temperatureMax)
    }

    @Test
    fun whenGetWeatherByCityName_ThenGetWeatherDataAndSaveCityWeatherInfo_ReturnCityWithTemperatureData() {
        val cityArg = City(
            name = "Mexico",
            temperature = 26.84,
            temperatureMax = 30.71,
            temperatureMin = 24.12
        )
        val cityRepositoryMock: ICityRepository = Mockito.mock(ICityRepository::class.java)
        Mockito.`when`(cityRepositoryMock.save(cityArg)).thenReturn(cityArg)
        Mockito.`when`(cityRepositoryMock.get(cityArg.name)).thenThrow(EmptyResultDataAccessException::class.java)

        val weatherClientMock: IWeatherClient = Mockito.mock(IWeatherClient::class.java)
        Mockito.`when`(weatherClientMock.getTemperatureByCityName("Mexico")).thenReturn(
            CityResponse(
                main = Main(
                    temp = 26.84,
                    tempMin = 24.12,
                    tempMax = 30.71,
                )
            )
        )

        val cityService = CityService(
            weatherClient = weatherClientMock,
            cityRepository = cityRepositoryMock
        )
        val cityResult = cityService.getCityWeatherAndSaveDataToRepository("Mexico")

        Mockito.verify(cityRepositoryMock, Mockito.times(1)).save(cityArg)

        assertEquals(26.84, cityResult.temperature)
        assertEquals(24.12, cityResult.temperatureMin)
        assertEquals(30.71, cityResult.temperatureMax)
    }

    @Test
    fun whenGetWeatherByCityName_ThenGetWeatherDataAndReplaceCityWeatherInfo_ReturnCityWithTemperatureData() {
        val cityArg = City(
            name = "Mexico",
            temperature = 26.84,
            temperatureMax = 30.71,
            temperatureMin = 24.12
        )
        val cityRepositoryMock: ICityRepository = Mockito.mock(ICityRepository::class.java)
        Mockito.`when`(cityRepositoryMock.save(cityArg)).thenReturn(cityArg)
        Mockito.`when`(cityRepositoryMock.get(cityArg.name)).thenReturn(cityArg)

        val weatherClientMock: IWeatherClient = Mockito.mock(IWeatherClient::class.java)
        Mockito.`when`(weatherClientMock.getTemperatureByCityName("Mexico")).thenReturn(
            CityResponse(
                main = Main(
                    temp = 26.84,
                    tempMin = 24.12,
                    tempMax = 30.71,
                )
            )
        )

        val cityService = CityService(
            weatherClient = weatherClientMock,
            cityRepository = cityRepositoryMock
        )
        val cityResult = cityService.getCityWeatherAndSaveDataToRepository("Mexico")

        Mockito.verify(cityRepositoryMock, Mockito.times(0)).save(cityArg)

        assertEquals(26.84, cityResult.temperature)
        assertEquals(24.12, cityResult.temperatureMin)
        assertEquals(30.71, cityResult.temperatureMax)
    }

    @Test
    fun whenGetWeatherByCityName_ThenWeatherClientNotFoundDataAndSearchInRepository_AndReturnCityFromRepository() {
        val cityArg = City(
            name = "Mexico",
            temperature = 26.84,
            temperatureMax = 30.71,
            temperatureMin = 24.12
        )
        val cityRepositoryMock: ICityRepository = Mockito.mock(ICityRepository::class.java)
        Mockito.`when`(cityRepositoryMock.save(cityArg)).thenReturn(cityArg)
        Mockito.`when`(cityRepositoryMock.get(cityArg.name)).thenReturn(cityArg)

        val weatherClientMock: IWeatherClient = Mockito.mock(IWeatherClient::class.java)
        Mockito.`when`(weatherClientMock.getTemperatureByCityName("Mexico"))
            .thenThrow(NotFound::class.java)

        val cityService = CityService(
            weatherClient = weatherClientMock,
            cityRepository = cityRepositoryMock
        )
        assertDoesNotThrow {
            val cityResult = cityService.getCityWeatherAndSaveDataToRepository("Mexico")

            assertEquals(26.84, cityResult.temperature)
            assertEquals(24.12, cityResult.temperatureMin)
            assertEquals(30.71, cityResult.temperatureMax)
        }

        Mockito.verify(cityRepositoryMock, Mockito.times(0)).save(cityArg)
        Mockito.verify(cityRepositoryMock, Mockito.times(1)).get(cityArg.name)
    }

    @Test
    fun whenGetWeatherByCityName_ThenWeatherClientNotFoundAndRepositoryDontHaveCityData_AndThrowsNullPointerException() {
        val cityArg = City(
            name = "Mexico",
            temperature = 26.84,
            temperatureMax = 30.71,
            temperatureMin = 24.12
        )
        val cityRepositoryMock: ICityRepository = Mockito.mock(ICityRepository::class.java)
        Mockito.`when`(cityRepositoryMock.save(cityArg)).thenReturn(cityArg)
        Mockito.`when`(cityRepositoryMock.get(cityArg.name)).thenThrow(EmptyResultDataAccessException::class.java)

        val weatherClientMock: IWeatherClient = Mockito.mock(IWeatherClient::class.java)
        Mockito.`when`(weatherClientMock.getTemperatureByCityName("Mexico"))
            .thenThrow(NotFound::class.java)

        val cityService = CityService(
            weatherClient = weatherClientMock,
            cityRepository = cityRepositoryMock
        )
        assertThrows(NullPointerException::class.java) {
            cityService.getCityWeatherAndSaveDataToRepository("Mexico")
        }

        Mockito.verify(cityRepositoryMock, Mockito.times(0)).save(cityArg)
        Mockito.verify(cityRepositoryMock, Mockito.times(1)).get(cityArg.name)
    }

    @Test
    fun whenGetLastTenWeatherValues_ThenGetLastTenWeatherValuesFromRepository_AndReturnTenLastValues() {
        val cityRepositoryMock: ICityRepository = Mockito.mock(ICityRepository::class.java)
        Mockito.`when`(cityRepositoryMock.getLastTenValues()).thenReturn(
            listOf(
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
        )

        val weatherClientMock: IWeatherClient = Mockito.mock(IWeatherClient::class.java)

        val cityService = CityService(
            weatherClient = weatherClientMock,
            cityRepository = cityRepositoryMock
        )
        val values = cityService.getLastTenWeatherValues()

        assertEquals(10, values.size)
    }
}