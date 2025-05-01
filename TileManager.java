package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class TileManager {

	Tile background;
	Tile[] tile;
	//Tile[][] tiles;
	int mapTileNum[][];
	GamePanel gp;
	
	public TileManager(GamePanel gp)
	{
		this.gp = gp;
		//background = new Tile();
		//tiles = new Tile[9][gp.MAX_SCREEN_COL];
		tile = new Tile[10];
		//mapTileNum = new int[gp.MAX_SCREEN_ROW][gp.MAX_SCREEN_COL]; //help shouldn't it be reversed
		mapTileNum = new int[gp.MAX_WORLD_ROW][gp.MAX_WORLD_COL];
		getTileImage();
		loadMap();
	}
	
	public void getTileImage()
	{
		/**
		background.image = load("/objects/HamsterBackgroundFinal.png");
		for(int r = 0; r < tiles.length; r++)
		{
			for(int c = 0; c < tiles[r].length; c++)
			{
				tiles[r][c] = new Tile();
				tiles[r][c].image = load("/objects/HamsterWoodChips-1.png.png");
			}
		}*/
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/objects/HamsterBackgroundFinal.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/objects/HamsterWoodChips-1.png.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//can probably remove later lolz
	public void loadMap()
	{
		try {
			InputStream is = getClass().getResourceAsStream("/maps/map01.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
		//	while(col < gp.MAX_SCREEN_COL && row < gp.MAX_SCREEN_ROW)
			while(/*col < gp.MAX_WORLD_COL && */row < gp.MAX_WORLD_ROW)
			{
				String line = br.readLine();
				if(line == null)
					break;
				while(col < gp.MAX_WORLD_COL)//CHANGE BACK TO SCREEN
				{
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[row][col] = num;
					col++;
				}
				if(col == gp.MAX_WORLD_COL) //CHANGE BACK TO screen
				{
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public BufferedImage load(String path)
	{
		try
		{
			return 	ImageIO.read(getClass().getResourceAsStream(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void draw(Graphics2D g2)
	{
		/*
		g2.drawImage(background.image, 0, 0, gp.SCREEN_WIDTH, 144 * gp.SCALE, gp);
		int startY = 144;
		for(int r = 0; r < tiles.length; r++)
		{
			int startX = 0;
			for(int c = 0; c < tiles[r].length; c++)
			{
				g2.drawImage(tiles[r][c].image, startX * gp.SCALE, startY * gp.SCALE, gp.TILE_SIZE, gp.TILE_SIZE, gp);
				startX += gp.ORIGINAL_TILE_SIZE;
			}
			startY += gp.ORIGINAL_TILE_SIZE;
		}
		int worldCol = 0;
		int worldRow = 0; 
		//int x = 0;
		//int y = 0;
		boolean background = false;
		while(worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW)
		{
			int tileNum = mapTileNum[worldRow][worldCol];
			int worldX = worldCol * gp.SCALED_TILE_SIZE;
			int worldY = worldRow * gp.SCALED_TILE_SIZE;
			int screenX = worldX - gp.pikimin.worldX + gp.pikimin.screenX; //so this is drawing the background, it will be 0 - the pikmin's world X + the pikimins screen y
			int screenY = worldY - gp.pikimin.worldY + gp.pikimin.screenY; //world coordinates never change, always stay int he middle of the canvas. ScreenY is where the pikmin is moving to
			
			if(worldX + gp.SCALED_TILE_SIZE > gp.pikimin.worldX - gp.pikimin.screenX && 
			   worldX - gp.SCALED_TILE_SIZE < gp.pikimin.worldX + gp.pikimin.screenX &&
			   worldY + gp.SCALED_TILE_SIZE > gp.pikimin.worldY - gp.pikimin.screenY && 
			   worldY - gp.SCALED_TILE_SIZE < gp.pikimin.worldY + gp.pikimin.screenY)
			{
				if(tileNum == 0 && !background)
				{
					g2.drawImage(tile[tileNum].image, screenX, screenY, gp.SCREEN_WIDTH, 18 * gp.SCALED_TILE_SIZE, null); //originally 0, 0 for x and y
					background = true;
				}
				else if(tileNum == 0 && background) {}
				else {
					g2.drawImage(tile[tileNum].image , screenX, screenY, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE, null);
				}
			}
			worldCol++;
		//	x+= gp.TILE_SIZE;
			if(worldCol == gp.MAX_WORLD_COL)
			{
				worldCol = 0;
		//		x = 0; 
				worldRow++;
		//		y += gp.TILE_SIZE;
			}	
		}
		*/
		
		boolean background = false;
		for(int worldRow = 0; worldRow < gp.MAX_WORLD_ROW; worldRow++)
		{
			for(int worldCol = 0; worldCol < gp.MAX_WORLD_COL; worldCol++)
			{
				int tileNum = mapTileNum[worldRow][worldCol];
				
				//calculate world coordinates
				int worldX = worldCol * gp.SCALED_TILE_SIZE;
				int worldY = worldRow * gp.SCALED_TILE_SIZE;
				
				//calculate screen coordinates {camera relative)
				int screenX = worldX - gp.pikimin.worldX + gp.pikimin.screenX;
				int screenY = worldY - gp.pikimin.worldY + gp.pikimin.screenY;
				
				//Only draw tiles that are visible on screen(with buffer for smooth scrolling)
				//this is an optimization to avoid drawing tiles that are off-screen
				if(worldX + gp.SCALED_TILE_SIZE > gp.pikimin.worldX - gp.pikimin.screenX - gp.SCALED_TILE_SIZE &&
				   worldX - gp.SCALED_TILE_SIZE < gp.pikimin.worldX + gp.pikimin.screenX + gp.SCALED_TILE_SIZE &&
				   worldY + gp.SCALED_TILE_SIZE > gp.pikimin.worldY - gp.pikimin.screenY - gp.SCALED_TILE_SIZE && 
				   worldY - gp.SCALED_TILE_SIZE < gp.pikimin.worldY + gp.pikimin.screenY + gp.SCALED_TILE_SIZE)
				{
					if(tileNum == 0)
					{
						//if(!background)
						//{
							g2.drawImage(tile[tileNum].image, 0, 0, gp.SCREEN_WIDTH, 144 * gp.SCALE, null);
						//	background = true;
					//	}
					}else {
						g2.drawImage(tile[tileNum].image, screenX, screenY, gp.SCALED_TILE_SIZE, gp.SCALED_TILE_SIZE, null);
					}
				}
			}
		}
	}
}
