package pl.edu.agh.msm.dense.packing;


public abstract class InitialConfiguration {

    protected final Bin bin;
    protected final CircleGenerator circleGenerator;


    InitialConfiguration(Bin bin, CircleGenerator circleGenerator) {
        this.bin = bin;
        this.circleGenerator = circleGenerator;
    }


    public abstract void init();


    protected void addNewCircleInUpperLeftCorner() {
        Circle circle = this.circleGenerator.generateNewCircle();
        circle.setCoords(Coords.coords(circle.getR(), circle.getR()));
        bin.addCircle(circle);
    }


    public Bin getBin() {
        return bin;
    }


    public CircleGenerator getCircleGenerator() {
        return circleGenerator;
    }

}
