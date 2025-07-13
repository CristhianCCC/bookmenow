package com.bookmenowparent.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

//setting up the routes
@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                //route for service-------------------------------------------------------------------------------------
                .route("catalog-service", r -> r
                        .path("/catalogs/**")
                        .uri("lb://catalog")
                )
                //route for booking-------------------------------------------------------------------------------------
                .route("booking-service", r -> r
                        .path("/booking/**")
                        .uri("http://localhost:8062")
                )
                //route for review--------------------------------------------------------------------------------------
                .route("review-service", r -> r
                        .path("/review/**")
                        .uri("http://localhost:8063")
                )

                //route for payment-------------------------------------------------------------------------------------
                .route("payment-service", r -> r
                        .path("/payment/**")
                        .uri("http://localhost:8064")
                )

                //route for notification--------------------------------------------------------------------------------
                .route("notification-service", r -> r
                        .path("/notification/**")
                        .uri("http://localhost:8065")
                )
                // auth route for login/signup -------------------------------------------------------------------------
                .route("auth-route", r -> r
                        .path("/auth/**")
                        .uri("lb://user") //name of the micro service
                )
                //authorization path for user (validation)--------------------------------------------------------------
                .route(p -> p
                        .path("/users/**")
                        .filters(f -> f.addRequestHeader("user-service", "Request"))
                        .uri("lb://user")) //name of the micro service
                .build();
    }
}
