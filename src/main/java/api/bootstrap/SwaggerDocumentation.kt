package api.bootstrap

import org.springframework.context.annotation.Configuration
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import org.springframework.context.annotation.Bean
import springfox.documentation.spring.web.plugins.Docket



@Configuration
@EnableSwagger2
class SwaggerDocumentation {

    @Bean
    fun mainConfig(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
    }
}