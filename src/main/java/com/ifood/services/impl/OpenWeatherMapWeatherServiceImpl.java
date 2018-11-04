package com.ifood.services.impl;

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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service("OpenWeatherMapWeather")
public class OpenWeatherMapWeatherServiceImpl implements WeatherService {

    private final Logger logger = LoggerFactory.getLogger(OpenWeatherMapWeatherServiceImpl.class);

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
    @HystrixCommand(fallbackMethod = "getWeather_Fallback", ignoreExceptions = { WeatherCityNotFoundException.class })
    public WeatherVO getWeather(String city) {

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
            return new WeatherVO(weatherResponse);
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
    private WeatherVO getWeather_Fallback(String city) {

        logger.error("E=Fallback - Erro ao pesquisar clima por cidade, city={}", city);
        return accuWeatherService.getWeather(city);
    }

    private HttpEntity<?> createDefaultHttpEntity() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
