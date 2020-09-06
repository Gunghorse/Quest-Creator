# pathes-layout

## API mockup for quest creator 
API that alowe to manupulate quests in quest creator database.

### Present endpoints:
    Quest endpoints:
    - GET     /quest/
    - GET     /quest/get/{questID}
    - POST    /quest/create
    - POST    /quest/point/create
    - DELETE  /quest/point/delete/{pointID}
    - DELETE  /quest/delete/{questID}
    - GET     /quest/get/points
    - POST    /quest/add/point/{questID}
    - PUT     /quest/update/{questID}?title=New Title&description=Wow its new too
    - PUT     /quest/update/point/{pointID}?title=New Title&status=visible
    - GET     /quest/session/{userID}
    - POST    /quest/session/start/{questID}
    - POST    /quest/session/end/{sessionID}
    - GET     /quest/near/{pointID}
    - GET     /quest/isOnPoint?coordinates=50.065514,19.941228
    - GET     /quest/pointsInRadius?coordinates=50.065514,19.941228&radius=150
    
    User endpoints:
    - GET     /user/
    - POST    /user/login
    - POST    /user/register
    
## Built With
* [Spring Boot](https://spring.io/projects/spring-boot) - "Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can "just run"."(c)
* [MongoDB](https://www.mongodb.com/) - noSQL database

## Run
 You need to have installed mongoDB on your computer, then make maven update and download all dependecies (if you work with Intellij)
 After it you can just run.
    
