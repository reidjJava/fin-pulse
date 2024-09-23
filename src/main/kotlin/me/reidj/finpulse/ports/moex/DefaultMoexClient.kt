package me.reidj.finpulse.ports.moex

import me.reidj.finpulse.configuration.MoexConfiguration
import me.reidj.finpulse.properties.ExchangeProperties
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class DefaultMoexClient(
    private val moexConfiguration: MoexConfiguration,
    private val exchangeProperties: ExchangeProperties
) : MoexApiClient {

    private val httpClient = HttpClient.newBuilder().build()
    private val log = LoggerFactory.getLogger(DefaultMoexClient::class.java)

    private fun consume(source: String): String {
        val httpRequest = HttpRequest.newBuilder()
            .GET()
            .uri(moexConfiguration.generateMoexUrl(exchangeProperties.source[source]!!))
            .build()

        log.info("Got response from moex")

        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body()
    }

    override fun getETF() = consume("etf")

    override fun getStock() = consume("stock")

    override fun getBound() = consume("bound")

    override fun getOFZ() = consume("ofz")
}