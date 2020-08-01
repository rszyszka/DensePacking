package pl.edu.agh.msm.dense.packing.model;

abstract public class BinSphereMixer {
    protected final Bin bin;
    protected SphereCollision collision;
    protected double currentSphereXPos;
    protected double currentSphereYPos;
    protected double currentSphereZPos;
    private boolean mixing;
    private double gravityValue;


    protected BinSphereMixer(Bin bin) {
        mixing = true;
        this.bin = bin;
        gravityValue = 0.2;
    }

    public static BinSphereMixer create(Bin bin) {
        if (bin.getZSize() == 1) {
            return new BinSphereMixer2D(bin);
        } else {
            return new BinSphereMixer3D(bin);
        }
    }

    public void stop() {
        mixing = false;
    }


    public void reverseGravity() {
        gravityValue = -gravityValue;
        bin.getSpheres().forEach(sphere -> sphere.setVelocity(new Velocity((Math.random() - 0.5), (Math.random() - 0.5), (Math.random() - 0.5))));
    }


    public void simulateContinuously() {
        setMixing(true);
        while (isMixing()) {
            performStep();
        }
    }


    public void performStep() {
        for (Sphere sphere : bin.getSpheres()) {
            backupSpherePosition(sphere);
            updateSpherePosition(sphere);
            resolveCollisionsWithOtherSpheres(sphere);
            resolveCollisionsWithBoundaryPlanes(sphere);
            revertSpherePositionIfItIsOverlappingOtherSpheres(sphere);
            applyGravityToSphereVelocity(sphere);
        }
    }


    private void backupSpherePosition(Sphere sphere) {
        currentSphereXPos = sphere.getCoords().getX();
        currentSphereYPos = sphere.getCoords().getY();
        currentSphereZPos = sphere.getCoords().getZ();
    }


    protected abstract void updateSpherePosition(Sphere sphere);


    protected void updateSphereXPosition(Sphere sphere) {
        sphere.getCoords().setX(currentSphereXPos + sphere.getVelocity().getX());
    }


    protected void updateSphereYPosition(Sphere sphere) {
        sphere.getCoords().setY(currentSphereYPos + sphere.getVelocity().getY());
    }


    protected void updateSphereZPosition(Sphere sphere) {
        sphere.getCoords().setZ(currentSphereZPos + sphere.getVelocity().getZ());
    }


    private void resolveCollisionsWithOtherSpheres(Sphere sphere) {
        for (Sphere otherSphere : bin.getSpheres()) {
            if (sphere.equals(otherSphere)) {
                continue;
            }
            if (Utils.areSpheresOverlapping(sphere, otherSphere)) {
                resolveSpheresCollision(sphere, otherSphere);
                updateSpherePosition(sphere);
            }
        }
    }


    private void resolveSpheresCollision(Sphere sphere, Sphere otherSphere) {
        collision.resolve(sphere, otherSphere);
    }


    protected abstract void resolveCollisionsWithBoundaryPlanes(Sphere sphere);


    protected void resolveCollisionWithYZPlane(Sphere sphere) {
        if (Utils.isOverlappingSize(sphere.getCoords().getX(), sphere.getR(), bin.getXSize())) {
            sphere.getVelocity().negateX();
            updateSphereXPosition(sphere);
        }
    }


    protected void resolveCollisionWithXZPlane(Sphere sphere) {
        if (Utils.isOverlappingSize(sphere.getCoords().getY(), sphere.getR(), bin.getYSize())) {
            sphere.getVelocity().negateY();
            updateSphereYPosition(sphere);
        }
    }


    protected void resolveCollisionWithXYPlane(Sphere sphere) {
        if (Utils.isOverlappingSize(sphere.getCoords().getZ(), sphere.getR(), bin.getZSize())) {
            sphere.getVelocity().negateZ();
            updateSphereZPosition(sphere);
        }
    }


    private void revertSpherePositionIfItIsOverlappingOtherSpheres(Sphere sphere) {
        if (Utils.isSphereOverlappingAnyOtherSphereInBin(sphere, bin)) {
            sphere.getCoords().setX(currentSphereXPos);
            sphere.getCoords().setY(currentSphereYPos);
            sphere.getCoords().setZ(currentSphereZPos);
        }
    }


    private void applyGravityToSphereVelocity(Sphere sphere) {
        sphere.getVelocity().addToY(gravityValue);
        sphere.getVelocity().multiplyY(0.9);

        sphere.getVelocity().multiplyX(0.99);
        sphere.getVelocity().multiplyZ(0.99);
    }

    public boolean isMixing() {
        return mixing;
    }

    public void setMixing(boolean mixing) {
        this.mixing = mixing;
    }
}
