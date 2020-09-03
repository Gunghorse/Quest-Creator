package com.github.gunghorse.pathes.quests;

import com.github.gunghorse.pathes.sessions.SessionRepository;
import com.github.gunghorse.pathes.user.UserRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quest")
public class QuestController {

    private QuestRepository questRepository;
    private QuestPointRepository questPointRepository;
    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    @RequestMapping(value="/create", method = RequestMethod.POST)
    private void createQuest(@RequestBody Quest quest){
        questRepository.save(quest);
    }

    /**
     * TODO: Endpoints:
     *
     * 1. /add/point/{questID}
     * 2. /get/by/[title | creatorID]
     */

}
