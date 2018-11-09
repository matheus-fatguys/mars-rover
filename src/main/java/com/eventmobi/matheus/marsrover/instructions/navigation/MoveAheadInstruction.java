package com.eventmobi.matheus.marsrover.instructions.navigation;

import com.eventmobi.matheus.marsrover.domain.Rover;

public class MoveAheadInstruction  extends NavigationInstruction{
    
    public static final String NAME = "Move Ahead";

    public MoveAheadInstruction(String roverName) {
        super(roverName);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(Rover rover) {
        rover.moveAhead();
    }
}
