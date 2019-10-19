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

### Step 3 : sort your teams

 - in your `TeamsController` you can add a String parameter in the method annotated with `@GetMapping`
 - annotate this params with `@RequestParam(value = "sort")` to tell Spring that you will use it as a url query param
 - sort the result from the `TeamRepository` using this param with standard Kotlin (like `sortBy`)


### Step 4 : List players from a team
Those teams are available on http://api.football-data.org/v2/teams/{teamId} with a GET method
and the header ('X-Auth-Token', '1d6f36da87a84ef8aa34ea2d5118d187')

 - create a new controller for players
 - add a method with an Int param for the id an specified the path as "/teams/{id}/players"
 - annotate this param with `@PathVariable` so Spring knows he can retreive it in the url path
 - create a PlayersRepository like in step 2 and return those players

In order to add a specified error return code when team id is not found:
 - create a `TeamNotFoundException` extending `RuntimeException`
 - in `PlayersRepository` throw a `TeamNotFoundException` when an error occurs
 - create a new class `PlayersControllerAdvice` and annotate it with `@ControllerAdvice` so Spring knows this class will handle errors
 - add a method for handling `TeamNotFoundException` and specify the error code and message you want
 ```
 @ExceptionHandler(TeamNotFoundException::class)
     fun invalidParams(exc: TeamNotFoundException): ResponseEntity<ApiError> {
         return ResponseEntity.status(HttpStatus.NOT_FOUND)
             .contentType(MediaType.APPLICATION_JSON)
             .body("No team found for this id")

     }
 ```

### Step 5 : add some documentation
We'll here create a swagger documentation for our API and to do so, we'll need two more imports
```
    implementation "io.springfox:springfox-swagger2:2.9.2"
```
will create the endpoint documenting the API at : http://localhost:8080/v2/api-docs

```
    implementation "io.springfox:springfox-swagger-ui:2.9.2"
```
will create the page display the API documentation at : http://localhost:8080/swagger-ui.html

We just need to add some configuration, with a new class :
```
@Configuration
@EnableSwagger2
class SwaggerDocumentation {

    @Bean
    fun config() : Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
    }
}
```
Here we can select the endpoints and path we want to hide or show.