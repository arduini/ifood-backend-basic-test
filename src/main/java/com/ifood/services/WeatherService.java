package com.ifood.services;

import com.ifood.vos.WeatherVO;

public interface WeatherService {

    WeatherVO getWeather(String city);
}
