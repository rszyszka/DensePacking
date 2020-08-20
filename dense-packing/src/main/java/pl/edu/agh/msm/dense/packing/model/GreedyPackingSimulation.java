package pl.edu.agh.msm.dense.packing.model;

import pl.edu.agh.msm.core.Simulation;
import pl.edu.agh.msm.core.Space;


public class GreedyPackingSimulation extends Simulation {

    private final Bin bin;
    private final GreedyPacker packer;


    public GreedyPackingSimulation(Space space, GreedyPacker packer) {
        super(space);
        bin = packer.getBin();
        this.packer = packer;
        this.packer.createInitialConfiguration();
    }


    @Override
    public boolean performStep() {
        return packer.tryToPackNextCircle();
    }


    public double computeMathDensityLevel() {
        return bin.getZSize() == 1 ? computeMathCircleDensityLevel() : computeMathSpheresDensityLevel();
    }

    private double computeMathSpheresDensityLevel() {
        return bin.getSpheres().stream()
                .mapToDouble(circle -> 1.3333333333333333333 * Math.PI * Math.pow(circle.getR(), 3))
                .sum() / (double) (space.getXSize() * space.getYSize() * space.getZSize());
    }

    private double computeMathCircleDensityLevel() {
        return bin.getSpheres().stream()
                .mapToDouble(circle -> Math.PI * circle.getR() * circle.getR())
                .sum() / (double) (space.getXSize() * space.getYSize() * space.getZSize());
    }

}
