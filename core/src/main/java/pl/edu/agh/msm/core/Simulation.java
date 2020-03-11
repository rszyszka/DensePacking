package pl.edu.agh.msm.core;

public abstract class Simulation {

    protected Space space;
    protected int xSize;
    protected int ySize;
    protected int zSize;
    private boolean notFinished;


    public Simulation(Space space) {
        this.space = space;
        xSize = space.getXSize();
        ySize = space.getYSize();
        zSize = space.getZSize();
        notFinished = true;
    }


    protected abstract boolean performStep();


    public void simulateContinuously() {
        while (notFinished) {
            notFinished = performStep();
        }
    }


    public Space getSpace() {
        return space;
    }


    public void setSpace(Space space) {
        this.space = space;
    }


    public boolean isFinished() {
        return !notFinished;
    }

}
