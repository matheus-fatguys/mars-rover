package com.eventmobi.matheus.marsrover.instructions.navigation;

import com.eventmobi.matheus.marsrover.domain.Rover;
import java.util.ArrayList;
import java.util.List;

public class InstructionsInstruction extends NavigationInstruction {

    private static final char SPIN_LEFT_INPUT = 'L';
    private static final char SPIN_RIGHT_INPUT = 'R';
    private static final char MOVE_AHEAD_INPUT = 'M';

    public static final String NAME = "Instructions";

    public static InstructionsInstruction build(String roverName, String instructions) {

        List<NavigationInstruction> instructionList = new ArrayList<>();

        for (int i = 0; i < instructions.length(); i++) {
            char input = instructions.charAt(i);
            NavigationInstruction navigationInstruction = null;

            if (SPIN_LEFT_INPUT == input) {
                navigationInstruction = new SpinLeftInstruction(roverName);
            } else if (SPIN_RIGHT_INPUT == input) {
                navigationInstruction = new SpinRightInstruction(roverName);
            } else if (MOVE_AHEAD_INPUT == input) {
                navigationInstruction = new MoveAheadInstruction(roverName);
            }

            if (navigationInstruction != null) {
                instructionList.add(navigationInstruction);
            }
        }

        return new InstructionsInstruction(roverName, instructionList);
    }

    InstructionsInstruction(String roverName, List<NavigationInstruction> instructionList) {
        super(roverName);
        this.instructionList = instructionList;
    }

    private List<NavigationInstruction> instructionList;

    public List<NavigationInstruction> getInstructionList() {
        return instructionList;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(Rover rover) {
        if (getRoverName().equals(rover.getName())) {
            for (NavigationInstruction navigationInstruction : instructionList) {
                navigationInstruction.execute(rover);
            }
        }
    }
}
