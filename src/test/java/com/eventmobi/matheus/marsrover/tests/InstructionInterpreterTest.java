package com.eventmobi.matheus.marsrover.tests;

import com.eventmobi.matheus.marsrover.domain.Facing;
import com.eventmobi.matheus.marsrover.instructions.Instruction;
import com.eventmobi.matheus.marsrover.instructions.InstructionInterpreter;
import com.eventmobi.matheus.marsrover.instructions.InstructionType;
import com.eventmobi.matheus.marsrover.instructions.navigation.InstructionsInstruction;
import com.eventmobi.matheus.marsrover.instructions.configuration.LandingInstruction;
import com.eventmobi.matheus.marsrover.instructions.configuration.PlateauInstruction;
import com.eventmobi.matheus.marsrover.instructions.navigation.MoveAheadInstruction;
import com.eventmobi.matheus.marsrover.instructions.navigation.SpinLeftInstruction;
import com.eventmobi.matheus.marsrover.instructions.navigation.SpinRightInstruction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class InstructionInterpreterTest {
    
    public InstructionInterpreterTest() {
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
     //when input is "Plateau: 5 5" then instruction type is configuration, instruction name is Plateau and topX=5 and topY=5
     public void when_input_is_plateau_5_5_then() {
         String input = "Plateau: 5 5";
         InstructionInterpreter interpreter = new InstructionInterpreter();
         Instruction  instruction = interpreter.interpretSingleInput(input);
         Assert.assertTrue("Plateau instruction type not correctly interpreted. Should be Configuration", InstructionType.CONFIGURATION.equals(instruction.getType()));
         Assert.assertTrue("Plateau instruction name not correctly interpreted. Should be Plateau", PlateauInstruction.NAME.equals(instruction.getName()));
         PlateauInstruction plateauInstruction = (PlateauInstruction)instruction;
         Assert.assertTrue("Plateau top X coordinate not correctly interpreted. Should be 5", plateauInstruction.getTopX()==5);
         Assert.assertTrue("Plateau top Y coordinate not correctly interpreted. Should be 5", plateauInstruction.getTopY()==5);
     }
     
     @Test
     //when input is "Rover1 Landing:1 2 N" then instruction type is configuration, instruction name is Landing and roverName=Rover1 and x=1 and y=2 and facing=North
     public void when_input_is_Rover1_Landing_1_2_N_then() {
         String input = "Rover1 Landing:1 2 N";
         InstructionInterpreter interpreter = new InstructionInterpreter();
         Instruction  instruction = interpreter.interpretSingleInput(input);
         Assert.assertTrue("Landing instruction type not correctly interpreted. Should be Configuration", InstructionType.CONFIGURATION.equals(instruction.getType()));
         Assert.assertTrue("Landing instruction name not correctly interpreted. Should be Landing", LandingInstruction.NAME.equals(instruction.getName()));
         LandingInstruction landingInstruction = (LandingInstruction)instruction;
         Assert.assertTrue("Landing rover name not correctly interpreted. Should be Rover1", landingInstruction.getRoverName().equals("Rover1"));
         Assert.assertTrue("Landing X coordinate not correctly interpreted. Should be 1", landingInstruction.getX()==1);
         Assert.assertTrue("Landing Y coordinate not correctly interpreted. Should be 2", landingInstruction.getY()==2);
         Assert.assertTrue("Landing facing not correctly interpreted. Should be North", Facing.North.equals(landingInstruction.getFacing()));
     }
     
     @Test
     //when input is "Rover1 Instructions:LMLMLMLMM" then instruction type is Navigation, instruction name is Instructions and instruction list is 
     //SpinLeftInstrution, MoveAheadInstrution, SpinLeftInstrution, MoveAheadInstrution,SpinLeftInstrution, MoveAheadInstrution,SpinLeftInstrution, MoveAheadInstrution, MoveAheadInstrution
     public void when_input_is_Rover1_Instructions_LMLMLMLMM_then() {
         String input = "Rover1 Instructions:LMLMLMLMM";
         InstructionInterpreter interpreter = new InstructionInterpreter();
         Instruction  instruction = interpreter.interpretSingleInput(input);
         Assert.assertTrue("Instructions instruction type not correctly interpreted. Should be Navigation", InstructionType.NAVIGATION.equals(instruction.getType()));
         Assert.assertTrue("Instructions instruction name not correctly interpreted. Should be Instructions", InstructionsInstruction.NAME.equals(instruction.getName()));
         InstructionsInstruction instructionsInstruction = (InstructionsInstruction)instruction;
         Assert.assertTrue("Landing rover name not correctly interpreted. Should be Rover1", instructionsInstruction.getRoverName().equals("Rover1"));
         Assert.assertTrue("Instructions list not correctly interpreted. Its size should be exactly 9", instructionsInstruction.getInstructionList().size()==9);
         Assert.assertTrue("Instructions not correctly interpreted. 1st instruction Should be SpinLeftInstrution", instructionsInstruction.getInstructionList().get(0) instanceof SpinLeftInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 2nd instruction Should be MoveAheadInstrution", instructionsInstruction.getInstructionList().get(1) instanceof MoveAheadInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 3rd instruction Should be SpinLeftInstrution", instructionsInstruction.getInstructionList().get(2) instanceof SpinLeftInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 4th instruction Should be MoveAheadInstrution", instructionsInstruction.getInstructionList().get(3) instanceof MoveAheadInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 5th instruction Should be SpinLeftInstrution", instructionsInstruction.getInstructionList().get(4) instanceof SpinLeftInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 6th instruction Should be MoveAheadInstrution", instructionsInstruction.getInstructionList().get(5) instanceof MoveAheadInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 7th instruction Should be SpinLeftInstrution", instructionsInstruction.getInstructionList().get(6) instanceof SpinLeftInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 8th instruction Should be MoveAheadInstrution", instructionsInstruction.getInstructionList().get(7) instanceof MoveAheadInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 9th instruction Should be MoveAheadInstrution", instructionsInstruction.getInstructionList().get(8) instanceof MoveAheadInstruction);
     }
     
     @Test
     //when input is "Rover2 Instructions:MMRMMRMRRM" then instruction type is Navigation, instruction name is Instructions and instruction list is 
     //MoveAheadInstrution, MoveAheadInstrution, SpinRightInstruction, MoveAheadInstrution, MoveAheadInstrution, SpinRightInstruction,MoveAheadInstrution, MoveAheadInstrution,SpinRightInstruction, SpinRightInstruction, MoveAheadInstrution
     public void when_input_is_Rover2_Instructions_MMRMMRMRRM_then() {
         String input = "Rover2 Instructions:MMRMMRMRRM";
         InstructionInterpreter interpreter = new InstructionInterpreter();
         Instruction  instruction = interpreter.interpretSingleInput(input);
         Assert.assertTrue("Instructions instruction type not correctly interpreted. Should be Navigation", InstructionType.NAVIGATION.equals(instruction.getType()));
         Assert.assertTrue("Instructions instruction name not correctly interpreted. Should be Instructions", InstructionsInstruction.NAME.equals(instruction.getName()));
         InstructionsInstruction instructionsInstruction = (InstructionsInstruction)instruction;
         Assert.assertTrue("Landing rover name not correctly interpreted. Should be Rover1", instructionsInstruction.getRoverName().equals("Rover2"));
         Assert.assertTrue("Instructions list not correctly interpreted. Its size should be exactly 10", instructionsInstruction.getInstructionList().size()==10);
         Assert.assertTrue("Instructions not correctly interpreted. 1st instruction Should be MoveAheadInstruction", instructionsInstruction.getInstructionList().get(0) instanceof MoveAheadInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 2nd instruction Should be MoveAheadInstrution", instructionsInstruction.getInstructionList().get(1) instanceof MoveAheadInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 3rd instruction Should be SpinRightInstruction", instructionsInstruction.getInstructionList().get(2) instanceof SpinRightInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 4th instruction Should be MoveAheadInstrution", instructionsInstruction.getInstructionList().get(3) instanceof MoveAheadInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 5th instruction Should be MoveAheadInstruction", instructionsInstruction.getInstructionList().get(4) instanceof MoveAheadInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 6th instruction Should be SpinRightInstruction", instructionsInstruction.getInstructionList().get(5) instanceof SpinRightInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 7th instruction Should be MoveAheadInstruction", instructionsInstruction.getInstructionList().get(6) instanceof MoveAheadInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 8th instruction Should be SpinRightInstruction", instructionsInstruction.getInstructionList().get(7) instanceof SpinRightInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 9th instruction Should be SpinRightInstruction", instructionsInstruction.getInstructionList().get(8) instanceof SpinRightInstruction);
         Assert.assertTrue("Instructions not correctly interpreted. 10th instruction Should be MoveAheadInstrution", instructionsInstruction.getInstructionList().get(9) instanceof MoveAheadInstruction);
     }
}
