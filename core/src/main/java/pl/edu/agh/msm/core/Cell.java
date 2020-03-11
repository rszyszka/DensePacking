package pl.edu.agh.msm.core;

public class Cell {

    protected int id;
    protected boolean border;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isBorder() {
        return border;
    }

    public void setBorder(boolean border) {
        this.border = border;
    }
}
