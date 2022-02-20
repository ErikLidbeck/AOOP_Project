package application.gameobjects;

import framework.GameObject;

/**
 * A GameObject of type BlankGoal
 * @author Erik L
 * @author Max M
 */

@SuppressWarnings("serial")
public class BlankGoal extends GameObject{
	/**
	 * Constructs a Blank GameObject with its corresponding image
	 * @precondition src/icons/blankmarked.png must be a working directory
	 */
	public BlankGoal(){
		setImageIcon("src/icons/blankmarked.png");
	}
	/**
	 * Constructs a BlankGoal GameObject with its corresponding image and a specific location
	 * @param x x-coordinate 
	 * @param y y-coordinate
	 * @precondition x > 0
	 * @precondition y > 0
	 * @precondition src/icons/blankmarked.png must be a working directory
	 */
	public BlankGoal(int x, int y){
		setImageIcon("src/icons/blankmarked.png");
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
