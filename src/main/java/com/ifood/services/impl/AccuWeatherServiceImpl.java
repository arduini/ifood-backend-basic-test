package com.ifood.services.impl;

import com.ifood.exceptions.WeatherCityNotFoundException;
import com.ifood.services.WeatherService;
import com.ifood.vos.AccuWeatherCityVO;
import com.ifood.vos.AccuWeatherVO;
import com.ifood.vos.WeatherVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service("AccuWeather")
public class AccuWeatherServiceImpl implements WeatherService {

    private final Logger logger = LoggerFactory.getLogger(AccuWeatherServiceImpl.class);

    private final static String WEATHER_SOURCE = "AccuWeather";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${accuweather.api.key}")
    private String apiKey;

    @Value("${accuweather.url}")
    private String baseUrl;

    @Cacheable(value = "accuWeatherByCity", key = "#city")
    public Optional<WeatherVO> getWeather(String city) {

        Long locationKey = getLocationKeyByCityName(city);

        logger.info("I=Pesquisando clima por locationKey da cidade, locationKey={}", locationKey);
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "currentconditions/v1/{locationKey}?apikey={apiKey}")
                .buildAndExpand(locationKey, apiKey);

        try {

            HttpEntity<List<AccuWeatherVO>> response = restTemplate.exchange(
                    uri.toUriString(),
                    HttpMethod.GET,
                    createDefaultHttpEntity(),
                    new ParameterizedTypeReference<List<AccuWeatherVO>>(){});

            if (response == null) {
                logger.info("I=Clima nao encontrado por locationId, city={}, locationId={}", city, locationKey);
                throw new WeatherCityNotFoundException("Cidade não encontrada");
            }

            List<AccuWeatherVO> weatherResponse = response.getBody();

            if (weatherResponse == null || weatherResponse.size() < 1) {
                logger.info("I=Clima nao encontrado por locationId, city={}, locationId={}", city, locationKey);
                throw new WeatherCityNotFoundException("Cidade não encontrada");
            }

            logger.info("I=Clima da cidade encontrado, city={}, weather={}", city, weatherResponse);
            return Optional.ofNullable(translateToGenericWeatherVO(weatherResponse.get(0)));
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

    private Long getLocationKeyByCityName(String city) {

        logger.info("I=Pesquisando locationKey pelo nome da cidade, city={}", city);
        UriComponents uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "locations/v1/cities/search?apikey={appKey}&q={city}")
                .buildAndExpand(apiKey, city);

        try {

            HttpEntity<List<AccuWeatherCityVO>> response = restTemplate.exchange(
                    uri.toUriString(),
                    HttpMethod.GET,
                    createDefaultHttpEntity(),
                    new ParameterizedTypeReference<List<AccuWeatherCityVO>>(){});


            if (response == null) {
                logger.info("I=LocationId nao encontrado, city={}", city);
                throw new WeatherCityNotFoundException("Cidade não encontrada");
            }

            List<AccuWeatherCityVO> weatherResponse = response.getBody();

            if (weatherResponse == null || weatherResponse.size() < 1) {
                logger.info("I=LocationId nao encontradp, city={}", city);
                throw new WeatherCityNotFoundException("Cidade não encontrada");
            }

            return Long.valueOf(weatherResponse.get(0).getKey());
        }
        catch (HttpClientErrorException e) {

            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {

                logger.info("I=LocationId nao encontrado, city={}", city);
                throw new WeatherCityNotFoundException("Cidade não encontrada");
            }

            logger.error("E=Erro ao pesquisar locationId pelo nome da cidade, city={}", city, e);
            throw e;
        }
        catch (Exception e) {

            logger.error("E=Erro ao pesquisar locationId pelo nome da cidade, city={}", city, e);
            throw e;
        }
    }

    private HttpEntity<?> createDefaultHttpEntity() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }


    private WeatherVO translateToGenericWeatherVO(AccuWeatherVO accuWeatherVO) {

        if(accuWeatherVO == null) {

            return null;
        }

        WeatherVO weatherVO = new WeatherVO();

        if (accuWeatherVO.getTemperature() != null && accuWeatherVO.getTemperature().getMetric() != null) {

            weatherVO.setTemperature(BigDecimal.valueOf(accuWeatherVO.getTemperature().getMetric().getValue()));
        }

        weatherVO.setHumidity(accuWeatherVO.getRelativeHumidity());
        weatherVO.setDescription(accuWeatherVO.getWeatherText());
        weatherVO.setSource(WEATHER_SOURCE);

        return weatherVO;
    }
}
