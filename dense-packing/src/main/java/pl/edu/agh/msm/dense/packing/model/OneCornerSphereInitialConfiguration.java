package pl.edu.agh.msm.dense.packing.model;


public class OneCornerSphereInitialConfiguration extends InitialConfiguration {

    public OneCornerSphereInitialConfiguration(Bin bin, SphereGenerator sphereGenerator) {
        super(bin, sphereGenerator);
    }


    @Override
    public void init() {
        addNewCircleInUpperLeftCorner();
    }

}
