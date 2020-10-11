package com.github.gunghorse.questCreator.quests;

import com.github.gunghorse.questCreator.quests.points.QuestPoint;
import com.github.gunghorse.questCreator.quests.points.QuestPointDTO;
import com.github.gunghorse.questCreator.repositories.QuestPointRepository;
import com.github.gunghorse.questCreator.quests.points.QuestStartPoint;
import com.github.gunghorse.questCreator.repositories.QuestRepository;
import com.github.gunghorse.questCreator.user.User;
import com.github.gunghorse.questCreator.repositories.UserRepository;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller responsible for communication with quests.
 */
@RestController
@RequestMapping("${v1API}/quest")
public class QuestController {

    private final QuestRepository questRepository;
    private final QuestPointRepository questPointRepository;
    private final UserRepository userRepository;
    private final QuestService questService;

    private final int MIN_RADIUS = 10;   // m

    public QuestController(QuestRepository questRepository,
                           QuestPointRepository questPointRepository,
                           UserRepository userRepository) {
        this.questRepository = questRepository;
        this.questPointRepository = questPointRepository;
        this.userRepository = userRepository;
        this.questService = new QuestService(null, questPointRepository);
    }

    /**
     * URL looks like:
     * GET /quest/
     *
     * @return List of all quests
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody List<Quest> getAllQuests() {
        return (List<Quest>) questRepository.findAll();
    }

    /**
     * URL looks like
     * GET /quest/points
     *
     * @return all quest points
     */
    @RequestMapping(value = "/points", method = RequestMethod.GET)
    public @ResponseBody List<QuestPoint> getAllQuestPoints() {
        return (List<QuestPoint>) questPointRepository.findAll();
    }

    /**
     * URL looks like
     * GET /quest/startPoint
     *
     * @return all start points
     */
    @RequestMapping(value = "/startPoints", method = RequestMethod.GET)
    public @ResponseBody List<QuestStartPoint> getAllStartQuestPoints() {
        List<Quest> quests = (List<Quest>) questRepository.findAll();
        return quests.stream().map(Quest::getStartPoint).collect(Collectors.toList());
    }

    /**
     * URL looks like:
     * GET /quest/my
     *
     * @param principal - here we have username of user being logged in
     * @return list of quest created by login user
     */
    @RequestMapping(value="/my", method = RequestMethod.GET)
    public @ResponseBody List<Quest> getQuestCreatedBy(Principal principal){
        return questRepository.findByCreatorUsername(principal.getName());
    }

    /**
     * URL looks like:
     * POST /quest/create
     *
     * @param questDTO data transfer object with necessary data for creating quest
     * @param principal - here we have username of user being logged in.
     *                  Need it for setting quest's creator
     */
    @RequestMapping(value="/create", method = RequestMethod.POST)
    public void createQuest(@RequestBody QuestDTO questDTO, Principal principal){
        User creator = userRepository.findByUsername(principal.getName());
        Quest newQuest = Quest.builder()
                .title(questDTO.getTitle())
                .description(questDTO.getDescription())
                .build();
        newQuest.setCreator(creator);
        questRepository.save(newQuest);
    }

    @Deprecated
    @RequestMapping(value = "/point/onPoint", method = RequestMethod.GET)
    public boolean isOnPoint(@RequestParam Map<String, String> coords){
        double longitude = Double.parseDouble(coords.get("lon"));
        double latitude = Double.parseDouble(coords.get("lat"));
        return !questService.allPointsInRadiusAroundPlayer(
                new Point(longitude, latitude), MIN_RADIUS).isEmpty();
    }

    @Deprecated
    @RequestMapping(value = "/point/allInRadius", method = RequestMethod.GET)
    public List<QuestPoint> getAllPointsInRadius(@RequestParam Map<String, String> coordsAndRadius){
        double longitude = Double.parseDouble(coordsAndRadius.get("lon"));
        double latitude = Double.parseDouble(coordsAndRadius.get("lat"));
        double radius = Double.parseDouble(coordsAndRadius.get("radius"));
        return questService.allPointsInRadiusAroundPlayer(new Point(longitude, latitude), radius);
    }

    /**
     * URL looks like:
     * GET /quest/point/onQuestPoint?lon=50.061667&lat=19.937347&questId=6
     *
     * Check if user is not further than MIN_RADIUS from any point from certain quest.
     *
     * @param coords here we need to have 'lon' and 'lat'
     * @return boolean is user on point
     */
    @RequestMapping(value = "/point/onQuestPoint", method = RequestMethod.GET)
    public boolean isOnPointFromQuest(
            @RequestParam Map<String, String> coords,
            @RequestParam Long questId){
        double longitude = Double.parseDouble(coords.get("lon"));
        double latitude = Double.parseDouble(coords.get("lat"));
        return !questService.questPointsInRadiusAroundPlayer(
                new Point(longitude, latitude), MIN_RADIUS, questId)
                .isEmpty();
    }

    /**
     * URL looks like:
     * GET /quest/point/inRadius?lon=50.061667&lat=19.937347&radius=150&questId=6
     *
     * Seek all points from the certain quest
     * in circle with radius 'radius' and centre in point 'lon' and 'lat'
     *
     * @param coordsAndRadius here are 'lon' and 'lat' and 'radius'
     * @return list of points in given radius
     */
    @RequestMapping(value = "/point/inRadius", method = RequestMethod.GET)
    public List<QuestPoint> getQuestPointsInRadius(
            @RequestParam Map<String, String> coordsAndRadius,
            @RequestParam Long questId){
        double longitude = Double.parseDouble(coordsAndRadius.get("lon"));
        double latitude = Double.parseDouble(coordsAndRadius.get("lat"));
        double radius = Double.parseDouble(coordsAndRadius.get("radius"));
        return questService.questPointsInRadiusAroundPlayer(new Point(longitude, latitude), radius, questId);
    }

    /**
     * URL looks like:
     * GET /quest/point/questPoints?lon=50.061667&lat=19.937347&questId=6
     *
     * Seek all points from the certain quest
     * in circle with MIN_RADIUS and centre in point 'lon' and 'lat'
     *
     * @param coords here are 'lon' and 'lat'
     * @return list of points in given radius
     */
    @RequestMapping(value = "/point/questPoints", method = RequestMethod.GET)
    public List<QuestPoint> getClosestQuestPoints(
            @RequestParam Map<String, String> coords,
            @RequestParam Long questId){
        double longitude = Double.parseDouble(coords.get("lon"));
        double latitude = Double.parseDouble(coords.get("lat"));
        return questService.questPointsInRadiusAroundPlayer(new Point(longitude, latitude), MIN_RADIUS, questId);
    }

    /**
     * URL looks like:
     * GET /quest/point/updateState?lon=50.061667&lat=19.937347&questId=6
     *
     * If user is on quest point, update its state.
     *
     * @param coords here are 'lon' and 'lat'
     */
    @RequestMapping(value = "/point/updateState", method = RequestMethod.POST)
    public void updatePointsState(
            @RequestParam Map<String, String> coords,
            @RequestParam Long questId,
            HttpServletRequest req){
        User user = userRepository.findByUsername(req.getRemoteUser());
        Quest quest = questRepository.findById(questId).get();
        List<QuestPoint> points = getClosestQuestPoints(coords, questId);
        for (QuestPoint point : points){
            user.visitPoint(quest, point);
        }
    }


    /**
     * URL looks like:
     * POST quest/point/create
     *
     * Create new quest point in given quest. Quest specified by its ID.
     *
     * @param questPointDTO data transfer object with necessary data for creating quest point
     */
    @RequestMapping(value = "/point/create", method = RequestMethod.POST)
    public void createQuestPoint(@RequestBody QuestPointDTO questPointDTO){
        Quest quest = questRepository.findById(questPointDTO.getQuestID()).get();
        QuestPoint questPoint;
        if(quest.getPoints().isEmpty()){
            questPoint = QuestStartPoint.builder()
                    .title(questPointDTO.getTitle())
                    .description(questPointDTO.getDescription())
                    .location(new Point(questPointDTO.getLongitude(), questPointDTO.getLatitude()))
                    .build();
        }else{
            questPoint = QuestPoint.builder()
                    .title(questPointDTO.getTitle())
                    .description(questPointDTO.getDescription())
                    .location(new Point(questPointDTO.getLongitude(), questPointDTO.getLatitude()))
                    .build();
        }
        quest.addPoint(questPoint);
        questPointRepository.save(questPoint);
        questRepository.save(quest);
    }
}