package com.ifood.controllers;

import com.ifood.exceptions.WeatherCityNotFoundException;
import com.ifood.services.WeatherService;
import com.ifood.vos.GenericResponseVO;
import com.ifood.vos.WeatherVO;
import com.ifood.vos.OpenWeatherMapWeatherVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/weather")
public class WeatherController {

    private final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    @Qualifier("OpenWeatherMapWeather")
    private WeatherService weatherService;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GenericResponseVO> about(@RequestParam("city") String city) {

        logger.info("I=Pesquisando clima por cidade, city={}", city);

        try {
            WeatherVO weather = weatherService.getWeather(city);

            if (weather == null) {
                logger.warn("W=Problemas ao pesquisar clima da cidade, city={}", city);
                return new ResponseEntity<>(new GenericResponseVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Sorry, an error occurred. Please, try again later!"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            logger.info("I=Pesquisa de clima por cidade realizado com sucesso, city={}, weatherResp={}", city, weather);
            return new ResponseEntity<>(new GenericResponseVO(HttpStatus.OK.value(), weather), HttpStatus.OK);
        }
        catch (WeatherCityNotFoundException e) {

            logger.info("I=Cidade nao encontrada, city={}", city);
            return new ResponseEntity<>(new GenericResponseVO(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {

            logger.error("I=Erro ao pesquisar clima da cidade, city={}", city, e);
            return new ResponseEntity<>(new GenericResponseVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
