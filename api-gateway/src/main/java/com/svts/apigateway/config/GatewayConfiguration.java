package com.svts.apigateway.config;

import com.svts.apigateway.filter.AuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@Configuration
@Slf4j
public class GatewayConfiguration {

    @Bean
    public CorsWebFilter corsWebFilter() {

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOriginPattern("*");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, AuthenticationFilter authenticationFilter) {

        AuthenticationFilter.Config config = new AuthenticationFilter.Config();
        GatewayFilter authFilter = authenticationFilter.apply(config);
        CorsPostFilter corsPostFilter = new CorsPostFilter();
        return builder.routes()

                // API Gateway -> Appointment Service
                .route("proxy-appointment-service 1", r -> r.path("/proxy-appointment/**")
                        .filters(f -> f.rewritePath("/proxy-appointment/(?<segment>.*)", "/appointment/${segment}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE")
                                .filter(corsPostFilter))
                        .uri("lb://APPOINTMENT-SERVICE"))

                // API Gateway -> Appointment Service
                .route("proxy-appointment-service 2", r -> r.path("/proxy-patient/**")
                        .filters(f -> f.rewritePath("/proxy-patient/(?<segment>.*)", "/patient/${segment}")
                                .dedupeResponseHeader("Access-Control-Allow-Origin", "RETAIN_UNIQUE")
                                .dedupeResponseHeader("Access-Control-Allow-Credentials", "RETAIN_UNIQUE")
                                .filter(corsPostFilter))
                        .uri("lb://APPOINTMENT-SERVICE"))
                .build();
    }
}
