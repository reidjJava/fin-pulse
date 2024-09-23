package me.reidj.finpulse.ports.exchangerate

import me.reidj.finpulse.configuration.ExchangeRateConfiguration
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class DefaultExchangeRateClient(
    private val exchangeRateConfiguration: ExchangeRateConfiguration
) : ExchangeRateApiClient {

    private val httpClient = HttpClient.newBuilder().build()

    companion object {
        private val log = LoggerFactory.getLogger(DefaultExchangeRateClient::class.java)
    }

    override fun send(currency: String): HttpResponse<String> {
        val httpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(exchangeRateConfiguration.generateExchangeRateUrl(currency))
            .build()

        log.info("Got response from exchangerate")

        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString())
    }
}