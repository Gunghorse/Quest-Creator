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

    /**
     * Finding all points in a given radius from user location from the all quests.
     * @param userCoordinates to find points around
     * @param radius to find points in
     * @return points in given radius from user location
     */
    public List<QuestPoint> allPointsInRadiusAroundPlayer(Point userCoordinates, double radius){
        List<QuestPoint> allPoints = ((List<QuestPoint>) questPointRepository.findAll());
        return pointsInRadiusAroundPlayer(allPoints, userCoordinates, radius);
    }

    /**
     * Finding all points in a given radius from user location from the certain quest.
     * @param userCoordinates to find points around
     * @param radius to find points in
     * @param questId is an id of quests to load points from
     * @return points in given radius from user location
     */
    public List<QuestPoint> questPointsInRadiusAroundPlayer(Point userCoordinates, double radius, Long questId){
        List<QuestPoint> allPoints = questPointRepository.findByQuestId(questId);
        return pointsInRadiusAroundPlayer(allPoints, userCoordinates, radius);
    }

    /**
     * Finding all points in a given radius from user location from the given set of points.
     * @param allPoints is a set of points to find closest points from
     * @param userCoordinates to find points around
     * @param radius to find points in
     * @return points in given radius from user location
     */
    private List<QuestPoint> pointsInRadiusAroundPlayer(List<QuestPoint> allPoints, Point userCoordinates, double radius){
        List<QuestPoint> nearestPoints = new ArrayList<>();
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