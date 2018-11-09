package com.eventmobi.matheus.marsrover.instructions.navigation;

import com.eventmobi.matheus.marsrover.instructions.InstructionType;
import com.eventmobi.matheus.marsrover.instructions.UnicastInstruction;

public abstract class NavigationInstruction extends UnicastInstruction{
    private String roverName;

    public NavigationInstruction(String roverName) {
        this.roverName = roverName;
    }
    
    
    public String getRoverName() {
        return roverName;
    }

    @Override
    public InstructionType getType() {
        return InstructionType.NAVIGATION;
    }
    
}
