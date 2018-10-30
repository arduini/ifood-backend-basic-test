package com.ifood.controllers;

import com.ifood.exceptions.WeatherCityNotFoundException;
import com.ifood.services.WeatherService;
import com.ifood.vos.GenericResponseVO;
import com.ifood.vos.WeatherResponseVO;
import com.ifood.vos.WeatherVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private WeatherService weatherService;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GenericResponseVO> about(@RequestParam("city") String city) {

        logger.info("I=Pesquisando clima por cidade, city={}", city);

        try {
            WeatherVO weather = weatherService.getWeather(city);
            WeatherResponseVO weatherResp = new WeatherResponseVO(weather);

            return new ResponseEntity<>(new GenericResponseVO(HttpStatus.OK.value(), weatherResp), HttpStatus.OK);
        }
        catch (WeatherCityNotFoundException e) {

            return new ResponseEntity<>(new GenericResponseVO(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {

            return new ResponseEntity<>(new GenericResponseVO(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
