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

package battleshipView;

import java.io.IOException;

import javafx.application.Application;

public class BSMain {

	public static void main(String[] args) throws IOException {
		Application.launch(BSView.class, args);
	}
	
}
