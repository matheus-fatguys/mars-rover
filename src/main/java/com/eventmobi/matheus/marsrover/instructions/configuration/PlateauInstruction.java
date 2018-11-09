/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventmobi.matheus.marsrover.instructions.configuration;

import com.eventmobi.matheus.marsrover.domain.Rover;

/**
 *
 * @author y2gh
 */
public class PlateauInstruction  extends ConfigurationInstruction{

    public static final String NAME = "Plateau";

    public static PlateauInstruction build(String[] args) {
        try{
            return new PlateauInstruction(Integer.parseInt(args[0].trim()), Integer.parseInt(args[1].trim()));
        }
        catch(NumberFormatException ex){
            return null;
        }
    }
    
    
    private int topX;
    private int topY;

    PlateauInstruction(int topX, int topY) {
        this.topX = topX;
        this.topY = topY;
    }

    public int getTopX() {
        return topX;
    }

    public int getTopY() {
        return topY;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(Rover rover) {
        rover.configurePlateau(topX, topY);
    }

    @Override
    public boolean isUnicast() {
        return false;
    }

}
