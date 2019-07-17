package api.bootstrap

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class BootstrappApi {

    @Bean
    fun restTemplate() = RestTemplate()

    @Bean
    fun objectMapper(): ObjectMapper = jacksonObjectMapper()

}

fun main(args: Array<String>) {
    SpringApplication.run(BootstrappApi::class.java, *args)
}
