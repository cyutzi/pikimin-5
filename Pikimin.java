package entity;

import java.awt.Color; 
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Pikimin extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	Map<String, BufferedImage[]> animations = new HashMap<>();
	
	//scroll the background as the player goes
	public final int screenX;
	public final int screenY;
	
	public Pikimin(GamePanel gp, KeyHandler keyH)
	{
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.SCREEN_WIDTH/2 - (gp.SCALED_TILE_SIZE/2);
		screenY = gp.SCREEN_HEIGHT/2 - (gp.SCALED_TILE_SIZE/2);
		
		setDefaultValues(); 
		getPlayerImage();
	}
	
	public void getPlayerImage()
	{
			BufferedImage[] down = {load("/player/PikiminFinalWalkForward-1.png.png"),
					load("/player/PikiminFinalWalkForward-2.png.png"),
					load("/player/PikiminFinalWalkForward-3.png.png"),
					load("/player/PikiminFinalWalkForward-4.png.png"),
					load("/player/PikiminFinalWalkForward-5.png.png"),
					load("/player/PikiminFinalWalkForward-6.png.png"),
					load("/player/PikiminFinalWalkForward-7.png.png"),
					load("/player/PikiminFinalWalkForward-8.png.png")};
			BufferedImage[] up = {load("/player/PikiminFinalWalkBackward-1.png.png"),
					load("/player/PikiminFinalWalkBackward-2.png.png"),
					load("/player/PikiminFinalWalkBackward-3.png.png"),
					load("/player/PikiminFinalWalkBackward-4.png.png"),
					load("/player/PikiminFinalWalkBackward-5.png.png"),
					load("/player/PikiminFinalWalkBackward-6.png.png"),
					load("/player/PikiminFinalWalkBackward-7.png.png"),
					load("/player/PikiminFinalWalkBackward-8.png.png")};
			BufferedImage[] idle = {load("/player/PikiminFinal-1.png.png"),
					load("/player/PikiminFinal-2.png.png"),
					load("/player/PikiminFinal-3.png.png")};
			BufferedImage[] left = {load("/player/PikiminWalkLeft-1.png.png"),
					load("/player/PikiminWalkLeft-2.png.png"),
					load("/player/PikiminWalkLeft-3.png.png")};
			BufferedImage[] right = {load("/player/PikiminWalkRight-1.png.png"),
					load("/player/PikiminWalkRight-2.png.png"),
					load("/player/PikiminWalkRight-3.png.png")};
			animations.put("down", down);
			animations.put("up", up);
			animations.put("idle", idle);
			animations.put("right", right);
			animations.put("left", left);
	}
	public void setDefaultValues()
	{
		worldX = gp.SCALED_TILE_SIZE * 20;
		worldY = gp.SCALED_TILE_SIZE * 10;
		speed = 4;
		direction = "down";
	}
	
	public void update()
	{
		String oldDirection = direction;
		if(keyH.downPressed == true || keyH.upPressed == true 
				|| keyH.rightPressed == true || keyH.leftPressed == true)
		{
			
		if(keyH.downPressed == true)
		{
			direction = "down";
			worldY += speed;
		}
		else if(keyH.upPressed == true)
		{
			direction = "up";
			worldY -= speed;
		}
		//allow for diagonal movement
		if(keyH.leftPressed == true)
		{
			direction = "left";
			worldX -= speed;
		}
		else if(keyH.rightPressed == true)
		{
			direction = "right";
			worldX += speed;
		}
		}else
		{
			direction = "idle";
		}
		
		if(!direction.equals(oldDirection))
		{
			spriteCounter = 0;
			spriteNum = 1;
		}
		spriteCounter++;
		//player image changes every 10 frames
		if(spriteCounter > 10)
		{
			BufferedImage[] frames = animations.get(direction);
			if(frames.length != 0 && frames != null)
			{
				if(spriteNum == frames.length)
				{
					spriteNum = 1;
				}else
					spriteNum++;
			}
			
			spriteCounter = 0;
		}
	}
	
	public void draw(Graphics2D g2)
	{
		BufferedImage[] images = animations.get(direction);
		BufferedImage image = images[spriteNum-1]; //recommended to mod by frames.length but idk
		g2.drawImage(image, screenX, screenY, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE * 2, null);
	}
}
