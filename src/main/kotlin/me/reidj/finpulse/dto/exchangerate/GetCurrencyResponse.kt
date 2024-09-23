package me.reidj.finpulse.dto.exchangerate

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.math.BigDecimal

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GetCurrencyResponse(
    val date: String,
    val timeLastUpdated: Long,
    val rates: Map<String, BigDecimal>
)