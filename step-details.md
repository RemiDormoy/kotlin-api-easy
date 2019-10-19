### step 1 : Hello World
First, we'll need to create an empty SpringBoot application.
 - create a first kotlin file under src/main/java/package.name
 - add a main function :
 ```
 fun main(args: Array<String>) {
     SpringApplication.run(YourApiName::class.java, *args)
 }
 ```
 - create a class annotated as a `@SpringBootApplication`
 - run your main function

Then, to create our first endpoint:
 - add a new kotlin class like `HelloWorldController`
 - annotate it with `@RestController`
 - add a method and annotate it with `@GetMapping("/myPath")` so Spring will know you're building a get endpoint
 - make your method return some content
 - build your app, and test http://localhost:8080/myPath in your browser

