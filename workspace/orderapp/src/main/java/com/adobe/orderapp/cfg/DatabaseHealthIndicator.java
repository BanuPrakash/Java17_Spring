package com.adobe.orderapp.cfg;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


//http://localhost:8080/actuator/health/Database
@Component("Database")
@RequiredArgsConstructor
public class DatabaseHealthIndicator implements HealthIndicator, HealthContributor {
    private final DataSource dataSource;

    @Override
    public Health health() {
        try (Connection con  = dataSource.getConnection()){
            Statement statement = con.createStatement();
            statement.executeQuery("select * from products");
        } catch (SQLException ex) {
            return Health.outOfService().withException(ex).build();
        }
        return Health.up().build();
    }
}
