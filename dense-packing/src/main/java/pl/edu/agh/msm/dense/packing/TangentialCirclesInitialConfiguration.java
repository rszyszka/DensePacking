package pl.edu.agh.msm.dense.packing;


import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class TangentialCirclesInitialConfiguration extends InitialConfiguration {

    public TangentialCirclesInitialConfiguration(Bin bin, CircleGenerator circleGenerator) {
        super(bin, circleGenerator);
    }


    @Override
    public void init() {
        addNewCircleInUpperLeftCorner();
        addNewCircleNextToFirstOne();
    }


    private void addNewCircleNextToFirstOne() {
        Circle circle = circleGenerator.generateNewCircle();
        Circle circleInBin = bin.getCircles().get(0);

        int rC = circle.getR();
        int rA = circleInBin.getR();

        Coords coordsA = circleInBin.getCoords();
        double y = sqrt(pow(rC + rA, 2) - pow(rC - coordsA.getX(), 2)) + coordsA.getY();

        circle.setCoords(Coords.coords(rC, y));
        bin.addCircle(circle);
    }

}
