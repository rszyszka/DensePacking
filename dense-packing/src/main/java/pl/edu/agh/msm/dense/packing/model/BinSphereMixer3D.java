package pl.edu.agh.msm.dense.packing.model;

public class BinSphereMixer3D extends BinSphereMixer {

    public BinSphereMixer3D(Bin bin) {
        super(bin);
        collision = new SphereCollision3D();
    }

    @Override
    protected void updateSpherePosition(Sphere sphere) {
        updateSphereXPosition(sphere);
        updateSphereYPosition(sphere);
        updateSphereZPosition(sphere);
    }

    @Override
    protected void resolveCollisionsWithBoundaryPlanes(Sphere sphere) {
        resolveCollisionWithYZPlane(sphere);
        resolveCollisionWithXZPlane(sphere);
        resolveCollisionWithXYPlane(sphere);
    }
}
