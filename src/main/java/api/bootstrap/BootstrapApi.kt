package api.bootstrap

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class BootstrappApi {

}

fun main(args: Array<String>) {
    SpringApplication.run(BootstrappApi::class.java, *args)
}
