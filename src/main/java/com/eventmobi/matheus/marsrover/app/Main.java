package com.eventmobi.matheus.marsrover.app;

import com.eventmobi.matheus.marsrover.domain.Mars;
import com.eventmobi.matheus.marsrover.instructions.InstructionInterpreter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args){
        List<String> signalsReceived = new ArrayList();
        try {
            File inputFile = new File(args[0]);
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader reader = new BufferedReader(fileReader);
            InstructionInterpreter interpreter = new InstructionInterpreter();
            String signal = reader.readLine();
            while ( null != signal && !signal.trim().equals("") ) {
                signalsReceived.add(signal);
                signal = reader.readLine();
            }
            
            Mars mars = interpreter.interpretBatch(signalsReceived);
            
            System.out.println(mars.getStatus());
        } catch (IOException ex) {
            throw new RuntimeException("IO Error");
        }
    }
}
