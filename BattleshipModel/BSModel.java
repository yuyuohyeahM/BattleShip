/**
 * @author Mike Yu
 * Description: 
 * 		This is a game called battleship, in the pregame, both player need to place their ships
 * 		there will be 7 ships in your board. After the ships are placed, Server player can fire a location 
 * 		on your opponent's board, then the Client does.... the result will not show up immediately, instead,
 * 		it show up after your opponent also makes a move; so both your's and your opponent's hit result will
 * 		show up at once. Once all the ships in one of the board are sunk, the game is over. If you lose
 * 		 you get to see your opponent's ships' locaitons.
 */

package battleshipModel;

import java.util.Observable;

import general.Board;
import general.Move;

@SuppressWarnings("deprecation")
public class BSModel extends Observable {
	private Board board;
	private Board oppoBoard;
	private boolean p = true;
	private boolean preG = true;
	
	
	public BSModel() {
		this.board = new Board();
		this.oppoBoard = new Board();
		
	}
	
	/**
	 * place a "size" size ship on the location (r,c) in "dir" direction.
	 * @param size - the size of the ship.
	 * @param r - the row location for where the ship is placed
	 * @param c - the column location for where the ship is placed
	 * @param dir - 0 or 90, horizontal or vertical.
	 */
	public void placeShip(int size, int r ,int c, int dir) {
		if (dir == 90) { // right
			for (int i = 0; i<size;i++) {
				this.board.placing(r+i, c);
			}
		} else if (dir == 0) { // down
			for (int i = 0; i<size;i++) {
				this.board.placing(r, c+i);
			}
		}
		setChanged();
		notifyObservers();
	}
	
	public void placeOppoShip(int size, int r ,int c, int dir) {
		if (dir == 90) { // right
			for (int i = 0; i<size;i++) {
				this.oppoBoard.placing(r+i, c);
			}
		} else if (dir == 0) { // down
			for (int i = 0; i<size;i++) {
				this.oppoBoard.placing(r, c+i);
			}
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * perform a hit.
	 * @param r - the row location for where the ship is placed
	 * @param c - the column location for where the ship is placed
	 */
	public void hit(int r, int c) {
		this.getBoard().hitLoc(r, c);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * perform a hit.
	 * @param r - the row location for where the ship is placed
	 * @param c - the column location for where the ship is placed
	 */
	public void hitOppo(int r, int c) {
		this.getOppoBoard().hitLoc(r, c);
		setChanged();
		notifyObservers(this.oppoBoard);
	}
	
	/**
	 * return the board.
	 * @return - board
	 */  
	public Board getBoard() {
		return this.board;
	}
	/**
	 * set the board to o.
	 * @param o - Board
	 */
	public void setBoard(Board o) {
		this.board = o;
	}
	
	/**
	 * return the opponnent's board.
	 * @return - oppeBoard
	 */  
	public Board getOppoBoard() {
		return this.oppoBoard;
	}
	
	/**
	 * update the opponent's board
	 * @param o - Board
	 */
	public void setOppoBoard(Board o) {
		this.oppoBoard = o;
	}
	
	/**
	 * return the player status.
	 * @return - true -> player1
	 * 		   - false -> player2
	 */  
	public boolean getP() {
		return this.p;
	}
	
	/**
	 * Set player to p, true for Server, false for Client.
	 * @param p
	 */
	public void setP(boolean p) {
		this.p = p;
	}
	
	/**
	 * update it to true when starting firing the opponent's board.
	 */
	public void donePlace() {
		this.preG = false;
	}
	
	/**
	 * return if the game is placing ship or hitting.
	 * @return - preG
	 */
	public boolean getPreG() {
		return this.preG;
	}
	
}
