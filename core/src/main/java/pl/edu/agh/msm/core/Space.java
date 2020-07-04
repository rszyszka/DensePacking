package pl.edu.agh.msm.core;

public class Space {
    private final int xSize;
    private final int ySize;
    private final int zSize;
    private final Cell[][][] cells;

    public Space(int xSize, int ySize, int zSize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;

        cells = new Cell[xSize][ySize][zSize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                for (int k = 0; k < zSize; k++) {
                    cells[i][j][k] = new Cell();
                }
            }
        }
    }


    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public int getZSize() {
        return zSize;
    }

    public Cell[][][] getCells() {
        return cells;
    }
}
