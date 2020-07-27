package pl.edu.agh.msm.dense.packing.model;

public class OscillatingSphereGenerator extends LargestSphereGenerator {
    private final int originCounter;
    private final int realOriginMaxRadius;
    private int counter;
    private int modifier;

    public OscillatingSphereGenerator(int minRadius, int maxRadius, int counter) {
        super(minRadius, maxRadius);
        realOriginMaxRadius = originMaxRadius;
        modifier = 1;
        originCounter = this.counter = counter;
    }

    @Override
    public void resetMaxRadius() {
        counter--;
        if (counter == 0) {
            changeDirectionIfNecessary();
            originMaxRadius -= modifier;
            counter = originCounter;
        }
        super.resetMaxRadius();
    }

    private void changeDirectionIfNecessary() {
        if (modifier > 0 && originMaxRadius == minRadius) {
            modifier *= -1;
        } else if (modifier < 0 && originMaxRadius == realOriginMaxRadius) {
            modifier *= -1;
        }
    }
}
