package com.ifood.services.impl;

import com.ifood.services.WeatherService;
import com.ifood.vos.WeatherVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("AccuWeather")
public class AccuWeatherServiceImpl implements WeatherService {

    private final Logger logger = LoggerFactory.getLogger(AccuWeatherServiceImpl.class);

    public WeatherVO getWeather(String city) {

        // TODO: Implementar busca do clima utilizando API alternativa
        logger.info("I=Pesquisando clima por cidade, city={}", city);
        return null;
    }
}
