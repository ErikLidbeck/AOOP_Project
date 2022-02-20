package application.gameobjects;

import framework.GameObject;

/**
 * A GameObject of type Blank
 * @author Erik L
 * @author Max M
 */

@SuppressWarnings("serial")
public class Blank extends GameObject{
	/**
	 * Constructs a Blank GameObject with its corresponding image
	 * @precondition src/icons/blank.png must be a working directory
	 */
	public Blank(){
		setImageIcon("src/icons/blank.png");
	}
	/**
	 * Constructs a Blank GameObject with its corresponding image and a specific location
	 * @param x x-coordinate 
	 * @param y y-coordinate
	 * @precondition x > 0
	 * @precondition y > 0
	 * @precondition src/icons/blank.png must be a working directory
	 */
	public Blank(int x, int y){
		setImageIcon("src/icons/blank.png");
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
