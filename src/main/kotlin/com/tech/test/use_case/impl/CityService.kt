package com.tech.test.use_case.impl

import com.tech.test.domain.City
import com.tech.test.infrastructure.client.IWeatherClient
import com.tech.test.infrastructure.repository.ICityRepository
import com.tech.test.use_case.ICityService
import java.lang.Exception
import java.lang.NullPointerException

class CityService(private val weatherClient: IWeatherClient, private val cityRepository: ICityRepository) :
    ICityService {
    override fun getTemperatureByCityName(cityName: String): City {
        return weatherClient.getTemperatureByCityName(cityName = cityName).run {
            City(
                name = cityName,
                temperature = this.main.temp,
                temperatureMax = this.main.tempMax,
                temperatureMin = this.main.tempMin
            )
        }
    }

    override fun saveTemperatureCity(city: City): City = cityRepository.save(city)

    override fun getCityWeatherAndSaveDataToRepository(cityName: String): City {
        return try {
            getTemperatureByCityName(cityName).run {
                saveTemperatureCity(this)
            }
        } catch (e: Exception) {
            return try {
                cityRepository.get(cityName)
            } catch (ex: Exception) {
                throw NullPointerException()
            }
        }
    }

    override fun getLastTenWeatherValues(): List<City> = cityRepository.getLastTenValues()
}
