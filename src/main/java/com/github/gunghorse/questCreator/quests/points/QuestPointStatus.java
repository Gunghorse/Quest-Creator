package com.github.gunghorse.questCreator.quests.points;

public interface QuestPointStatus {
    enum VISITED implements QuestPointStatus{
        VISITED;
    }
    enum UNVISITED implements QuestPointStatus{
        VISIBLE,
        INVISIBLE;
    }
}
