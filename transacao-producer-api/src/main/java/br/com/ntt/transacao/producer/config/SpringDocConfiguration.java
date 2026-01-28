package br.com.ntt.transacao.producer.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer").bearerFormat("JWT")))
                .addServersItem(new Server()
                        .url("http://localhost:8081")
                        .description("Local Server"))
                .addServersItem(new Server()
                        .url("https://api.transacao-producer-api.prd")
                        .description("Production Server"))
                .info(new Info()
                        .title("API Transacao Producer API")
                        .version("1.0.0")
                        .description("API Transacao Producer API")
                        .contact(new Contact()
                                .name("Jess Mach")
                                .email("machadojessica1997@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
