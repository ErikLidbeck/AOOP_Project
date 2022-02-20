package application;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import framework.*;
import application.gameobjects.*;
/**
 * @author Erik L
 * @author Max M
 * @invariant (xArrDim && yArrDim) > 0
 * @invariant OFFSET = 100
 * @invariant gameObjects = 3x3 2D-Array
 * @invariant !gameObjects.contains(null)
 */
public class TicTacToe extends Game{
	private int xArrDim = 0;
	private int yArrDim = 0;
	private final int OFFSET = 100;
	private boolean won;
	private int movesDone = 0;
	private GameObject[][] gameObjects;
	private JPanel level;

	private String currentPlayerSymbol;
	private final String CROSS = "src/icons/X.png";
	private final String CIRCLE = "src/icons/O.png";

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Game game = new TicTacToe();
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public Dimension setWindowDimension() {
		return new Dimension(400,400);
	}

	/**
	 * Creates Game Level
	 * @postcondition gameObjects != null
	 * @postcondition xArrDim = gameObjects X Dimension
	 * @postcondition yArrDim = gameObjects Y Dimension
	 * @postcondition won = false
	 * @postcondition currentPlayerSymbol = CIRCLE
	 * @postcondition level contains all gameObjects
	 * @return level
	 */
	public JComponent createGameLevel() {
		GameObject[][] imgsArr = {
				{new TTTPlate(), new TTTPlate(), new TTTPlate()},
				{new TTTPlate(), new TTTPlate(), new TTTPlate()},
				{new TTTPlate(), new TTTPlate(), new TTTPlate()}
		};
		won = false;
		gameObjects = imgsArr;
		currentPlayerSymbol = CIRCLE;
		level = new JPanel();
		level.setLayout(null);
		int width = 0;
		int height = 0;

		for(GameObject[] gObjects: gameObjects) {
			for(GameObject o: gObjects) {
				o.setLocation(xArrDim*OFFSET, yArrDim*OFFSET);
				level.add(o);
				xArrDim++;
				if(width < xArrDim*OFFSET) {
					width = xArrDim*OFFSET;
				}
			}
			xArrDim = 0;
			yArrDim++;
			if(height < yArrDim*OFFSET) {
				height = yArrDim*OFFSET;
			}
		}
		Dimension levelDimensions = new Dimension(width, height);
		level.setPreferredSize(levelDimensions);
		level.addMouseListener(new ClickListener());
		return level;
	}
	/**
	 * Switches the current player
	 */
	private void changePlayerSymbol() {
		if(currentPlayerSymbol == CIRCLE) {
			currentPlayerSymbol = CROSS;
		}
		else if(currentPlayerSymbol == CROSS) {
			currentPlayerSymbol = CIRCLE;
		}
	}
	/**
	 * Checks if either player won by having three in a row
	 * @param x What row to check
	 * @return String with information about who won, or if a win is yet to occur
	 */
	private String checkRowWinner(int x) {
		if(!(gameObjects[x][0].getImageIcon().toString().equals("src/icons/empty.png"))
				&& gameObjects[x][0].getImageIcon().toString().equals(gameObjects[x][1].getImageIcon().toString())
				&& gameObjects[x][1].getImageIcon().toString().equals(gameObjects[x][2].getImageIcon().toString())) {
			if(gameObjects[x][2].getImageIcon().toString().equals(CIRCLE))
				return "Circle win";
			else
				return "Cross win";
		}
		return "no win";
	}
	/**
	 * Checks if either player won by having three in a column
	 * @param x What column to check
	 * @return String with information about who won, or if a win is yet to occur
	 */
	private String checkColumnWinner(int x) {
		if(!(gameObjects[0][x].getImageIcon().toString().equals("src/icons/empty.png"))
				&& gameObjects[0][x].getImageIcon().toString().equals(gameObjects[1][x].getImageIcon().toString())
				&& gameObjects[1][x].getImageIcon().toString().equals(gameObjects[2][x].getImageIcon().toString())) {
			if(gameObjects[2][x].getImageIcon().toString().equals(CIRCLE))
				return "Circle win";
			else
				return "Cross win";
		}
		return "no win";
	}
	/**
	 * Checks if either player won by having three along one diagonal
	 * @return String with information about who won, or if a win is yet to occur
	 */
	private String checkDiagonalWinner() {
		if(!(gameObjects[0][0].getImageIcon().toString().equals("src/icons/empty.png")) 
				&& gameObjects[0][0].getImageIcon().toString().equals(gameObjects[1][1].getImageIcon().toString())
				&& gameObjects[1][1].getImageIcon().toString().equals(gameObjects[2][2].getImageIcon().toString())) {
			if(gameObjects[2][2].getImageIcon().toString().equals(CIRCLE))
				return "Circle win";
			else
				return "Cross win";
		}
		return "no win";
	}
	/**
	 * Checks if either player won by having three along the other diagonal
	 * @return String with information about who won, or if a win is yet to occur
	 */
	private String checkAntiDiagonalWinner() {
		if(!(gameObjects[0][2].getImageIcon().toString().equals("src/icons/empty.png")) 
				&& gameObjects[0][2].getImageIcon().toString().equals(gameObjects[1][1].getImageIcon().toString())
				&& gameObjects[1][1].getImageIcon().toString().equals(gameObjects[2][0].getImageIcon().toString())) {
			if(gameObjects[0][0].getImageIcon().toString().equals(CIRCLE))
				return "Circle win";
			else
				return "Cross win";
		}
		return "no win";
	}
	/**
	 * Calls our functions for win conditions to see if someone has won
	 * @postcondition won != null
	 * @return String with information about who won, if it's a draw, or the board is full
	 */
	private String checkWinner() {
		String rowWin;
		String colWin;
		String diaWin;
		String antiDiaWin;
		for(int i=0 ; i<3 ;i++) {
			rowWin = checkRowWinner(i);
			if(!rowWin.equals("no win")) {
				won = true;
				return rowWin;
			}
			colWin = checkColumnWinner(i);
			if(!colWin.equals("no win")) {
				won = true;
				return colWin;
			}
		}
		diaWin = checkDiagonalWinner();
		if(!diaWin.equals("no win")) {
			won = true;
			return diaWin;
		}
		antiDiaWin = checkAntiDiagonalWinner();
		if(!antiDiaWin.equals("no win")) {
			won = true;
			return antiDiaWin;
		}
		if(movesDone==9) {
			won = true;
			return "Draw";
		}
		return "no winner";
	}

	private class ClickListener extends MouseAdapter{
		/**
		 * Puts cross or circle on the clicked component if it's not already occupied
		 * @param e MouseEvent
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if(!won){
				Point p = new Point(e.getX(),e.getY());
				GameObject go = (GameObject) level.getComponentAt(p);
				if(go.getImageIcon().toString() != CROSS && go.getImageIcon().toString() != CIRCLE) {
					go.setImageIcon(currentPlayerSymbol);
					level.repaint();
					changePlayerSymbol();
					movesDone++;
					String result = checkWinner();
					if(!result.equals("no winner")) {
						winPopUp(result);
					}
				}
			}
		}
	}
}

