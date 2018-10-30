package com.ifood.vos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponseVO implements Serializable {

    private static final long serialVersionUID = 5034724668035641559L;

    private Integer StatusCode;
    private String message;

    private WeatherResponseVO weather;

    public GenericResponseVO(Integer statusCode, String message) {
        this.StatusCode = statusCode;
        this.message = message;
    }

    public GenericResponseVO(Integer statusCode, WeatherResponseVO weather) {
        this.StatusCode = statusCode;
        this.weather = weather;
    }

    public Integer getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(Integer statusCode) {
        StatusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WeatherResponseVO getWeather() {
        return weather;
    }

    public void setWeather(WeatherResponseVO weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GenericResponseVO{");
        sb.append("StatusCode=").append(StatusCode);
        sb.append(", message='").append(message).append('\'');
        sb.append(", weather=").append(weather);
        sb.append('}');
        return sb.toString();
    }
}
