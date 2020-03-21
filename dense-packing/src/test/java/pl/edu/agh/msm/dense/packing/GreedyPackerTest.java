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
    private CircleGenerator circleGenerator;
    private HolesFinder holesFinder;


    @BeforeEach
    public void setup() {
        Bin bin = new Bin(100, 100);

        circleGenerator = mock(CircleGenerator.class);
        when(circleGenerator.generateNewCircle()).thenAnswer(invocationOnMock -> new Circle(10));

        configuration = spy(new TangentialCirclesInitialConfiguration(bin, circleGenerator));
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
        when(holesFinder.findForCircle(any())).thenReturn(spiedList);
        when(holesFinder.findHoleWithMaximumDegree()).thenReturn(new Hole(null, coords(30, 10)));

        boolean packed = packer.tryToPackNextCircle();

        assertTrue(packed);
        verify(circleGenerator).generateNewCircle();
        verify(holesFinder).findForCircle(any());
        verify(holesFinder).findHoleWithMaximumDegree();
    }


    @Test
    public void shouldPackThirdCircle() {
        List<Hole> spiedList = spy(new ArrayList<>());
        when(spiedList.isEmpty()).thenReturn(true, true, false);
        when(circleGenerator.setLowerRadiusIfPossible(anyInt())).thenReturn(true);
        when(holesFinder.findForCircle(any())).thenReturn(spiedList);
        when(holesFinder.findHoleWithMaximumDegree()).thenReturn(new Hole(null, coords(30, 10)));

        boolean packed = packer.tryToPackNextCircle();

        assertTrue(packed);
        verify(circleGenerator, times(3)).generateNewCircle();
        verify(holesFinder, times(3)).findForCircle(any());
        verify(holesFinder).findHoleWithMaximumDegree();
    }

    @Test
    public void shouldFailOnPacking() {
        List<Hole> spiedList = spy(new ArrayList<>());
        when(spiedList.isEmpty()).thenReturn(true);
        when(circleGenerator.setLowerRadiusIfPossible(anyInt())).thenReturn(true, false);
        when(holesFinder.findForCircle(any())).thenReturn(spiedList);

        boolean packed = packer.tryToPackNextCircle();

        assertFalse(packed);
        verify(circleGenerator, times(2)).generateNewCircle();
        verify(holesFinder, times(2)).findForCircle(any());
    }

}