### Step 1 : Hello World
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


### Step 2 : Listing french football teams
Those teams are available on http://api.football-data.org/v2/competitions/2015/teams with a GET method
and the header ('X-Auth-Token', '1d6f36da87a84ef8aa34ea2d5118d187')

 - create a Team data class with the fields you want to return
 - create a new kotlin class `TeamsRepository`
 - add a `RestTemplate` in its constructor to be able to make http requests
 - create the headers with
 ```
 HttpHeaders().apply { set("X-Auth-Token", "1d6f36da87a84ef8aa34ea2d5118d187") }
 ```
 - create a `Response` data class with the same structure as the json returned by api.football-data.org
 - use the `RestTemplate` to do the call with
 ```
 restTemplate.exchange(url, HttpMethod.GET, HttpEntity<Any>(headers), Response::class.java).body
 ```
 - then you can map the `Response` object into a list of `Team`

Finaly, in order to be able to use this repository
 - inject it into your controller by passing it on the constructor
 - add the `@Component` annotation on top of your repository class so Spring can now how to provide it
 - add the `jacksonObjectMapper` to your project
 ```
 implementation "com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+"
 ```
 - in your application class, provide the RestTemplate and the ObjectMapper we will use
 ```
    @Bean
    fun restTemplate() = RestTemplate()

    @Bean
    fun objectMapper(): ObjectMapper = jacksonObjectMapper()
 ```
 - run your app and test your new endpoint


