package com.tcs.service.template.error

import com.tcs.service.template.error.customexception.DataNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import com.tcs.service.template.error.ErrorResponseEntity.Companion.notFound

@ControllerAdvice
class ExceptionHandlers {
	
	@ExceptionHandler(DataNotFoundException::class)
    fun resourceNotFoundException(exception: DataNotFoundException) =
            exception.message?.let { notFound(it) }
            //badReqeust("No Customer Found")
}