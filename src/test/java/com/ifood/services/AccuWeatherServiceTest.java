package com.ifood.services;

import com.ifood.exceptions.WeatherCityNotFoundException;
import com.ifood.services.impl.AccuWeatherServiceImpl;
import com.ifood.vos.AccuWeatherCityVO;
import com.ifood.vos.AccuWeatherVO;
import com.ifood.vos.OpenWeatherMapWeatherVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccuWeatherServiceTest {

    @InjectMocks
    private final AccuWeatherServiceImpl weatherService = new AccuWeatherServiceImpl();

    @Mock
    RestTemplate restTemplate;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(weatherService, "apiKey", "apiKey");
        ReflectionTestUtils.setField(weatherService, "baseUrl", "http://teste");
    }

    @Test
    public void getWeatherSuccess() {

        AccuWeatherCityVO locationKeyResponse = new AccuWeatherCityVO();
        locationKeyResponse.setKey("123");
        List<AccuWeatherCityVO> locLlist = new ArrayList<>();
        locLlist.add(locationKeyResponse);
        ResponseEntity<List<AccuWeatherCityVO>> locResponseEntity = new ResponseEntity<>(locLlist, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherCityVO>>(){})))
                .thenReturn(locResponseEntity);

        AccuWeatherVO response = new AccuWeatherVO();
        response.setWeatherText("Teste");
        List<AccuWeatherVO> respList = new ArrayList<>();
        respList.add(response);
        ResponseEntity<List<AccuWeatherVO>> responseEntity = new ResponseEntity<>(respList, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherVO>>(){})))
                .thenReturn(responseEntity);

        weatherService.getWeather("city");

        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherCityVO>>(){}));
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherVO>>(){}));
    }


    @Test(expected = WeatherCityNotFoundException.class)
    public void getWeatherLocationKeyResponseNull() {

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherCityVO>>(){})))
                .thenReturn(null);

        weatherService.getWeather("city");

        //verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }

    @Test(expected = WeatherCityNotFoundException.class)
    public void getWeatherLocationKeyResponseEmpty() {

        ResponseEntity<List<AccuWeatherCityVO>> locResponseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.ACCEPTED);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherCityVO>>(){})))
                .thenReturn(locResponseEntity);

        weatherService.getWeather("city");

        //verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }

    @Test(expected = WeatherCityNotFoundException.class)
    public void getWeatherLocationNotFoundException() {

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherCityVO>>(){})))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        weatherService.getWeather("city");

        //verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }

    @Test(expected = Exception.class)
    public void getWeatherLocationGenericException() {

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherCityVO>>(){})))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        weatherService.getWeather("city");

        //verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }

    @Test(expected = WeatherCityNotFoundException.class)
    public void getWeatherNullResponse() {

        AccuWeatherCityVO locationKeyResponse = new AccuWeatherCityVO();
        locationKeyResponse.setKey("123");
        List<AccuWeatherCityVO> locLlist = new ArrayList<>();
        locLlist.add(locationKeyResponse);
        ResponseEntity<List<AccuWeatherCityVO>> locResponseEntity = new ResponseEntity<>(locLlist, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherCityVO>>(){})))
                .thenReturn(locResponseEntity);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class)))
                .thenReturn(null);

        weatherService.getWeather("city");

        //verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }


    @Test(expected = WeatherCityNotFoundException.class)
    public void getWeatherEmptyResponse() {

        AccuWeatherCityVO locationKeyResponse = new AccuWeatherCityVO();
        locationKeyResponse.setKey("123");
        List<AccuWeatherCityVO> locLlist = new ArrayList<>();
        locLlist.add(locationKeyResponse);
        ResponseEntity<List<AccuWeatherCityVO>> locResponseEntity = new ResponseEntity<>(locLlist, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherCityVO>>(){})))
                .thenReturn(locResponseEntity);

        ResponseEntity<List<AccuWeatherVO>> responseEntity = new ResponseEntity<>(new ArrayList<>(), HttpStatus.ACCEPTED);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherVO>>(){})))
                .thenReturn(responseEntity);

        weatherService.getWeather("city");

        //verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }

    @Test(expected = WeatherCityNotFoundException.class)
    public void getWeatherCityNotFound() {

        AccuWeatherCityVO locationKeyResponse = new AccuWeatherCityVO();
        locationKeyResponse.setKey("123");
        List<AccuWeatherCityVO> locLlist = new ArrayList<>();
        locLlist.add(locationKeyResponse);
        ResponseEntity<List<AccuWeatherCityVO>> locResponseEntity = new ResponseEntity<>(locLlist, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherCityVO>>(){})))
                .thenReturn(locResponseEntity);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherVO>>(){})))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        weatherService.getWeather("city");

        //verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }

    @Test(expected = HttpClientErrorException.class)
    public void getWeatherGenericHttpClientErrorException() {

        AccuWeatherCityVO locationKeyResponse = new AccuWeatherCityVO();
        locationKeyResponse.setKey("123");
        List<AccuWeatherCityVO> locLlist = new ArrayList<>();
        locLlist.add(locationKeyResponse);
        ResponseEntity<List<AccuWeatherCityVO>> locResponseEntity = new ResponseEntity<>(locLlist, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherCityVO>>(){})))
                .thenReturn(locResponseEntity);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherVO>>(){})))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        weatherService.getWeather("city");

        //verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }

    @Test(expected = Exception.class)
    public void getWeatherGenericException() {

        AccuWeatherCityVO locationKeyResponse = new AccuWeatherCityVO();
        locationKeyResponse.setKey("123");
        List<AccuWeatherCityVO> locLlist = new ArrayList<>();
        locLlist.add(locationKeyResponse);
        ResponseEntity<List<AccuWeatherCityVO>> locResponseEntity = new ResponseEntity<>(locLlist, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherCityVO>>(){})))
                .thenReturn(locResponseEntity);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(new ParameterizedTypeReference<List<AccuWeatherVO>>(){})))
                .thenThrow(new RuntimeException());

        weatherService.getWeather("city");

        //verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }
}
