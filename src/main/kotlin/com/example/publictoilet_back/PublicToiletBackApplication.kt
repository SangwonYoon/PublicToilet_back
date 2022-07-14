package com.example.publictoilet_back

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@EnableCaching
@SpringBootApplication
class PublicToiletBackApplication

fun main(args: Array<String>) {
	runApplication<PublicToiletBackApplication>(*args)
}
