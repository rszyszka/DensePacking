package pl.edu.agh.msm.dense.packing.model;

public class GravityXZ extends Gravity {
    @Override
    public void applyToSphereVelocity(Sphere sphere) {
        sphere.getVelocity().addToY(gravityValue);
        sphere.getVelocity().multiplyY(GRAVITY_DIR_DRAG);

        sphere.getVelocity().multiplyX(OTHER_DIR_DRAG);
        sphere.getVelocity().multiplyZ(OTHER_DIR_DRAG);
    }
}
