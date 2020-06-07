package pl.edu.agh.msm.dense.packing;

public class Sphere {
    private final int r;
    private Coords coords;

    public Sphere(int r) {
        this.r = r;
        coords = Coords.coords(0, 0, 0);
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

    @Override
    public String toString() {
        return "Sphere{" +
                "r=" + r +
                ", coords=" + coords +
                '}';
    }
}
