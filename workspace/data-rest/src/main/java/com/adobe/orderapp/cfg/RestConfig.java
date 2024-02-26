package com.adobe.orderapp.cfg;

import com.adobe.orderapp.entity.Product;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Component
public class RestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
//        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
        cors.addMapping("/products").allowedOrigins("http://abc.com")
                .allowedMethods("GET", "POST");
        ExposureConfiguration configuration = config.getExposureConfiguration();
        configuration.forDomainType(Product.class)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(HttpMethod.DELETE));
        };
    }

