package me.reidj.finpulse.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "moex")
class MoexProperties
@ConstructorBinding
constructor(val apiUrl: String)
