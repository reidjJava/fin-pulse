package me.reidj.finpulse.dto.finance

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class LatestPriceResponse(val financialInstruments: List<FinancialInstrumentDto>)

