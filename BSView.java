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

import java.util.Observable;
import java.util.Observer;

import battleshipController.BSController;
import battleshipModel.BSModel;
import general.Board;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


@SuppressWarnings("deprecation")
public class BSView extends Application implements Observer{
	//Constants for the game boards
	boolean postGame = true;
	BSModel player1;
	BSController con;
	int[] shipSize = new int[]{5,4,3,2,2,1,1};
	int too = 0;
	boolean round = true;
	boolean playerS = true;
	int dir = 90;
	HBox hb;
	GridPane gp1;
	GridPane gp2;

	
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("SHIPS");
		player1 = new BSModel();
		player1.addObserver(this);
		con = new BSController(player1);
		hb = new HBox();
		hb.setPrefSize(1200, 500);
		gp1 = createBoard();
		gp2 = createBoard();
		hb.getChildren().addAll(gp1,gp2);
		CheckBox ser = new CheckBox("Launch as Server");
		CheckBox cli = new CheckBox("Launch as Client");
		ser.setOnAction((event) -> {
			if (ser.isSelected()) {
				round = true;
				// startserver
				con.startSer(4002);
				try {
					start(stage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		cli.setOnAction((event) -> {
			if (cli.isSelected()) {
				round = false;
				// startserver
				con.startCli("localhost", 4002);
				playerS = false;
				try {
					start(stage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		hb.getChildren().addAll(ser,cli);
		Scene sc = new Scene(hb);
		sc.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.R) {
					if (dir == 90) {
						dir = 0;
						clearB();
					} else {
						dir = 90;
						clearB();
					}
				}
			}
		});
		preplay();
		stage.setScene(sc);
		stage.show();
	}
	
	/**
	 *  clear the board when R is pressed, which means set the color of the Label to
	 *  whatever color it need to be depend on its' status.
	 */
	public void clearB() {
		if (playerS) {
			Board bd1 = con.getBoard();
			for (int i = 0;i<10;i++) {
				for (int j = 0;j<10;j++) {
					if (bd1.getLoc(i, j).checkHasShip() && bd1.getLoc(i, j).checkIsSink()) {
						((Label) gp1.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
					} else if (bd1.getLoc(i, j).checkHasShip() == false && bd1.getLoc(i, j).checkIsSink() == true) {
						((Label) gp1.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
	
					} else if (bd1.getLoc(i, j).checkHasShip() == false && bd1.getLoc(i, j).checkIsSink() == false) {
						((Label) gp1.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
	
					} else if (bd1.getLoc(i, j).checkHasShip() == true && bd1.getLoc(i, j).checkIsSink() == false) {
						((Label) gp1.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
	
					}
				}
			}
		} else {
			Board bd2 = con.getBoard();
			for (int i = 0;i<10;i++) {
				for (int j = 0;j<10;j++) {
					if (bd2.getLoc(i, j).checkHasShip() && bd2.getLoc(i, j).checkIsSink()) {
						((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
					} else if (bd2.getLoc(i, j).checkHasShip() == false && bd2.getLoc(i, j).checkIsSink() == true) {
						((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
	
					} else if (bd2.getLoc(i, j).checkHasShip() == false && bd2.getLoc(i, j).checkIsSink() == false) {
						((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
	
					} else if (bd2.getLoc(i, j).checkHasShip() == true && bd2.getLoc(i, j).checkIsSink() == false) {
						((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
	
					}
				}
			}
		}
	}
	
	
	
	
	/**
	 * Add event handlers for every label in both boards.
	 */
	public void preplay() {
				for (Node node : gp1.getChildren()){
					// When a Label is clicked, perform placeship or hit.
					((Label) node).setOnMouseClicked(new EventHandler<MouseEvent>(){
						@SuppressWarnings("static-access")
						@Override
						public void handle(MouseEvent arg0) {
							// placing ships
							if (postGame && playerS) {
								int row = gp1.getRowIndex((Label) node);
								int col = gp1.getColumnIndex((Label) node);
								if (dir == 90) {
									if (shipSize[too] + row <= 10) {
										con.makePlace(shipSize[too],row, col, dir);
										too += 1;
									}
								} else if (dir == 0) {
									if (shipSize[too] + col <= 10) {
										con.makePlace(shipSize[too],row, col, dir);
										too += 1;
									}
								}
								if (too == 7) {
									con.donePlace();
									postGame = false;
									System.out.println("done placing");
								}
						} else  if (!postGame && !playerS){
							int row = gp1.getRowIndex((Label) node);
							int col = gp1.getColumnIndex((Label) node);
							con.makeHit(con.getOppoBoard(), row,col);
							updateOppoB();
						}
						}
					});
					// when mouse enter a Label, show the user where the ship is going to be set
					// if they place it.
					((Label) node).setOnMouseEntered(new EventHandler<MouseEvent>(){
						@SuppressWarnings("static-access")
						@Override
						public void handle(MouseEvent arg0) {
							if (postGame && playerS) {
								int row = gp1.getRowIndex((Label) node);
								int col = gp1.getColumnIndex((Label) node);
								if (dir == 90 && shipSize[too] + row <= 10) {
									for (int i = 0; i < shipSize[too]*10;i+=10) {
										((Label)gp1.getChildren().get((row*10+col)+i)).setBackground(new Background(
												new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
									}
				
								} else if (dir == 0 && shipSize[too] + col <= 10) {
									for (int i = 0; i < shipSize[too];i++) {
										((Label)gp1.getChildren().get((row*10+col)+i)).setBackground(new Background(
												new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
									}
								}
						}	
						}
					});
					// if mouse leave a Label, set it back to the status it was.
					((Label) node).setOnMouseExited(new EventHandler<MouseEvent>(){
						@SuppressWarnings("static-access")
						@Override
						public void handle(MouseEvent arg0) {
							if (postGame && playerS) {
								int row = gp1.getRowIndex((Label) node);
								int col = gp1.getColumnIndex((Label) node);
								if (dir == 90 && shipSize[too] + row <= 10) {
									for (int i = 0; i < shipSize[too]*10;i+=10) {
										if (con.getBoard().getLoc(row+ i/10, col).checkHasShip() == false) {
										((Label)gp1.getChildren().get((row*10+col)+i)).setBackground(new Background(
												new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));}
									}
				
								} else if (dir == 0 && shipSize[too] + col <= 10) {
									for (int i = 0; i < shipSize[too];i++) {
										if (con.getBoard().getLoc(row, col+i).checkHasShip() == false) {
										((Label)gp1.getChildren().get((row*10+col)+i)).setBackground(new Background(
												new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));}
									}
								}
							}	
						}
					});
				}
			
				for (Node node : gp2.getChildren()){
					// When a Label is clicked, perform placeship or hit.
					((Label) node).setOnMouseClicked(
						new EventHandler<MouseEvent>(){
						@SuppressWarnings("static-access")
						@Override
						public void handle(MouseEvent arg0) {
							// placing ships
							if (postGame && !playerS) {
								int row = gp2.getRowIndex((Label) node);
								int col = gp2.getColumnIndex((Label) node);
								if (dir == 90) {
									if (shipSize[too] + row <= 10) {
										con.makePlace(shipSize[too],row, col, dir);
										too += 1;
									}
								} else if (dir == 0) {
									if (shipSize[too] + col <= 10) {
										con.makePlace(shipSize[too],row, col, dir);
										too += 1;
									}
								}
								if (too == 7) {
									con.donePlace();
									System.out.println("done placing");
									postGame = false;
									((Label) node).removeEventHandler(MouseEvent.ANY,this);
								}
							} else if (!postGame && playerS){
								int row = gp2.getRowIndex((Label) node);
								int col = gp2.getColumnIndex((Label) node);
								con.makeHit(con.getOppoBoard(), row,col);
								updateOppoB();
								
							}
						}	
					});
					// when mouse enter a Label, show the user where the ship is going to be set
					// if they place it.
					((Label) node).setOnMouseEntered(new EventHandler<MouseEvent>(){
						@SuppressWarnings("static-access")
						@Override
						public void handle(MouseEvent arg0) {
							if (postGame && !playerS) {
								int row = gp2.getRowIndex((Label) node);
								int col = gp2.getColumnIndex((Label) node);
								if (dir == 90 && shipSize[too] + row <= 10) {
									for (int i = 0; i < shipSize[too]*10;i+=10) {
										((Label)gp2.getChildren().get((row*10+col)+i)).setBackground(new Background(
												new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
									}
				
								} else if (dir == 0 && shipSize[too] + col <= 10) {
									for (int i = 0; i < shipSize[too];i++) {
										((Label)gp2.getChildren().get((row*10+col)+i)).setBackground(new Background(
												new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
									}
								}
							} 
						}	
					});
					// if mouse leave a Label, set it back to the status it was.
					((Label) node).setOnMouseExited(new EventHandler<MouseEvent>(){
						@SuppressWarnings("static-access")
						@Override
						public void handle(MouseEvent arg0) {
							if (postGame && !playerS) {
								int row = gp2.getRowIndex((Label) node);
								int col = gp2.getColumnIndex((Label) node);
								if (dir == 90 && shipSize[too] + row <= 10) {
									for (int i = 0; i < shipSize[too]*10;i+=10) {
										if (con.getBoard().getLoc(row+ i/10, col).checkHasShip() == false) {
										((Label)gp2.getChildren().get((row*10+col)+i)).setBackground(new Background(
												new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));}
									}
				
								} else if (dir == 0 && shipSize[too] + col <= 10) {
									for (int i = 0; i < shipSize[too];i++) {
										if (con.getBoard().getLoc(row, col+i).checkHasShip() == false) {
										((Label)gp2.getChildren().get((row*10+col)+i)).setBackground(new Background(
												new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));}
									}
								}
							}
						}	
					});
				}
	}
	
	public void updateOppoB() {
		if (playerS) {
			Board bd = con.getOppoBoard();
			for (int i = 0;i<10;i++) {
				for (int j = 0;j<10;j++) {
					if (bd.getLoc(i, j).checkHasShip() && bd.getLoc(i, j).checkIsSink()) {
						((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
					} else if (bd.getLoc(i, j).checkHasShip() == false && bd.getLoc(i, j).checkIsSink() == true){
						((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

					}
				}
			}
			
			
			
		} else {
			Board bd = con.getOppoBoard();
			for (int i = 0;i<10;i++) {
				for (int j = 0;j<10;j++) {
					if (bd.getLoc(i, j).checkHasShip() && bd.getLoc(i, j).checkIsSink()) {
						((Label) gp1.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
					} else if (bd.getLoc(i, j).checkHasShip() == false && bd.getLoc(i, j).checkIsSink() == true){
						((Label) gp1.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

					}
				}
			}
			
			Board bd2 = con.getBoard();
			for (int i = 0;i<10;i++) {
				for (int j = 0;j<10;j++) {
					if (bd2.getLoc(i, j).checkHasShip() && bd2.getLoc(i, j).checkIsSink()) {
						((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
					} else if (bd2.getLoc(i, j).checkHasShip() == false && bd2.getLoc(i, j).checkIsSink() == true) {
						((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

					}
				}
			}
		}
		
		
	}
	
	
	/**
	 * Create a board for a player.
	 * 
	 * @return
	 * 	GridPane-gp, a board GridPand filled with 10*10 Labels.
	 */
	public GridPane createBoard() {
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 10, 10));
		for (int i = 0;i<10;i++) {
			gp.getRowConstraints().add(new RowConstraints(45));
			gp.getColumnConstraints().add(new ColumnConstraints(45));
		}
		for (int i = 0;i<10;i++) {
			for (int j = 0;j<10;j++) {
				Label l = new Label();
				l.setPrefSize(40, 40);
				l.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
				GridPane.setConstraints(l,j,i);
				gp.getChildren().add(l);
			}
		}
		return gp;
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// update player1's board.
		if (playerS) {
			// if placing ships
			if (postGame) {
				Board bd = con.getBoard();
				for (int i = 0;i<10;i++) {
					for (int j = 0;j<10;j++) {
						if (bd.getLoc(i, j).checkHasShip()) {
							((Label) gp1.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
						}
					}
				}
			} else { // game started, perform hit 
				Board bd2 = con.getBoard();
				for (int i = 0;i<10;i++) {
					for (int j = 0;j<10;j++) {
						if (bd2.getLoc(i, j).checkHasShip() && bd2.getLoc(i, j).checkIsSink()) {
							((Label) gp1.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
							con.setLife();
						} else if (bd2.getLoc(i, j).checkHasShip() == false && bd2.getLoc(i, j).checkIsSink() == true) {
							((Label) gp1.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

						}
					}
				}
				
				Board bd = con.getOppoBoard();
				for (int i = 0;i<10;i++) {
					for (int j = 0;j<10;j++) {
						if (bd.getLoc(i, j).checkHasShip() && bd.getLoc(i, j).checkIsSink()) {
							((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
							con.setScore();
						} else if (bd.getLoc(i, j).checkHasShip() == false && bd.getLoc(i, j).checkIsSink() == true){
							((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

						}
					}
				}
			}
		// update player2's board.
		} else {
			// if placing ships
			if (postGame) {
				Board bd = con.getBoard();
				for (int i = 0;i<10;i++) {
					for (int j = 0;j<10;j++) {
						if (bd.getLoc(i, j).checkHasShip()) {
							((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
						}
					}
				}
			} else {// game started, perform hit 
				Board bd2 = con.getBoard();
				for (int i = 0;i<10;i++) {
					for (int j = 0;j<10;j++) {
						if (bd2.getLoc(i, j).checkHasShip() && bd2.getLoc(i, j).checkIsSink()) {
							((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
							con.setLife();
						} else if (bd2.getLoc(i, j).checkHasShip() == false && bd2.getLoc(i, j).checkIsSink() == true) {
							((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

						}
					}
				}
				Board bd = con.getOppoBoard();
				for (int i = 0;i<10;i++) {
					for (int j = 0;j<10;j++) {
						if (bd.getLoc(i, j).checkHasShip() && bd.getLoc(i, j).checkIsSink()) {
							((Label) gp1.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
							con.setScore();
						} else if (bd.getLoc(i, j).checkHasShip() == false && bd.getLoc(i, j).checkIsSink() == true){
							((Label) gp1.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

						}
					}
				}
			}
		}
		
		// check if the game is ended or not
		if (con.getScore() == 18) {
			// if you hit all of your opponent's ships, you win
			System.out.println(con.getLife());
			Alert a = new Alert(Alert.AlertType.INFORMATION);
			a.setHeaderText("Good Game!");
			a.setContentText("Win");
			a.show();
		} else {
			con.resetScore();
		}
		if (con.getLife() == 0) {
			// if all of your ships are sunk, you lose.
			System.out.println(con.getLife());
			Alert a = new Alert(Alert.AlertType.INFORMATION);
			a.setHeaderText("Good Game!");
			a.setContentText("Lost");
			// if you lose, present all your opponent's ships.
			if (playerS) {
				Board bd = con.getOppoBoard();
				for (int i = 0;i<10;i++) {
					for (int j = 0;j<10;j++) {
						if (bd.getLoc(i, j).checkHasShip()) {
							((Label) gp2.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
							con.setScore();
						} 
					}
				}
			} else {
				Board bd = con.getOppoBoard();
				for (int i = 0;i<10;i++) {
					for (int j = 0;j<10;j++) {
						if (bd.getLoc(i, j).checkHasShip()) {
							((Label) gp1.getChildren().get(i*10 + j)).setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
							con.setScore();
						}
					}
				}
			}
			a.show();
			
		} else {
			con.resetLife();
		}
	}
	
}
