package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CirclesInCornersInitialConfigurationTest {


    @Test
    public void shouldCreateInitialConfigurationWithCirclesInCorners() {
        ArgumentCaptor<Sphere> circleArgumentCaptor = ArgumentCaptor.forClass(Sphere.class);
        SphereGenerator sphereGenerator = mock(SphereGenerator.class);
        when(sphereGenerator.generateNewSphere()).thenAnswer(invocation -> new Sphere(10));
        Bin bin = spy(new Bin(100, 100));
        CirclesInCornersInitialConfiguration configuration = new CirclesInCornersInitialConfiguration(bin, sphereGenerator);

        configuration.init();

        assertEquals(2, configuration.getBin().getNumberOfCirclesPacked());
        verify(sphereGenerator, times(2)).generateNewSphere();
        verify(bin, times(2)).addSphere(circleArgumentCaptor.capture());
        assertEquals(10, circleArgumentCaptor.getAllValues().get(0).getCoords().getX());
        assertEquals(90, circleArgumentCaptor.getAllValues().get(1).getCoords().getY());
    }

}