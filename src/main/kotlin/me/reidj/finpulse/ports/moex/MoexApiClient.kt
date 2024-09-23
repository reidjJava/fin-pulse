package me.reidj.finpulse.ports.moex

import me.reidj.finpulse.service.source.BoundConsumer
import me.reidj.finpulse.service.source.ETFConsumer
import me.reidj.finpulse.service.source.OFZConsumer
import me.reidj.finpulse.service.source.StockConsumer

interface MoexApiClient : ETFConsumer, StockConsumer, BoundConsumer, OFZConsumer