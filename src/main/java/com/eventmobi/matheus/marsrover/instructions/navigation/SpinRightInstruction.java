package com.eventmobi.matheus.marsrover.instructions.navigation;

import com.eventmobi.matheus.marsrover.domain.Rover;

public class SpinRightInstruction extends NavigationInstruction{
    
    public static final String NAME = "Spin Right";

    public SpinRightInstruction(String roverName) {
        super(roverName);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(Rover rover) {
        rover.spingRight();
    }
}
