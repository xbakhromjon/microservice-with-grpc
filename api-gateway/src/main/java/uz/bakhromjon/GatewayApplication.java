package uz.bakhromjon;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("s1", r -> r.path("/s1/api/**")
                        .uri("http://localhost:8081"))
                .route("s2", r -> r.path("/s2/api/**")
                        .uri("http://localhost:8082"))
                .route("s3", r -> r.path("/s3/api/**")
                        .uri("http://localhost:8083"))
                .build();
    }
}