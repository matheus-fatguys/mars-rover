/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventmobi.matheus.marsrover.rl4j;

/**
 *
 * @author Matheus Araújo
 */
public enum RoverAction {

    MoveAhead(0, "M"),
    SpinLeft(1, "L"),
    SpinRight(2, "R");

    private final int code;
    private final String instruction;

    RoverAction(int code, String strCode) {
        this.code = code;
        this.instruction = strCode;
    }

    public int getCode() {
        return code;
    }

    public String getInstruction() {
        return instruction;
    }
    
    public static RoverAction getByCode(int code){
        for (RoverAction value : RoverAction.values()) {
            if(value.getCode()==code){
                return value;
            }
        }
        return null;
    }

}
