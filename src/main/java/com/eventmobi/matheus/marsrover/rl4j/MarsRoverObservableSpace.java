/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eventmobi.matheus.marsrover.rl4j;

import com.eventmobi.matheus.marsrover.domain.Goal;
import com.eventmobi.matheus.marsrover.domain.Obstacle;
import com.eventmobi.matheus.marsrover.domain.Plateau;
import com.eventmobi.matheus.marsrover.domain.Rover;
import org.deeplearning4j.rl4j.space.Encodable;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;

/**
 *
 * @author Matheus Araújo
 */
public class MarsRoverObservableSpace implements Encodable {

    public static int convertTo1DimensionCoordinate(int yLength, int x, int y) {
        int index = x * yLength + y;
        return index;
    }

    private Rover rover;
    private PlateauCoordinateRepresentation[][] plateauRepresentation;
    private String stringRepresentation;

    public Rover getRover() {
        return rover;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
        Plateau plateau = rover.getPlateau();
        plateauRepresentation = new PlateauCoordinateRepresentation[plateau.getTopX()][plateau.getTopY()];
        buildPlateauRepresentation();
    }
    

    public String getStringRepresentation() {
        return stringRepresentation;
    }

    public PlateauCoordinateRepresentation[][] getPlateauRepresentation() {
        return plateauRepresentation;
    }

    @Override
    public double[] toArray() {
        double array[] = new double[rover.getPlateau().getTopX() * rover.getPlateau().getTopY()];
        for (int x = 0; x < rover.getPlateau().getTopX(); x++) {
            for (int y = 0; y < rover.getPlateau().getTopY(); y++) {
                PlateauCoordinateRepresentation rep = plateauRepresentation[x][y];
                int i = convertTo1DimensionCoordinate(rover.getPlateau().getTopY(), x, y);
                array[i] = rep.getCode();
            }
        }
        return array;
    }

    @Override
    public boolean isSkipped() {
        return false;
    }

    @Override
    public INDArray getData() {
        double[] darray = toArray();
        float[] array = new float[darray.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) darray[i];
        }
        NDArray nd = new NDArray(array, new int[]{array.length});
        return nd;
    }

    @Override
    public Encodable dup() {
        MarsRoverObservableSpace d= new MarsRoverObservableSpace();
        d.setRover(rover);
        return d;
    }

    public int[] getShape() {

        int x = rover.getPlateau().getTopX();
        int y = rover.getPlateau().getTopY();

        return new int[]{x, y};
    }

    void buildPlateauRepresentation() {
        Plateau plateau = rover.getPlateau();
        for (int x = 0; x < plateau.getTopX(); x++) {
            for (int y = 0; y < plateau.getTopY(); y++) {
                plateauRepresentation[x][y] = PlateauCoordinateRepresentation.Empty;
            }
        }
        for (int g = 0; g < plateau.getNumberOfGoals(); g++) {
            Goal goal = plateau.getGoal(g);
            plateauRepresentation[goal.getX()][goal.getY()] = PlateauCoordinateRepresentation.Goal;
        }
        for (int o = 0; o < plateau.getNumberOfObstacles(); o++) {
            Obstacle obstacle = plateau.getObstacle(o);
            plateauRepresentation[obstacle.getX()][obstacle.getY()] = PlateauCoordinateRepresentation.Obstacle;
        }
        plateauRepresentation[rover.getX()][rover.getY()] = PlateauCoordinateRepresentation.getByOutputRepresentation(rover.getFacing().getInputRepresentation());

    }

    void printRepresentation() {
        buildStringRepresentation();
        System.out.println(stringRepresentation.toString());
    }

    void buildStringRepresentation() {
        Plateau plateau = rover.getPlateau();
        StringBuilder sb = new StringBuilder();
//        sb.append("|");
//        for (int x = 0; x < plateau.getTopX(); x++) {
//            sb.append("-|");
//        }
//        sb.append("\n");

        for (int y = plateau.getTopY() - 1; y >= 0; y--) {
            sb.append("|");
            for (int x = 0; x < plateau.getTopX(); x++) {
                PlateauCoordinateRepresentation rep = plateauRepresentation[x][y];
                sb.append(rep.getConsoleOutputRepresentation() + "|");
            }
            sb.append("\n");
        }
        stringRepresentation = sb.toString();
    }

}
