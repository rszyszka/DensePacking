package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class RandomCircleGeneratorTest {

    private RandomCircleGenerator circleGenerator;

    @BeforeEach
    void setUp() {
        circleGenerator = new RandomCircleGenerator(5, 10);
    }

    @RepeatedTest(5)
    public void shouldGenerateNewCircleWithinPossibleRadiusRange() {
        int circleRadius = circleGenerator.generateNewCircle().getR();

        assertTrue(circleRadius >= 5, "Radius = " + circleRadius + " is below given range");
        assertTrue(circleRadius <= 10, "Radius = " + circleRadius + " is above given range");
    }

    @Test
    public void shouldDiminishMaximumRadius() {
        boolean result = circleGenerator.setLowerRadiusIfPossible(8);
        assertTrue(result);
    }

    @Test
    public void shouldReturnFailOnDiminishingMaximumRadius() {
        boolean result = circleGenerator.setLowerRadiusIfPossible(5);
        assertFalse(result);
    }

    @Test
    public void shouldGenerateCircleWhenMinRadiusEqualsMaxRadius() {
        circleGenerator = new RandomCircleGenerator(5, 5);

        Circle circle = circleGenerator.generateNewCircle();

        assertEquals(5, circle.getR());
    }

}