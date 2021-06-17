package io.project.app.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
@EnableEurekaClient
public class GatewayApplication {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(GatewayApplication.class);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.run(args);
    }

    @Bean
    public RouteLocator toAccount(RouteLocatorBuilder builder) {
        log.info("Request to account");
        return builder.routes()
                .route("account_route", r -> r
                .path("/account/api/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://account/")
                )
                .build();
    }

    @Bean
    public RouteLocator toFriend(RouteLocatorBuilder builder) {
        log.info("Request to Friend");
        return builder.routes()
                .route("friend_route", r -> r
                .path("/friend/api/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://friend/")
                )
                .build();
    }

}
