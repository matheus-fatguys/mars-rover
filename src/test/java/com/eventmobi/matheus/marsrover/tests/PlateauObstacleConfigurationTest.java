/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventmobi.matheus.marsrover.tests;

import com.eventmobi.matheus.marsrover.domain.Obstacle;
import com.eventmobi.matheus.marsrover.domain.Plateau;
import com.eventmobi.matheus.marsrover.domain.Rover;
import com.eventmobi.matheus.marsrover.domain.exception.PlateauConfigurationException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Matheus Araújo
 */
public class PlateauObstacleConfigurationTest {
    @Test
     public void when_plateau_receives_obstacle_configuration_signal() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         plateau.addObstacle(5, 5);
         Obstacle obstacle = plateau.getObstacle(0);
         Assert.assertTrue("Obstacle X coordinate not correctly interpreted. Should be 5", obstacle.getX()==5);
         Assert.assertTrue("Obstacle Y coordinate not correctly interpreted. Should be 5", obstacle.getY()==5);
     }
     
    @Test
     public void when_plateau_receives_a_second_obstacle_configuration_signal() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         plateau.addObstacle(5, 5);
         plateau.addObstacle(4, 6);
         Obstacle obstacle = plateau.getObstacle(0);
         Assert.assertTrue("Obstacle X coordinate not correctly interpreted. Should be 5", obstacle.getX()==5);
         Assert.assertTrue("Obstacle Y coordinate not correctly interpreted. Should be 5", obstacle.getY()==5);
         obstacle = plateau.getObstacle(1);
         Assert.assertTrue("Obstacle X coordinate not correctly interpreted. Should be 4", obstacle.getX()==4);
         Assert.assertTrue("Obstacle Y coordinate not correctly interpreted. Should be 6", obstacle.getY()==6);
     }
     
     @Test(expected = PlateauConfigurationException.class)
     public void when_plateau_receives_obstacle_configuration_signal_trying_to_add_a_obstacle_where_there_is_already_another_obstacle_should_throw_excpetion() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         plateau.addObstacle(5, 5);
         plateau.addObstacle(5, 5);
     }
     
     @Test(expected = PlateauConfigurationException.class)
     public void when_plateau_receives_obstacle_configuration_signal_trying_to_add_a_obstacle_out_of_its_bounds_should_throw_excpetion() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         plateau.addObstacle(9, 9);
     }
     
     @Test(expected = PlateauConfigurationException.class)
     public void when_plateau_receives_obstacle_configuration_signal_trying_to_add_a_obstacle_with_negative_coordinates_must_throw_excpetion() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         plateau.addObstacle(-1, -1);
     }
     
     @Test()
     public void when_plateau_didnt_receive_obstacle_configuration_number_of_obstacles_must_be_equal_to_zero() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         Assert.assertEquals("wrong number of obstacles computed. should be 0", 0, plateau.getNumberOfObstacles());
     }
     
     @Test()
     public void when_plateau_receives_obstacle_configuration_number_of_obstacles_must_be_equal_to_the_number_of_added_obstacles() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         plateau.addObstacle(1, 1);
         plateau.addObstacle(1, 2);
         Assert.assertEquals("wrong number of obstacles computed. should be 2", 2, plateau.getNumberOfObstacles());
     }
}
