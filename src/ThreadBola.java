
import java.util.ArrayList;

public class ThreadBola implements Runnable{
    private BallComponent comp;
    public static int STEPS;
    public static int DELAY;
    Ball ball;
    
	ThreadBola(BallComponent compbola, int stepbola, int delaybola, Ball bola){
	    comp = compbola;
	    STEPS = stepbola;
	    DELAY = delaybola;
	    ball = bola;
	}
	
	public void run() {
		try{
            
            for (int i = 0; i < STEPS; i++) {
                ball.move(comp.getBounds());
                //comp.paint(comp.getGraphics());
                comp.repaint();
                Thread.sleep(DELAY);
            }
		}catch(Exception e){
			
		}
		
	}

}
