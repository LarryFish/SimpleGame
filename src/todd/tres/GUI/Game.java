package todd.tres.GUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import todd.tres.Sound.Sound;
import todd.tres.Sprites.Ball;
import todd.tres.Sprites.Racquet;


@SuppressWarnings("serial")
public class Game extends JPanel {

  public CopyOnWriteArrayList<Ball> balls = new CopyOnWriteArrayList<Ball>();
  public Racquet racquet = new Racquet(this);
  Sound backgroundMusic = new Sound(Sound.back);
  Sound gameOver = new Sound(Sound.gameover);

  public Game() throws LineUnavailableException, UnsupportedAudioFileException, IOException {

    addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {}

      @Override
      public void keyReleased(KeyEvent e) {
        racquet.keyReleased(e);
      }

      @Override
      public void keyPressed(KeyEvent e) {
        racquet.keyPressed(e);
      }
    });
    setFocusable(true);
    backgroundMusic.loop();
  }

  private void move() throws IOException, UnsupportedAudioFileException, LineUnavailableException,
      InterruptedException {

    for (Ball ball : balls) {
      ball.move();
      racquet.move();
    }

  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    int ballCount = balls.size();
    g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
    g.drawString("Ball Count:" + Integer.toString(ballCount), 100, 100);
    for (Ball ball : balls) {
      ball.paint(g2d);
      racquet.paint(g2d);
    }

  }

  public void gameOver() {
    backgroundMusic.stop();
    gameOver.play();
    JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
    System.exit(ABORT);
  }

  public static void main(String[] args) throws InterruptedException, LineUnavailableException,
      UnsupportedAudioFileException, IOException {
    JFrame frame = new JFrame("Mini Tennis");


    Game game = new Game();
    Ball ball = new Ball(game, Ball.pokeball);

    game.balls.add(ball);

    frame.add(game);
    game.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(900, 1000);
    frame.setVisible(true);

    JFrame panel = new JFrame();
    panel.setSize(100, 100);
    panel.setVisible(true);
    JButton addBall = new JButton();

    addBall.setBounds(50, 50, 30, 30);
    addBall.setText("Add Ball");
    addBall.setVisible(true);

    addBall.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Ball newBall;
        try {
          newBall = new Ball(game, Ball.pokeball);
          game.balls.add(newBall);
          game.grabFocus();
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }

      }
    });
    panel.add(addBall);

    game.grabFocus();

    while (true) {
      game.move();
      game.paintImmediately(0, 0, 900, 1000);
      Thread.sleep((long) 7.5);
    }
  }
}
