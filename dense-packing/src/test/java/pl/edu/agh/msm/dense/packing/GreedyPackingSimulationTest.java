package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import pl.edu.agh.msm.core.Space;

public class GreedyPackingSimulationTest {

    GreedyPackingSimulation simulation;
    Bin bin;


    @BeforeEach
    public void setup() {
        bin = new Bin(100, 100);
        CircleGenerator circleGenerator = new RandomCircleGenerator(5, 15);
        InitialConfiguration initialConfiguration = new TangentialCirclesInitialConfiguration(bin, circleGenerator);
        HolesFinder holesFinder = new HolesFinder(bin);
        GreedyPacker packer = new GreedyPacker(initialConfiguration, holesFinder);
        Space space = new Space(100, 100, 1);
        simulation = new GreedyPackingSimulation(space, packer);
    }


    @RepeatedTest(10)
    public void shouldPerformRealSimulation() {
        simulation.simulateContinuously();

        bin.getCircles().forEach(System.out::println);
    }


}
