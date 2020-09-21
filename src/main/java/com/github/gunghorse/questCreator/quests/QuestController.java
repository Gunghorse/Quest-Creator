package com.github.gunghorse.questCreator.quests;

import com.github.gunghorse.questCreator.quests.points.QuestPointRepository;
import com.github.gunghorse.questCreator.user.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quest")
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
    @PreAuthorize("authenticated")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Quest> getAllQuests() {
        return (List<Quest>) questRepository.findAll();
    }
}
