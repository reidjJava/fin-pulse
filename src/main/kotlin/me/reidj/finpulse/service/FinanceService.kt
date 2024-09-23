package me.reidj.finpulse.service

import me.reidj.finpulse.dto.finance.FinancialInstrumentDto
import me.reidj.finpulse.dto.finance.LatestPriceResponse
import me.reidj.finpulse.repository.FinanceRepository
import org.springframework.stereotype.Service
import javax.sql.DataSource

@Service
class FinanceService(
    dataSource: DataSource,
) : FinanceRepository(dataSource) {

    fun fetchLatestPriceResponse(): LatestPriceResponse {
        val price = fetchLatestPrices(listOf("stocks", "bounds", "ofz", "etf"))
        return LatestPriceResponse(price.map { FinancialInstrumentDto(it.first, it.second) })
    }
}