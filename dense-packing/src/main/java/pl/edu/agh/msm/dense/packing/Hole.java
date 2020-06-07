package pl.edu.agh.msm.dense.packing;

import java.util.List;

public class Hole {

    private final List<Object> parentObjects;
    private final Coords coords;
    private double degree;


    Hole(List<Object> parentObjects, Coords coords) {
        this.parentObjects = parentObjects;
        this.coords = coords;
        this.degree = 0.0;
    }


    public List<Object> getParentObjects() {
        return parentObjects;
    }


    public Coords getCoords() {
        return coords;
    }


    public double getDegree() {
        return degree;
    }


    public void setDegree(double degree) {
        this.degree = degree;
    }

}
