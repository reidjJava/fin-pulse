package me.reidj.finpulse.service

import com.fasterxml.jackson.databind.ObjectMapper
import me.reidj.finpulse.dto.source.GetSourceResponse
import me.reidj.finpulse.properties.ExchangeProperties
import me.reidj.finpulse.repository.FinanceRepository
import me.reidj.finpulse.service.source.ETFConsumer
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class ETFService(
    dataSource: DataSource,
    private val etfConsumer: ETFConsumer,
    private val objectMapper: ObjectMapper,
    private val exchangeProperties: ExchangeProperties
): FinanceRepository(dataSource) {

    @Scheduled(cron = "\${exchange.cron}")
    fun getETF() {
        if (!exchangeProperties.enabled) {
            return
        }
        val response = etfConsumer.getETF()
        val sourceResponse = objectMapper.readValue(response, GetSourceResponse::class.java)
        insertSource(sourceResponse, "etf")
    }
}