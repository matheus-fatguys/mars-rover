package com.eventmobi.matheus.marsrover.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Mars {
    
    private Map<String, Rover> roverMap = new HashMap();

    public List<Rover> getRobotlist() {
        List<Rover> roverList = new ArrayList<>();
        for (Rover rover : roverMap.values()) {
            roverList.add(rover);                    
        }
        return roverList;
    }
    
    
    public String getStatus(){
        List<Rover> roverList = new ArrayList<>();
        for (Rover rover : roverMap.values()) {
            roverList.add(rover);                    
        }
        Collections.sort(roverList, new Comparator(){
            @Override
            public int compare(Object rover1, Object rover2) {
                return ((Rover)rover1).getName().compareTo(((Rover)rover2).getName());
            }
            
        });
        String status = "";
        for (Rover rover : roverList) {
            status+=rover.transmitCurrentStatus()+"\n";
        }
        return status;
    }


    public void thereIsARoverNamed(String roverName) {
        if(!roverMap.containsKey(roverName)){
            Rover newRover = new Rover();
            newRover.setName(roverName);
            roverMap.put(roverName, newRover);
        }
    }

    public void notifyRovers(String signal) {
        for (Rover rover : roverMap.values()) {
            rover.receiveSignal(signal);
        }
    }
    
}
