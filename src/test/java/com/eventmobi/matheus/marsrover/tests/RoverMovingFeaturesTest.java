package com.eventmobi.matheus.marsrover.tests;

import com.eventmobi.matheus.marsrover.domain.Facing;
import com.eventmobi.matheus.marsrover.domain.Rover;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RoverMovingFeaturesTest {
    Rover rover = new Rover();
    
    public RoverMovingFeaturesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        rover = new Rover();
        rover.configurePlateau(5, 5);
    }
    
    @After
    public void tearDown() {
    }

     
     @Test
     public void when_rover_is_heading_east_and_moves_ahead_then_x_increments_by_1_and_y_remains_the_same() {
         
         rover.configureLanding(1, 2, Facing.East);
         rover.moveAhead();
         
         Assert.assertTrue("Rover move ahead not correctly incremented. x coordinate should be 2", rover.getX() == 2);
         Assert.assertTrue("Rover move ahead correctly done. y coordinate should remain the same", rover.getY() == 2);
     }
     
     @Test
     public void when_rover_is_heading_west_and_moves_ahead_then_x_decrements_by_1_and_y_remains_the_same() {
         Rover rover = new Rover();
         rover.configureLanding(1, 2, Facing.West);
         rover.moveAhead();
         
         Assert.assertTrue("Rover move ahead not correctly decremented. x coordinate should be 0", rover.getX() == 0);
         Assert.assertTrue("Rover move ahead not correctly done. y coordinate should remain the same", rover.getY() == 2);
     }
     
     @Test
     public void when_rover_is_heading_north_and_moves_ahead_then_y_increments_by_1_and_x_remains_the_same() {
         rover.configureLanding(1, 2, Facing.North);
         rover.moveAhead();
         
         Assert.assertTrue("Rover move ahead not correctly done. x coordinate should remain the same", rover.getX() == 1);
         Assert.assertTrue("Rover move ahead not correctly incremented. y coordinate should be 3", rover.getY() == 3);
     }
     
     @Test
     public void when_rover_is_heading_south_and_moves_ahead_then_y_decrements_by_1_and_x_remains_the_same() {
         rover.configureLanding(1, 2, Facing.South);
         rover.moveAhead();
         
         Assert.assertTrue("Rover move ahead not correctly done. x coordinate should remain the same", rover.getX() == 1);
         Assert.assertTrue("Rover move ahead not correctly decremented. y coordinate should be 1", rover.getY() == 1);
     }
     
     @Test
     public void when_rover_is_facing_plateaus_border_and_is_ordered_to_move_ahead__then_it_should_ignore_the_order() {
         rover.configurePlateau(5, 5);
         rover.configureLanding(5, 5, Facing.North);
         rover.moveAhead();
         
         Assert.assertTrue("Rover move ahead not correctly done. x coordinate should remain the same", rover.getX() == 5);
         Assert.assertTrue("Rover move ahead not correctly decremented. y coordinate should  remain the same", rover.getY() == 5);
         
         rover.configureLanding(5, 5, Facing.East);
         rover.moveAhead();
         
         Assert.assertTrue("Rover move ahead not correctly done. x coordinate should remain the same", rover.getX() == 5);
         Assert.assertTrue("Rover move ahead not correctly decremented. y coordinate should  remain the same", rover.getY() == 5);
         
         rover.configureLanding(0, 0, Facing.South);
         rover.moveAhead();
         
         Assert.assertTrue("Rover move ahead not correctly done. x coordinate should remain the same", rover.getX() == 0);
         Assert.assertTrue("Rover move ahead not correctly decremented. y coordinate should  remain the same", rover.getY() == 0);
         
         rover.configureLanding(0, 0, Facing.West);
         rover.moveAhead();
         
         Assert.assertTrue("Rover move ahead not correctly done. x coordinate should remain the same", rover.getX() == 0);
         Assert.assertTrue("Rover move ahead not correctly decremented. y coordinate should  remain the same", rover.getY() == 0);
     }
}
