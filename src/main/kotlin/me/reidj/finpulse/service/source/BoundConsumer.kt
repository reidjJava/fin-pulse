package me.reidj.finpulse.service.source

@FunctionalInterface
interface BoundConsumer {

    fun getBound(): String
}