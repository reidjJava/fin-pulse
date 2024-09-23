package me.reidj.finpulse.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "spring.datasource")
class DataSourceProperties
@ConstructorBinding
constructor(
    val username: String,
    val password: String,
    val url: String
)
