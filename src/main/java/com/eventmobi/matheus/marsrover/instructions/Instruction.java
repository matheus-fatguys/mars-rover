package com.eventmobi.matheus.marsrover.instructions;

import com.eventmobi.matheus.marsrover.domain.Rover;

public interface Instruction {

    public String getName();
    public InstructionType getType();
    public boolean isUnicast();
    public void execute(Rover rover);
    
}
