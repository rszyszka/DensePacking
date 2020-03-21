package pl.edu.agh.msm.dense.packing;

import java.util.List;

public class Hole {

    private List<Circle> parentCircles;
    private Coords coords;
    private double degree;


    Hole(List<Circle> parentCircles, Coords coords) {
        this.parentCircles = parentCircles;
        this.coords = coords;
        this.degree = 0.0;
    }


    public List<Circle> getParentCircles() {
        return parentCircles;
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
