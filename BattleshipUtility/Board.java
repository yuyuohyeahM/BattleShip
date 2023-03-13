/**
 * @author Mike Yu, Bryson Mineart
 * This class represent the board of the game, each player should have two in the model.
 */

package general;

import java.io.Serializable;

public class Board implements Serializable{
	private Loc[][] grid;

    public Board() {
    	this.grid = new Loc[10][10];
    	for (int i = 0 ; i < 10; i++) {
    		for (int j = 0 ; j < 10; j++) {
    	    	  this.grid[i][j] = new Loc(i,j);
        	}
    	}
    }

    

    /**
     * return the board
     * @return 
     * 		- grid - board
     */
    public Loc[][] getGrid() {
    	return this.grid;
    }
    
    /**
     * returns the size of the board
     * @return
     */
    //  
    public int size() {
	return grid.length;
    }
    
    /**
     * plus 1 when adding ship to board.
     */
    public void placing(int i, int j) {
    	this.grid[i][j].setShip(true);
    }
    
    
    /**
     * sink a location in this board.
     * @param r - the row location to sink
     * @param c - the col location to sink
     */
    public void hitLoc(int r ,int c) {
    	this.grid[r][c].setSunk(true);
    }
    /**
     * 
     * returns the location at (i, j) 
     */
    public Loc getLoc(int i, int j) {
	    return grid[i][j];
    }
    


}
