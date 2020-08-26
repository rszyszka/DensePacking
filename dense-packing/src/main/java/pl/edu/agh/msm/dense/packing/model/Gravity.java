package pl.edu.agh.msm.dense.packing.model;

public abstract class Gravity {
    protected static final double GRAVITY_DIR_DRAG = 0.9;
    protected static final double OTHER_DIR_DRAG = 0.99;
    protected double gravityValue = 0.2;

    protected Gravity next;

    public Gravity changeState() {
        if (gravityValue < 0) {
            reverse();
            return next;
        }
        reverse();
        return this;
    }

    public void setNext(Gravity next) {
        this.next = next;
    }

    public void reverse() {
        gravityValue = -gravityValue;
    }

    public abstract void applyToSphereVelocity(Sphere sphere);
}
