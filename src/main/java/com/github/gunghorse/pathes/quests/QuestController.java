package com.github.gunghorse.pathes.quests;

import com.github.gunghorse.pathes.sessions.Session;
import com.github.gunghorse.pathes.sessions.SessionRepository;
import com.github.gunghorse.pathes.user.User;
import com.github.gunghorse.pathes.user.UserRepository;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quest")
public class QuestController {

    private final QuestRepository questRepository;
    private final QuestPointRepository questPointRepository;
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;

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
     * URL looks like:
     *      GET /quest/
     *
     * @return List of all quests
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Quest> getAllQuests(){
        return questRepository.findAll();
    }

    /**
     * URL looks like:
     *      GET /quest/get/{questID}
     *
     * Get quest by its id
     *
     * @param questID id of quest which needed to be got
     * @return Quest object
     */
    @RequestMapping(value = "/get/{questID}", method = RequestMethod.GET)
    public Quest getQuest(@PathVariable String questID){
        return questRepository.findById(questID).get();
    }

    /**
     * URL looks like:
     *      POST /quest/create
     *
     * Create new quest.
     * @param quest send object of Quest in POST body
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createQuest(@RequestBody Quest quest){
        questRepository.save(quest);
    }

    /**
     * TODO: need to test!
     * URL looks like:
     *      POST /quest/point/create
     *
     * Create new quest point.
     * @param quest send object of QuestPoint in POST body
     */
    @RequestMapping(value = "/point/create", method = RequestMethod.POST)
    public void createQuestPoint(@RequestBody QuestPoint quest){
        questPointRepository.save(quest);
    }

    /**
     * URL looks like:
     *      DELETE /quest/point/delete/{pointID}
     *
     * Delete questPoint by ID
     * @param pointID ID of point to delete
     */
    @RequestMapping(value = "/point/delete/{pointID}", method = RequestMethod.DELETE)
    public void deletePoint(@PathVariable String pointID){
        questPointRepository.deleteById(pointID);
    }

    /**
     * URL looks like:
     *      DELETE /quest/delete/{questID}
     *
     * Delete quest by ID
     * @param questID ID of quest to delete
     */
    @RequestMapping(value = "/delete/{questID}")
    public void deleteQuest(@PathVariable String questID){
        Quest quest = questRepository.findById(questID).get();
        for (QuestPoint point : quest.getQuestPoints())
            questPointRepository.delete(point);
        questRepository.delete(quest);
    }

    /**
     * URL looks like:
     *      GET /quest/get/points
     * Get all existed points
     * @return List of all points
     */
    @RequestMapping(value = "/get/points", method = RequestMethod.GET)
    public List<QuestPoint> getAllQuestPoints(){
        return questPointRepository.findAll();
    }

    /**
     * URL looks like:
     *      POST /quest/add/point/{questID}
     *
     * Add new point to quest
     *
     * @param questID ID of quest where we want to add point
     * @param questPoint sent object of Point in POST body
     */
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
     * URL looks like:
     *      PUT /quest/update/{questID}?title=New Title&description=Wow its new too
     *
     * Gives ability to edit points.
     * @param questID ID of editable quest
     * @param updateInfo pass new values as request parameters, you can edit such fields:
     *                   - title - change title of quest
     *                   - description - change description
     */
    @RequestMapping(value = "/update/{questID}", method = RequestMethod.PUT)
    public void updateQuest(@PathVariable String questID, @RequestParam Map<String, String> updateInfo){
        Quest questToUpdate = questRepository.findById(questID).get();
        if(updateInfo.containsKey("title"))
            questToUpdate.setTitle(updateInfo.get("title"));
        if(updateInfo.containsKey("description"))
            questToUpdate.setDescription(updateInfo.get("description"));
        questRepository.save(questToUpdate);
    }

    /**
     * URL looks like:
     *      PUT /quest/update/point/{pointID}?title=New Title&status=visible
     *
     * Gives ability to edit points.
     * @param pointID ID of editable point
     * @param updateInfo pass new values as request parameters, you can edit such fields:
     *                   - title - change title of point
     *                   - description - change description
     *                   - parentPointID - set parent point
     *                   - questID - relate with new quest
     *                   - status - change status of point
     *                   - lat - change latitude of point
     *                   - long - change longitude of point
     */
    @RequestMapping(value = "/update/point/{pointID}", method = RequestMethod.PUT)
    public void updatePoint(@PathVariable String pointID, @RequestParam Map<String, String> updateInfo){
        QuestPoint questPointToUpdate = questPointRepository.findById(pointID).get();
        if(updateInfo.containsKey("title"))
            questPointToUpdate.setTitle(updateInfo.get("title"));
        if(updateInfo.containsKey("description"))
            questPointToUpdate.setDescription(updateInfo.get("description"));
        if(updateInfo.containsKey("parentPointID"))
            questPointToUpdate.setParentPointID(updateInfo.get("parentPointID"));
        if(updateInfo.containsKey("questID"))
            questPointToUpdate.setParentPointID(updateInfo.get("questID"));
        if(updateInfo.containsKey("status"))
            questPointToUpdate.setParentPointID(updateInfo.get("status"));
        if(updateInfo.containsKey("lat")) {
            GeoJsonPoint loc = new GeoJsonPoint(
                    Double.parseDouble(updateInfo.get("lat")),
                    questPointToUpdate.getLocation().getY());
            questPointToUpdate.setLocation(loc);
        }
        if(updateInfo.containsKey("long")){
            GeoJsonPoint loc = new GeoJsonPoint(
                    questPointToUpdate.getLocation().getX(),
                    Double.parseDouble(updateInfo.get("long")));
            questPointToUpdate.setLocation(loc);
        }
        questPointRepository.save(questPointToUpdate);
    }


    /**
     * URL looks like:
     *      GET /quest/session/{userID}
     *
     * Get all sessions of specified user user
     *
     * BUG: if quest is deleted Jackson could not convert to json
     * @param userID id of user which sessions we want to get
     * @return list of sessions of current user
     */
    @RequestMapping(value = "/session/{userID}", method = RequestMethod.GET)
    public List<Session> getUserSessions(@PathVariable String userID){
        return sessionRepository.findByUser(userRepository.findByLogin(userID));
    }


    /**
     * URL looks like:
     *      POST /quest/session/start/{questID}
     *
     * @param questID id of quest which we want start to play
     * @param user object of User sent in POST body
     * @return boolean is session started or not
     */
    @RequestMapping(value = "/session/start/{questID}", method = RequestMethod.POST)
    public boolean startSession(@PathVariable String questID, @RequestBody User user){
        Quest startedQuest = questRepository.findById(questID).get();
        User userInGame = userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if(userInGame != null){
            sessionRepository.save(new Session(userInGame, startedQuest));
            return true;
        }
        return false;
    }

    /**
     * URL looks like:
     *     POST /quest/session/end/{sessionID}
     *
     * Ended session by its id
     * @param sessionID id of session that should be ended
     */
    @RequestMapping(value = "/session/end/{sessionID}", method = RequestMethod.POST)
    public void endSession(@PathVariable String sessionID){
        Session endedSession = sessionRepository.findById(sessionID).get();
        endedSession.setEnded(true);
        sessionRepository.save(endedSession);
    }

    /**
     * URL looks like:
     *     GET /quest/near/{pointID}
     *
     * @param pointID id of interested point
     * @return List of questPoints in 300 m radius near pointID
     */

    @RequestMapping(value = "/near/{pointID}", method = RequestMethod.GET)
    public List<QuestPoint> getNearestPoint(@PathVariable String pointID){
        GeoJsonPoint currentPoint = questPointRepository.findById(pointID).get().getLocation();
        return questPointRepository.findByLocationNear(currentPoint,
                300);
    }

    /**
     * TODO: make depended from specified user and session
     * URL looks like:
     *     GET /quest/isOnPoint?coordinates=50.065514,19.941228
     *
     * Serve to check is user coordinates are on any quest point from all points table;
     * Checked is in 6 m radius, which is a GPS mistake
     * @param coordinates array with two elements where first element is latitude and second is longitude
     * @return boolean is your coordinates on any points
     */
    @RequestMapping(value = "/isOnPoint", method = RequestMethod.GET)
    public boolean isOnPoint(@RequestParam double[] coordinates){
        List<QuestPoint> questPoints = questPointRepository
                .findByLocationNear(new GeoJsonPoint(coordinates[0], coordinates[1]), 6);
        return !questPoints.isEmpty();
    }

    /**
     *  URL looks like:
     *      GET   /quest/pointsInRadius?coordinates=50.065514,19.941228&radius=150
     *
     * @param coordinates first element is latitude and second is longitude
     * @param radius [METERS] find in this radius with centre in coordinates
     * @return List of points in this radius
     */
    @RequestMapping(value = "/pointsInRadius", method = RequestMethod.GET)
    public List<QuestPoint> getPointsInRadius(@RequestParam double[] coordinates, @RequestParam int radius){
        return questPointRepository
                .findByLocationNear(new GeoJsonPoint(coordinates[0], coordinates[1]), radius);
    }

}
