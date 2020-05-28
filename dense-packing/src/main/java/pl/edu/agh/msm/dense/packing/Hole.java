package pl.edu.agh.msm.dense.packing;

import java.util.List;

public class Hole {

    private List<Sphere> parentSpheres;
    private Coords coords;
    private double degree;


    Hole(List<Sphere> parentSpheres, Coords coords) {
        this.parentSpheres = parentSpheres;
        this.coords = coords;
        this.degree = 0.0;
    }


    public List<Sphere> getParentSpheres() {
        return parentSpheres;
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
