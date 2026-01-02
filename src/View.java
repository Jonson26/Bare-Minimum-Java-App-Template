import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.lang.Thread;

/*
This class contains most of the game logic.
It is also used to "glue" all the other classes together
*/
public class View extends JPanel implements MouseListener, KeyListener {
	private Renderer renderer;
	private BufferedImage background; //Empty game board saved to save time on rendering
	private String textEntered;
	private boolean keyLeftPressed, keyRightPressed, keyUpPressed, keyDownPressed;
	int y_offset;
	
	public View(){
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
		
		addMouseListener(this);
		addKeyListener(this);
		
		renderer = new Renderer();
		background = renderer.renderBackGround(); //Immediately prerender the background
		textEntered = "";
		keyLeftPressed = false;
		keyRightPressed = false;
		keyUpPressed = false;
		keyDownPressed = false;
		
		new AnimationThread().start();
	}
	
	//Method where the rendering happens
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
		
		BufferedImage frame = renderer.renderFunction(background, 0, y_offset); 
		frame = renderer.renderText(frame, textEntered, 10, 10);
		if(keyLeftPressed)  frame = renderer.renderText(frame, "<-", 100, 100);
		if(keyRightPressed) frame = renderer.renderText(frame, "->", 118, 100);
		if(keyUpPressed)    frame = renderer.renderText(frame, "Ʌ",  111,  90);
		if(keyDownPressed)  frame = renderer.renderText(frame, "V",  111, 100);
		BufferedImage scaledFrame = renderer.scale(frame, Util.SCALE); //Scale the resulting frame
		g.drawImage(scaledFrame, 0, 0, this); //Copy the frame onto the screen
    }
	
	
	
	@Override
	public void mouseClicked(MouseEvent e){
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e){
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e){
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e){
		
	}
	
	@Override
	public void mouseExited(MouseEvent e){
		
	}
	
    @Override
    public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT:
				keyLeftPressed = true;
				break;
			case KeyEvent.VK_RIGHT:
				keyRightPressed = true;
				break;
			case KeyEvent.VK_UP:
				keyUpPressed = true;
				break;
			case KeyEvent.VK_DOWN:
				keyDownPressed = true;
				break;
			default:
				break;
		}
		repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT:
				keyLeftPressed = false;
				break;
			case KeyEvent.VK_RIGHT:
				keyRightPressed = false;
				break;
			case KeyEvent.VK_UP:
				keyUpPressed = false;
				break;
			case KeyEvent.VK_DOWN:
				keyDownPressed = false;
				break;
			default:
				break;
		}
		repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        textEntered += keyChar;
		System.out.println(textEntered);
		repaint();
    }
	
	private class AnimationThread extends Thread{
		private int delta = 0;
		
		public void update(){
			delta++;
		}
		
		@Override
		public void run(){
			while(true){
				try{
					sleep((int)(1000/30));
				}catch(Exception ex){
					System.out.println("Insomnia!");
				}
				
				update();
				
				y_offset = delta%(Util.SIDE/2);
				
				repaint();
			}
		}
	}
}
