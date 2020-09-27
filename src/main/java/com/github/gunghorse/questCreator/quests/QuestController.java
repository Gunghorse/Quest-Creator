package com.github.gunghorse.questCreator.quests;

import com.github.gunghorse.questCreator.quests.points.QuestPoint;
import com.github.gunghorse.questCreator.quests.points.QuestPointDTO;
import com.github.gunghorse.questCreator.quests.points.QuestPointRepository;
import com.github.gunghorse.questCreator.quests.points.QuestStartPoint;
import com.github.gunghorse.questCreator.user.User;
import com.github.gunghorse.questCreator.user.UserRepository;
import org.springframework.data.geo.Point;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("${v1API}/quest")
public class QuestController {

    private final QuestRepository questRepository;
    private final QuestPointRepository questPointRepository;
    private final UserRepository userRepository;

    public QuestController(QuestRepository questRepository,
                           QuestPointRepository questPointRepository,
                           UserRepository userRepository) {
        this.questRepository = questRepository;
        this.questPointRepository = questPointRepository;
        this.userRepository = userRepository;
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
        User creator = userRepository.findByUsername(principal.getName());
        return questRepository.findByCreator(creator);
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public void createQuest(@RequestBody QuestDTO questDTO, Principal principal){
        User creator = userRepository.findByUsername(principal.getName());
        Quest newQuest = new Quest(questDTO.getTitle(), questDTO.getDescription());
        newQuest.setCreator(creator);
        questRepository.save(newQuest);
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
