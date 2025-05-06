package com.jm_admin_management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.server.url1}")
    private String serverUrl1;
    @Value("${swagger.server.desc1}")
    private String serverDesc1;
    @Value("${swagger.server.url2}")
    private String serverUrl2;
    @Value("${swagger.server.desc2}")
    private String serverDesc2;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Job Matrix Admin Management API")
                        .version("1.0")
                        .description("API documentation for the Admin Management Microservice"))
                .servers(List.of(
                        new Server().url(serverUrl1).description(serverDesc1),
                        new Server().url(serverUrl2).description(serverDesc2)
                ));
    }
}
