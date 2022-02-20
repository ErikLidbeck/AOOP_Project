package application.gameobjects;

import framework.GameObject;

/**
 * A GameObject of type Crate
 * @author Erik L
 * @author Max M
 */

@SuppressWarnings("serial")
public class Crate extends GameObject{
	/**
	 * Constructs a Crate GameObject with its corresponding image
	 * @precondition src/icons/crate.png must be a working directory
	 */
	public Crate(){
		setImageIcon("src/icons/crate.png");
	}
	/**
	 * Constructs a Crate GameObject with its corresponding image and a specific location
	 * @param x x-coordinate 
	 * @param y y-coordinate
	 * @precondition x > 0
	 * @precondition y > 0
	 * @precondition src/icons/crate.png must be a working directory
	 */
	public Crate(int x, int y){
		setImageIcon("src/icons/crate.png");
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
