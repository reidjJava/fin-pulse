package me.reidj.finpulse.ports.exchangerate

import java.net.http.HttpResponse

interface ExchangeRateApiClient {

    fun send(currency: String): HttpResponse<String>
}