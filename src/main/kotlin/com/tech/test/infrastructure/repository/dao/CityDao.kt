package com.tech.test.infrastructure.repository.dao

import com.tech.test.infrastructure.repository.model.CityEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CityDao : JpaRepository<CityEntity, Long> {
    fun findByNameEquals(name: String): CityEntity
}