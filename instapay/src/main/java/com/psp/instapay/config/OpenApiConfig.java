package com.psp.instapay.config;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "InstaPay(PSP) REST API Documentation",
                description = "API documentation for InstaPay(PSP) Service",
                version = "v1.0.0",
                contact = @Contact(
                        name = "Zyad Mohammad",
                        email = "zyadmohammad00@gmail.com",
                        url = "https://www.linkedin.com/in/zyad-mohammad-ce/"
                ),
                license = @License(
                        name = "Apache License 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        security = @SecurityRequirement(
                name = "bearerAuth",
                scopes = {"read", "write"}
        ),
        externalDocs = @ExternalDocumentation(
                description = "GitHub Repository",
                url = "https://github.com/ZiadMohamed9/Payment-service-provider-PSP-Instapay-clone"
        )
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}