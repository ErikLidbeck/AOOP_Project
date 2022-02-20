package application.gameobjects;

import framework.GameObject;

/**
 * A GameObject of type Wall
 * @author Erik L
 * @author Max M
 */

@SuppressWarnings("serial")
public class Wall extends GameObject{
	/**
	 * Constructs a Wall GameObject with its corresponding image
	 * @precondition src/icons/wall.png must be a working directory
	 */
	public Wall(){
		setImageIcon("src/icons/wall.png");
	}
	/**
	 * Constructs a Wall GameObject with its corresponding image and a specific location
	 * @param x x-coordinate 
	 * @param y y-coordinate
	 * @precondition x > 0
	 * @precondition y > 0
	 * @precondition src/icons/wall.png must be a working directory
	 */
	public Wall(int x, int y){
		setImageIcon("src/icons/wall.png");
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
