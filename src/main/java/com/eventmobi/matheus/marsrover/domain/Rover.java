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

    public void configurePlateau(int topX, int topY) {
        plateau = new Plateau(topX, topY);
    }
    
    public String transmitCurrentStatus(){
        return name+": "+x+" "+y+" "+facing.getInputRepresentation();
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

    public void configureLanding(int x, int y, Facing facing) {
        this.facing=facing;
        this.x = x;
        this.y = y;
    }

    public void spingLeft() {
        switch(facing){
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
        switch(facing){
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
    
    public void moveAhead(){
        switch(facing){
            case North:
                if(y<plateau.getTopY()){
                    y++;
                }
                break;
            case East:
                if(x<plateau.getTopX()){
                    x++;
                }
                break;
            case South:
                if(y>0){
                    y--;
                }
                break;
            case West:
                if(x>0){
                    x--;
                }
                break;
        }
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
    
}
