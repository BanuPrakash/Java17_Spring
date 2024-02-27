package com.adobe.orderapp.cfg;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class ObserveConfig {
    @Bean
    ObservedAspect observedAspect(ObservationRegistry register) {
    return new ObservedAspect(register);
    }
}
