package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CirclesInCornersInitialConfigurationTest {


    @Test
    public void shouldCreateInitialConfigurationWithCirclesInCorners() {
        ArgumentCaptor<Circle> circleArgumentCaptor = ArgumentCaptor.forClass(Circle.class);
        CircleGenerator circleGenerator = mock(CircleGenerator.class);
        when(circleGenerator.generateNewCircle()).thenAnswer(invocation -> new Circle(10));
        Bin bin = spy(new Bin(100, 100));
        CirclesInCornersInitialConfiguration configuration = new CirclesInCornersInitialConfiguration(bin, circleGenerator);

        configuration.init();

        assertEquals(2, configuration.getBin().getNumberOfCirclesPacked());
        verify(circleGenerator, times(2)).generateNewCircle();
        verify(bin, times(2)).addCircle(circleArgumentCaptor.capture());
        assertEquals(10, circleArgumentCaptor.getAllValues().get(0).getCoords().getX());
        assertEquals(90, circleArgumentCaptor.getAllValues().get(1).getCoords().getY());
    }

}