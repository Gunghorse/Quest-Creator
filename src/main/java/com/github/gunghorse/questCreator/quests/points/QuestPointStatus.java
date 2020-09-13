package com.github.gunghorse.questCreator.quests.points;

public enum QuestPointStatus {
    VISITED{
        @Override
        public String toString() {
            return "VISITED";
        }
    },
    UNVISITED_VISIBLE{
        @Override
        public String toString() {
            return "UNVISITED_VISIBLE";
        }
    },
    UNVISITED_INVISIBLE{
        @Override
        public String toString() {
            return "UNVISITED_INVISIBLE";
        }
    }
}