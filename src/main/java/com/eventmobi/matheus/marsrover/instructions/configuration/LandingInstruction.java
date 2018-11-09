/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventmobi.matheus.marsrover.instructions.configuration;

import com.eventmobi.matheus.marsrover.domain.Facing;
import com.eventmobi.matheus.marsrover.domain.Rover;
import com.eventmobi.matheus.marsrover.instructions.InstructionType;
import com.eventmobi.matheus.marsrover.instructions.UnicastInstruction;

/**
 *
 * @author y2gh
 */
public class LandingInstruction extends UnicastInstruction {

    public static final String NAME = "Landing";

    public static LandingInstruction build(String roverName, String[] args) {
        return new LandingInstruction(roverName, Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()), Facing.getByInput(args[2]));
    }

    private int x;
    private int y;
    private Facing facing;
    private String roverName;

    public LandingInstruction(String roverName, int x, int y, Facing facing) {
        this.x = x;
        this.y = y;
        this.facing = facing;
        this.roverName = roverName;
    }

    @Override
    public String getName() {
        return NAME;
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

    public String getRoverName() {
        return roverName;
    }

    @Override
    public void execute(Rover rover) {
        if(roverName.equals(rover.getName())){
            rover.configureLanding(x, y, facing);
        }
    }

    @Override
    public InstructionType getType() {
        return InstructionType.CONFIGURATION;
    }

}
