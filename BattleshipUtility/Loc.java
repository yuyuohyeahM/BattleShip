/**
 * @author Mike Yu
 * This class for storing informations of the location in the board
 */

package general;

import java.io.Serializable;

public class Loc implements Serializable{
	 	public final int row;
	    public final int col;
	    private boolean hasShip;
	    private boolean sunk;

	    //constructor
	    //x is row, y is column
		public Loc(int x, int y) {
			this.row = x;
			this.col = y;
			this.hasShip = false;
			this.sunk = false;
	    }
	    
		
		/**
		 *  check if this location has ship
		 * @return
		 */
	    public boolean checkHasShip() {
	    	return this.hasShip;
	    }
	    /**
	     * check if this location is hit before.
	     * @return
	     */
	    public boolean checkIsSink() {
	    	return this.sunk;
	    }
	    /**
	     * set this.hasShip to status, usually call when performing a hit.
	     * @param status
	     */
	    public void setShip(boolean status) {
	    	this.hasShip = status;
	    }
	    /**
	     * set this.sunk to status.
	     * @param status
	     */
	    public void setSunk(boolean status) {
	    	this.sunk = status;
	    }
}
