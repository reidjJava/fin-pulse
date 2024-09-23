package me.reidj.finpulse.service.source

@FunctionalInterface
interface StockConsumer {

    fun getStock(): String
}