package pl.edu.agh.msm.dense.packing.model;

public class Sphere {
    protected final int r;
    protected Coords coords;
    private Velocity velocity;

    public Sphere(int r) {
        this.r = r;
        coords = Coords.coords(0, 0, 0);
        velocity = new Velocity((Math.random() - 0.5), (Math.random() - 0.5), (Math.random() - 0.5));
    }

    public int getR() {
        return r;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "r=" + r +
                ", coords=" + coords +
                '}';
    }
}
