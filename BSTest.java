package Testting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import battleshipController.BSController;
import battleshipModel.BSModel;
import general.*;

class BSTest {
	
	// test Board, Loc
	@Test
	void testBoard() {
		Board b = new Board();
		assertEquals(b.size(),10);
		assertEquals(b.getLoc(1, 1).col,1);
		assertEquals(b.getLoc(1, 1).row,1);
		b.placing(0, 0);
		b.hitLoc(0, 0);
		assertEquals(b.getLoc(0, 0).checkHasShip(),true);
		assertEquals(b.getLoc(0, 0).checkIsSink(),true);
		Loc[][] l = b.getGrid();
		b.getLoc(0, 0).setShip(false);
		b.getLoc(0, 0).setSunk(false);
	}
	// test Move
	@Test
	void testMove() {
		Board b = new Board();
		Move m1 = new Move(b,3,4,5,90);
		Move m2 = new Move(b,3,4);
		assertEquals(m2.getBoard(),b);
		assertEquals(m1.getSize(),3);
		assertEquals(m1.getRow(),4);
		assertEquals(m1.getCol(),5);
		assertEquals(m1.getDir(),90);
	}
	
	@Test
	void testModel() {
		BSModel mod = new BSModel();
		BSController con = new BSController(mod);
		assertEquals(con.getBoard(),mod.getBoard());
		assertEquals(con.getGameStatus(), mod.getPreG());
		assertEquals(con.getLife(), 18);
		assertEquals(con.getOppoBoard(), mod.getOppoBoard());
		assertEquals(con.getplayer(), mod.getP() );
		con.setLife();
		con.setScore();
		con.resetLife();
		con.resetScore();
		assertEquals(con.getScore(), 0);
		con.donePlace();
	}

}
