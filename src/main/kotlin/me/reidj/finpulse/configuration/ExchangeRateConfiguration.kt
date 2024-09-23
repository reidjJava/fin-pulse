package me.reidj.finpulse.configuration

import me.reidj.finpulse.properties.ExchangeRateProperties
import org.springframework.context.annotation.Configuration
import java.net.URI

@Configuration
class ExchangeRateConfiguration(
    private val exchangeRateProperties: ExchangeRateProperties
) {
    fun generateExchangeRateUrl(currency: String): URI {
        return URI.create("${exchangeRateProperties.apiUrl}/${currency}")
    }
}