/**
 * @(#)Text2.java
 *
 *
 * @author 
 * @version 1.00 2017/5/3
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class CheapKeyboardInput implements KeyListener {
	
	private boolean[] keys;
	
	public CheapKeyboardInput() {
		this.keys = new boolean[ 256 ];
	}
	
	public boolean keyDown( int keyCode ) {
		return  this.keys[ keyCode ];
	}
	
	@Override public void keyPressed( KeyEvent e ) {
		int keyCode = e.getKeyCode();
		if ( keyCode >= 0 && keyCode < this.keys.length)
			this.keys[ keyCode ] = true;
			
	}
	
	@Override public void keyReleased( KeyEvent e ) {
		int keyCode = e.getKeyCode();
		if ( keyCode >= 0 && keyCode < this.keys.length )
			this.keys[ keyCode ] = false;
			
	}
	
	@Override public void keyTyped( KeyEvent e ) {
		//Not needed
	}
	
}