package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static pl.edu.agh.msm.dense.packing.Coords.coords;


class GreedyPackerTest {

    private GreedyPacker packer;

    private InitialConfiguration configuration;
    private SphereGenerator sphereGenerator;
    private HolesFinder holesFinder;


    @BeforeEach
    public void setup() {
        Bin bin = new Bin(100, 100);

        sphereGenerator = mock(SphereGenerator.class);
        when(sphereGenerator.generateNewSphere()).thenAnswer(invocationOnMock -> new Sphere(10));

        configuration = spy(new TangentialCirclesInitialConfiguration(bin, sphereGenerator));
        doNothing().when(configuration).init();

        holesFinder = mock(HolesFinder.class);

        packer = new GreedyPacker(configuration, holesFinder);
        packer.createInitialConfiguration();
    }


    @Test
    public void shouldInvokeInitialConfigurationCreation() {
        verify(configuration).init();
    }


    @Test
    public void shouldPackNextCircleCorrectly() {
        List<Hole> spiedList = spy(new ArrayList<>());
        when(spiedList.isEmpty()).thenReturn(false);
        when(holesFinder.findForSphere(any())).thenReturn(spiedList);
        when(holesFinder.findHoleWithMaximumDegree()).thenReturn(new Hole(null, coords(30, 10)));

        boolean packed = packer.tryToPackNextCircle();

        assertTrue(packed);
        verify(sphereGenerator).generateNewSphere();
        verify(holesFinder).findForSphere(any());
        verify(holesFinder).findHoleWithMaximumDegree();
    }


    @Test
    public void shouldPackThirdCircle() {
        List<Hole> spiedList = spy(new ArrayList<>());
        when(spiedList.isEmpty()).thenReturn(true, true, false);
        when(sphereGenerator.setLowerRadiusIfPossible(anyInt())).thenReturn(true);
        when(holesFinder.findForSphere(any())).thenReturn(spiedList);
        when(holesFinder.findHoleWithMaximumDegree()).thenReturn(new Hole(null, coords(30, 10)));

        boolean packed = packer.tryToPackNextCircle();

        assertTrue(packed);
        verify(sphereGenerator, times(3)).generateNewSphere();
        verify(holesFinder, times(3)).findForSphere(any());
        verify(holesFinder).findHoleWithMaximumDegree();
    }

    @Test
    public void shouldFailOnPacking() {
        List<Hole> spiedList = spy(new ArrayList<>());
        when(spiedList.isEmpty()).thenReturn(true);
        when(sphereGenerator.setLowerRadiusIfPossible(anyInt())).thenReturn(true, false);
        when(holesFinder.findForSphere(any())).thenReturn(spiedList);

        boolean packed = packer.tryToPackNextCircle();

        assertFalse(packed);
        verify(sphereGenerator, times(2)).generateNewSphere();
        verify(holesFinder, times(2)).findForSphere(any());
    }

}