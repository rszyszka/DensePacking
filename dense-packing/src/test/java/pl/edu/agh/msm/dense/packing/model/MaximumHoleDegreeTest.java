package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MaximumHoleDegreeTest {
    public static final int X_SIZE = 100;
    public static final int Y_SIZE = 100;
    public static final int Z_SIZE = 1;
    public static final int MIN_R = 5;
    public static final int MAX_R = 10;

    @Test
    public void generatePackingTest() {
        Bin bin = new Bin(X_SIZE, Y_SIZE, Z_SIZE);
        SphereGenerator sphereGenerator = new LargestSphereGenerator(MIN_R, MAX_R);
        InitialConfiguration initialConfiguration = new TangentialCirclesInitialConfiguration(bin, sphereGenerator);
        HolesFinder holesFinder = HolesFinder.create(bin);
        initialConfiguration.init();
        Sphere sphere = sphereGenerator.generateNewSphere();
        holesFinder.findForSphere(sphere);

        Hole bestHole = holesFinder.findHoleWithMaximumDegree();
        System.out.println(bestHole);
        Assertions.assertEquals(0.07, Utils.roundUp(bestHole.getDegree(), 2));
    }
}
