package com.ifood.services;

import com.ifood.vos.WeatherVO;

import java.util.Optional;

public interface WeatherService {

    Optional<WeatherVO> getWeather(String city);
}
