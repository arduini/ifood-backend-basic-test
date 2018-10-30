package com.ifood.exceptions;

public class WeatherCityNotFoundException extends RuntimeException {

    public WeatherCityNotFoundException(String message) {
        super(message);
    }
}
