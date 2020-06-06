package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TangentialCirclesInitialConfigurationTest {

    private SphereGenerator sphereGenerator;
    private TangentialCirclesInitialConfiguration configuration;
    private Bin bin;

    @BeforeEach
    void setUp() {
        sphereGenerator = mock(SphereGenerator.class);
        bin = spy(new Bin(100, 100));
        configuration = new TangentialCirclesInitialConfiguration(bin, sphereGenerator);
    }

    @Test
    public void shouldCreateInitialConfigurationWithTangentialCircles() {
        ArgumentCaptor<Sphere> circleArgumentCaptor = ArgumentCaptor.forClass(Sphere.class);
        when(sphereGenerator.generateNewSphere()).thenAnswer(invocation -> new Sphere(10));

        configuration.init();

        assertEquals(2, configuration.getBin().getNumberOfCirclesPacked());
        verify(sphereGenerator, times(2)).generateNewSphere();
        verify(bin, times(2)).addSphere(circleArgumentCaptor.capture());
        assertEquals(10, circleArgumentCaptor.getAllValues().get(0).getCoords().getY());
        assertEquals(30, circleArgumentCaptor.getAllValues().get(1).getCoords().getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
    public void shouldCreateCorrectConfigurationForUnequalCircles(int r) {
        Sphere c1 = new Sphere(14);
        Sphere c2 = new Sphere(r);
        when(sphereGenerator.generateNewSphere()).thenReturn(c1, c2);

        configuration.init();

        assertEquals(0, Utils.computeDistanceBetweenCircuits(c1, c2));
    }

}