package com.ifood.controllers;

import com.ifood.services.WeatherService;
import com.ifood.vos.GenericResponseVO;
import com.ifood.vos.WeatherVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/weather")
public class WeatherController {

    private final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    @Qualifier("OpenWeatherMapWeather")
    private WeatherService weatherService;

    @GetMapping
    public ResponseEntity<GenericResponseVO> about(@RequestParam("city") String city) {

        logger.info("I=Pesquisando clima por cidade, city={}", city);

        Optional<WeatherVO> weather = weatherService.getWeather(city);

        if (!weather.isPresent()) {
            logger.warn("W=Problemas ao pesquisar clima da cidade, city={}", city);
            return new ResponseEntity<>(new GenericResponseVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Sorry, an error occurred. Please, try again later!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        logger.info("I=Pesquisa de clima por cidade realizado com sucesso, city={}, weatherResp={}", city, weather);
        return new ResponseEntity<>(new GenericResponseVO(HttpStatus.OK.value(), weather.get()), HttpStatus.OK);
    }
}
