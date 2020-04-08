/**
 * @(#)SimpleLoop.java
 *
 *
 * @Clemens 
 * @version 1.00 2017/5/2
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * 	SimpleLoop is a JFrame implements the Runnable interface, which 
 *	indicates this object is Thread executable. When we create a new
 *	Thread and start it, it will execute the Runnable object's run()
 *	method. 
 *
 *	There are two ways we could execute a new Object in a different
 *	Thread. The first is to have that object class extend Thread itself.
 *	However, if we do this then each time an instance is created so to
 *	is a new Thread, and each Thread has its own object associated with
 *	it. If we implement Runnable, than multiple Threads can access the
 *	same object instance... AND if we extend Thread we can't inherit from
 *	any other objects, like JFrame!
 *
 *	Instructions for inheriting from this class:
 *	
 *	1)
 *
 */
public class SimpleLoop extends JFrame implements Runnable {
	
	
	/** Animation Thread **/
	private Thread animationThread;
	
	/** 
	 *	True if animation thread is running.
	 *	Volatile - Prevents multiple threads from accessing
	 *			   this field at the same time.
	 **/
	private volatile boolean running;
	
	/** Buffering Strategy for Graphics object */
	private BufferStrategy bs;
	
	/** Canvas to draw animation/game onto */
	protected Canvas canvas;
	
	/** App details - Changeable in child classes (protected) **/
	protected Color appBackground = Color.BLACK;
	protected Color appBorder = Color.LIGHT_GRAY;
	protected float appBorderScale = 0.8f;
	protected Font appFont = new Font("Courier New", Font.PLAIN, 14);
	protected Color appFontColor = Color.GREEN;
	protected int appSleep = 1000;
	protected String appTitle = "TBD-Title";
	protected int appWidth = 640;
	protected int appHeight = 480;
	
	
	public SimpleLoop() {
		/**Nothing done here yet, handled in createAndShowGUI() **/
	}
	
	/**
	 * 	Create and set up all components, then show GUI. Handled here
	 *	instead of the constructor in order to allow control as to when
	 *	and where (what Thread) this object is instantiated.
	 */
	private void createAndShowGUI() {
		this.setTitle(this.appTitle);
		
		/** Canvas to draw onto */
		this.canvas = new Canvas();
		this.canvas.setBackground(this.appBackground);
		this.canvas.setIgnoreRepaint(true);
		
		/** Add the canvas to the container in JFrame */
		this.getContentPane().add(this.canvas);
		
		/** Set sizes */
		this.canvas.setSize(this.appWidth, this.appHeight);
		//Figures out sizes of all components (based on PreferredSize)
		this.pack(); 
		
		/** Set up input listeners for canvas */
		//Keyboard
		//Mouse
		
		/** Start Window */
		this.setVisible(true);
		/** Create buffere from canvas */
		this.canvas.createBufferStrategy(2);
		this.bs = this.canvas.getBufferStrategy();
		this.canvas.requestFocus(); //Buttons immediately work
		
		/** Create Thread */
		// Remember, this object is a Runnable object, thus this object's
		//	run() method is invoked when we start this Thread.
		this.animationThread = new Thread(this);
		/** Start Thread, which will invoke this class's run() method */
		this.animationThread.start();
	}
	
	/**
	 * 	Invoked by the Animation Thread when it is started. Invokes the
	 *	initialize() method to initialize all of YOUR objects for program,
	 *	then begins the game/animation loop.
	 *	
	 */
	@Override public void run() {
		//Set running (for loop)
		this.running = true;
		
		/** Responsible for setting up your objects in game/animation. */
		this.initialize();
		
		while (this.running) {			
			// Step 1 - Process inputs
			this.processInput();
			// Step 2 - Update objects
			this.updateObjects();
			// Step 3 - Render (draw) next frame
			this.renderFrame();
			// Step 4 - Pause (sleep) this Thread
			this.sleep();
		}
		this.terminate();
	}
	
	/**
	 * Initialize FrameRate & other objects for program.
	 */
	protected void initialize() {
		//Nothing yet
	}
	
	/**
	 * Terminates any other running Threads
	 */
	protected void terminate() {
		//Nothing yet
	}
	
	/**
	 *	Responsible for processing inputs (keyboard & mouse)
	 */
	protected void processInput() {
		//Nothing yet
	}
	
	/**
	 *	Updates objects based on polled inputs
	 */
	protected void updateObjects() {
		//Nothing yet
	}
	
	/**
	 *	Render next frame. Handles accessing the Graphics object from the
	 *	BufferedStrategy and preparing the Graphics for drawing.
	 */
	protected void renderFrame() {
		do {
			do {
				Graphics g = null;
				try {
					//Retrieve graphics from Canvas buffer strategy
					g = this.bs.getDrawGraphics();
					//Clear Graphics screen for next frame
					g.clearRect(0, 0, this.getWidth(), this.getHeight());
					//Render frame
					this.render(g);
				} finally {
					//Dispose of Graphics object, new one created next frame
					if (g != null) 
						g.dispose();		
				}
				//Make sure Window Manager has not destroyed Graphics
			} while (bs.contentsRestored());
			//Show this buffer, sets JFrame to this buffer
			this.bs.show();
		} while (bs.contentsLost());
	}
	
	/**
	 *	Renders (paints) each component of game
	 */
	protected void render(Graphics g)
	{
		g.setFont(this.appFont);
		g.setColor(this.appFontColor);
		
	}
	
	/**
	 * Pause Thread
	 */
	private void sleep()
	{
		/** Delay Thread between frames */
			try {
				//Static method, sleeps the Thread from
				//	which this method was invoke.
				Thread.sleep(this.appSleep);
			} catch (InterruptedException e) {
				/** NOTHING TO DO */
			}
	}
	
	/**
	 *	Handles shutting things down safely when window is closed
	 */
	protected void onWindowClosing()
	{
		//Attempt to switch of running (false), and end animation thread.
		//	*** This can potentially throw exceptions because killing
		//		the Thread may kill process in middle, so we catch that
		//		exception.
		try {
			//Turn running off
			this.running = false;
			
			//.join() will pause this Thread, wait for animation thread
			//	to finish, then kill animation Thread. Think of this like
			//	the two lanes of the 'Thread Highway' merging into one lane.
			//
			//	NOTE: This is potential for possible DEADLOCK - Main Thread is
			//			waiting on animation Thread to finish, if there were more
			//			Threads involved we could get a stalemate.
			//
			this.animationThread.join();
			
		} catch (InterruptedException e) {
			//Print stack for the exception.
			e.printStackTrace();
		}
		//Exit program
		System.exit(0);
		
	}
	
//=============================================================================

	/**
	 *	Static method that takes SimpleLoop object, sets up our window
	 *	listeners, activates the SimpleLoop inside protected swing Thread.
	 *	
	 *	This method should be invoked by the main method, and a new instance
	 *	of a SimpleLoop object should be passed
	 */
	protected static void launchApp(final SimpleLoop app)
	{
		//Add window listener to to app to handle safe shutdown
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				app.onWindowClosing();
			}
		});
		//Active Swing app in protected Swing Thread.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				app.createAndShowGUI();
			}
		});
	}
	
}