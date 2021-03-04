/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventmobi.matheus.marsrover.rl4j.tests;

import com.eventmobi.matheus.marsrover.domain.Facing;
import com.eventmobi.matheus.marsrover.domain.Plateau;
import com.eventmobi.matheus.marsrover.domain.Rover;
import com.eventmobi.matheus.marsrover.rl4j.MarsRoverObservableSpace;
import com.eventmobi.matheus.marsrover.rl4j.PlateauCoordinateRepresentation;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Matheus Araújo
 */
public class MarsRoverObservableSpaceTest {

    @Test
    public void shape_must_match_plateu_topx_and_topy() {
        String signal = "Plateau: 5 6 ";
        Rover rover = new Rover();
        rover.receiveSignal(signal);
        rover.configureLanding(0, 0, Facing.East);
        Plateau plateau = rover.getPlateau();
        MarsRoverObservableSpace observableSpace = new MarsRoverObservableSpace();
        observableSpace.setRover(rover);
        int[] shape = observableSpace.getShape();
        Assert.assertEquals("Shape's number of dimensions not correctly set. should be 2", 2, shape.length);
        Assert.assertEquals("Shape's first dimension not correctly set. should be 5", plateau.getTopX(), shape[0]);
        Assert.assertEquals("Shape's second dimension not correctly set. should be 6", plateau.getTopX(), shape[0]);

    }

    @Test
    public void plateau_representation_must_match_plateau_configuration_and_rover_state() {
        String signal = "Plateau: 8 8 ";
        Rover rover = new Rover();
        rover.receiveSignal(signal);
        rover.configureLanding(1, 1, Facing.East);
        Plateau plateau = rover.getPlateau();

        MarsRoverObservableSpace observableSpace = new MarsRoverObservableSpace();
        observableSpace.setRover(rover);

        PlateauCoordinateRepresentation[][] plateauRepresentation = observableSpace.getPlateauRepresentation();

        Assert.assertEquals("plateau Representation's number of dimensions not correctly set. should be 8", 8, plateauRepresentation.length);
        Assert.assertEquals("plateau Representation's number of dimensions not correctly set. should be 8", 8, plateauRepresentation.length);

        for (int x = 0; x < plateau.getTopX(); x++) {
            for (int y = 0; y < plateau.getTopY(); y++) {
                PlateauCoordinateRepresentation rep = plateauRepresentation[x][y];
                if (x != rover.getX() && y != rover.getY()) {
                    Assert.assertEquals("plateau Representation's not correctly showing Obstacle state " + PlateauCoordinateRepresentation.Obstacle.getOutputRepresentation(), PlateauCoordinateRepresentation.Empty, rep);
                }
            }
        }

        Assert.assertEquals("plateau Representation's not correctly showing rover state " + PlateauCoordinateRepresentation.RoverFacingEast.getOutputRepresentation(), PlateauCoordinateRepresentation.RoverFacingEast, plateauRepresentation[rover.getX()][rover.getY()]);

    }

    @Test
    public void plateau_representation_must_match_plateau_configuration_and_rover_goal_obstacle_state() {
        String signal = "Plateau: 8 8 ";
        Rover rover = new Rover();
        rover.receiveSignal(signal);
        rover.configureLanding(1, 1, Facing.East);
        Plateau plateau = rover.getPlateau();
        plateau.addGoal(6, 7);
        plateau.addObstacle(4, 0);
        MarsRoverObservableSpace observableSpace = new MarsRoverObservableSpace();
        observableSpace.setRover(rover);

        PlateauCoordinateRepresentation[][] plateauRepresentation = observableSpace.getPlateauRepresentation();

        Assert.assertEquals("plateau Representation's number of dimensions not correctly set. should be 8", 8, plateauRepresentation.length);
        Assert.assertEquals("plateau Representation's number of dimensions not correctly set. should be 8", 8, plateauRepresentation.length);

        Assert.assertEquals("plateau Representation's not correctly showing rover state " + PlateauCoordinateRepresentation.RoverFacingEast.getOutputRepresentation(), PlateauCoordinateRepresentation.RoverFacingEast, plateauRepresentation[rover.getX()][rover.getY()]);
        Assert.assertEquals("plateau Representation's not correctly showing goal state " + PlateauCoordinateRepresentation.Goal.getOutputRepresentation(), PlateauCoordinateRepresentation.Goal, plateauRepresentation[6][7]);
        Assert.assertEquals("plateau Representation's not correctly showing Obstacle state " + PlateauCoordinateRepresentation.Obstacle.getOutputRepresentation(), PlateauCoordinateRepresentation.Obstacle, plateauRepresentation[4][0]);

        for (int x = 0; x < plateau.getTopX(); x++) {
            for (int y = 0; y < plateau.getTopY(); y++) {
                PlateauCoordinateRepresentation rep = plateauRepresentation[x][y];
                if (x != rover.getX() && y != rover.getY()
                        &&x != 6 && y != 7
                        &&x != 4 && y != 0
                        ) {
                    Assert.assertEquals("plateau Representation's not correctly showing Obstacle state " + PlateauCoordinateRepresentation.Obstacle.getOutputRepresentation(), PlateauCoordinateRepresentation.Empty, rep);
                }
            }
        }
    }
    
    @Test
    public void convert_2_dimension_to_1() {
        Assert.assertEquals(0, MarsRoverObservableSpace.convertTo1DimensionCoordinate(5,0,0));
        Assert.assertEquals(1, MarsRoverObservableSpace.convertTo1DimensionCoordinate(5,0,1));
        Assert.assertEquals(4, MarsRoverObservableSpace.convertTo1DimensionCoordinate(5,0,4));
        Assert.assertEquals(5, MarsRoverObservableSpace.convertTo1DimensionCoordinate(5,1,0));
        Assert.assertEquals(6, MarsRoverObservableSpace.convertTo1DimensionCoordinate(5,1,1));
        Assert.assertEquals(9, MarsRoverObservableSpace.convertTo1DimensionCoordinate(5,1,4));
        Assert.assertEquals(24, MarsRoverObservableSpace.convertTo1DimensionCoordinate(5,4,4));
    }
        

    @Test
    public void plateau_array_representation_must_match_plateau_configuration_and_rover_goal_obstacle_state() {
        String signal = "Plateau: 8 8 ";
        Rover rover = new Rover();
        rover.receiveSignal(signal);
        rover.configureLanding(1, 1, Facing.East);
        Plateau plateau = rover.getPlateau();
        plateau.addGoal(6, 7);
        plateau.addObstacle(4, 0);
        MarsRoverObservableSpace observableSpace = new MarsRoverObservableSpace();
        observableSpace.setRover(rover);

        double[] arrayRepresentation = observableSpace.toArray();

        Assert.assertEquals("plateau Representation's length set. should be 8*8=64", 64, arrayRepresentation.length);
        
        Assert.assertEquals("plateau Representation's not correctly showing rover state " + PlateauCoordinateRepresentation.RoverFacingEast.getCode(), PlateauCoordinateRepresentation.RoverFacingEast.getCode(), (int)arrayRepresentation[MarsRoverObservableSpace.convertTo1DimensionCoordinate(plateau.getTopY(),rover.getX(),rover.getY())]);
        Assert.assertEquals("plateau Representation's not correctly showing goal state " + PlateauCoordinateRepresentation.Goal.getCode(), PlateauCoordinateRepresentation.Goal.getCode(), (int)arrayRepresentation[MarsRoverObservableSpace.convertTo1DimensionCoordinate(plateau.getTopY(),6,7)]);
        Assert.assertEquals("plateau Representation's not correctly showing Obstacle state " + PlateauCoordinateRepresentation.Obstacle.getCode(), PlateauCoordinateRepresentation.Obstacle.getCode(), (int)arrayRepresentation[MarsRoverObservableSpace.convertTo1DimensionCoordinate(plateau.getTopY(),4,0)]);

        for (int x = 0; x < plateau.getTopX(); x++) {
            for (int y = 0; y < plateau.getTopY(); y++) {
                int rep = (int)arrayRepresentation[MarsRoverObservableSpace.convertTo1DimensionCoordinate(plateau.getTopY(),x,y)];
                if (x != rover.getX() && y != rover.getY()
                        &&x != 6 && y != 7
                        &&x != 4 && y != 0
                        ) {
                    Assert.assertEquals("plateau Representation's not correctly showing Obstacle state " + PlateauCoordinateRepresentation.Obstacle.getCode(), PlateauCoordinateRepresentation.Empty.getCode(), rep);
                }
            }
        }
        observableSpace.getData();
    }
}
