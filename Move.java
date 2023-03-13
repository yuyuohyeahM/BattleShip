/**
 * @author Mike Yu, Bryson Mineart
 * This class is to store the needed elements for a move.
 */


package general;

import java.io.Serializable;

public class Move implements Serializable{
	private int size;
	private int row;
	private int col;
	private int dir;
	private Board b;
	
	/**
	 * Move for placing ship.
	 * @param b - board- sent to opponent to update.
	 * @param size - size of the ship
	 * @param row - index row to place
	 * @param col - index col to place
	 * @param dir - direction of the ship.
	 */
	public Move(Board b,int size, int row, int col, int dir) {
		this.b = b;
		this.size = size;
		this.row = row;
		this.col = col;
		this.dir = dir;
	}
	
	
	/**
	 * Move for firing ship.
	 * @param b - board- sent to opponent to update.
	 * @param row - index row to place
	 * @param col - index col to place
	 */
	public Move(Board b,int row, int col) {
		this.row = row;
		this.col = col;
		this.b = b;
	}
	
	/**
	 * return row stored in this Object.
	 * @return
	 */
	public int getRow(){
		return this.row;
	}
	
	/**
	 * return col stored in this Object.
	 * @return
	 */
	public int getCol(){
		return this.col;
	}
	/**
	 * return size stored in this Object.
	 * @return
	 */
	public int getSize(){
		return this.size;
	}
	
	/**
	 * return dir stored in this Object.
	 * @return
	 */
	public int getDir(){
		return this.dir;
	}
	/**
	 * return board stored in this Object.
	 * @return
	 */
	public Board getBoard(){
		return this.b;
	}
}
