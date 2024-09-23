package me.reidj.finpulse.controller

import me.reidj.finpulse.dto.finance.LatestPriceResponse
import me.reidj.finpulse.service.FinanceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/finance")
class FinanceController(
    private val financeService: FinanceService
) {

    @GetMapping("/latest_price")
    fun getLatestPrice(): ResponseEntity<LatestPriceResponse> {
        return ResponseEntity.ok(financeService.fetchLatestPriceResponse())
    }
}