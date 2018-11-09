package com.eventmobi.matheus.marsrover.instructions.navigation;

import com.eventmobi.matheus.marsrover.domain.Rover;

public class SpinLeftInstruction extends NavigationInstruction{
    
    public static final String NAME = "Spin Left";

    public SpinLeftInstruction(String roverName) {
        super(roverName);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(Rover rover) {
        rover.spingLeft();
    }
    
}
