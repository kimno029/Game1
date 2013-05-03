
package OurGame;

import java.awt.Rectangle;

public class Physics {

	
	Solid solid;
	Rectangle gameBounds;
	public Physics(Solid solid){
		this.solid = solid;
		System.out.println(" Solid px at 100, 561: " + solid.solidPx(100, 561) );
	}
	
	// Rectangle next is pass by value, and is tested for hitdetection
	// and gets modified for correct values if hit occurs 
	//
	
	public void tryMove(Rectangle current, Rectangle next){
		boolean movingRight = true;
		boolean movingUp = true;
		int xPlus = 0, yPlus = 0;
		
		if(next.x < current.x){
			movingRight = false;
		}
		if(next.y > current.y){
			movingUp = false;
		}
		int okX = next.x;
		int okY = next.y;
		if(movingRight){
			xPlus = next.width;
		}
		if(!movingUp){
			yPlus = next.height;
		}
		int dx = next.x - current.x;
		int dy = next.y - current.y;
		
		boolean noHit = false; 
		while(!noHit)
		{
			if(
					solid.solidPx(okX+xPlus, okY+yPlus)
					|| solid.solidPx(okX+xPlus, okY+next.height) 
					|| solid.solidPx(okX+next.width, okY+yPlus)
			){
				
				if(dx != 0)
					okX -= dx/Math.abs(dx);
				if(dy != 0)
					okY -= dy/Math.abs(dy);
				if(dx == 0 && dy == 0){
					okX = current.x;
					okY = current.y;
					break;
				}
			}else{
				noHit = true;
			}
		}
		
		// Sets final variables for the next rectangle which
		// is base by and used by user object.
		
		next.x = okX;
		next.y = okY;
	}
	
}

