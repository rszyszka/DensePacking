package pl.edu.agh.msm.dense.packing.model;

import java.util.List;

public class Hole extends Sphere {

    private final List<Object> parentObjects;
    private double degree;


    Hole(List<Object> parentObjects, Coords coords, int r) {
        super(r);
        this.parentObjects = parentObjects;
        this.coords = coords;
        this.degree = 0.0;
    }


    public List<Object> getParentObjects() {
        return parentObjects;
    }


    public double getDegree() {
        return degree;
    }


    public void setDegree(double degree) {
        this.degree = degree;
    }


    @Override
    public String toString() {
        return "Hole{" +
                "parentObjects=" + parentObjects +
                ", coords=" + coords +
                ", r=" + r +
                ", degree=" + degree +
                '}';
    }
}
