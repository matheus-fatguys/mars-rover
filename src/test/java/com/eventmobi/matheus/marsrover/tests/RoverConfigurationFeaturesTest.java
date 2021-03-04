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

public class RoverConfigurationFeaturesTest {
    
    public RoverConfigurationFeaturesTest() {
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
     //when rover is is configured with a plateau 5 5 then plateau topX=5 and topY=5
     public void when_rover_is_configured_with_plateau_5_5() {
         Rover rover = new Rover();
         rover.configurePlateau(5, 5);
         
         Assert.assertTrue("Plateau top X coordinate not correctly configurated. Should be 5", rover.getPlateau().getTopX()==5);
         Assert.assertTrue("Plateau top Y coordinate not correctly configurated. Should be 5", rover.getPlateau().getTopY()==5);
     }
     
     @Test
     //when rover is is configured with a landing at  1 2 N then X=1 and Y=2 and facing = North
     public void when_rover_is_configured_with_landing_1_2_N() {
         Rover rover = new Rover();
         rover.configureLanding(1, 2, Facing.North);
         
         Assert.assertTrue("Rover X coordinate not correctly configurated. Should be 1", rover.getX()==1);
         Assert.assertTrue("Rover Y coordinate not correctly configurated. Should be 2", rover.getY()==2);
         Assert.assertTrue("Rover facing not correctly configurated. Should be North", Facing.North.equals(rover.getFacing()));
     }
}
