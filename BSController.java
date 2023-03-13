/**
 * @author Mike Yu, Bryson Mineart
 * Description: 
 * 		This is a game called battleship, in the pregame, both player need to place their ships
 * 		there will be 7 ships in your board. After the ships are placed, Server player can fire a location 
 * 		on your opponent's board, then the Client does.... the result will not show up immediately, instead,
 * 		it show up after your opponent also makes a move; so both your's and your opponent's hit result will
 * 		show up at once. Once all the ships in one of the board are sunk, the game is over. If you lose
 * 		 you get to see your opponent's ships' locaitons.
 */
package battleshipController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import battleshipModel.BSModel;
import general.Board;
import general.Move;
import javafx.application.Platform;

public class BSController {
	private BSModel mod1;
	private Socket connection;
	static ObjectOutputStream output;
	static ObjectInputStream input;
	private int score = 0;
	private int life = 18;
	
	public BSController(BSModel mod1) {
		this.mod1 = mod1;
	}
	
	/**
	 *  If the "launch as Server" checkBox is checked, launch this as a server.
	 * @param server
	 * @param socket
	 */
	public void startSer(int socket) {
		try {
			ServerSocket server = new ServerSocket(socket);
			connection = server.accept();
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 *  If the "launch as Client" checkBox is checked, launch this as a client.
	 * @param server
	 * @param socket
	 */
	public void startCli(String server, int socket) {
		try {
			connection = new Socket(server, socket);

		mod1.setP(false);
			output = new ObjectOutputStream(connection.getOutputStream());
			input = new ObjectInputStream(connection.getInputStream());
			Thread t = new Thread(() -> {
				try {
					Move m = (Move)input.readObject();
					Platform.runLater(() -> {
						if (mod1.getPreG()) {
							mod1.setOppoBoard(m.getBoard());
							mod1.placeOppoShip(m.getSize(),m.getRow(),m.getCol(),m.getDir());
						} else {
							//mod1.hitOppo(m.getRow(), m.getCol());
							mod1.setBoard(m.getBoard());
							mod1.hit(m.getRow(), m.getCol());

						}
					});
				}
				catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			});
			t.start();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	
	private void sendMove(Move m) {
			try {
				output.writeObject(m);
				output.flush();
				Move newM = (Move) input.readObject();
				Platform.runLater(() -> {
					mod1.setP(true);
					if (mod1.getPreG()) {
						mod1.setOppoBoard(newM.getBoard());
						mod1.placeOppoShip(newM.getSize(),newM.getRow(),newM.getCol(),newM.getDir());
					}
				});

			}catch (IOException e) {
				e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
			}
	}
	
	private void sendBoard(Move b) {
			try {
				output.writeObject(b);
				output.flush();
				Move newB = (Move) input.readObject();
				//Platform.runLater(() -> {
				mod1.setBoard(newB.getBoard());
				mod1.hit(newB.getRow(), newB.getCol());
				//});


			}catch (IOException e) {
				e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
	}
	
	
	
	/**
	 *  notify your opponent about your move and wait.
	 * @param size
	 * @param r - row
	 * @param c - dir
	 * @param dir
	 */
	public void makePlace(int size, int r ,int c, int dir) {
		Move m = new Move(mod1.getBoard(),size,r,c,dir);
		mod1.placeShip(size, r, c, dir);
		sendMove(m);
	}
	
	/**
	 * notify your opponent about your move and wait.
	 * @param board
	 * @param r
	 * @param c
	 */
	public void makeHit(Board board,int r ,int c) {
		mod1.hitOppo(r,c);
		Move m = new Move(mod1.getOppoBoard(),r,c);
		sendBoard(m);
	}
	
	/**
	 * return true if you are server, false if client
	 * @return
	 */
	public boolean getplayer() {
		return this.mod1.getP();
	}
	
	/**
	 * return true if the game status is placing ships
	 * false if firing board.
	 * @return mod1.getPreG
	 */
	public boolean getGameStatus() {
		return this.mod1.getPreG();
	}
	
	/**
	 * return your opponent's board.
	 * @return
	 */
	public Board getOppoBoard() {
		return mod1.getOppoBoard();
	}
	
	/**
	 * set true if the board is done placing ships.
	 */
	public void donePlace() {
		this.mod1.donePlace();
	}
	
	/**
	 * return the score you now have.
	 * @return
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * imcrement score by 1
	 */
	public void setScore() {
		this.score += 1;
	}
	
	/**
	 * reset score to 0
	 */
	public void resetScore() {
		this.score = 0;
	}
	
	/**
	 * return the number of ships left in your board.
	 * @return
	 */
	public int getLife() {
		return this.life;
	}
	
	/**
	 * decrement your ships by 1
	 */
	public void setLife() {
		this.life -= 1;
	}
	/**
	 * reset the number of ships.
	 */
	public void resetLife() {
		this.life = 18;
	}
	

	/**
	 * return the board of player depend on the "turns"
	 * @param turns - if true, reutnr player1's board, if false, return player2's board.
	 * @return - return the board of player
	 */
	public Board getBoard() {
		return mod1.getBoard();
	}
	
	
}
