package com.ifood;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author s2it_aoliveira
 * @version $Revision: $<br/>
 * $Id: $
 * @since 21/01/19 12:19
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
