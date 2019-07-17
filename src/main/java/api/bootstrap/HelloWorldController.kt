package api.bootstrap

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @GetMapping("/helloWorld")
    fun helloWorld() = "HelloWorld"
}