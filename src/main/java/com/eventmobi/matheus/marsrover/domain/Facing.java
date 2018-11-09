package com.eventmobi.matheus.marsrover.domain;

public enum Facing {

    North("N"),
    East("E"),
    South("S"),
    West("W");

    private String inputRepresentation;

    Facing(String inputRepresentation) {
        this.inputRepresentation = inputRepresentation;
    }

    public static Facing getByInput(String input) {
        Facing f = null;
        for (Facing value : Facing.values()) {
            if(value.inputRepresentation.equals(input)){
                return value;
            }
        }
        return f;
    }

    public String getInputRepresentation() {
        return inputRepresentation;
    }
    
    
}
