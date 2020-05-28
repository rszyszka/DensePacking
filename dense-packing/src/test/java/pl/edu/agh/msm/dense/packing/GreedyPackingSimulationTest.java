package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.msm.core.Space;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GreedyPackingSimulationTest {

    GreedyPackingSimulation simulation;
    Bin bin;


    @BeforeEach
    public void setup() {
        bin = new Bin(1001, 1001);
        Space space = new Space(1001, 1001, 1);

        GreedyPacker packer = mock(GreedyPacker.class);
        when(packer.getBin()).thenReturn(bin);
        doNothing().when(packer).createInitialConfiguration();
        when(packer.tryToPackNextCircle()).thenReturn(false);

        simulation = new GreedyPackingSimulation(space, packer);
    }

    @Test
    public void shouldComputeDensityLevelCorrectly() {
        Sphere c1 = new Sphere(500);
        c1.setCoords(Coords.coords(500, 500));
        bin.addCircle(c1);

        simulation.simulateContinuously();

        double expected = (Math.PI * c1.getR() * c1.getR()) / (double) (bin.getXSize() * bin.getYSize());
        assertEquals(expected, simulation.computeMathDensityLevel());
    }

}
