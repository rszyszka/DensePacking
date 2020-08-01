package pl.edu.agh.msm.dense.packing.model;

public class GravityYZ extends Gravity {
    @Override
    public void applyToSphereVelocity(Sphere sphere) {
        sphere.getVelocity().addToX(gravityValue);
        sphere.getVelocity().multiplyX(GRAVITY_DIR_DRAG);

        sphere.getVelocity().multiplyY(OTHER_DIR_DRAG);
        sphere.getVelocity().multiplyZ(OTHER_DIR_DRAG);
    }
}
