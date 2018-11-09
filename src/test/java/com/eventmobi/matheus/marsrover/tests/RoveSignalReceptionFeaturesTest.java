package com.eventmobi.matheus.marsrover.tests;

import com.eventmobi.matheus.marsrover.domain.Facing;
import com.eventmobi.matheus.marsrover.domain.Rover;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RoveSignalReceptionFeaturesTest {
    
    public RoveSignalReceptionFeaturesTest() {
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
     //when signal is "Plateau: 5 5" then rover must be configured with Plateau and topX=5 and topY=5
     public void when_rover_receives_plateau_configuration_signal() {
         String signal = "Plateau: 5 5";
         Rover rover = new Rover();
         rover.receiveSignal(signal);
         
         Assert.assertTrue("Plateau top X coordinate not correctly interpreted. Should be 5", rover.getPlateau().getTopX()==5);
         Assert.assertTrue("Plateau top Y coordinate not correctly interpreted. Should be 5", rover.getPlateau().getTopY()==5);
     }
    
     @Test
     //when signal is "Rover1 Landing:1 2 N" and rover name is Rover1 then rover must be configured with landing and X=1 and topY=2
     public void when_rover_receives_landing_configuration_signal() {
         String signal = "Rover1 Landing:1 2 N";
         Rover rover = new Rover();
         rover.setName("Rover1");
         rover.receiveSignal(signal);
         
         Assert.assertTrue("Landing X coordinate not correctly interpreted. Should be 5", rover.getX()==1);
         Assert.assertTrue("Landing Y coordinate not correctly interpreted. Should be 5", rover.getY()==2);
     }
    
     @Test
     //when Rover 1 receives signal "Rover1 Instructions:LMLMLMLMM" after "Rover1 Landing:1 2 N" and "Plateau: 5 5" then rover status should be Rover1:1 3 N
     //
     public void when_rover_receives_instructions_signal_staus_should_be() {
         Rover rover = new Rover();
         rover.setName("Rover1");
         String signal = "Plateau: 5 5";
         rover.receiveSignal(signal);
         signal = "Rover1 Landing:1 2 N";
         rover.receiveSignal(signal);
         signal = "Rover1 Instructions:LMLMLMLMM";
         rover.receiveSignal(signal);
         
         Assert.assertTrue("Instructions not correctly interpreted. x Should be 5", rover.getX()==1);
         Assert.assertTrue("Instructions not correctly interpreted. y Should be 5", rover.getY()==3);
         Assert.assertTrue("Instructions not correctly interpreted. Facing Should be North", Facing.North.equals(rover.getFacing()));
     }
     @Test
     
//when Rovers 1 and 2 receive signals 
//     Plateau:5 5
//    Rover1 Landing:1 2 N
//    Rover1 Instructions:LMLMLMLMM
//    Rover2 Landing:3 3 E
//    Rover2 Instructions:MMRMMRMRRM
     
//     Status should be
//     Rover1:1 3 N
//     Rover2:5 1 E
//
     public void when_rover_receives_sequence_of_signlas_status_should_be() {
         Rover rover1 = new Rover();
         rover1.setName("Rover1");
         Rover rover2 = new Rover();
         rover2.setName("Rover2");
         
         String signal = "Plateau: 5 5";
         rover1.receiveSignal(signal);
         rover2.receiveSignal(signal);
         signal = "Rover1 Landing:1 2 N";
         rover1.receiveSignal(signal);
         rover2.receiveSignal(signal);
         signal = "Rover1 Instructions:LMLMLMLMM";
         rover1.receiveSignal(signal);
         rover2.receiveSignal(signal);
         signal = "Rover2 Landing:3 3 E";
         rover1.receiveSignal(signal);
         rover2.receiveSignal(signal);
         signal = "Rover2 Instructions:MMRMMRMRRM";
         rover1.receiveSignal(signal);
         rover2.receiveSignal(signal);
         
         Assert.assertTrue("Instructions not correctly interpreted. Rover1.x Should be 5", rover1.getX()==1);
         Assert.assertTrue("Instructions not correctly interpreted. Rover1.y Should be 5", rover1.getY()==3);
         Assert.assertTrue("Instructions not correctly interpreted. Rover1.Facing Should be North", Facing.North.equals(rover1.getFacing()));
         Assert.assertTrue("Instructions not correctly interpreted. Rover2.x Should be 5", rover2.getX()==5);
         Assert.assertTrue("Instructions not correctly interpreted. Rover2.y Should be 5", rover2.getY()==1);
         Assert.assertTrue("Instructions not correctly interpreted. Rover2.Facing Should be North", Facing.East.equals(rover2.getFacing()));
     }
     
     @Test
     //when signal is to another rover then rover must ignore it
     public void when_signal_is_to_another_rover_then_rover_should_ignore_it() {
         String signal = "Rover2 Landing:1 2 N";
         Rover rover = new Rover();
         rover.setName("Rover1");
         rover.receiveSignal(signal);
         
         Assert.assertTrue("Landing instruction not correctly interpreted. x Should remain the same and be 0", rover.getX()==0);
         Assert.assertTrue("Landing instruction not correctly interpreted. x Should remain the same and be 0", rover.getY()==0);
     }
}
