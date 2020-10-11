package com.github.gunghorse.questCreator.quests.points;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuestPointDTO {
    private Long questID;
    private String pointStatus;
    private String title;
    private String description;
    private double longitude;
    private double latitude;
}
