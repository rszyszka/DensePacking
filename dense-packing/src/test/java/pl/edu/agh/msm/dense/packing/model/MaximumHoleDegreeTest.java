package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Test;
import pl.edu.agh.msm.core.Space;

import java.util.List;

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

        GreedyPacker packer = new GreedyPacker(initialConfiguration, holesFinder);
        Space space = new Space(X_SIZE, Y_SIZE, Z_SIZE);
        GreedyPackingSimulation simulation = new GreedyPackingSimulation(space, packer);

        ForEachMinDistance distance = new ForEachMinDistance(bin);
        Sphere sphere = sphereGenerator.generateNewSphere();

        List<Hole> holes = holesFinder.findForSphere(sphere);

        holes.forEach(hole -> {
            System.out.println(hole);
            System.out.println(distance.compute(hole));
        });
    }

}
