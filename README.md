This repository is build with 7 branches, representing a step by step build of a simple api based on Springboot and Kotlin.
During the project, we will build two endpoint listing some Football teams and Football players

master : 
 - contains only gradle imports needed to start a project Springbook/Kotlin
 
1-hello-world :
  - we initialise the Springboot application and build the first endpoint : /helloworld (who will respond helloworld)

2-teams-listing : 
 - we build the first "real" endpoint, retreiving some teams on "http://api.football-data.org/v2/competitions/2015/teams"
   and returning them into /teams

3-teams-filter :
 - we add a sorting param on /teams and use it to structure our code

4-players-listing : 
 - we build a second endpoint, /teams/{id}/players, using the id of /teams to list some players with "http://api.football-data.org/v2/teams/527"
 - we add our first error handling for when we enter an id we do not know

5-swagger-documentation : 
 - we add a beutiful swagger documentation of our project, accessible on /swagger-ui.html

6-added-unit-and-integration-test : 
 - we add some unit tests, and integrations test on our controller and repository
