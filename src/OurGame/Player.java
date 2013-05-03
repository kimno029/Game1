package OurGame;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Player {
	// Movement and position
	private int x, dx, y, dy, mSpeed;
	static int xMax = 10, yMax = 16,jSpeed = 17; // jSpeed Jump Speed
	static int gravity = 1; 
	static final int w = 50,h = 50;  // Width and Height
	private boolean mLeft, mRight, mJump, mShoot;
	private boolean airBorn, jumping, wallHit; // airBorn = on solid ground, jumping = jump cooldown
	
	Solid solid; // Solid object, holds ground
	Physics physics;
	Rectangle rectCurrent, rectNext;
	
	// Graphics
	Image still; // sprite
	Rectangle imgRect; // Rectangle holding variables for sprite rendering
	String state; // holds animation state
	boolean facingR; // facing right, for animation
	
	// misc
	private int mCounter; // counter with +1 per tick. 1-100, checked in move()
	
	public Player(Solid blocks, Physics physics){
		this.physics = physics;
		solid = blocks;
		// Positional variables

		x = 2 * 50;
		y = 5 * 50;
		mRight = mJump = mLeft = mShoot = false;
		wallHit = jumping = false;
		airBorn = true; // true
		mSpeed = 1;
		dy = dx = 0;
		
		// Graphic variables		
		
		ImageIcon i = new ImageIcon("img/jackWalk.png");
		still = i.getImage();
		// animation variables
		imgRect = new Rectangle(0,0,w,h);
		state ="";
		facingR = true;
		
		
		mCounter  = 1;
	}
	
	public void move(){
		
		if(!mJump){
			jumping = false;
			if(dy < 0)
				dy = 0;
		}
		if(!airBorn)
		{
			if(mJump && !jumping){ //jump pressed
				airBorn = true;
				dy -= jSpeed;
				jumping = true;
				
			}else
				dy  = 0;
		
		}
		
		
		//if(mJump) System.out.println("Airborn: " + airBorn);
		
		if( mLeft || mRight ){
			if(mLeft)
				dx -= mSpeed;
			if(mRight)
				dx += mSpeed;
		}else{
			// deacceleration in X if neither LEFT or RIGHT are pressed
			 if(dx < 0) dx += 1;
			 if(dx > 0) dx -= 1;
			 
		}
		
		// -> walljump
		//
		if (airBorn && !jumping && wallHit && mJump) {
			jumping = true;
			dy -= jSpeed;
			if (dx < 0)
				dx = xMax;
			else if(dx > 0)
				dx = -xMax;
		}
		
		
		if(airBorn){ // gravity
			dy += gravity;
			//System.out.println("GRAVITY is ON");
		} 
		
		// Controls and sets maxSpeed 
		if(dx > xMax) dx = xMax;
		if(dx < -xMax) dx = -xMax;
		
		if(dy > yMax) dy = yMax;
		if(dy < -yMax) dy = -yMax;
		
		//
		// ---< HitDetection >---
		//
		
		int exY = y;
		int exX = x;
		if( dy > 0) exY += h; // extended y; if moving down, add height
		if( dx > 0) exX += w; // extended x; if moving right, add width 
		
		
		if(!airBorn) // sets airBorn = true if no ground under players feet
		{
			
			if (
					gravity < solid.nextSolidDown( y + h , x)
					&& gravity < solid.nextSolidDown(y + h, x + w) )
			{	
				airBorn = true;
				dy += gravity;
			}
		}
		
		if(airBorn){ // hitDetect Y
			if (solid.solidPx( x, exY + dy) || solid.solidPx(x + w , exY + dy)) { // Corrects dy if hit will occur

				if(dy>0)airBorn = false; // If hitting ground on downmovement, not airBorn
				int i = dy;
				if (Math.abs(dy) > 0) {
					while ( solid.solidPx( x, exY + i)
							|| solid.solidPx( x + w, exY + i) ) {
						if (dy < 0)
							i++;
						else
							i--;
						if(i == 0) break;
						
					}
				}
				dy = i;
			}
			
		}
		
		wallHit = false;
		while(solid.solidPx(exX+dx, y ) || solid.solidPx(exX+dx, y+h) ){ // hitDetect x
			wallHit = true;
			if(Math.abs(dx) < 2){
				dx = 0;
				break;				
			}			
			dx-=dx/Math.abs(dx);
			
		}
		if(wallHit && dy>2)
			dy/=2;
		
		/* new Hitdetection
		rectNext = new Rectangle(x+dx,y+dy,w,h);
		
		physics.tryMove(new Rectangle(x,y,w,h), rectNext);
		
		// Execute movement
		x = rectNext.x;
		y = rectNext.y;
		*/
		x += dx;
		y += dy;
		
	// debug
		if(mCounter > 100) {mCounter = 1;}
		mCounter++;
		
	}
	
	public String toString(){
		return new String("x: " + x + ", y: " + y + ", dx: " + dx + ", dy: " + dy + 
				",next solid: " + solid.nextSolidDown(y+h, x+w) + ", airborn: " + airBorn);
	}
	
	public int getX(){
		return x;		
	}
	public int getY(){
		return y;
	}
	
	public int getDy(){
		return dy;
	}
	public int getDx(){
		return dx;
	}
	
	public void setDxy(int pdx, int pdy){
		/*
		dy = pdy;
		dx = pdx;
		*/
	}
	
	public void paint(Graphics2D g){
		// = "groundIdle";
		if (true) { // disables animations
			// logic for state.
			if (!airBorn)
				state = "ground";
			if(!airBorn && dx == 0) state = "idle";
			if (airBorn && dy < 0)
				state = "airUp";
			if (airBorn && dy > 0)
				state = "airDown";
			if (wallHit && Math.abs(dy) > 0)
				state = "wallGlide";
			
			if (state.equals("idle"))
				imgRect.y = 100;
			else if (state.equals("ground"))
				imgRect.y = 0; // 0
			else if (state.equals("airUp"))
				imgRect.y = 50; // 50
			else if (state.equals("airDown"))
				imgRect.y = 50; // 100
			else if (state.equals("wallGlide"))
				imgRect.y = 150;
			//
			// - FACING
			//
			if (mRight)
				facingR = true;
			if (mLeft)
				facingR = false;
			//
			if (facingR){
				imgRect.x = 0;
			}else{
				imgRect.x = 100;
			}

			// animation, frame 1 or 2
			if ((mCounter % 25) > 12)
				imgRect.x += 50;

		}
		g.drawImage(still, x, y, x + w, y + h,
				imgRect.x, imgRect.y, 
				imgRect.x + imgRect.width, imgRect.y + imgRect.height, null);	
		
	}
	
	
	public Image getStill(){
		return still;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
			mLeft = true;
		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
			mRight = true;
				
		if(key == KeyEvent.VK_SPACE)
			mJump = true;		
		if(key == KeyEvent.VK_C)
			mShoot = true;
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
			mLeft = false;
				
		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
			mRight = false;

		if(key == KeyEvent.VK_SPACE)
			mJump = false;
		if(key == KeyEvent.VK_C)
			mShoot = false;
	}
}
