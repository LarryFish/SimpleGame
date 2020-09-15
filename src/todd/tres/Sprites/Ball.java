package todd.tres.Sprites;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import todd.tres.GUI.Game;
import todd.tres.Sound.Sound;

@SuppressWarnings("unused")
public class Ball {

  // Instance Variables
  public static final String pokeball =
      "C:/Users/tat20/eclipse-workspace/SimpleGame/src/todd/tres/Sprites/pokeball.png";

  private static final int DIAMETER = 32;
  private static final int SPEED = 2;
  public int x = 0;
  public int y = 0;
  int xa = SPEED;
  int ya = SPEED;
  private Game game;
  BufferedImage image;


  // Ball Constructor
  public Ball(Game game, String filePath) throws IOException {
    this.game = game;
    image = ImageIO.read(new File(filePath));
  }

  // Resize image method
  public static BufferedImage resize(BufferedImage image, int newW, int newH) {
    Image tmp = image.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();
    return dimg;
  }

  public boolean collisionBall() {
    for (int i = 0; i < game.balls.size(); i++) {
      if (game.balls.get(i).getBounds().intersects(this.getBounds())) {
        return true;
      }
      // Rectangle thisBall = this.getBounds();
      // Rectangle hitBall = game.balls.get(i).getBounds();
      // int thisArray[] = new int[image.getHeight()];
      // int hitArray[] = new int[image.getHeight()];
      // for (int v = 0; v < DIAMETER; v++) {
      // for (int n = 0; n < DIAMETER; n++) {
      // thisArray[v] = image.getRGB(v, n);
      // }
      // }
      // int thisLine;
      // int hitLine;
      // if (thisBall.y <= hitBall.y) {
      // thisLine = hitBall.y - thisBall.y;
      // hitLine = 0;
      // } else {
      // thisLine = 0;
      // hitLine = thisBall.y - hitBall.y;
      // }
      //
      // x = thisBall.x - hitBall.x;
      // int maxLines = Math.max(thisBall.height, hitBall.height);
      // for (int line = Math.max(thisLine, hitLine); line < maxLines; line++) {
      // long thisMask = thisArray[thisLine];
      // long hitMask = hitArray[hitLine];
      // if (x < 0) {
      // thisMask <<= (-x);
      // } else {
      // hitMask <<= x;
      // }
      // if ((thisMask & hitMask) != 0) {
      // return true;
      // } else {
      // return false;
      // }
      // }
      // }
    }
    return false;
  }

  public Ball ballCollisionID() {
    for (int i = 0; i < game.balls.size(); i++) {
      if (game.balls.get(i).getBounds().intersects(this.getBounds())) {
        Ball collisionBall = game.balls.get(i);
        return collisionBall;
      }
    }
    return null;
  }


  // Method that changes the direction of the ball if
  // it reaches the border of the frame
  public void move() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
    boolean changeDirection = true;
    Sound ballSound = new Sound(Sound.ballSound);
    Ball ball2 = ballCollisionID();
    if (x + xa < 0)
      xa = SPEED;
    else if (x + xa > game.getWidth() - DIAMETER)
      xa = -SPEED;
    else if (y + ya < 0)
      ya = SPEED;
    else if (y + ya > game.getHeight() - DIAMETER) {
      game.gameOver();
    } else if (collisionRacquet()) {
      ya = -SPEED;
      y = (int) (game.racquet.getTopY() - DIAMETER);
    } else if (collisionBall() && this.x > ball2.x && this.y == ball2.y) {
      this.xa = -SPEED;
      ball2.xa = SPEED;
      // this.x = ball2.x + DIAMETER;
      // ball2.x = this.x - DIAMETER;
    } else if (collisionBall() && this.x < ball2.x && this.y == ball2.y) {
      this.xa = SPEED;
      ball2.xa = -SPEED;
      // this.x = ball2.x - DIAMETER;
      // ball2.x = this.x + DIAMETER;
    } else if (collisionBall() && this.y > ball2.y && this.x == ball2.x) {
      this.ya = SPEED;
      ball2.ya = -SPEED;
      // this.y = ball2.y + DIAMETER;
      // ball2.y = this.y - DIAMETER;
    } else if (collisionBall() && this.y < ball2.y && this.x == ball2.x) {
      this.ya = -SPEED;
      ball2.ya = SPEED;
      // this.y = ball2.y - DIAMETER;
      // ball2.y = this.y + DIAMETER;
    } else if (collisionBall() && this.y > ball2.y && this.x < ball2.x) {
      if (this.y - ball2.y < ball2.x - this.x) {
        this.ya = SPEED / 2;
        this.xa = -SPEED;
        ball2.ya = -SPEED / 2;
        ball2.xa = SPEED;
      } else {
        this.ya = SPEED;
        this.xa = -SPEED / 2;
        ball2.ya = -SPEED;
        ball2.xa = SPEED / 2;
      }
    } else if (collisionBall() && this.y < ball2.y && this.x < ball2.x) {
      if (ball2.y - this.y < ball2.x - this.x) {
        this.ya = -SPEED / 2;
        this.xa = -SPEED;
        ball2.ya = SPEED / 2;
        ball2.xa = SPEED;
      } else {
        this.ya = -SPEED;
        this.xa = -SPEED / 2;
        ball2.ya = SPEED;
        ball2.xa = SPEED / 2;
      }
    } else if (collisionBall() && this.y > ball2.y && this.x > ball2.x) {
      if (this.y - ball2.y < this.x - ball2.x) {
        this.ya = SPEED / 2;
        this.xa = SPEED;
        ball2.ya = -SPEED / 2;
        ball2.xa = -SPEED;
      } else {
        this.ya = SPEED;
        this.xa = SPEED / 2;
        ball2.ya = -SPEED;
        ball2.xa = -SPEED / 2;
      }
    } else if (collisionBall() && this.y < ball2.y && this.x > ball2.x) {
      if (ball2.y - this.y < this.x - ball2.x) {
        this.ya = -SPEED / 2;
        this.xa = SPEED;
        ball2.ya = SPEED / 2;
        ball2.xa = -SPEED;
      } else {
        this.ya = -SPEED;
        this.xa = SPEED / 2;
        ball2.ya = SPEED;
        ball2.xa = -SPEED / 2;
      }
    } else {
      changeDirection = false;
    }
    if (changeDirection) {
      ballSound.play();
    }
    x = x + xa;
    y = y + ya;
  }

  // Method that detects a collision between the ball hit box
  // and the racquet hit box
  public boolean collisionRacquet() {
    if (game.racquet.getBounds().intersects(getBounds())) {
      game.racquet.hitCount = game.racquet.hitCount + 1;
    }
    return game.racquet.getBounds().intersects(getBounds());
  }



  // Method that paints the Ball
  public void paint(Graphics2D g) {
    image = resize(image, DIAMETER, DIAMETER);
    g.drawImage(image, null, x, y);
    // Ellipse2D.Double shape = new Ellipse2D.Double(x, y, DIAMETER, DIAMETER);
    // g.setPaint(Color.RED);
    // g.fill(shape);
    // g.draw(shape);
  }

  // Method that gets the ball hit box position
  public Rectangle getBounds() {
    return new Rectangle(x, y, DIAMETER, DIAMETER);
  }



}

