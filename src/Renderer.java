import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Arrays;

/*
This class contains most of the rendering code.
*/
public class Renderer{
	
	public Renderer(){
	}
	
	public BufferedImage renderBackGround(){
		BufferedImage out = new BufferedImage(Util.SIDE, Util.SIDE, BufferedImage.TYPE_INT_ARGB_PRE);
		
		Graphics g = out.getGraphics();
		
		g.setColor(Color.GRAY);
		g.fillRect(0,0,Util.SIDE,Util.SIDE);
		
		g.setColor(Color.GREEN);
		g.drawRect(0,0,Util.SIDE-1,Util.SIDE-1);
		
		return out;
	}
	
	public BufferedImage renderFunction(BufferedImage background, int x_o, int y_o){
		BufferedImage out = new BufferedImage(Util.SIDE, Util.SIDE, BufferedImage.TYPE_INT_ARGB_PRE);
		
		Graphics g = out.getGraphics();
		
		g.drawImage(background, 0, 0, null);
		
		g.setColor(Color.RED);
		
		int p_x = 0-Util.SIDE/2;
		int p_y = 0;
		
		for(int i=0; i<Util.SIDE; i++){
			int x = i-Util.SIDE/2;
			int y = (int)(Util.f(x)/100);
			
			g.drawLine(p_x+x_o+Util.SIDE/2, p_y+y_o, x+x_o+Util.SIDE/2, y+y_o);
			
			p_x = x;
			p_y = y;
		}
		
		return out;
	}
	
	public BufferedImage renderText(BufferedImage background, String text, int x_o, int y_o){
		BufferedImage out = new BufferedImage(Util.SIDE, Util.SIDE, BufferedImage.TYPE_INT_ARGB_PRE);
		
		Graphics g = out.getGraphics();
		
		g.drawImage(background, 0, 0, null);
		
		g.setColor(Color.CYAN);
		
		g.drawChars(text.toCharArray(), 0, text.length(), x_o, y_o);
		
		return out;
	}
	
	//Method used to scale a game frame to the desired size. Useful because monitors are getting bigger and bigger.
	public static BufferedImage scale(BufferedImage before, double scale) {
		int w = before.getWidth();
		int h = before.getHeight();
		// Create a new image of the proper size
		int w2 = (int) (w * scale);
		int h2 = (int) (h * scale);
		BufferedImage after = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_ARGB);
		AffineTransform scaleInstance = AffineTransform.getScaleInstance(scale, scale);
		AffineTransformOp scaleOp 
			= new AffineTransformOp(scaleInstance, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

		scaleOp.filter(before, after);
		return after;
	}
}