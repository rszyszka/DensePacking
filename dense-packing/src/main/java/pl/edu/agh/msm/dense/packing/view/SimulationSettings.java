package pl.edu.agh.msm.dense.packing.view;

import pl.edu.agh.msm.dense.packing.model.InitialConfiguration;
import pl.edu.agh.msm.dense.packing.model.PenaltyType;
import pl.edu.agh.msm.dense.packing.model.SphereGenerator;

public class SimulationSettings {
    private int sizeX = 200;
    private int sizeY = 200;
    private int sizeZ = 200;
    private int minR = 20;
    private int maxR = 40;
    private double penaltyValue = 0.02;
    private PenaltyType penaltyType = PenaltyType.GLOBAL;
    private int oscillationStep = 1;
    private SphereGenerator.Type sphereGeneratorType = SphereGenerator.Type.RANDOM;
    private InitialConfiguration.Type initialConfigurationType = InitialConfiguration.Type.TANGENTIAL_SPHERES;


    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getSizeZ() {
        return sizeZ;
    }

    public void setSize(int x, int y, int z) {
        sizeX = x;
        sizeY = y;
        sizeZ = z;
    }

    public int getMinR() {
        return minR;
    }

    public int getMaxR() {
        return maxR;
    }

    public void setRadiusRange(int minR, int maxR) {
        this.minR = minR;
        this.maxR = maxR;
    }

    public double getPenaltyValue() {
        return penaltyValue;
    }

    public PenaltyType getPenaltyType() {
        return penaltyType;
    }

    public void setPenalty(PenaltyType penaltyType, double penaltyValue) {
        this.penaltyType = penaltyType;
        this.penaltyValue = penaltyValue;
    }

    public int getOscillationStep() {
        return oscillationStep;
    }

    public SphereGenerator.Type getSphereGeneratorType() {
        return sphereGeneratorType;
    }

    public void setSphereGeneratorProperties(SphereGenerator.Type sphereGeneratorType, int oscillationStep) {
        this.sphereGeneratorType = sphereGeneratorType;
        this.oscillationStep = oscillationStep;
    }

    public InitialConfiguration.Type getInitialConfigurationType() {
        return initialConfigurationType;
    }

    public void setInitialConfigurationType(InitialConfiguration.Type initialConfigurationType) {
        this.initialConfigurationType = initialConfigurationType;
    }
}
