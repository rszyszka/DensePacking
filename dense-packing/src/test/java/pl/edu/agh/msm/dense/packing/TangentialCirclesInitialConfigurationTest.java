package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TangentialCirclesInitialConfigurationTest {

    private CircleGenerator circleGenerator;
    private TangentialCirclesInitialConfiguration configuration;
    private Bin bin;

    @BeforeEach
    void setUp() {
        circleGenerator = mock(CircleGenerator.class);
        bin = spy(new Bin(100, 100));
        configuration = new TangentialCirclesInitialConfiguration(bin, circleGenerator);
    }

    @Test
    public void shouldCreateInitialConfigurationWithTangentialCircles() {
        ArgumentCaptor<Circle> circleArgumentCaptor = ArgumentCaptor.forClass(Circle.class);
        when(circleGenerator.generateNewCircle()).thenAnswer(invocation -> new Circle(10));

        configuration.init();

        assertEquals(2, configuration.getBin().getNumberOfCirclesPacked());
        verify(circleGenerator, times(2)).generateNewCircle();
        verify(bin, times(2)).addCircle(circleArgumentCaptor.capture());
        assertEquals(10, circleArgumentCaptor.getAllValues().get(0).getCoords().getY());
        assertEquals(30, circleArgumentCaptor.getAllValues().get(1).getCoords().getY());
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
    public void shouldCreateCorrectConfigurationForUnequalCircles(int r) {
        Circle c1 = new Circle(14);
        Circle c2 = new Circle(r);
        when(circleGenerator.generateNewCircle()).thenReturn(c1, c2);

        configuration.init();

        assertEquals(0, Utils.computeDistanceBetweenCircuits(c1, c2));
    }

}