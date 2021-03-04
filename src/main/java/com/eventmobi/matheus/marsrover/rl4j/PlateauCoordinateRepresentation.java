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
public enum PlateauCoordinateRepresentation {
    Empty(0, " ", " "),
    RoverFacingNorth(1, "N", "A"),
    RoverFacingSouth(2, "S", "V"),
    RoverFacingEast(3, "E", ">"),
    RoverFacingWest(4, "W", "<"),
    Goal(5, "G", "G"),
    Obstacle(6, "O", "X");

    private final int code;
    private final String outputRepresentation;
    private final String consoleOutputRepresentation;

    PlateauCoordinateRepresentation(int code, String outputRepresentation, String consoleOutputRepresentation) {
        this.code = code;
        this.outputRepresentation = outputRepresentation;
        this.consoleOutputRepresentation=consoleOutputRepresentation;
    }

    public int getCode() {
        return code;
    }

    public String getOutputRepresentation() {
        return outputRepresentation;
    }

    public String getConsoleOutputRepresentation() {
        return consoleOutputRepresentation;
    }
    
    public static PlateauCoordinateRepresentation getByCode(int code){
        for (PlateauCoordinateRepresentation value : PlateauCoordinateRepresentation.values()) {
            if(value.getCode()==code){
                return value;
            }
        }
        return null;
    }
    
    public static PlateauCoordinateRepresentation getByOutputRepresentation(String outputRepresentation){
        for (PlateauCoordinateRepresentation value : PlateauCoordinateRepresentation.values()) {
            if(value.getOutputRepresentation().equals(outputRepresentation)){
                return value;
            }
        }
        return null;
    }
}
