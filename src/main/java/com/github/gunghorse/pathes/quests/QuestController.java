package com.github.gunghorse.pathes.quests;

import com.github.gunghorse.pathes.sessions.Session;
import com.github.gunghorse.pathes.sessions.SessionRepository;
import com.github.gunghorse.pathes.user.User;
import com.github.gunghorse.pathes.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/quest")
public class QuestController {

    private QuestRepository questRepository;
    private QuestPointRepository questPointRepository;
    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    public QuestController(QuestRepository questRepository,
                           QuestPointRepository questPointRepository,
                           SessionRepository sessionRepository,
                           UserRepository userRepository) {
        this.questRepository = questRepository;
        this.questPointRepository = questPointRepository;
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Quest> getAllQuests(){
        return questRepository.findAll();
    }

    @RequestMapping(value = "/get/{questID}", method = RequestMethod.GET)
    public Quest getQuest(@PathVariable String questID){
        return questRepository.findById(questID).get();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createQuest(@RequestBody Quest quest){
        questRepository.save(quest);
    }


    @RequestMapping(value = "/point/delete/{pointID}", method = RequestMethod.DELETE)
    public void deletePoint(@PathVariable String pointID){
        questPointRepository.deleteById(pointID);
    }

    @RequestMapping(value = "/delete/{questID}")
    public void deleteQuest(@PathVariable String questID){
        Quest quest = questRepository.findById(questID).get();
        for (QuestPoint point : quest.getQuestPoints())
            questPointRepository.delete(point);
        questRepository.delete(quest);
    }

    @RequestMapping(value = "/get/points", method = RequestMethod.GET)
    public List<QuestPoint> getAllQuestPoints(){
        return questPointRepository.findAll();
    }

    @RequestMapping(value = "/add/point/{questID}", method = RequestMethod.POST)
    public void addPoint(@PathVariable String questID, @RequestBody QuestPoint questPoint){
        Quest quest = questRepository.findById(questID).get();
        questPoint.setQuestID(questID);
        questPointRepository.save(questPoint);
        QuestPoint questPointSaved = questPointRepository.findByQuestIDAndTitleAndLocation(
                questID, questPoint.getTitle(), questPoint.getLocation()).get(0);
        quest.addQuestPoint(questPointSaved);
        questRepository.save(quest);
    }


    /**
     * BUG: if quest is deleted Jackson could not convert to json
     * @param userID
     * @return
     */
    @RequestMapping(value = "/session/{userID}", method = RequestMethod.GET)
    public List<Session> getUserSessions(@PathVariable String userID){
        List<Session> sessionsOfUser = sessionRepository.findByUser(userRepository.findByLogin(userID));
        return sessionsOfUser;
    }

    @RequestMapping(value = "/start/session/{questID}", method = RequestMethod.POST)
    public boolean startSession(@PathVariable String questID, @RequestBody User user){
        Quest startedQuest = questRepository.findById(questID).get();
        User userInGame = userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if(userInGame != null){
            sessionRepository.save(new Session(userInGame, startedQuest));
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/session/end/{sessionID}", method = RequestMethod.POST)
    public void endSession(@PathVariable String sessionID){
        Session endedSession = sessionRepository.findById(sessionID).get();
        endedSession.setEnded(true);
        sessionRepository.save(endedSession);
    }


    @RequestMapping(value = "/near/{pointID}", method = RequestMethod.GET)
    public List<QuestPoint> getNearestPoint(@PathVariable String pointID){
        GeoJsonPoint currentPoint = questPointRepository.findById(pointID).get().getLocation();
        List<QuestPoint> questPoints = questPointRepository.findByLocationNear(currentPoint,
                300);
        return questPoints;
    }

    /**
     * URL looks like:
     *      /quest/isOnPoint?coordinates=50.065514,19.941228
     *
     * @param coordinates
     * @return
     */
    @RequestMapping(value = "/isOnPoint", method = RequestMethod.GET)
    public boolean isOnPoint(@RequestParam double[] coordinates){
        List<QuestPoint> questPoints = questPointRepository
                .findByLocationNear(new GeoJsonPoint(coordinates[0], coordinates[1]), 6);
        return !questPoints.isEmpty();
    }

    /**
     *  URL looks like:
     *         /quest/pointsInRadius?coordinates=50.065514,19.941228&radius=150
     *
     * @param coordinates
     * @param radius
     * @return
     */
    @RequestMapping(value = "/pointsInRadius", method = RequestMethod.GET)
    public List<QuestPoint> getPointsInRadius(@RequestParam double[] coordinates, @RequestParam int radius){
        List<QuestPoint> questPoints = questPointRepository
                .findByLocationNear(new GeoJsonPoint(coordinates[0], coordinates[1]), radius);
        return questPoints;
    }

}
