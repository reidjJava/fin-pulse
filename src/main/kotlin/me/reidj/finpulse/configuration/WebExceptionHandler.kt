package me.reidj.finpulse.configuration

import me.reidj.finpulse.dto.error.ErrorDto
import me.reidj.finpulse.exception.WebAppException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody

@ControllerAdvice
class WebExceptionHandler {
    @ExceptionHandler(value = [WebAppException::class])
    @ResponseBody
    fun handleWebAppException(exception: WebAppException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(exception.httpStatus)
            .body(ErrorDto(exception.message))
    }

    @ExceptionHandler(value = [MissingServletRequestParameterException::class])
    fun handleMissingRequiredParameters(exception: MissingServletRequestParameterException): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorDto(exception.message))
    }

    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    fun handleOtherExceptions(exception: Exception): ResponseEntity<ErrorDto> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorDto(exception.message))
    }
}