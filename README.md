# pathes-layout

## API mockup for quest creator 
API that alowe to manupulate quests in quest creator database.

## Quick start
You need to run server, then you can call APIs commaands.
In first call of `localhost:8080` this will call a simple `/login` HTML page.
You can login or registrate new account if you have not.
After logining you can go to simple HTML page `/quest` where you can create new quest and points to this quest.
If you want use APIs commands you have to call it like this `localhost:8080/api/v1/*`

## Present endpoints
    Quest endpoints :
    - GET api/v1/quest
    - GET api/v1/quest/my
    - GET api/v1/
    
## Built With
* [Spring Boot](https://spring.io/projects/spring-boot) - "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run"."(c)
* [Neo4J](https://neo4j.com/) - graph database

## Run
 You need to have installed mongoDB on your computer, then make maven update and download all dependecies (if you work with Intellij)
 After it you can just run.
    
