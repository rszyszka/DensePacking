package pl.edu.agh.msm.dense.packing.view;

import javafx.concurrent.Task;
import pl.edu.agh.msm.dense.packing.model.Bin;
import pl.edu.agh.msm.dense.packing.model.BinSphereMixer;
import pl.edu.agh.msm.dense.packing.model.Sphere;

import java.util.LinkedList;
import java.util.List;

public class MixingBallsTask extends Task<Double> {
    private final BinSphereMixer simulation;
    private final Bin bin;

    public MixingBallsTask(BinSphereMixer simulation, Bin bin) {
        this.simulation = simulation;
        this.bin = bin;
    }

    @Override
    protected Double call() {
        simulation.setMixing(true);
        while (simulation.isMixing()) {
            updateValue(Math.random());
            simulation.performStep();
        }
        return Math.random();
    }

    private List<Sphere> getSphereListCopy() {
        return new LinkedList<>(bin.getSpheres());
    }

}
