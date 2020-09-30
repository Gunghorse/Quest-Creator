package com.github.gunghorse.questCreator.quests;

import com.github.gunghorse.questCreator.quests.points.QuestPoint;
import com.github.gunghorse.questCreator.quests.points.QuestPointDTO;
import com.github.gunghorse.questCreator.quests.points.QuestPointRepository;
import com.github.gunghorse.questCreator.quests.points.QuestStartPoint;
import com.github.gunghorse.questCreator.user.User;
import com.github.gunghorse.questCreator.user.UserRepository;
import org.springframework.data.geo.Point;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${v1API}/quest")
public class QuestController {

    private final QuestRepository questRepository;
    private final QuestPointRepository questPointRepository;
    private final UserRepository userRepository;
    private QuestServices questServices;

    public QuestController(QuestRepository questRepository,
                           QuestPointRepository questPointRepository,
                           UserRepository userRepository) {
        this.questRepository = questRepository;
        this.questPointRepository = questPointRepository;
        this.userRepository = userRepository;
        this.questServices = new QuestServices(null, questPointRepository);
    }

    /**
     * URL looks like:
     * GET /quest/
     *
     * @return List of all quests
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public @ResponseBody List<Quest> getAllQuests(Principal principal) {
        List<Quest> quests = (List<Quest>) questRepository.findAll();
        return quests;
    }

    @RequestMapping(value="/my", method = RequestMethod.GET)
    public @ResponseBody List<Quest> getQuestCreatedBy(Principal principal){
        return questRepository.findByCreatorUsername(principal.getName());
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public void createQuest(@RequestBody QuestDTO questDTO, Principal principal){
        User creator = userRepository.findByUsername(principal.getName());
        Quest newQuest = new Quest(questDTO.getTitle(), questDTO.getDescription());
        newQuest.setCreator(creator);
        questRepository.save(newQuest);
    }

    @RequestMapping(value = "/point/onPoint", method = RequestMethod.GET)
    public boolean isOnPoint(@RequestParam Map<String, String> coordsAndRadius){
        //TODO: Figure out, WHAT THE FUCK!!!
        double longitude = Double.parseDouble(coordsAndRadius.get("lon"));
        double latitude = Double.parseDouble(coordsAndRadius.get("lat"));
        return questServices.startPointsInRadiusAroundPlayer(new Point(longitude, latitude), 15.0) != null;
    }

    @RequestMapping(value = "/point/inRadius", method = RequestMethod.GET)
    public List<QuestPoint> getQuestPointsInRadius(@RequestParam Map<String, String> coordsAndRadius){
        double longitude = Double.parseDouble(coordsAndRadius.get("lon"));
        double latitude = Double.parseDouble(coordsAndRadius.get("lat"));
        double radius = Double.parseDouble(coordsAndRadius.get("radius"));
        return questServices.startPointsInRadiusAroundPlayer(new Point(longitude, latitude), radius);
    }

    @RequestMapping(value = "/point/create", method = RequestMethod.POST)
    public void createQuestPoint(@RequestBody QuestPointDTO questPointDTO){
        Quest quest = questRepository.findById(questPointDTO.getQuestID()).get();
        QuestPoint questPoint;
        if(quest.getPoints().isEmpty()){
            questPoint = new QuestStartPoint(questPointDTO.getTitle(),
                    questPointDTO.getDescription(),
                    new Point(questPointDTO.getLongitude(), questPointDTO.getLatitude()));
        }else{
            questPoint = new QuestPoint(questPointDTO.getTitle(),
                    questPointDTO.getDescription(),
                    new Point(questPointDTO.getLongitude(), questPointDTO.getLatitude()));
        }
        quest.setPoint(questPoint);
        questPointRepository.save(questPoint);
        questRepository.save(quest);
    }
}
