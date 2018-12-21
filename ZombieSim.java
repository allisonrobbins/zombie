package cs2113.zombies;

import cs2113.util.DotPanel;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.MouseListener;
import javax.swing.*;


public class ZombieSim extends JFrame{

	private static final long serialVersionUID = -5176170979783243427L;

	/** The Dot Panel object you will draw to */
	protected static DotPanel dp;

	/* Define constants using static final variables */
	public static final int MAX_X = 200;
	public static final int MAX_Y = 200;
	private static final int DOT_SIZE = 3;
	private static final int NUM_HUMANS = 200;
	private static final int NUM_BUILDINGS = 80;
	private boolean testBreak = false;
	/*
	 * This fills the frame with a "DotPanel", a type of drawing canvas that
	 * allows you to easily draw squares to the screen.
	 */
	public ZombieSim (){
		this.setSize(MAX_X * DOT_SIZE, MAX_Y * DOT_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Braaiinnnnnsss");
		Container cPane = this.getContentPane();
		cPane.setLayout(new BorderLayout());// border layout so that the body count is at top
		/* Create and set the size of the panel */
		dp = new DotPanel(MAX_X, MAX_Y, DOT_SIZE);
		City world = new City(MAX_X, MAX_Y, NUM_BUILDINGS, NUM_HUMANS);//calls city constructor
		//setting up the body count label...
		JLabel bodyCount = new JLabel();
		bodyCount.setText("Total Body Count: "+world.allZombies.size());
		bodyCount.setForeground(Color.RED);
		bodyCount.setBackground(Color.DARK_GRAY);
		//inner classes for the mouse and keyboard:
		class mouseClickListener implements MouseListener
		{
			//every time mouse is clicked, sends coordinates to city class to create zombie
			public void mouseClicked(MouseEvent e) {
				int xpos = e.getXOnScreen()/DOT_SIZE;
				int ypos = (e.getYOnScreen()/DOT_SIZE)-20;
				world.addZombie(xpos,ypos);
			}
			public void mousePressed(MouseEvent e) {//not using this
			}
			public void mouseReleased(MouseEvent e) {//not using this
			}
			public void mouseEntered(MouseEvent e) {//not using this
			}
			public void mouseExited(MouseEvent e) {//not using this
			}
		}
		class spaceBar implements KeyListener
		{
			public void keyTyped(KeyEvent e) {//not using this
			}
			//this checks if the key pressed was spacebar, then sets world to reset
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==32)//32 is key code for space bar
				{
					System.out.println("Space bar pressed: RESET!");
					testBreak = true;//the boolean I use to test if world needs to reset
				}
				else
				{
					System.out.println("Other key pressed");//only resets if space bar was pressed
				}
			}
			public void keyReleased(KeyEvent e) { }//not using this
		}
		dp.addMouseListener(new mouseClickListener());//add mouse to dot panel
		this.addKeyListener(new spaceBar());//add key to JFrame
		/* Add the panel to the frame */
		cPane.add(dp);
		cPane.add(bodyCount,BorderLayout.NORTH);
		this.pack();
		dp.init();
		dp.clear();
		dp.setPenColor(Color.red);
		this.setVisible(true);
		/* This is the Run Loop (aka "simulation loop" or "game loop")
		 * It will loop forever, first updating the state of the world
		 * (e.g., having humans take a single step) and then it will
		 * draw the newly updated simulation. Since we don't want
		 * the simulation to run too fast for us to see, it will sleep
		 * after repainting the screen. Currently it sleeps for
		 * 33 milliseconds, so the program will update at about 30 frames
		 * per second.
		 */
		while(true)
		{
			// Run update rules for world and everything in it
			world.update();
			bodyCount.setText("Total Body Count: "+world.allZombies.size());
			// Draw to screen and then refresh
			world.draw();
			dp.repaintAndSleep(33);
			if(testBreak==true)
			{
				break;
			}
		}
	}
	public static void main(String[] args) {
		/* Create a new GUI window every time it is reset*/
		while(true)
		{
			new ZombieSim();
		}
	}
}
