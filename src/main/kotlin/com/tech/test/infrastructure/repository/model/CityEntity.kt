package com.tech.test.infrastructure.repository.model

import javax.persistence.*

@Entity
@Table(name = "city")
data class CityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long,
    val name: String,
    val temperature: Double,
    val temperatureMin: Double,
    val temperatureMax: Double
)