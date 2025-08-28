package com.pesicvladica.expensetracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;

@Configuration
public class ObjectParsingConfiguration {

    @Bean
    public HalConfiguration halConfiguration() {
        return new HalConfiguration()
                .withRenderSingleLinks(HalConfiguration.RenderSingleLinks.AS_ARRAY);
    }
}
