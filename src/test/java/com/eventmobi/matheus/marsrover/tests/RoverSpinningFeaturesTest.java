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

public class RoverSpinningFeaturesTest {
    
    public RoverSpinningFeaturesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

     
     @Test
     public void when_rover_is_heading_north_and_spins_left_then_should_face_west() {
         Rover rover = new Rover();
         rover.configureLanding(1, 2, Facing.North);
         rover.spingLeft();
         
         Assert.assertTrue("Rover sping not correctly done. Should be facing west", Facing.West.equals(rover.getFacing()));
     }
     
     @Test
     public void when_rover_is_heading_west_and_spins_left_then_should_face_south() {
         Rover rover = new Rover();
         rover.configureLanding(1, 2, Facing.West);
         rover.spingLeft();
         
         Assert.assertTrue("Rover sping not correctly done. Should be facing South", Facing.South.equals(rover.getFacing()));
     }
     
     @Test
     public void when_rover_is_heading_south_and_spins_left_then_should_face_east() {
         Rover rover = new Rover();
         rover.configureLanding(1, 2, Facing.South);
         rover.spingLeft();
         
         Assert.assertTrue("Rover sping not correctly done. Should be facing East", Facing.East.equals(rover.getFacing()));
     }
     
     @Test
     public void when_rover_is_heading_east_and_spins_left_then_should_face_north() {
         Rover rover = new Rover();
         rover.configureLanding(1, 2, Facing.East);
         rover.spingLeft();
         
         Assert.assertTrue("Rover sping not correctly done. Should be facing North", Facing.North.equals(rover.getFacing()));
     }
     
     @Test
     public void when_rover_is_heading_north_and_spins_right_then_should_face_east() {
         Rover rover = new Rover();
         rover.configureLanding(1, 2, Facing.North);
         rover.spingRight();
         
         Assert.assertTrue("Rover sping not correctly done. Should be facing East", Facing.East.equals(rover.getFacing()));
     }
     
     @Test
     public void when_rover_is_heading_east_and_spins_right_then_should_face_south() {
         Rover rover = new Rover();
         rover.configureLanding(1, 2, Facing.East);
         rover.spingRight();
         
         Assert.assertTrue("Rover sping not correctly done. Should be facing South", Facing.South.equals(rover.getFacing()));
     }
     
     @Test
     public void when_rover_is_heading_south_and_spins_right_then_should_face_west() {
         Rover rover = new Rover();
         rover.configureLanding(1, 2, Facing.South);
         rover.spingRight();
         
         Assert.assertTrue("Rover sping not correctly done. Should be facing West", Facing.West.equals(rover.getFacing()));
     }
     
     @Test
     public void when_rover_is_heading_west_and_spins_right_then_should_face_north() {
         Rover rover = new Rover();
         rover.configureLanding(1, 2, Facing.West);
         rover.spingRight();
         
         Assert.assertTrue("Rover sping not correctly done. Should be facing North", Facing.North.equals(rover.getFacing()));
     }
     
}
