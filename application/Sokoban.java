package application;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import application.gameobjects.*;
import framework.*;
import javax.sound.sampled.Clip;
/**
 * @author Erik L
 * @author Max M
 * @invariant (xArrDim && yArrDim) > 0
 * @invariant nCorrectCrates >= 0
 * @invariant levelImgsArray = square 2D-array
 * @invariant layers contains theMovables & theLevel
 * @invariant theMovables contains player and crates
 * @invariant theLevel contains walls, blanks and goals
 * @invariant crates.size() >= 0
 * @invariant goals.size() >= 0
 */
public class Sokoban extends Game{
	private int xArrDim = 0;
	private int yArrDim = 0;
	private final int OFFSET = 30;
	private final int LEFT = 1;
	private final int RIGHT = 2;
	private final int UP = 3;
	private final int DOWN = 4;
	private boolean won;
	private int nCorrectCrates;

	private GameObject[][] levelImgsArray;
	private JLayeredPane layers;
	private JPanel theMovables;
	private JPanel theLevel;
	private Player player;
	private ArrayList<Crate> crates;
	private ArrayList<BlankGoal> goals;
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Game game = new Sokoban();
	}

	/**
	 * Creates game level
	 * @postcondition xArrDim = levelImgsArray X Dimension
	 * @postcondition yArrDim = levelImgsArray Y Dimension
	 * @postcondition levelImgsArray = square 2D-array
	 * @postcondition layers contains theMovables & theLevel
	 * @postcondition theMovables contains player and crates
	 * @postcondition theLevel contains walls, blanks and goals
	 * @postcondition crates.size() >= 0
	 * @postcondition goals.size() >= 0
	 * @return layers
	 */
	public JComponent createGameLevel() {
		GameObject[][] imgsArr = {
				{null, null, new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), null},
				{new Wall(), new Wall(), new Wall(), new Blank(), new Blank(), new Blank(), new Wall(), null},
				{new Wall(), new BlankGoal(), player = new Player(), new Crate(), new Blank(), new Blank(), new Wall(), null},
				{new Wall(), new Wall(), new Wall(), new Blank(), new Crate(), new BlankGoal(), new Wall(), null},
				{new Wall(), new BlankGoal(), new Wall(), new Wall(), new Crate(), new Blank(), new Wall(), null},
				{new Wall(), new Blank(), new Wall(), new Blank(), new BlankGoal(), new Blank(), new Wall(), new Wall()},
				{new Wall(), new Crate(), new BlankGoal(), new Crate(), new Crate(), new Crate(), new BlankGoal(), new Wall()},
				{new Wall(), new Blank(), new Blank(), new Blank(), new BlankGoal(), new Blank(), new Blank(), new Wall()},
				{new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall(), new Wall()},
		};

		playBGMusic("src/Sounds/DS3_WAV.wav");

		nCorrectCrates = 0;
		won = false;
		levelImgsArray = imgsArr;
		crates = new ArrayList<Crate>();
		goals = new ArrayList<BlankGoal>();
		int width = 0;
		int height = 0;
		layers = new JLayeredPane();
		theMovables = new JPanel();
		theMovables.setOpaque(false);
		theMovables.setLayout(null);
		theLevel = new JPanel();
		theLevel.setOpaque(false);
		theLevel.setLayout(null);
		for(GameObject[] gObjects:levelImgsArray){
			for(GameObject i:gObjects){
				if(i != null){
					i.setLocation(xArrDim*OFFSET, yArrDim*OFFSET);
					if(i.getClass() == Crate.class){
						crates.add((Crate) i);
					}
					if(i.getClass() == BlankGoal.class){
						goals.add((BlankGoal) i);
					}
					if(i.getClass() == Player.class || i.getClass() == Crate.class){
						theMovables.add(i);
						theLevel.add(new Blank(xArrDim*OFFSET, yArrDim*OFFSET));
					}
					else{
						theLevel.add(i);
					}
				}
				
				xArrDim++;
				if(width < xArrDim*OFFSET){
					width = xArrDim*OFFSET;
				}
			}
			xArrDim = 0;
			yArrDim++;
			if(height < yArrDim*OFFSET){
				height = yArrDim*OFFSET;
			}
		}
		Dimension levelDimensions = new Dimension(width, height);
		layers.add(theLevel, new Integer(1),0);
		layers.add(theMovables, new Integer(2),0);
		theLevel.setBounds(0, 0, width, height);
		theMovables.setBounds(0, 0, width, height);
		layers.setPreferredSize(levelDimensions);
		return layers;
	}
	/**
	 * @param movingObject GameObject which may want to move
	 * @param dir Direction of check
	 * @precondition dir = (LEFT || RIGHT || UP || DOWN)
	 * @return true if wall is blocking, false if no wall
	 */
	public boolean checkWallCollision(GameObject movingObject, int dir){
		switch(dir){
		case LEFT:
			if(theLevel.getComponentAt(movingObject.getX()-OFFSET, movingObject.getY()).getClass() == Wall.class){
				return true;
			}
			break;
		case RIGHT:
			if(theLevel.getComponentAt(movingObject.getX()+OFFSET, movingObject.getY()).getClass() == Wall.class){
				return true;
			}
			break;
		case UP:
			if(theLevel.getComponentAt(movingObject.getX(), movingObject.getY()-OFFSET).getClass() == Wall.class){
				return true;
			}
			break;
		case DOWN:
			if(theLevel.getComponentAt(movingObject.getX(), movingObject.getY()+OFFSET).getClass() == Wall.class){
				return true;
			}
			break;
		}
		return false;
	}
	/**
	 * Checks if game is won
	 * @return won true if won, false if not won
	 */
	public boolean checkWin() {
		if(nCorrectCrates == crates.size() && won != true) {
			won = true;
			getBGMusic().stop();
		}
		return won;
	}
	/**
	 * Check if crate is on goal
	 * @param c Crate which may be on goal
	 * @precondition goals.size() > 0
	 * @return true if c is on goal, false if not
	 */
	public boolean isOnGoal(Crate c){
		for(BlankGoal goal:goals) {
			if(goal.getLocation().equals(c.getLocation())) {
				return true;
			}
		}
		return false;
	};
	/**
	 * 
	 * @param movingObject GameObject which may want to moves
	 * @param dir Direction of check
	 * @precondition dir = (LEFT || RIGHT || UP || DOWN)
	 * @return blockingCrate null if no crate blocking
	 */
	public Crate checkCrateCollision(GameObject movingObject, int dir) {
		Crate blockingCrate = null;
		switch(dir){
		case LEFT:
			if(theMovables.getComponentAt(movingObject.getX()-OFFSET, movingObject.getY()).getClass() == Crate.class){
				blockingCrate = (Crate) theMovables.getComponentAt(movingObject.getX()-OFFSET, movingObject.getY());
				return blockingCrate;
			}
			break;
		case RIGHT:
			if(theMovables.getComponentAt(movingObject.getX()+OFFSET, movingObject.getY()).getClass() == Crate.class){
				blockingCrate = (Crate) theMovables.getComponentAt(movingObject.getX()+OFFSET, movingObject.getY());
				return blockingCrate;
			}
			break;
		case UP:
			if(theMovables.getComponentAt(movingObject.getX(), movingObject.getY()-OFFSET).getClass() == Crate.class){
				blockingCrate = (Crate) theMovables.getComponentAt(movingObject.getX(), movingObject.getY()-OFFSET);
				return blockingCrate;
			}
			break;
		case DOWN:
			if(theMovables.getComponentAt(movingObject.getX(), movingObject.getY()+OFFSET).getClass() == Crate.class){
				blockingCrate = (Crate) theMovables.getComponentAt(movingObject.getX(), movingObject.getY()+OFFSET);
				return blockingCrate;
			}
			break;
		}
		return blockingCrate;
	}

	@Override
	/**
	 * Moves player in direction of arrow keys
	 * Resets if "R" is pressed
	 * @param e KeyEvent
	 */
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getExtendedKeyCode();
		if(!checkWin()) {
			switch(keyCode){
			case KeyEvent.VK_LEFT:
				moveLeft(player);
				if(checkWin()) {
					winPopUp("YOU WON!");
				}
				break;
			case KeyEvent.VK_RIGHT:
				moveRight(player);
				if(checkWin()) {
					winPopUp("YOU WON!");
				}
				break;
			case KeyEvent.VK_UP:
				moveUp(player);
				if(checkWin()){
					winPopUp("YOU WON!");
				}
				break;
			case KeyEvent.VK_DOWN:
				moveDown(player);
				if(checkWin()) {
					winPopUp("YOU WON!");
				}
				break;
			}
		}
		if(keyCode == KeyEvent.VK_R) {
			resetGameLevel();
		}
	}
	/**
	 * Move gObj left
	 * @param gObj Moving GameObject
	 * @return true if gObj could be moved, false if not
	 */
	public boolean moveLeft(GameObject gObj) {
		Crate possibleCrate = checkCrateCollision(gObj, LEFT);
		if(checkWallCollision(gObj, LEFT)) {
			return false;
		}
		else if(possibleCrate == null ) {
			if(gObj.getClass() == Crate.class) {
				if(isOnGoal((Crate)gObj)) {
					gObj.setImageIcon("src/icons/crate.png");
					nCorrectCrates--;
				}
				gObj.setLocation(gObj.getX()-OFFSET, gObj.getY());
				if(isOnGoal((Crate)gObj)){
					playSound("src/Sounds/fastOOF.wav");
					gObj.setImageIcon("src/icons/cratemarked.png");
					nCorrectCrates++;
				}
			}
			else {
				gObj.setLocation(gObj.getX()-OFFSET, gObj.getY());
			}
			return true;
		}
		else if(possibleCrate != null && gObj.getClass() == Crate.class){
			return false;
		}
		else if(moveLeft(possibleCrate)){
			gObj.setLocation(gObj.getX()-OFFSET, gObj.getY());
			return true;
		}
		return false;
	}
	/**
	 * Move gObj right
	 * @param gObj Moving GameObject
	 * @return true if gObj could be moved, false if not
	 */
	public boolean moveRight(GameObject gObj) {
		Crate possibleCrate = checkCrateCollision(gObj, RIGHT);
		if(checkWallCollision(gObj, RIGHT)) {
			return false;
		}
		else if(possibleCrate == null ) {
			if(gObj.getClass() == Crate.class) {
				if(isOnGoal((Crate)gObj)) {
					gObj.setImageIcon("src/icons/crate.png");
					nCorrectCrates--;
				}
				gObj.setLocation(gObj.getX()+OFFSET, gObj.getY());
				if(isOnGoal((Crate)gObj)){
					playSound("src/Sounds/fastOOF.wav");
					gObj.setImageIcon("src/icons/cratemarked.png");
					nCorrectCrates++;
				}
			}
			else {
				gObj.setLocation(gObj.getX()+OFFSET, gObj.getY());
			}
			return true;
		}
		else if(possibleCrate != null && gObj.getClass() == Crate.class){
			return false;
		}
		else if(moveRight(possibleCrate)){
			gObj.setLocation(gObj.getX()+OFFSET, gObj.getY());
			return true;
		}
		return false;
	}
	/**
	 * Move gObj up
	 * @param gObj Moving GameObject
	 * @return true if gObj could be moved, false if not
	 */
	public boolean moveUp(GameObject gObj) {
		Crate possibleCrate = checkCrateCollision(gObj, UP);
		if(checkWallCollision(gObj, UP)) {
			return false;
		}
		else if(possibleCrate == null ) {
			if(gObj.getClass() == Crate.class) {
				if(isOnGoal((Crate)gObj)) {
					gObj.setImageIcon("src/icons/crate.png");
					nCorrectCrates--;
				}
				gObj.setLocation(gObj.getX(), gObj.getY()-OFFSET);
				if(isOnGoal((Crate)gObj)){
					playSound("src/Sounds/fastOOF.wav");
					gObj.setImageIcon("src/icons/cratemarked.png");
					nCorrectCrates++;
				}
			}
			else {
				gObj.setLocation(gObj.getX(), gObj.getY()-OFFSET);
			}
			return true;
		}
		else if(possibleCrate != null && gObj.getClass() == Crate.class){
			return false;
		}
		else if(moveUp(possibleCrate)){
			gObj.setLocation(gObj.getX(), gObj.getY()-OFFSET);
			return true;
		}
		return false;
	}
	/**
	 * Move gObj down
	 * @param gObj Moving GameObject
	 * @return true if gObj could be moved, false if not
	 */
	public boolean moveDown(GameObject gObj) {
		Crate possibleCrate = checkCrateCollision(gObj, DOWN);
		if(checkWallCollision(gObj, DOWN)) {
			return false;
		}
		else if(possibleCrate == null ) {
			if(gObj.getClass() == Crate.class) {
				if(isOnGoal((Crate)gObj)) {
					gObj.setImageIcon("src/icons/crate.png");
					nCorrectCrates--;
				}
				gObj.setLocation(gObj.getX(), gObj.getY()+OFFSET);
				if(isOnGoal((Crate)gObj)){
					playSound("src/Sounds/fastOOF.wav");
					gObj.setImageIcon("src/icons/cratemarked.png");
					nCorrectCrates++;
				}
			}
			else {
				gObj.setLocation(gObj.getX(), gObj.getY()+OFFSET);
			}
			return true;
		}
		else if(possibleCrate != null && gObj.getClass() == Crate.class){
			return false;
		}
		else if(moveDown(possibleCrate)){
			gObj.setLocation(gObj.getX(), gObj.getY()+OFFSET);
			return true;
		}
		return false;
	}
	/**
	 * Resets game
	 */
	public void resetGameLevel(){
		won = false;
		nCorrectCrates = 0;
		xArrDim = 0;
		yArrDim = 0;
		for(GameObject[] gObjects:levelImgsArray){
			for(GameObject i:gObjects){
				if(i != null){
					if(i.getClass() == Crate.class){
						i.setImageIcon("src/icons/crate.png");
					}
					i.setLocation(xArrDim*OFFSET, yArrDim*OFFSET);
				}
				xArrDim++;
			}
			xArrDim = 0;
			yArrDim++;
		}
		getBGMusic().setFramePosition(0);
		getBGMusic().loop(Clip.LOOP_CONTINUOUSLY);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	@Override
	public Dimension setWindowDimension() {
		return new Dimension(500,500);
	}
}
