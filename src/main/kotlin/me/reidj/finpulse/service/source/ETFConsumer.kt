package me.reidj.finpulse.service.source

@FunctionalInterface
interface ETFConsumer {

    fun getETF(): String
}