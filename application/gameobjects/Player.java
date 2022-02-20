package application.gameobjects;

import framework.GameObject;

/**
 * A GameObject of type Player
 * @author Erik L
 * @author Max M
 */

@SuppressWarnings("serial")
public class Player extends GameObject{
	/**
	 * Constructs a Player GameObject with its corresponding image
	 * @precondition src/icons/player.png must be a working directory
	 */
	public Player(){
		setImageIcon("src/icons/player.png");
	}
	/**
	 * Constructs a Player GameObject with its corresponding image and a specific location
	 * @param x x-coordinate 
	 * @param y y-coordinate
	 * @precondition x > 0
	 * @precondition y > 0
	 * @precondition src/icons/player.png must be a working directory
	 */
	public Player(int x, int y){
		setImageIcon("src/icons/player.png");
		this.setLocation(x, y);
	}
	/**
	 * Sets the size of the GameObject to a fixed size of 30*30
	 */
	@Override
	public void setObjectSize() {
		setSize(30,30);
	}
	
}
