package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Entity {
	//renamed it to worldX and worldY from x and y
	public int worldX, worldY;
	public int speed;
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public BufferedImage load(String path)
	{
		try
		{
			return 	ImageIO.read(getClass().getResourceAsStream(path));
		}catch(IOException e) { e.printStackTrace();}
		return null;
	}

}