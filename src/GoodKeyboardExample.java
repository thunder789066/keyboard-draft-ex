import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class GoodKeyboardExample extends SimpleLoop {
	
	Keyboard keyboard = new Keyboard();
	
	public GoodKeyboardExample() {
		this.appSleep = 10;
		this.appTitle = "Good Keyboard Input Example";
		this.chars = "";
	}
	
	
	@Override protected void processInput() {
		this.keyboard.process();
	}
	
	@Override protected void render(Graphics g) {
		super.render(g);
		
		g.drawString("Sleep Value: " + this.appSleep, 20, 30);
		g.drawString("Press UP to increase sleep", 20, 42);
		g.drawString("Press DOWN to decrease sleep", 20, 54);
		
		g.drawString(this.chars, 5, 70);
	}
	

//===========================================================================

	public static void main(String[] args) {
		launchApp( new GoodKeyboardExample() );
	}

}