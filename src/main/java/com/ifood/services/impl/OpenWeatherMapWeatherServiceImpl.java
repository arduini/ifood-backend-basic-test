package com.ifood.services.impl;

import static com.ifood.utils.ConversionsUtil.convertKelvinToCelsius;
import static com.ifood.utils.ConversionsUtil.convertSecondsToDate;

import com.ifood.exceptions.WeatherCityNotFoundException;
import com.ifood.services.WeatherService;
import com.ifood.vos.OpenWeatherMapWeatherVO;
import com.ifood.vos.WeatherVO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.RoundingMode;
import java.util.Optional;

@Service("OpenWeatherMapWeather")
public class OpenWeatherMapWeatherServiceImpl implements WeatherService {

    private final Logger logger = LoggerFactory.getLogger(OpenWeatherMapWeatherServiceImpl.class);

    private final static String WEATHER_SOURCE = "OpenWeatherMap";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("AccuWeather")
    private WeatherService accuWeatherService;

    @Value("${openweathermap.app.id}")
    private String appId;

    @Value("${openweathermap.url}")
    private String baseUrl;

    @Cacheable(value = "weatherByCity", key = "#city")
    @HystrixCommand(fallbackMethod = "getWeatherFallback", ignoreExceptions = { WeatherCityNotFoundException.class })
    public Optional<WeatherVO> getWeather(String city) {

        logger.info("I=Pesquisando clima por cidade, city={}", city);
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "?q={city}&APPID={appId}")
                .buildAndExpand(city, appId);

        try {

            HttpEntity<OpenWeatherMapWeatherVO> response = restTemplate.exchange(
                    uri.toUriString(),
                    HttpMethod.GET,
                    createDefaultHttpEntity(),
                    OpenWeatherMapWeatherVO.class);

            if (response == null) {
                logger.info("I=Cidade nao encontrada, city={}", city);
                throw new WeatherCityNotFoundException("Cidade não encontrada");
            }

            OpenWeatherMapWeatherVO weatherResponse = response.getBody();

            logger.info("I=Clima da cidade encontrado, city={}, weather={}", city, weatherResponse);
            return Optional.ofNullable(translateToGenericWeatherVO(weatherResponse));
        }
        catch (HttpClientErrorException e) {

            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {

                logger.info("I=Cidade nao encontrada, city={}", city);
                throw new WeatherCityNotFoundException("Cidade não encontrada");
            }

            logger.error("E=Erro ao pesquisar clima por cidade, city={}", city, e);
            throw e;
        }
        catch (Exception e) {

            logger.error("E=Erro ao pesquisar clima por cidade, city={}", city, e);
            throw e;
        }
    }

    @SuppressWarnings("unused")
    private Optional<WeatherVO> getWeatherFallback(String city) {

        logger.error("E=Fallback - Erro ao pesquisar clima por cidade, city={}", city);

        try {
            return accuWeatherService.getWeather(city);
        }
        catch (Exception e){
            logger.error("E=Problemas ao pesquisar clima por cidade no fallback, city={}", city, e);
            return Optional.empty();
        }
    }

    private HttpEntity<?> createDefaultHttpEntity() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }


    private WeatherVO translateToGenericWeatherVO(OpenWeatherMapWeatherVO openWeatherVO) {

        if(openWeatherVO == null) {

            return null;
        }

        WeatherVO weatherVO = new WeatherVO();

        weatherVO.setCityId(openWeatherVO.getId());
        weatherVO.setCityName(openWeatherVO.getName());

        if (openWeatherVO.getWeather() != null && openWeatherVO.getWeather().size() > 0) {

            weatherVO.setMain(openWeatherVO.getWeather().get(0).getMain());
            weatherVO.setDescription(openWeatherVO.getWeather().get(0).getDescription());
        }

        if (openWeatherVO.getMain() != null) {

            weatherVO.setTemperature(convertKelvinToCelsius(openWeatherVO.getMain().getTemp()).setScale(2, RoundingMode.FLOOR));
            weatherVO.setMaxTemp(convertKelvinToCelsius(openWeatherVO.getMain().getTempMax()).setScale(2, RoundingMode.FLOOR));
            weatherVO.setMinTemp(convertKelvinToCelsius(openWeatherVO.getMain().getTempMin()).setScale(2, RoundingMode.FLOOR));
            weatherVO.setHumidity(openWeatherVO.getMain().getHumidity());
        }

        if (openWeatherVO.getSys() != null) {

            weatherVO.setSunrise(convertSecondsToDate(openWeatherVO.getSys().getSunrise()));
            weatherVO.setSunset(convertSecondsToDate(openWeatherVO.getSys().getSunset()));
        }

        weatherVO.setSource(WEATHER_SOURCE);

        return weatherVO;
    }
}
