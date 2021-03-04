package com.eventmobi.matheus.marsrover.domain;

import com.eventmobi.matheus.marsrover.instructions.Instruction;
import com.eventmobi.matheus.marsrover.instructions.InstructionInterpreter;

public class Rover {

    private InstructionInterpreter interpreter = new InstructionInterpreter();
    private Plateau plateau;
    private int x;
    private int y;
    private Facing facing;
    private String name;
    private boolean tryedToMoveToForbidenPosition;
    private boolean died;

    public void configurePlateau(int topX, int topY) {
        plateau = new Plateau(topX, topY);
    }

    public String transmitCurrentStatus() {
        return name + ": " + x + " " + y + " " + facing.getInputRepresentation();
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Facing getFacing() {
        return facing;
    }

    public boolean isDied() {
        return died;
    }

    public void setDied(boolean died) {
        this.died = died;
    }    

    public boolean isTryedToMoveToForbidenPosition() {
        return tryedToMoveToForbidenPosition;
    }
    

    public void configureLanding(int x, int y, Facing facing) {
        this.facing = facing;
        this.x = x;
        this.y = y;
    }

    public void spingLeft() {
        switch (facing) {
            case North:
                facing = Facing.West;
                break;
            case West:
                facing = Facing.South;
                break;
            case South:
                facing = Facing.East;
                break;
            case East:
                facing = Facing.North;
                break;
        }
    }

    public void spingRight() {
        switch (facing) {
            case North:
                facing = Facing.East;
                break;
            case East:
                facing = Facing.South;
                break;
            case South:
                facing = Facing.West;
                break;
            case West:
                facing = Facing.North;
                break;
        }
    }

    public void moveAhead() {
        int newX = x;
        int newY = y;
        tryedToMoveToForbidenPosition=false;
        switch (facing) {
            case North:
                if (newY < plateau.getTopY()-1) {
                    newY++;
                }
                else{
                    tryedToMoveToForbidenPosition=true;
                }
                break;
            case East:
                if (newX < plateau.getTopY()-1) {
                    newX++;
                }
                else{
                    tryedToMoveToForbidenPosition=true;
                }
                break;
            case South:
                if (newY > 0) {
                    newY--;
                }
                else{
                    tryedToMoveToForbidenPosition=true;
                }
                break;
            case West:
                if (newX > 0) {
                    newX--;
                }
                else{
                    tryedToMoveToForbidenPosition=true;
                }
                break;
        }
        if (plateau != null && plateau.isThereAnObstacleAt(newX, newY)) {
            tryedToMoveToForbidenPosition=true;                
            return;
        }
        x = newX;
        y = newY;
    }

    public void receiveSignal(String signal) {
        Instruction instruction = interpreter.interpretSingleInput(signal);
        instruction.execute(this);
    }

    public InstructionInterpreter getInterpreter() {
        return interpreter;
    }

    public void setInterpreter(InstructionInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMissionAccomplished() {
        return plateau.isThereAGoalAt(x, y);
    }

}
