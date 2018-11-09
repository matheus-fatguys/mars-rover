/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventmobi.matheus.marsrover.instructions.configuration;

import com.eventmobi.matheus.marsrover.instructions.Instruction;
import com.eventmobi.matheus.marsrover.instructions.InstructionType;

/**
 *
 * @author y2gh
 */
public abstract class ConfigurationInstruction implements Instruction {

    @Override
    public InstructionType getType() {
        return InstructionType.CONFIGURATION;
    }
    
    @Override
    public boolean isUnicast() {
        return true;
    }

}
