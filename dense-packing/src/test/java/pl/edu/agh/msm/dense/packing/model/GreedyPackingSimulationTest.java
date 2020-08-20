package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Test;
import pl.edu.agh.msm.core.Space;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static pl.edu.agh.msm.dense.packing.model.Utils.roundUp;

public class GreedyPackingSimulationTest {

    GreedyPackingSimulation simulation;
    Bin bin;

    GreedyPacker packer;

    private void mockBehaviour(Bin bin) {
        packer = mock(GreedyPacker.class);
        when(packer.getBin()).thenReturn(bin);
        doNothing().when(packer).createInitialConfiguration();
        when(packer.tryToPackNextCircle()).thenReturn(false);
    }

    @Test
    public void shouldComputeDensityLevelCorrectly() {
        bin = new Bin(1001, 1001);
        Space space = new Space(1001, 1001, 1);
        mockBehaviour(bin);
        simulation = new GreedyPackingSimulation(space, packer);

        Sphere c1 = new Sphere(500);
        c1.setCoords(Coords.coords(500, 500));
        bin.addSphere(c1);

        simulation.simulateContinuously();

        double expected = (Math.PI * c1.getR() * c1.getR()) / (double) (bin.getXSize() * bin.getYSize());
        expected = roundUp(expected, 4);
        assertEquals(expected, roundUp(simulation.computeMathDensityLevel(), 4));
    }


    @Test
    void shouldGenerateNewSphereWithDiminishedRadius() {
        bin = new Bin(100, 100, 100);
        Space space = new Space(100, 100, 100);
        mockBehaviour(bin);
        simulation = new GreedyPackingSimulation(space, packer);

        Sphere c1 = new Sphere(50);
        c1.setCoords(Coords.coords(50, 50, 50));
        bin.addSphere(c1);

        simulation.simulateContinuously();

        double expected = 1.3333333333333333333 * Math.PI * Math.pow(c1.getR(), 3) / (double) (bin.getXSize() * bin.getYSize() * bin.getZSize());
        expected = roundUp(expected, 2);
        assertEquals(expected, roundUp(simulation.computeMathDensityLevel(), 2));
    }

}
