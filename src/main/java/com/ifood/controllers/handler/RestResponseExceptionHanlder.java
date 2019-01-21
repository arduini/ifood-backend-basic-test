package com.ifood.controllers.handler;

import com.ifood.exceptions.WeatherCityNotFoundException;
import com.ifood.vos.GenericResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author s2it_aoliveira
 * @version $Revision: $<br/>
 * $Id: $
 * @since 21/01/19 13:06
 */
@ControllerAdvice
public class RestResponseExceptionHanlder extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WeatherCityNotFoundException.class)
    public final ResponseEntity<Object> handleWeatherCityNotFoundException(final Exception e, final WebRequest req) {

        logger.info("I=Cidade nao encontrada");
        return new ResponseEntity<>(new GenericResponseVO(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(final Exception e, final WebRequest req) {

        logger.error("I=Erro ao pesquisar clima da cidade", e);
        return new ResponseEntity<>(new GenericResponseVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
