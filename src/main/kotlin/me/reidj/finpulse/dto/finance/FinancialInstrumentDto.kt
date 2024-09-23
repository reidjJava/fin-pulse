package me.reidj.finpulse.dto.finance

import java.math.BigDecimal

data class FinancialInstrumentDto(
    val name: String,
    val price: BigDecimal
)
