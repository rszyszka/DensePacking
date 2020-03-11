package pl.edu.agh.msm.dense.packing;

import pl.edu.agh.msm.core.Simulation;
import pl.edu.agh.msm.core.Space;


public class GreedyPackingSimulation extends Simulation {

    private Bin bin;
    private GreedyPacker packer;


    public GreedyPackingSimulation(Space space, GreedyPacker packer) {
        super(space);
        bin = packer.getBin();
        this.packer = packer;
        this.packer.createInitialConfiguration();
    }


    @Override
    protected boolean performStep() {
        boolean packed = packer.tryToPackNextCircle();
        if (!packed) {
            SpaceFiller filler = new SpaceFiller(space);
            filler.fillWithAllCircles(bin);
        }
        return packed;
    }

}
