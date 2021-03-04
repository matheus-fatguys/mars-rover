/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventmobi.matheus.marsrover.rl4j.tests;

import com.eventmobi.matheus.marsrover.domain.Facing;
import com.eventmobi.matheus.marsrover.domain.Plateau;
import com.eventmobi.matheus.marsrover.domain.Rover;
import com.eventmobi.matheus.marsrover.rl4j.MarsRoverMDP;
import com.eventmobi.matheus.marsrover.rl4j.MarsRoverObservableSpace;
import com.eventmobi.matheus.marsrover.rl4j.PlateauCoordinateRepresentation;
import com.eventmobi.matheus.marsrover.rl4j.RoverAction;
import org.deeplearning4j.gym.StepReply;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Matheus Araújo
 */
public class MarsRoverMDPTest {

    @Test
    public void when_move_ahead_step_is_issued_should_have_correct_representation() {
        String signal = "Plateau: 5 6 ";
        Rover rover = new Rover();
        rover.receiveSignal(signal);
        rover.configureLanding(0, 0, Facing.North);
        Plateau plateau = rover.getPlateau();
        MarsRoverMDP mdp = new MarsRoverMDP();
        mdp.setRover(rover);
        MarsRoverObservableSpace observableSpace = mdp.getObservableSpace();
        int[] shape = observableSpace.getShape();
        Assert.assertEquals("Shape's number of dimensions not correctly set. should be 2", 2, shape.length);
        Assert.assertEquals("Shape's first dimension not correctly set. should be 5", plateau.getTopX(), shape[0]);
        Assert.assertEquals("Shape's second dimension not correctly set. should be 6", plateau.getTopX(), shape[0]);

        PlateauCoordinateRepresentation[][] plateauRepresentation = observableSpace.getPlateauRepresentation();
        Assert.assertEquals("plateau Representation's not correctly showing rover state " + PlateauCoordinateRepresentation.RoverFacingEast.getOutputRepresentation(), PlateauCoordinateRepresentation.RoverFacingNorth, plateauRepresentation[rover.getX()][rover.getY()]);

        StepReply<MarsRoverObservableSpace> stepReply = mdp.step(RoverAction.MoveAhead.getCode());
        observableSpace = stepReply.getObservation();
        plateauRepresentation = observableSpace.getPlateauRepresentation();
        Assert.assertEquals("rover didnt move correctly ", 1, rover.getY());
        Assert.assertEquals("rover didnt move correctly ", 0, rover.getX());
        Assert.assertEquals("plateau Representation's not correctly showing rover state " + PlateauCoordinateRepresentation.RoverFacingNorth.getOutputRepresentation(), PlateauCoordinateRepresentation.RoverFacingNorth, plateauRepresentation[rover.getX()][rover.getY()]);
        Assert.assertEquals("plateau Representation's not correctly showing state of position left by rover " + PlateauCoordinateRepresentation.Empty.getOutputRepresentation(), PlateauCoordinateRepresentation.Empty, plateauRepresentation[rover.getX()][rover.getY() - 1]);
        for (int x = 0; x < plateau.getTopX(); x++) {
            for (int y = 0; y < plateau.getTopY(); y++) {
                PlateauCoordinateRepresentation rep = plateauRepresentation[x][y];
                if (x != rover.getX() && y != rover.getY()) {
                    Assert.assertEquals("plateau Representation's not correctly showing Obstacle state " + PlateauCoordinateRepresentation.Obstacle.getOutputRepresentation(), PlateauCoordinateRepresentation.Empty, rep);
                }
            }
        }
    }
    
    @Test
    public void when_spin_step_is_issued_should_have_correct_representation() {
        String signal = "Plateau: 5 6 ";
        Rover rover = new Rover();
        rover.receiveSignal(signal);
        rover.configureLanding(0, 0, Facing.North);
        Plateau plateau = rover.getPlateau();
        MarsRoverMDP mdp = new MarsRoverMDP();
        mdp.setRover(rover);
        MarsRoverObservableSpace observableSpace = mdp.getObservableSpace();
        int[] shape = observableSpace.getShape();
        Assert.assertEquals("Shape's number of dimensions not correctly set. should be 2", 2, shape.length);
        Assert.assertEquals("Shape's first dimension not correctly set. should be 5", plateau.getTopX(), shape[0]);
        Assert.assertEquals("Shape's second dimension not correctly set. should be 6", plateau.getTopX(), shape[0]);

        PlateauCoordinateRepresentation[][] plateauRepresentation = observableSpace.getPlateauRepresentation();
        Assert.assertEquals("plateau Representation's not correctly showing rover state " + PlateauCoordinateRepresentation.RoverFacingNorth.getOutputRepresentation(), PlateauCoordinateRepresentation.RoverFacingNorth, plateauRepresentation[rover.getX()][rover.getY()]);

        StepReply<MarsRoverObservableSpace> stepReply = mdp.step(RoverAction.SpinLeft.getCode());
        observableSpace = stepReply.getObservation();
        plateauRepresentation = observableSpace.getPlateauRepresentation();
        Assert.assertEquals("rover didnt spin correctly ", Facing.West, rover.getFacing());
        Assert.assertEquals("rover didnt spin correctly ", 0, rover.getY());
        Assert.assertEquals("rover didnt spin correctly ", 0, rover.getX());
        Assert.assertEquals("plateau Representation's not correctly showing rover state " + PlateauCoordinateRepresentation.RoverFacingWest.getOutputRepresentation(), PlateauCoordinateRepresentation.RoverFacingWest, plateauRepresentation[rover.getX()][rover.getY()]);
        for (int x = 0; x < plateau.getTopX(); x++) {
            for (int y = 0; y < plateau.getTopY(); y++) {
                PlateauCoordinateRepresentation rep = plateauRepresentation[x][y];
                if (x != rover.getX() && y != rover.getY()) {
                    Assert.assertEquals("plateau Representation's not correctly showing Obstacle state " + PlateauCoordinateRepresentation.Obstacle.getOutputRepresentation(), PlateauCoordinateRepresentation.Empty, rep);
                }
            }
        }
    }
}
