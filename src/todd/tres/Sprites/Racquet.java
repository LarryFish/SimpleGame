package todd.tres.Sprites;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import todd.tres.GUI.Game;

public class Racquet {

  // Instance Variables
  private static final int Y = 930;
  private static final int WIDTH = 200;
  private static final int HEIGHT = 10;
  private static final int SPEED = 2;
  int x = 0;
  int xa = 0;
  private Game game;

  public int hitCount = 0;

  // Constructor
  public Racquet(Game game) {
    this.game = game;
  }

  // Move Method
  public void move() {
    if (x + xa > 0 && x + xa < game.getWidth() - WIDTH)
      x = x + xa;
  }

  // Paint Method
  public void paint(Graphics2D g) {
    Rectangle2D.Double shape = new Rectangle2D.Double(x, Y, WIDTH, HEIGHT);
    g.setPaint(Color.BLACK);
    g.fill(shape);
    g.draw(shape);
    g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    g.drawString("Racquet Hit Count:" + Integer.toString(hitCount), 100, 50);
  }

  // KeyReleased Method
  public void keyReleased(KeyEvent e) {
    xa = 0;
  }

  // KeyPressed Method
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT)
      xa = -SPEED;
    if (e.getKeyCode() == KeyEvent.VK_RIGHT)
      xa = SPEED;
  }

  // Method that returns raquet hit box position
  public Rectangle getBounds() {
    return new Rectangle(x, Y, WIDTH, HEIGHT);
  }

  // Method to return the location of the top of the racquet hit box
  public double getTopY() {
    return Y;
  }
}
