package com.ifood.services.impl;

import com.ifood.exceptions.WeatherCityNotFoundException;
import com.ifood.services.WeatherService;
import com.ifood.vos.WeatherVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OpenWeatherMapWeatherServiceImpl implements WeatherService {

    private final Logger logger = LoggerFactory.getLogger(OpenWeatherMapWeatherServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openweathermap.app.id}")
    private String appId;

    @Value("${openweathermap.url}")
    private String baseUrl;

    @Cacheable(value = "weatherByCity", key = "#city")
    public WeatherVO getWeather(String city) {

        logger.info("I=Pesquisando clima por cidade, city={}", city);
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "?q={city}&APPID={appId}")
                .buildAndExpand(city, appId);

        try {

            HttpEntity<WeatherVO> response = restTemplate.exchange(
                    uri.toUriString(),
                    HttpMethod.GET,
                    createDefaultHttpEntity(),
                    WeatherVO.class);

            WeatherVO weatherResponse = response.getBody();

            logger.info("I=Clima da cidade encontrado, city={}, weather={}", city, weatherResponse);
            return weatherResponse;
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

    private HttpEntity<?> createDefaultHttpEntity() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
