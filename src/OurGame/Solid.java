package OurGame;

import java.awt.Image;

import javax.swing.ImageIcon;
/**
 * Solid
 * 50 blockSize, screen 1200 x 600 = 24 x 12
 *
 */
public class Solid {
	int hPx, wPx; // Width and Height in pixels
	int wb, hb; // Width and Height in blocks
	static final int blockSize = 50;
	boolean[][] blocks;
	static Image img = new ImageIcon("img/block50.png").getImage();
	
	public Solid(int w, int h){
		hPx = h;
		wPx = w;
		wb = w/blockSize;
		hb = h/blockSize;
		blocks = new boolean[hb][wb];
		
		// 
		// -< REPLACE WITH LOAD-FUNCTION
		//
		for(int i = 0; i < wb; i++){
			for(int j = 0; j < hb; j++)
				blocks[j][i] = false;
		}
		for(int i = 0; i < wb; i++){ // true to top and bottom
			blocks[0][i] = true;
			blocks[blocks.length-1][i] = true;
		}
		
		for(int i=0; i < hb; i++){ // true to sides, left and right
			blocks[i][0] = true;
			int idxWidth = blocks[0].length;
			blocks[i][idxWidth-1] = true;
		}
		
		blocks[10][4] = true;
		blocks[9][4] = true;
		blocks[9][5] = true;
		blocks[5][6] = true;
		blocks[10][6] = true;
		blocks[9][6] = true;
		blocks[8][6] = true;
		blocks[10][7] = true;
		
		//
		// - < END OF REPLACE
		//
				
	}
	public int getSize(){
		return blockSize;
	}
	
	public int getWBlocks(){
		return wb;
	}
	public int getHBlocks(){
		return hb;
	}
	
	public Image getImg(){
		return img;
	}
	
	public boolean isSolid(int h, int w){
		if(h < hb && w < wb && h>=0 && w>=0)
			return blocks[h][w];
		return false;
	}
	
	public boolean solidPx(int x, int y){
		if(y < hb*blockSize && x < wb*blockSize && y>0 && x>0 ){
			return blocks[y/blockSize][x/blockSize];
		}
		else return false;
	}

	public int nextSolidDown(int h, int w){
		int distY = 0;
		for(int i = (h)/blockSize; i < hb; i++){
			if(blocks[i][w/blockSize]){
				distY = i*blockSize - h;
				break;
			}
		}
		//System.out.println("distY. " + distY);
		return distY;
	}
}
