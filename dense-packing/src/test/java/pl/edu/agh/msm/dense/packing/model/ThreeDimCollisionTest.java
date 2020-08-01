package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static pl.edu.agh.msm.dense.packing.model.Utils.computeDistanceBetweenMiddles;
import static pl.edu.agh.msm.dense.packing.model.Utils.roundUp;


public class ThreeDimCollisionTest {

    private static Velocity rotateByXY(Velocity velocity, double angle) {
        double x = velocity.getX() * Math.cos(angle) - velocity.getY() * Math.sin(angle);
        double y = velocity.getX() * Math.sin(angle) + velocity.getY() * Math.cos(angle);
        return new Velocity(x, y, velocity.getZ());
    }

    private static Velocity rotateByXZ(Velocity velocity, double angle) {
        double x = velocity.getX() * Math.cos(angle) - velocity.getZ() * Math.sin(angle);
        double z = velocity.getX() * Math.sin(angle) + velocity.getZ() * Math.cos(angle);
        return new Velocity(x, velocity.getY(), z);
    }

    @Test
    public void shouldRotate3DSphereCorrectly() {
        Sphere sphere1 = new Sphere(20);
        Sphere sphere2 = new Sphere(10);
        sphere2.setCoords(Coords.coords(60, 80, 10));

        double sphere1XPos = sphere1.getCoords().getX();
        double sphere1YPos = sphere1.getCoords().getY();
        double sphere1ZPos = sphere1.getCoords().getZ();
        double sphere2XPos = sphere2.getCoords().getX();
        double sphere2YPos = sphere2.getCoords().getY();
        double sphere2ZPos = sphere2.getCoords().getZ();

        double angle1 = -Math.atan2(sphere2YPos - sphere1YPos, sphere2XPos - sphere1XPos);
        Velocity firstRotation = rotateByXY(new Velocity(sphere2XPos, sphere2YPos, sphere2ZPos), angle1);
        double angle2 = -Math.atan2(firstRotation.getZ() - sphere1ZPos, firstRotation.getX() - sphere1XPos);

        Velocity result = rotateByXZ(rotateByXY(new Velocity(sphere2XPos, sphere2YPos, sphere2ZPos), angle1), angle2);
        Velocity backToRoot = rotateByXY(rotateByXZ(result, -angle2), -angle1);

        double expectedDistance = computeDistanceBetweenMiddles(sphere1, sphere2) + 10;
        double actualDistance = roundUp(result.getX() + 10, 10);

        Assertions.assertEquals(expectedDistance, actualDistance);
        Assertions.assertEquals(60, roundUp(backToRoot.getX(), 10));
        Assertions.assertEquals(80, roundUp(backToRoot.getY(), 10));
        Assertions.assertEquals(10, roundUp(backToRoot.getZ(), 10));
    }

}
