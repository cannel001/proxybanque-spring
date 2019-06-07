/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.swagger;

import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.or;
import org.springframework.context.annotation.Bean;
import springfox.documentation.service.Contact;

/**
 *
 * @author cannel
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo()).select().paths((com.google.common.base.Predicate<String>) postPaths()).build();
    }

    private Predicate<String> postPaths() {
        return or(regex("/api.*"), regex("/api.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Proxy Banque API")
                .description("Proxy banque API reference for developers")
                .termsOfServiceUrl("http://objis.com")
                .contact(contact).license("Team ProxyBanque")
                .licenseUrl("cannelseka@gmail.com").version("1.0").build();
    }

    Contact contact = new Contact("Seka Cannel", "objis.com", "cannelseka@@gmail.com");
    
}
