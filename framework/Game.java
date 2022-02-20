package framework;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Framework for game application
 * @author Erik L
 * @author Max M
 */
public abstract class Game implements KeyListener{
	private Clip BGMusic;
	private JFrame frame;

	/**
	 * Constructs a Game
	 * @precondition setWindowDimenstion() must be defined
	 * @precondition createGameLevel() must be defined
	 */
	public Game(){
		frame = new JFrame();
		frame.setLayout(new GridBagLayout());
		frame.setPreferredSize(setWindowDimension());
		frame.add(createGameLevel());
		frame.addKeyListener(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	/**
	 * Create dimension for JFrame
	 * @return Dimension
	 */
	public abstract Dimension setWindowDimension();
	/**
	 * Create a game JComponent
	 * @return Game to be displayed
	 */
	public abstract JComponent createGameLevel();	
	/**
	 * Creates pop up panel for winner
	 * @param text text to be displayed
	 */
	public void winPopUp(String text)
    {
        JOptionPane.showMessageDialog(frame, text, "WINNER!", JOptionPane.PLAIN_MESSAGE);
    }
	/**
	 * Play a sound file
	 * @param filePath
	 * @precondition filePath = path to existing file
	 */
	public void playSound(String filePath) {
		try {
			AudioInputStream sound = AudioSystem.getAudioInputStream(new File(filePath));
			Clip soundClip = AudioSystem.getClip();
			soundClip.open(sound);
			soundClip.start();
		}catch(Exception ex){
			System.out.println("Sound exception: " + ex.getMessage());
		}
	}
	
	/**
	 * Play background music
	 * @param filePath
	 * @precondition filePath = path to existing file
	 */
	public void playBGMusic(String filePath) {
		try{
			AudioInputStream backgroundMusic = AudioSystem.getAudioInputStream(new File(filePath));
			BGMusic = AudioSystem.getClip();
			BGMusic.open(backgroundMusic);
			BGMusic.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch(Exception ex){ 
			System.out.println("BGM exception: " + ex.getMessage());
		}
	}
	/**
	 * Gets the background music Clip
	 * @return BGMusic background music Clip
	 */
	public Clip getBGMusic(){
		return BGMusic;
	}
}
