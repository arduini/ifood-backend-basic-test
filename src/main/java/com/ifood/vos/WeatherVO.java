package com.ifood.vos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherVO implements Serializable {

    private static final long serialVersionUID = -36174204000728458L;

    public class Coordinates implements Serializable {

        private static final long serialVersionUID = 1667882577777785232L;
        private Double lon;
        private Double lat;

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Coordinates{");
            sb.append(", lon=").append(lon);
            sb.append("lat=").append(lat);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class WeatherData implements Serializable {

        private static final long serialVersionUID = 4493774771516469711L;
        private Integer id;
        private String main;
        private String description;
        private String icon;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("WeatherData{");
            sb.append("id=").append(id);
            sb.append(", main='").append(main).append('\'');
            sb.append(", description='").append(description).append('\'');
            sb.append(", icon='").append(icon).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    public class MainData implements Serializable {

        private static final long serialVersionUID = 3427885293062933658L;
        private Double temp;
        private Double pressure;
        private Integer humidity;
        @JsonProperty("temp_min")
        private Double tempMin;
        @JsonProperty("temp_max")
        private Double tempMax;
        @JsonProperty("sea_level")
        private Double seaLevel;
        @JsonProperty("grnd_level")
        private Double groundLevel;

        public Double getTemp() {
            return temp;
        }

        public void setTemp(Double temp) {
            this.temp = temp;
        }

        public Double getPressure() {
            return pressure;
        }

        public void setPressure(Double pressure) {
            this.pressure = pressure;
        }

        public Integer getHumidity() {
            return humidity;
        }

        public void setHumidity(Integer humidity) {
            this.humidity = humidity;
        }

        public Double getTempMin() {
            return tempMin;
        }

        public void setTempMin(Double tempMin) {
            this.tempMin = tempMin;
        }

        public Double getTempMax() {
            return tempMax;
        }

        public void setTempMax(Double tempMax) {
            this.tempMax = tempMax;
        }

        public Double getSeaLevel() {
            return seaLevel;
        }

        public void setSeaLevel(Double seaLevel) {
            this.seaLevel = seaLevel;
        }

        public Double getGroundLevel() {
            return groundLevel;
        }

        public void setGroundLevel(Double groundLevel) {
            this.groundLevel = groundLevel;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("MainData{");
            sb.append("temp=").append(temp);
            sb.append(", pressure=").append(pressure);
            sb.append(", humidity=").append(humidity);
            sb.append(", tempMin=").append(tempMin);
            sb.append(", tempMax=").append(tempMax);
            sb.append(", seaLevel=").append(seaLevel);
            sb.append(", groundLevel=").append(groundLevel);
            sb.append('}');
            return sb.toString();
        }
    }

    public class Wind implements Serializable {

        private static final long serialVersionUID = 5458083843800808328L;
        private Double speed;
        private Integer deg;

        public Double getSpeed() {
            return speed;
        }

        public void setSpeed(Double speed) {
            this.speed = speed;
        }

        public Integer getDeg() {
            return deg;
        }

        public void setDeg(Integer deg) {
            this.deg = deg;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Wind{");
            sb.append("speed=").append(speed);
            sb.append(", deg=").append(deg);
            sb.append('}');
            return sb.toString();
        }
    }

    public class Clouds implements Serializable {

        private static final long serialVersionUID = -3687427369265378458L;
        private Integer all;

        public Integer getAll() {
            return all;
        }

        public void setAll(Integer all) {
            this.all = all;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Clouds{");
            sb.append("all=").append(all);
            sb.append('}');
            return sb.toString();
        }
    }

    public class SysData implements Serializable {

        private static final long serialVersionUID = 2398073799413689396L;
        private Double message;
        private String country;
        private Long sunrise;
        private Long sunset;

        public Double getMessage() {
            return message;
        }

        public void setMessage(Double message) {
            this.message = message;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public Long getSunrise() {
            return sunrise;
        }

        public void setSunrise(Long sunrise) {
            this.sunrise = sunrise;
        }

        public Long getSunset() {
            return sunset;
        }

        public void setSunset(Long sunset) {
            this.sunset = sunset;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("SysData{");
            sb.append("message=").append(message);
            sb.append(", country='").append(country).append('\'');
            sb.append(", sunrise=").append(sunrise);
            sb.append(", sunset=").append(sunset);
            sb.append('}');
            return sb.toString();
        }
    }

    private Coordinates coord;
    private List<WeatherData> weather;
    private String base;
    private MainData main;
    private Wind wind;
    private Clouds clouds;
    private Long dt;
    private SysData sys;
    private Long id;
    private String name;
    private Integer cod;

    public Coordinates getCoord() {
        return coord;
    }

    public void setCoord(Coordinates coord) {
        this.coord = coord;
    }

    public List<WeatherData> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherData> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public MainData getMain() {
        return main;
    }

    public void setMain(MainData main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public SysData getSys() {
        return sys;
    }

    public void setSys(SysData sys) {
        this.sys = sys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("WeatherVO{");
        sb.append("coord=").append(coord);
        sb.append(", weather=").append(weather);
        sb.append(", base='").append(base).append('\'');
        sb.append(", main=").append(main);
        sb.append(", wind=").append(wind);
        sb.append(", clouds=").append(clouds);
        sb.append(", dt=").append(dt);
        sb.append(", sys=").append(sys);
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", cod=").append(cod);
        sb.append('}');
        return sb.toString();
    }
}
