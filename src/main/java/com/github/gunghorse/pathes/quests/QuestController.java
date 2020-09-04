package com.github.gunghorse.pathes.quests;

import com.github.gunghorse.pathes.sessions.Session;
import com.github.gunghorse.pathes.sessions.SessionRepository;
import com.github.gunghorse.pathes.user.User;
import com.github.gunghorse.pathes.user.UserRepository;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.bind.annotation.*;

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

    /**
     *  TODO: figured out why $near query did not work
     * @param pointID
     * @return
     */
    /*@RequestMapping(value = "/near/{pointID}", method = RequestMethod.GET)
    public GeoResults<QuestPoint> getNearestPoint(@PathVariable String pointID){
        Distance distance = new Distance(0.3, Metrics.KILOMETERS);
        GeoJsonPoint currentPoint = questPointRepository.findById(pointID).get().getLocation();
        GeoResults<QuestPoint> questPoints = questPointRepository.findByLocationNear(currentPoint,
                distance);
        return questPoints;
    }*/


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


    @RequestMapping(value = "/session/{userID}", method = RequestMethod.GET)
    public List<Session> getUserSessions(@PathVariable String userID){
        return sessionRepository.findByUser(userRepository.findByLogin(userID));
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
}
