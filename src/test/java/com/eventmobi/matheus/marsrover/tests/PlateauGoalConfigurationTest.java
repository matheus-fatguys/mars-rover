/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventmobi.matheus.marsrover.tests;

import com.eventmobi.matheus.marsrover.domain.Goal;
import com.eventmobi.matheus.marsrover.domain.Plateau;
import com.eventmobi.matheus.marsrover.domain.Rover;
import com.eventmobi.matheus.marsrover.domain.exception.PlateauConfigurationException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Matheus Araújo
 */
public class PlateauGoalConfigurationTest {
    @Test
     public void when_plateau_receives_goal_configuration_signal() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         plateau.addGoal(5, 5);
         Goal goal = plateau.getGoal(0);
         Assert.assertTrue("Goal X coordinate not correctly interpreted. Should be 5", goal.getX()==5);
         Assert.assertTrue("Goal Y coordinate not correctly interpreted. Should be 5", goal.getY()==5);
     }
     
    @Test
     public void when_plateau_receives_a_second_goal_configuration_signal() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         plateau.addGoal(5, 5);
         plateau.addGoal(4, 6);
         Goal goal = plateau.getGoal(0);
         Assert.assertTrue("Goal X coordinate not correctly interpreted. Should be 5", goal.getX()==5);
         Assert.assertTrue("Goal Y coordinate not correctly interpreted. Should be 5", goal.getY()==5);
         goal = plateau.getGoal(1);
         Assert.assertTrue("Goal X coordinate not correctly interpreted. Should be 4", goal.getX()==4);
         Assert.assertTrue("Goal Y coordinate not correctly interpreted. Should be 6", goal.getY()==6);
     }
     
     @Test(expected = PlateauConfigurationException.class)
     public void when_plateau_receives_goal_configuration_signal_trying_to_add_a_goal_where_there_is_already_another_goal_should_throw_excpetion() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         plateau.addGoal(5, 5);
         plateau.addGoal(5, 5);
     }
     
     @Test(expected = PlateauConfigurationException.class)
     public void when_plateau_receives_goal_configuration_signal_trying_to_add_a_goal_out_of_its_bounds_should_throw_excpetion() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         plateau.addGoal(9, 9);
     }
     
     @Test(expected = PlateauConfigurationException.class)
     public void when_plateau_receives_goal_configuration_signal_trying_to_add_a_goal_with_negative_coordinates_must_throw_excpetion() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         plateau.addGoal(-1, -1);
     }
     
     @Test()
     public void when_plateau_didnt_receive_goal_configuration_number_of_goals_must_be_equal_to_zero() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         Assert.assertEquals("wrong number of goals computed. should be 0", 0, plateau.getNumberOfGoals());
     }
     
     @Test()
     public void when_plateau_receives_goal_configuration_number_of_goals_must_be_equal_to_the_number_of_added_goals() {
         String signal = "Plateau: 8 8 ";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         Plateau  plateau = rover.getPlateau();
         plateau.addGoal(1, 1);
         plateau.addGoal(1, 2);
         Assert.assertEquals("wrong number of goals computed. should be 2", 2, plateau.getNumberOfGoals());
     }
}
