package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OneCornerSphereInitialConfigurationTest {
    private SphereGenerator sphereGenerator;
    private OneCornerSphereInitialConfiguration configuration;
    private Bin bin;

    @BeforeEach
    void setUp() {
        sphereGenerator = mock(SphereGenerator.class);
        bin = spy(new Bin(100, 100, 100));
        configuration = new OneCornerSphereInitialConfiguration(bin, sphereGenerator);
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
    public void shouldCreateCorrectConfigurationForUnequalCircles(int r) {
        Sphere sphere = new Sphere(r);
        when(sphereGenerator.generateNewSphere()).thenReturn(sphere);

        configuration.init();

        verify(bin).addSphere(sphere);
        assertEquals(r, sphere.getCoords().getX());
        assertEquals(r, sphere.getCoords().getY());
        assertEquals(r, sphere.getCoords().getZ());
    }
}