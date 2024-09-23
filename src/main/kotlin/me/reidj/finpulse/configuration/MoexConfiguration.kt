package me.reidj.finpulse.configuration

import me.reidj.finpulse.properties.MoexProperties
import org.springframework.context.annotation.Configuration
import java.net.URI

@Configuration
class MoexConfiguration(
    private val moexProperties: MoexProperties
) {
    fun generateMoexUrl(source: String): URI {
        return URI.create(String.format(moexProperties.apiUrl, source))
    }
}