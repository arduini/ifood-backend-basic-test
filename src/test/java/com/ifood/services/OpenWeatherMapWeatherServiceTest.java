package com.ifood.services;

import com.ifood.exceptions.WeatherCityNotFoundException;
import com.ifood.services.impl.OpenWeatherMapWeatherServiceImpl;
import com.ifood.vos.OpenWeatherMapWeatherVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenWeatherMapWeatherServiceTest {

    @InjectMocks
    private final OpenWeatherMapWeatherServiceImpl weatherService = new OpenWeatherMapWeatherServiceImpl();

    @Mock
    RestTemplate restTemplate;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(weatherService, "appId", "appId");
        ReflectionTestUtils.setField(weatherService, "baseUrl", "http://teste");
    }

    @Test
    public void getWeatherSuccess() {

        OpenWeatherMapWeatherVO response = new OpenWeatherMapWeatherVO();
        response.setId(1L);
        response.setName("Teste");
        ResponseEntity<OpenWeatherMapWeatherVO> responseEntity = new ResponseEntity<>(response, HttpStatus.ACCEPTED);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class)))
                .thenReturn(responseEntity);

        weatherService.getWeather("city");

        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }

    @Test(expected = WeatherCityNotFoundException.class)
    public void getWeatherNullResponse() {

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class)))
                .thenReturn(null);

        weatherService.getWeather("city");

//        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }

    @Test(expected = WeatherCityNotFoundException.class)
    public void getWeatherCityNotFound() {

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        weatherService.getWeather("city");

//        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }

    @Test(expected = HttpClientErrorException.class)
    public void getWeatherGenericHttpClientErrorException() {

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        weatherService.getWeather("city");

//        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }

    @Test(expected = Exception.class)
    public void getWeatherGenericException() {

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class)))
                .thenThrow(new RuntimeException());

        weatherService.getWeather("city");

//        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), any(), eq(OpenWeatherMapWeatherVO.class));
    }
}
