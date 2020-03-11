package pl.edu.agh.msm.dense.packing;

public class CirclesInCornersInitialConfiguration extends InitialConfiguration {
    CirclesInCornersInitialConfiguration(Bin bin, CircleGenerator circleGenerator) {
        super(bin, circleGenerator);
    }

    @Override
    public void init() {
        addNewCircleInUpperLeftCorner();
        addNewCircleInLowerLeftCorner();
    }


    private void addNewCircleInLowerLeftCorner() {
        Circle circle = circleGenerator.generateNewCircle();
        circle.setCoords(Coords.coords(circle.getR(), bin.getYSize() - circle.getR()));
        bin.addCircle(circle);
    }

}
