package com.github.gunghorse.questCreator.quests;

import com.github.gunghorse.questCreator.quests.points.QuestPointRepository;
import com.github.gunghorse.questCreator.user.User;
import com.github.gunghorse.questCreator.user.UserRepository;
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
    public List<Quest> getAllQuests(Principal principal) {
        return (List<Quest>) questRepository.findAll();
    }

    @RequestMapping(value="/my", method = RequestMethod.GET)
    public @ResponseBody List<Quest> getQuestCreatedBy(Principal principal){
        User creator = userRepository.findByUsername(principal.getName());
        return questRepository.findByCreator(creator);
    }


}
