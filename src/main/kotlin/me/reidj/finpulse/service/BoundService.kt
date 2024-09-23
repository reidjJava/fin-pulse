package me.reidj.finpulse.service

import com.fasterxml.jackson.databind.ObjectMapper
import me.reidj.finpulse.dto.source.GetSourceResponse
import me.reidj.finpulse.properties.ExchangeProperties
import me.reidj.finpulse.repository.FinanceRepository
import me.reidj.finpulse.service.source.BoundConsumer
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class BoundService(
    dataSource: DataSource,
    private val boundConsumer: BoundConsumer,
    private val objectMapper: ObjectMapper,
    private val exchangeProperties: ExchangeProperties
) : FinanceRepository(dataSource) {

    @Scheduled(cron = "\${exchange.cron}")
    fun getBound() {
        if (!exchangeProperties.enabled) {
            return
        }
        val response = boundConsumer.getBound()
        val sourceResponse = objectMapper.readValue(response, GetSourceResponse::class.java)
        insertSource(sourceResponse, "bounds")
    }
}