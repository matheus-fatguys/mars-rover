package com.eventmobi.matheus.marsrover.instructions;

import com.eventmobi.matheus.marsrover.domain.Mars;
import com.eventmobi.matheus.marsrover.instructions.configuration.PlateauInstruction;
import com.eventmobi.matheus.marsrover.instructions.configuration.LandingInstruction;
import com.eventmobi.matheus.marsrover.instructions.navigation.InstructionsInstruction;
import java.util.List;

public class InstructionInterpreter {

    public Instruction interpretSingleInput(String input) {
        Instruction instruction = null;

        String[] split = input.trim().split(":");
        String[] args = split[1].trim().split(" ");

        if (PlateauInstruction.NAME.equals(split[0])) {
            try {                
                instruction = PlateauInstruction.build(args);
            } 
            catch (NumberFormatException ex) {
                return null;
            }
        }
        else {
            String[] secondSplit  = split[0].trim().split(" ");
            String instructionName = secondSplit[1];
            String roverName = secondSplit[0];
            if(LandingInstruction.NAME.equals(instructionName)){
                instruction = LandingInstruction.build(roverName, args);
            }
            else if (InstructionsInstruction.NAME.equals(instructionName)){
                instruction = InstructionsInstruction.build(roverName, args[0]);
            }
        }

        return instruction;
    }

    public Mars interpretBatch(List<String> signalsReceived) {
        Mars mars = new Mars();
        for (String signal : signalsReceived) {
            Instruction instruction = interpretSingleInput(signal);
            if(instruction.isUnicast()) {
                String roverName = ((UnicastInstruction)instruction).getRoverName();
                mars.thereIsARoverNamed(roverName);
            }
        }
        for (String signal : signalsReceived) {
            mars.notifyRovers(signal);
        }
        return mars;
    }

}
