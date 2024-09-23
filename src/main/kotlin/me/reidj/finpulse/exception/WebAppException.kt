package me.reidj.finpulse.exception

import org.springframework.http.HttpStatus

class WebAppException(
    val httpStatus: HttpStatus,
    override val message: String
) : RuntimeException(message)