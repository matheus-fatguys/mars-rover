/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventmobi.matheus.marsrover.instructions;

/**
 *
 * @author y2gh
 */
public abstract class UnicastInstruction implements Instruction{
    
    public abstract String getRoverName();
    
    @Override
    public boolean isUnicast() {
        return true;
    }
}
