package main;

import java.awt.*; 

import javax.swing.*;
import entity.*;
import tile.*;

public class GamePanel extends JPanel implements Runnable{

	//SCREEN SETTINGS
	public final int UNSCALED_TILE_SIZE = 16; //16x16 tile, the default size of player characters/NPC
	public final int SCALE = 3;//4 the scale to convert the sprite to
	
	public final int SCALED_TILE_SIZE = UNSCALED_TILE_SIZE * SCALE; //48x48 tile
	
	//SETTINGS FOR THE SCREEN BOUNDED BY THE PANEL
	public final int MAX_SCREEN_COL = 32;
	public final int MAX_SCREEN_ROW = 18;
	
	public final int SCREEN_WIDTH = SCALED_TILE_SIZE * MAX_SCREEN_COL; // 1536 pixels
	public final int SCREEN_HEIGHT = SCALED_TILE_SIZE * MAX_SCREEN_ROW; //768 pixels
	
	
	//SETTINGS FOR THE WORLD CAMERA, UNBOUNDED AND SHIFTS WITH PLAYER MOVEMENT
	public final int MAX_WORLD_COL = 40;
	public final int MAX_WORLD_ROW = 22;
	
	public final int WORLD_WIDTH = SCALED_TILE_SIZE * MAX_WORLD_COL;
	public final int WORLD_HEIGHT = SCALED_TILE_SIZE * MAX_WORLD_ROW;
	
	//ALL THE OBJECTS DISPLAYED ON THE SCREEN
	KeyHandler keyH;
	Thread gameThread;
	public Pikimin pikimin;
	TileManager tile;
	
	final int FPS = 60;
	final int NANO = 1000000000;
	
	public GamePanel()
	{
		//CONSTRUCT ALL OF THE OBJECTS
		keyH = new KeyHandler();
		pikimin = new Pikimin(this, keyH);
		tile = new TileManager(this);
		
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);//later on, set this to the desk scene
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread()
	{
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run()
	{
		double interval = NANO/FPS;
		double nextDrawTime = System.nanoTime() + interval;
		while(gameThread != null)
		{
			//update information such as character positions
			update();
			//draw the screen with the updated information
			repaint();

			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/(Math.pow(10, 6));
			
				if(remainingTime < 0)
				{
					remainingTime = 0;
				}
				Thread.sleep((long) remainingTime);
				nextDrawTime += interval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//LATER ON, ADD A WAY TO IDENTIFY WHETHER OR NOT DIAGONAL MOVEMENT SHOULD BE ALLOWED
	//OR,TAKE CONTROL OF ANY SCENE WHERE THE PIKMIN IS TRYING TO MOVE DIAGONALLY(SAY, IF
	// THEY ARE TRYING TO GO UP A RAMP.

	public void update()
	{
		pikimin.update();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		
		Graphics2D g2 = (Graphics2D)g;
		
		tile.draw(g2);
		pikimin.draw(g2);
		g2.dispose(); //dispose of the graphics context and release any system resources that it
		//is using
	}
	
}