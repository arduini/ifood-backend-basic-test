package com.ifood.vos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import static com.ifood.utils.ConversionsUtil.convertKelvinToCelsius;
import static com.ifood.utils.ConversionsUtil.convertSecondsToDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherVO implements Serializable {

    private static final long serialVersionUID = -7840784094729845837L;

    private Long cityId;
    private String cityName;
    private String main;
    private String description;
    private BigDecimal temperature;
    private BigDecimal maxTemp;
    private BigDecimal minTemp;
    private Integer humidity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT-3")
    private Date sunrise;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT-3")
    private Date sunset;

    public WeatherVO() {
    }

    public WeatherVO(OpenWeatherMapWeatherVO weatherResp) {

        if(weatherResp == null) {

            return;
        }

        this.cityId = weatherResp.getId();
        this.cityName = weatherResp.getName();

        if (weatherResp.getWeather() != null && weatherResp.getWeather().size() > 0) {

            this.main = weatherResp.getWeather().get(0).getMain();
            this.description = weatherResp.getWeather().get(0).getDescription();
        }

        if (weatherResp.getMain() != null) {

            this.temperature = convertKelvinToCelsius(weatherResp.getMain().getTemp()).setScale(2, RoundingMode.FLOOR);
            this.maxTemp = convertKelvinToCelsius(weatherResp.getMain().getTempMax()).setScale(2, RoundingMode.FLOOR);
            this.minTemp = convertKelvinToCelsius(weatherResp.getMain().getTempMin()).setScale(2, RoundingMode.FLOOR);
            this.humidity = weatherResp.getMain().getHumidity();
        }

        if (weatherResp.getSys() != null) {

            this.sunrise = convertSecondsToDate(weatherResp.getSys().getSunrise());
            this.sunset = convertSecondsToDate(weatherResp.getSys().getSunset());
        }
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(BigDecimal maxTemp) {
        this.maxTemp = maxTemp;
    }

    public BigDecimal getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(BigDecimal minTemp) {
        this.minTemp = minTemp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(Date sunrise) {
        this.sunrise = sunrise;
    }

    public Date getSunset() {
        return sunset;
    }

    public void setSunset(Date sunset) {
        this.sunset = sunset;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WeatherResponseVO{");
        sb.append("cityId=").append(cityId);
        sb.append(", cityName='").append(cityName).append('\'');
        sb.append(", main='").append(main).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", temperature=").append(temperature);
        sb.append(", maxTemp=").append(maxTemp);
        sb.append(", minTemp=").append(minTemp);
        sb.append(", humidity=").append(humidity);
        sb.append(", sunrise=").append(sunrise);
        sb.append(", sunset=").append(sunset);
        sb.append('}');
        return sb.toString();
    }
}
