package me.reidj.finpulse.service.source

@FunctionalInterface
interface OFZConsumer {

    fun getOFZ(): String
}