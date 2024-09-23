package me.reidj.finpulse.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "exchange")
class ExchangeProperties
@ConstructorBinding
constructor(
    val enabled: Boolean,
    val source: Map<String, String>
)
