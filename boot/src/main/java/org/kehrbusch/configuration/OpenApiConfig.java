package org.kehrbusch.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.*;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.stream.Stream;

@Configuration
@OpenAPIDefinition(
        servers = @Server(url = "https://www.glasp-api.eu"),
        info = @Info(title = "GLASP REST API",
                version = "v1.00",
                description = "GLASP's REST API",
                contact = @Contact(name = "Support", url = "www.glasp.eu", email = "glasp.info@gmail.com"),
                license = @License(name = "MIT", url = "https://spdx.org/licenses/MIT"),
                termsOfService = "https:/glasp.eu/terms-of-service"))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
    @Bean
    public OpenApiCustomiser sortOperationsByTagName() {
        return openApi -> {
            Paths paths = openApi.getPaths().entrySet()
                    .stream()
                    .sorted(Comparator.comparing(entry -> getOperationTag(entry.getValue())))
                    .collect(Paths::new, (map, item) -> map.addPathItem(item.getKey(), item.getValue()), Paths::putAll);

            openApi.setPaths(paths);
        };
    }

    private String getOperationTag(PathItem pathItem) {
        return Stream.of(pathItem.getGet(), pathItem.getPost(), pathItem.getDelete(), pathItem.getPut(),
                        pathItem.getHead(), pathItem.getOptions(), pathItem.getTrace(), pathItem.getPatch())
                .filter(Objects::nonNull)
                .map(Operation::getTags)
                .flatMap(Collection::stream)
                .findFirst()
                .orElse("");
    }
}
