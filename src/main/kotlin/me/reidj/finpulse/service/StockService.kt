package me.reidj.finpulse.service

import com.fasterxml.jackson.databind.ObjectMapper
import me.reidj.finpulse.dto.source.GetSourceResponse
import me.reidj.finpulse.properties.ExchangeProperties
import me.reidj.finpulse.repository.FinanceRepository
import me.reidj.finpulse.service.source.StockConsumer
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class StockService(
    dataSource: DataSource,
    private val objectMapper: ObjectMapper,
    private val stockConsumer: StockConsumer,
    private val exchangeProperties: ExchangeProperties,
): FinanceRepository(dataSource) {

    @Scheduled(cron = "\${exchange.cron}")
    fun getStock() {
        if (!exchangeProperties.enabled) {
            return
        }
        val response = stockConsumer.getStock()
        val sourceResponse = objectMapper.readValue(response, GetSourceResponse::class.java)
        insertSource(sourceResponse, "stocks")
    }
}