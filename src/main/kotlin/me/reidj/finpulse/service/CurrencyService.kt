package me.reidj.finpulse.service

import com.fasterxml.jackson.databind.ObjectMapper
import me.reidj.finpulse.dto.exchangerate.GetCurrencyResponse
import me.reidj.finpulse.ports.exchangerate.ExchangeRateApiClient
import me.reidj.finpulse.properties.ExchangeRateProperties
import me.reidj.finpulse.repository.FinanceRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class CurrencyService(
    dataSource: DataSource,
    private val objectMapper: ObjectMapper,
    private val exchangeRateApiClient: ExchangeRateApiClient,
    private val exchangeRateProperties: ExchangeRateProperties
): FinanceRepository(dataSource) {

    @Scheduled(cron = "\${exchangerate.cron}")
    fun getCurrency() {
        if (!exchangeRateProperties.enabled) {
            return
        }
        val response = exchangeRateApiClient.send("RUB")
        val getCurrencyResponse = objectMapper.readValue(response.body(), GetCurrencyResponse::class.java)
        insertCurrency(getCurrencyResponse)
    }
}