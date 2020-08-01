package pl.edu.agh.msm.dense.packing.model;

public class GravityXY extends Gravity {
    @Override
    public void applyToSphereVelocity(Sphere sphere) {
        sphere.getVelocity().addToZ(gravityValue);
        sphere.getVelocity().multiplyZ(GRAVITY_DIR_DRAG);

        sphere.getVelocity().multiplyX(OTHER_DIR_DRAG);
        sphere.getVelocity().multiplyY(OTHER_DIR_DRAG);
    }
}
