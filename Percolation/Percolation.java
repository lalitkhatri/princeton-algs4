import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int virtualTop = 0;
    private int virtualBottom;
    private int gridSize;
    private WeightedQuickUnionUF wquf;
    private boolean[][] openSites;
    
    /**
     * Initialize N-by-N grid.
     * @throws IllegalArgumentException
     */
    public Percolation(int N) {
        if(N<=0){
        	throw new IllegalArgumentException();
        }
        gridSize = N;
        virtualBottom = gridSize * gridSize + 1;
        wquf = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        openSites = new boolean[gridSize][gridSize];
    }

    /**
     * Opens site (row i, column j).
     * @throws IllegalArgumentException
     */
    public void open(int i, int j) {
    	if(i<=0 || i>gridSize || j <=0 || j> gridSize) {
    		throw new IllegalArgumentException();
    	}
        openSites[i - 1][j - 1] = true;
        if (i == 1) {
            wquf.union(getQFIndex(i, j), virtualTop);
        }
        if (i == gridSize) {
            wquf.union(getQFIndex(i, j), virtualBottom);
        }

        if (j > 1 && isOpen(i, j - 1)) {
            wquf.union(getQFIndex(i, j), getQFIndex(i, j - 1));
        }
        if (j < gridSize && isOpen(i, j + 1)) {
            wquf.union(getQFIndex(i, j), getQFIndex(i, j + 1));
        }
        if (i > 1 && isOpen(i - 1, j)) {
            wquf.union(getQFIndex(i, j), getQFIndex(i - 1, j));
        }
        if (i < gridSize && isOpen(i + 1, j)) {
            wquf.union(getQFIndex(i, j), getQFIndex(i + 1, j));
        }
    }

    /**
     * Is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) {
    	if(i<=0 || i>gridSize || j <=0 || j> gridSize) {
    		throw new IllegalArgumentException();
    	}
        return openSites[i - 1][j - 1];
    }

    /**
     * Is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        if (0 < i && i <= gridSize && 0 < j && j <= gridSize) {
            return wquf.connected(virtualTop, getQFIndex(i , j));
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        return wquf.connected(virtualTop, virtualBottom);
    }

    private int getQFIndex(int i, int j) {
        return gridSize * (i - 1) + j;
    }
}

