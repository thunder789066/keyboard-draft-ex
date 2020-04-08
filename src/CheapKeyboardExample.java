/**
 * @(#)Text1.java
 *
 *
 * @author 
 * @version 1.00 2017/5/3
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class CheapKeyboardExample extends SimpleLoop {
	
	private int spacesCount;
	private String chars;
	private CheapKeyboardInput keyboard;
	
	public CheapKeyboardExample() {
		this.appSleep = 10;
		this.appTitle = "Broken Keyboard Input Example";
		this.chars = "";
	}
	
	@Override protected void initialize() {
		this.keyboard = new CheapKeyboardInput();
		this.canvas.addKeyListener(this.keyboard);
	}
	
	@Override protected void processInput() {
		//Adjust sleep time up or down
		if (this.keyboard.keyDown( KeyEvent.VK_DOWN ))
			this.appSleep /= 2;
		
		if (this.keyboard.keyDown( KeyEvent.VK_UP ))
			this.appSleep *= 2;
		
		//Backspace
		if (this.keyboard.keyDown( KeyEvent.VK_BACK_SPACE ))
			this.chars.substring( 0, this.chars.length() );
		
		//Check if typed was a letter
		for (int i = 0; i < 256; i++)
			//Check if key was pressed
			if ( this.keyboard.keyDown( i ) )
				//Check if key is single character
				if ( KeyEvent.getKeyText( i ).length() == 1 )
					this.chars += KeyEvent.getKeyText( i );
	}
	
	@Override protected void render(Graphics g) {
		super.render(g);
		
		g.drawString("Sleep Value: " + this.appSleep, 20, 30);
		g.drawString("Press UP to increase sleep", 20, 42);
		g.drawString("Press DOWN to decrease sleep", 20, 54);
		
		g.drawString( this.chars, 5, 70);
	}
	

//===========================================================================

	public static void main(String[] args) {
		//launchApp( new CheapKeyboardExample() );
	}

}