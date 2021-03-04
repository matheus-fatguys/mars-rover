package com.eventmobi.matheus.marsrover.domain;

import com.eventmobi.matheus.marsrover.domain.exception.PlateauConfigurationException;
import java.util.ArrayList;
import java.util.List;

public class Plateau {

    private final List<Goal> goalList = new ArrayList();
    private final List<Obstacle> obstacleList = new ArrayList();
    
    private final int topX;
    private final int topY;

    public Plateau(int topX, int topY) {
        this.topX = topX;
        this.topY = topY;
    }

    public int getTopX() {
        return topX;
    }

    public int getTopY() {
        return topY;
    }
    
    public void addGoal(int x, int y){
        Goal newGoal = new Goal(x, y);
        if(goalList.contains(newGoal)){
            throw new PlateauConfigurationException("Tryed to insert a goal where there is already another goal ("+x+","+y+")");
        }
        if(x>topX||y>topY){
            throw new PlateauConfigurationException("Tryed to insert a goal out of plateau's bounds ("+x+","+y+")");
        }
        if(x<0||y<0){
            throw new PlateauConfigurationException("Tryed to insert a goal out of plateau's bounds ("+x+","+y+")");
        }
        
        goalList.add(newGoal);
    }
    
    public Goal getGoal(int i){
        if(i>=goalList.size()){
            return null;
        }
        return goalList.get(i);
    }

    public int getNumberOfGoals() {
        return goalList.size();
    }
    
    public void addObstacle(int x, int y){
        Obstacle newObstacle = new Obstacle(x, y);
        if(obstacleList.contains(newObstacle)){
            throw new PlateauConfigurationException("Tryed to insert a obstacle where there is already another obstacle ("+x+","+y+")");
        }
        if(x>topX||y>topY){
            throw new PlateauConfigurationException("Tryed to insert a obstacle out of plateau's bounds ("+x+","+y+")");
        }
        if(x<0||y<0){
            throw new PlateauConfigurationException("Tryed to insert a obstacle out of plateau's bounds ("+x+","+y+")");
        }
        
        obstacleList.add(newObstacle);
    }
    
    public Obstacle getObstacle(int i){
        return obstacleList.get(i);
    }
    
    public int getNumberOfObstacles() {
        return obstacleList.size();
    }

    public boolean isThereAGoalAt(int x, int y) {
        for (Goal goal : goalList) {
            if(goal.isAtCoordinate(x, y)){
                return true;
            }
        }
        return false;
    }

    public boolean isThereAnObstacleAt(int x, int y) {
        for (Obstacle obstacle : obstacleList) {
            if(obstacle.isAtCoordinate(x, y)){
                return true;
            }
        }
        return false;
    }
    
}
