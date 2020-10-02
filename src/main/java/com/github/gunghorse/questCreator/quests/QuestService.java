package com.github.gunghorse.questCreator.quests;

import com.github.gunghorse.questCreator.quests.points.QuestPoint;
import com.github.gunghorse.questCreator.repositories.QuestPointRepository;
import com.github.gunghorse.questCreator.repositories.QuestStartPointRepository;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

public class QuestService {
    public static final int EARTH_RADIUS = 6371000; // m

    private QuestStartPointRepository questStartPointRepository;
    private QuestPointRepository questPointRepository;

    public QuestService(QuestStartPointRepository questStartPointRepository, QuestPointRepository questPointRepository) {
        this.questStartPointRepository = questStartPointRepository;
        this.questPointRepository = questPointRepository;
    }

    public List<QuestPoint> startPointsInRadiusAroundPlayer(Point userCoordinates, double radius){
        List<QuestPoint> nearestPoints = new ArrayList<>();
        List<QuestPoint> allPoints = ((List<QuestPoint>) questPointRepository.findAll());
        //List<QuestStartPoint> allQuests = ((List<QuestStartPoint>)questStartPointRepository.findAll());
        for(QuestPoint questStartPoint : allPoints) {
            Point pointsCoordinates = questStartPoint.getLocation();
            double dLambdaRad = Math.toRadians(userCoordinates.getY() - pointsCoordinates.getY());
            double userLatRad = Math.toRadians(userCoordinates.getX());
            double pointLatRad = Math.toRadians(pointsCoordinates.getX());

            double distance = EARTH_RADIUS * Math.acos(
                    (Math.sin(userLatRad)*Math.sin(pointLatRad)) +
                            (Math.cos(userLatRad)*Math.cos(pointLatRad)*Math.cos(dLambdaRad)));
            if(distance < radius){
                nearestPoints.add(questStartPoint);
            }
        }
        return nearestPoints;
    }
}
