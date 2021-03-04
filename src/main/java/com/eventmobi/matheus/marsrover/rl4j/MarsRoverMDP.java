/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventmobi.matheus.marsrover.rl4j;

import com.eventmobi.matheus.marsrover.domain.Facing;
import com.eventmobi.matheus.marsrover.domain.Goal;
import com.eventmobi.matheus.marsrover.domain.Plateau;
import com.eventmobi.matheus.marsrover.domain.Rover;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.ArrayObservationSpace;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

/**
 *
 * @author Matheus Araújo
 */
public class MarsRoverMDP implements MDP<MarsRoverObservableSpace, Integer, DiscreteSpace> {

    private String envId;
    private Rover rover;
    private ObservationSpace<MarsRoverObservableSpace> observationSpace;
    private MarsRoverObservableSpace observableSpace;
    private DiscreteSpace actionSpace = new DiscreteSpace(RoverAction.values().length);
    private boolean renderRepresentation;
    private boolean resetAtRandomPosition;
    private boolean useDistanceToCalculateReward;
    private boolean useTimeToCalculateReward;
    private boolean useHardPlateau;
    private boolean useRandomGoal;
    private boolean useRandomObstacles;
    private boolean renderingDelay = false;
    private boolean dieWhenCollisided = false;
    private boolean infinityEpochSteps = false;
    private int steps = 0;
    private double distanceToGoal = 0;
    private static final int MAX_STEPS = 50;
    private MarsRoverMDPGui gui = null;
    private int atSamePosition;
    private int epoch;
    private int epochReward;

    public MarsRoverMDP() {

    }

    public void setRover(Rover rover) {
        this.rover = rover;
        this.observableSpace = new MarsRoverObservableSpace();
        observableSpace.setRover(rover);
        this.observationSpace = new ArrayObservationSpace(observableSpace.getShape());
    }

    public boolean isInfinityEpochSteps() {
        return infinityEpochSteps;
    }

    public void setInfinityEpochSteps(boolean infinityEpochSteps) {
        this.infinityEpochSteps = infinityEpochSteps;
    }
    
    public boolean isDieWhenCollisided() {
        return dieWhenCollisided;
    }

    public void setDieWhenCollisided(boolean dieWhenCollisided) {
        this.dieWhenCollisided = dieWhenCollisided;
    }

    public String getEnvId() {
        return envId;
    }

    public void setEnvId(String envId) {
        this.envId = envId;
    }

    public boolean isUseRandomObstacles() {
        return useRandomObstacles;
    }

    public void setUseRandomObstacles(boolean useRandomObstacles) {
        this.useRandomObstacles = useRandomObstacles;
    }

    public boolean isUseRandomGoal() {
        return useRandomGoal;
    }

    public void setUseRandomGoal(boolean useRandomGoal) {
        this.useRandomGoal = useRandomGoal;
    }

    public boolean isRenderingDelay() {
        return renderingDelay;
    }

    public void setRenderingDelay(boolean renderingDelay) {
        this.renderingDelay = renderingDelay;
    }

    public double getDistanceToGoal() {
        return distanceToGoal;
    }

    public void setDistanceToGoal(double distanceToGoal) {
        this.distanceToGoal = distanceToGoal;
    }

    public boolean isUseHardPlateau() {
        return useHardPlateau;
    }

    public void setUseHardPlateau(boolean useHardPlateau) {
        this.useHardPlateau = useHardPlateau;
    }

    public boolean isUseDistanceToCalculateReward() {
        return useDistanceToCalculateReward;
    }

    public void setUseDistanceToCalculateReward(boolean useDistanceToCalculateReward) {
        this.useDistanceToCalculateReward = useDistanceToCalculateReward;
    }

    public boolean isUseTimeToCalculateReward() {
        return useTimeToCalculateReward;
    }

    public void setUseTimeToCalculateReward(boolean useTimeToCalculateReward) {
        this.useTimeToCalculateReward = useTimeToCalculateReward;
    }

    public boolean isRenderRepresentation() {
        return renderRepresentation;
    }

    public void setRenderRepresentation(boolean renderRepresentation) {
        this.renderRepresentation = renderRepresentation;
    }

    public boolean isResetAtRandomPosition() {
        return resetAtRandomPosition;
    }

    public void setResetAtRandomPosition(boolean resetAtRandomPosition) {
        this.resetAtRandomPosition = resetAtRandomPosition;
    }

    static Rover initRoverRandom(boolean useHardPlateau, boolean useRandomGoal, boolean randomObstacles) {
        Rover rover = initRover(useHardPlateau, useRandomGoal, randomObstacles);
        int x = 0;
        int y = 0;
        boolean freeSapce = false;
        while (!freeSapce) {
            x = (int) (Math.random() * rover.getPlateau().getTopX());
            y = (int) (Math.random() * rover.getPlateau().getTopY());
            freeSapce = !rover.getPlateau().isThereAGoalAt(x, y) && !rover.getPlateau().isThereAnObstacleAt(x, y);
        }
        int facingCode = (int) (3 * Math.random());
        Facing facing;
        if (facingCode == 0) {
            facing = Facing.North;
        } else if (facingCode == 1) {
            facing = Facing.East;
        } else if (facingCode == 1) {
            facing = Facing.South;
        } else {
            facing = Facing.West;
        }
        rover.configureLanding(x, y, facing);
        Goal goal = rover.getPlateau().getGoal(0);
        return rover;
    }

    public static Rover initRover(boolean useHardPlateau, boolean useRandomGoal, boolean randomObstacles) {
        if (useHardPlateau) {
            return initHarderRover(useRandomGoal, randomObstacles);
        }
        System.out.println("init rover");
        String signal = "Plateau: 4 4 ";
        Rover rover = new Rover();
        rover.receiveSignal(signal);
        rover.configureLanding(0, 0, Facing.North);
        Plateau plateau = rover.getPlateau();

        int xGoal = 3;
        int yGoal = 1;
        if (randomObstacles) {
            xGoal = 3;
            yGoal = 3;
        }
        if (useRandomGoal) {
            boolean freeSapce = false;
            while (!freeSapce) {
                xGoal = (int) (Math.random() * rover.getPlateau().getTopX());
                yGoal = (int) (Math.random() * rover.getPlateau().getTopY());
                freeSapce = !rover.getPlateau().isThereAGoalAt(xGoal, yGoal) && !rover.getPlateau().isThereAnObstacleAt(xGoal, yGoal);
            }
        }
        plateau.addGoal(xGoal, yGoal);

        if (randomObstacles) {
            int obstaclesCreated = 0;
            int obstaclesToCreate = 3;
            int xObstacle = 0;
            int yObstacle = 0;
            while (obstaclesCreated < obstaclesToCreate - 1) {
                boolean freeSapce = false;
                while (!freeSapce) {
                    xObstacle = (int) (Math.random() * rover.getPlateau().getTopX());
                    yObstacle = (int) (Math.random() * rover.getPlateau().getTopY());
                    freeSapce = xObstacle != rover.getX()
                            && yObstacle != rover.getY()
                            && xObstacle != xGoal
                            && yObstacle != yGoal
                            //                            && !rover.getPlateau().isThereAGoalAt(xObstacle, yObstacle) 
                            && !rover.getPlateau().isThereAnObstacleAt(xObstacle, yObstacle);
                }
                plateau.addObstacle(xObstacle, yObstacle);
                obstaclesCreated++;
            }
            boolean isEquaX = 5 >= (10 * Math.random());
            if (isEquaX) {
                xObstacle = xGoal;
                boolean freeSapce = false;
                while (!freeSapce) {
                    yObstacle = (int) (Math.random() * rover.getPlateau().getTopY());
                    freeSapce = xObstacle != rover.getX()
                            && yObstacle != rover.getY()
                            && yObstacle != yGoal
                            //                            && !rover.getPlateau().isThereAGoalAt(xObstacle, yObstacle) 
                            && !rover.getPlateau().isThereAnObstacleAt(xObstacle, yObstacle);
                }
            } else {
                yObstacle = yGoal;
                boolean freeSapce = false;
                while (!freeSapce) {
                    xObstacle = (int) (Math.random() * rover.getPlateau().getTopX());
                    freeSapce = xObstacle != rover.getX()
                            && yObstacle != rover.getY()
                            && xObstacle != xGoal
                            //                            && !rover.getPlateau().isThereAGoalAt(xObstacle, yObstacle) 
                            && !rover.getPlateau().isThereAnObstacleAt(xObstacle, yObstacle);
                }
            }
            plateau.addObstacle(xObstacle, yObstacle);
        } else {
            plateau.addObstacle(2, 0);
            plateau.addObstacle(2, 1);
            plateau.addObstacle(2, 2);
            plateau.addObstacle(1, 2);
            if (useRandomGoal) {
                boolean freeSapce = false;
                while (!freeSapce) {
                    xGoal = (int) (Math.random() * rover.getPlateau().getTopX());
                    yGoal = (int) (Math.random() * rover.getPlateau().getTopY());
                    freeSapce = !rover.getPlateau().isThereAGoalAt(xGoal, yGoal) && !rover.getPlateau().isThereAnObstacleAt(xGoal, yGoal);
                }
                Goal goal = rover.getPlateau().getGoal(0);
                goal.setX(xGoal);
                goal.setY(yGoal);
            }
        }

        return rover;
    }

    public static Rover initHarderRover(boolean useRandomGoal, boolean randomObstacles) {
        String signal = "Plateau: 8 8 ";
        Rover rover = new Rover();
        rover.receiveSignal(signal);
        rover.configureLanding(0, 0, Facing.North);
        Plateau plateau = rover.getPlateau();

        int xGoal = 5;
        int yGoal = 2;
        if (randomObstacles) {
            xGoal = 7;
            yGoal = 7;
        }
        if (useRandomGoal) {
            boolean freeSapce = false;
            while (!freeSapce) {
                xGoal = (int) (Math.random() * rover.getPlateau().getTopX());
                yGoal = (int) (Math.random() * rover.getPlateau().getTopY());
                freeSapce = !rover.getPlateau().isThereAGoalAt(xGoal, yGoal) && !rover.getPlateau().isThereAnObstacleAt(xGoal, yGoal);
            }
        }
        plateau.addGoal(xGoal, yGoal);

        if (randomObstacles) {
            int obstaclesCreated = 0;
            int obstaclesToCreate = 7;
            int xObstacle = 0;
            int yObstacle = 0;
            while (obstaclesCreated < obstaclesToCreate - 1) {
                boolean freeSapce = false;
                while (!freeSapce) {
                    xObstacle = (int) (Math.random() * rover.getPlateau().getTopX());
                    yObstacle = (int) (Math.random() * rover.getPlateau().getTopY());
                    freeSapce = xObstacle != rover.getX()
                            && yObstacle != rover.getY()
                            && xObstacle != xGoal
                            && yObstacle != yGoal
                            //                            && !rover.getPlateau().isThereAGoalAt(xObstacle, yObstacle) 
                            && !rover.getPlateau().isThereAnObstacleAt(xObstacle, yObstacle);
                }
                plateau.addObstacle(xObstacle, yObstacle);
                obstaclesCreated++;
            }
            boolean isEquaX = 5 >= (10 * Math.random());
            if (isEquaX) {
                xObstacle = xGoal;
                boolean freeSapce = false;
                while (!freeSapce) {
                    yObstacle = (int) (Math.random() * rover.getPlateau().getTopY());
                    freeSapce = xObstacle != rover.getX()
                            && yObstacle != rover.getY()
                            && yObstacle != yGoal
                            //                            && !rover.getPlateau().isThereAGoalAt(xObstacle, yObstacle) 
                            && !rover.getPlateau().isThereAnObstacleAt(xObstacle, yObstacle);
                }
            } else {
                yObstacle = yGoal;
                boolean freeSapce = false;
                while (!freeSapce) {
                    xObstacle = (int) (Math.random() * rover.getPlateau().getTopX());
                    freeSapce = xObstacle != rover.getX()
                            && yObstacle != rover.getY()
                            && xObstacle != xGoal
                            //                            && !rover.getPlateau().isThereAGoalAt(xObstacle, yObstacle) 
                            && !rover.getPlateau().isThereAnObstacleAt(xObstacle, yObstacle);
                }
            }
            plateau.addObstacle(xObstacle, yObstacle);
        } else {
            plateau.addObstacle(4, 0);
            plateau.addObstacle(4, 1);
            plateau.addObstacle(4, 2);
            plateau.addObstacle(4, 3);
            plateau.addObstacle(5, 3);
            plateau.addObstacle(6, 3);
            plateau.addObstacle(1, 5);
            plateau.addObstacle(2, 5);
            plateau.addObstacle(3, 5);
            plateau.addObstacle(4, 5);
            plateau.addObstacle(4, 6);
            plateau.addObstacle(4, 7);
            if (useRandomGoal) {
                boolean freeSapce = false;
                while (!freeSapce) {
                    xGoal = (int) (Math.random() * rover.getPlateau().getTopX());
                    yGoal = (int) (Math.random() * rover.getPlateau().getTopY());
                    freeSapce = !rover.getPlateau().isThereAGoalAt(xGoal, yGoal) && !rover.getPlateau().isThereAnObstacleAt(xGoal, yGoal);
                }
                Goal goal = rover.getPlateau().getGoal(0);
                goal.setX(xGoal);
                goal.setY(yGoal);
            }
        }
        return rover;
    }

    public Rover getRover() {
        return rover;
    }

    public MarsRoverObservableSpace getObservableSpace() {
        return observableSpace;
    }

    @Override
    public ObservationSpace<MarsRoverObservableSpace> getObservationSpace() {
        return observationSpace;
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return actionSpace;
    }

    @Override
    public MarsRoverObservableSpace reset() {
        System.out.println("reset: steps=" + steps);
        epoch++;
        if (resetAtRandomPosition) {
            setRover(initRoverRandom(useHardPlateau, useRandomGoal, useRandomObstacles));
        } else {
            setRover(initRover(useHardPlateau, useRandomGoal, useRandomObstacles));
        }
        MarsRoverObservableSpace newObs = new MarsRoverObservableSpace();
        newObs.setRover(rover);
        if (renderRepresentation) {
//            newObs.printRepresentation();
            if (gui == null) {
                gui = new MarsRoverMDPGui(null, false, renderingDelay);
                gui.setTitle(envId);
            }
            if (!gui.isVisible()) {
                gui.setVisible(true);
            }
            if (gui.isVisible()) {
                newObs.buildPlateauRepresentation();
                newObs.buildStringRepresentation();
                gui.setStringRepresentation(newObs.getStringRepresentation() + "\nRESET steps: " + steps
                        + "\nepoch: " + epoch + ", reward: " + epochReward
                );
            }
        }
        observableSpace = newObs;
        steps = 0;
        epochReward = 0;
        return observableSpace;
    }

    @Override
    public void close() {
        System.out.println("close");
        if (gui != null && gui.isVisible()) {
            gui.setVisible(false);
        }
    }

    @Override
    public StepReply<MarsRoverObservableSpace> step(Integer action) {
        steps++;
        RoverAction roverAction = RoverAction.getByCode(action);

        distanceToGoal = calculateDistanceToGoal();
        double newDistanceToGoal = distanceToGoal;
        switch (roverAction) {
            case MoveAhead:
                rover.moveAhead();
                newDistanceToGoal = calculateDistanceToGoal();
                break;
            case SpinLeft:
                rover.spingLeft();
                break;
            case SpinRight:
                rover.spingRight();
                break;
            default:
                System.out.println("INVALID INSTRUCTION");
        }

        int reward = calculateReward(newDistanceToGoal);

        observableSpace.buildPlateauRepresentation();
        epochReward += reward;
        if (renderRepresentation) {
            observableSpace.buildStringRepresentation();
            gui.setStringRepresentation(observableSpace.getStringRepresentation()
                    + "\nstep: " + steps + ", reward: " + reward
                    + "\nepoch: " + epoch + ", reward: " + epochReward
            );
//            System.out.println("[distanceToGoal:"+distanceToGoal+", newDistanceToGoal="+newDistanceToGoal+"]->"+reward);
        }
        distanceToGoal = newDistanceToGoal;
//        int reward = rover.getPlateau().isThereAGoalAt(rover.getX(), rover.getY()) ? 100 : 0;

        return new StepReply(observableSpace, reward, verifyDone(), null);
    }

    @Override
    public boolean isDone() {
        boolean done = verifyDone();
        if (rover.isMissionAccomplished()) {
            System.out.println("MISSION ACCOMPLISHED: steps=" + steps);
        }
        return done;
    }

    @Override
    public MDP<MarsRoverObservableSpace, Integer, DiscreteSpace> newInstance() {
        System.out.println("new instance");
        MarsRoverMDP mdp = new MarsRoverMDP();
        mdp.setRover(rover);
        return mdp;
    }

    private double calculateDistanceToGoal() {
        Goal goal = rover.getPlateau().getGoal(0);
        if (goal == null) {
            return 0;
        }
        return Math.sqrt(Math.pow(rover.getX() - goal.getX(), 2) + Math.pow(rover.getY() - goal.getY(), 2));
    }

    private int calculateReward(double newDistanceToGoal) {

        int reward = 0;
        int distanceFactor = 0;
        int timeFactor = 0;
        int goalFactor = 0;
        int sameCoordinateFactor = 0;
        int forbidenPositonFactor = 0;

        if (newDistanceToGoal == distanceToGoal && !infinityEpochSteps) {
            atSamePosition++;
            if (atSamePosition >= 6) {
                atSamePosition = 0;
                sameCoordinateFactor = -400;
            }
            sameCoordinateFactor = -10;
        }

        if (useDistanceToCalculateReward && !infinityEpochSteps) {
            if (newDistanceToGoal == distanceToGoal) {
                distanceFactor = 0;
            } else {
                distanceFactor = newDistanceToGoal < distanceToGoal ? 1 : -1;
            }
        }

        if (useTimeToCalculateReward && !infinityEpochSteps) {
            timeFactor = -4;
        }

        if (rover.getPlateau().isThereAGoalAt(rover.getX(), rover.getY())) {
            goalFactor = 1000;
        }

        if (rover.isTryedToMoveToForbidenPosition()) {
            forbidenPositonFactor = -10;
            if (dieWhenCollisided) {
                forbidenPositonFactor = -1000;
                rover.setDied(true);
            }
        }

        reward = goalFactor + distanceFactor + timeFactor + sameCoordinateFactor + forbidenPositonFactor;
        return reward;
    }

    private boolean verifyDone() {
        if (rover.isDied()) {
            return true;
        }
        boolean exceededSteps = infinityEpochSteps?false:steps >= MAX_STEPS;
        boolean done = rover.isMissionAccomplished() || exceededSteps;
        return done;
    }

}
