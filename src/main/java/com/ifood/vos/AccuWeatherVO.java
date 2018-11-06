package com.ifood.vos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccuWeatherVO implements Serializable {

    private static final long serialVersionUID = 5967165465371209050L;

    public static class Metric implements Serializable {

        private static final long serialVersionUID = -6451737847977305862L;

        @JsonProperty("Value")
        private Double value;

        public Double getValue() {
            return value;
        }

        public void setValue(Double value) {
            this.value = value;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Metric{");
            sb.append("value=").append(value);
            sb.append('}');
            return sb.toString();
        }
    }

    public static class Temperature implements Serializable {

        private static final long serialVersionUID = -2223949268531461443L;

        @JsonProperty("Metric")
        private Metric metric;

        public Metric getMetric() {
            return metric;
        }

        public void setMetric(Metric metric) {
            this.metric = metric;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Temperature{");
            sb.append("metric=").append(metric);
            sb.append('}');
            return sb.toString();
        }
    }

    @JsonProperty("Temperature")
    private Temperature temperature;
    @JsonProperty("WeatherText")
    private String weatherText;
    @JsonProperty("RelativeHumidity")
    private Integer relativeHumidity;

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public Integer getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(Integer relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AccuWeatherVO{");
        sb.append("temperature=").append(temperature);
        sb.append(", weatherText='").append(weatherText).append('\'');
        sb.append(", relativeHumidity=").append(relativeHumidity);
        sb.append('}');
        return sb.toString();
    }
}
