package me.reidj.finpulse.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "exchangerate")
class ExchangeRateProperties
@ConstructorBinding
constructor(
    val apiUrl: String,
    val enabled: Boolean
)