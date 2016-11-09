import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
 
class Bounce {
    public static void main(String[] args) {
        new BounceFrame();
    }
}
 
class BounceFrame extends JFrame {
    private BallComponent comp;
    public static final int STEPS = 1000;
    public static final int DELAY = 5;
 
    BounceFrame() {
        setTitle("Bounce");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Thread t = new Thread();
        comp = new BallComponent();
        add(comp, BorderLayout.CENTER);
 
        JPanel buttonPanel = new JPanel();
        addButton(buttonPanel, "Start", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBall();
            }
        });
        addButton(buttonPanel, "Close", new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }
 
    /* Adds a button to a container */
    public void addButton(Container c, String title, ActionListener listener) {
        JButton button = new JButton(title);
        c.add(button);
        button.addActionListener(listener);
    }
 
    /* Adds a bouncing ball to the panel and makes it bounce 1,000 times */
    public void addBall() {
    	try {
        	Ball ball = new Ball();
        	comp.add(ball);
        	ThreadBola balls = new ThreadBola(comp,STEPS,DELAY,ball);
            Thread newBall = new Thread(balls);
            newBall.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 
/* A ball that moves and bounces off the edges of a rectangle */
class Ball {
    private static final int XSIZE = 15;
    private static final int YSIZE = 15;
 
    private double x = 0;
    private double y = 0;
    private double dx = 1;
    private double dy = 1;
 
    /* Moves the ball to the next position, reversing direction if it hits one of the edges */
    public void move(Rectangle2D bounds) {
        x += dx;
        y += dy;
        if (x < bounds.getMinX()) {
            x = bounds.getMinX();
            dx = -dx;
        }
        if (x + XSIZE >= bounds.getMaxX()) {
            x = bounds.getMaxX() - XSIZE;
            dx = -dx;
        }
        if (y < bounds.getMinY()) {
            y = bounds.getMinY();
            dy = -dy;
        }
        if (y + YSIZE >= bounds.getMaxY()) {
            y = bounds.getMaxY() - YSIZE;
            dy = -dy;
        }
    }
 
    /* Gets the shape of the ball at its current position */
    public Ellipse2D getShape() {
        return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
    }
}
 
class BallComponent extends JPanel {
    private static final int DEFAULT_WIDTH = 450;
    private static final int DEFAULT_HEIGHT = 350;
 
    private java.util.List<Ball> balls = new ArrayList<>();
 
    public void add(Ball b) {
        balls.add(b);
    }
 
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // erase background
        Graphics2D g2 = (Graphics2D) g;
        for (Ball b : balls) {
            g2.fill(b.getShape());
        }
    }
 
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}