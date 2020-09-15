package todd.tres.Sound;


import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import todd.tres.GUI.Game;

@SuppressWarnings("serial")
public class Sound extends JFrame {

  // Instance Variables
  private Clip clip;
  @SuppressWarnings("unused")
  private Game game;
  public static final String back =
      "C:/Users/tat20/eclipse-workspace/SimpleGame/src/todd/tres/Sound/back.wav";
  public static final String ballSound =
      "C:/Users/tat20/eclipse-workspace/SimpleGame/src/todd/tres/Sound/ball.wav";
  public static final String gameover =
      "C:/Users/tat20/eclipse-workspace/SimpleGame/src/todd/tres/Sound/gameover.wav";

  // Constructor
  public Sound(String filePath)
      throws UnsupportedAudioFileException, LineUnavailableException, IOException {
    try {
      File file = new File(filePath);
      AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
      clip = AudioSystem.getClip();
      AudioFormat format = audioIn.getFormat();
      DataLine.Info info = new DataLine.Info(Clip.class, format);
      if (!AudioSystem.isLineSupported(info)) {
        JOptionPane.showMessageDialog(null, "Unsupported Audio File Exception",
            "Unsupported Audio File Exception", JOptionPane.ERROR_MESSAGE);
      }
      try {
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioIn);
        if (filePath == ballSound) {
          FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
          gainControl.setValue(-15);
        }
      } catch (LineUnavailableException ex) {
        JOptionPane.showMessageDialog(null, "Line Unavailable Exception",
            "Line Unavailable Exception", JOptionPane.ERROR_MESSAGE);
      }
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(null, "IOException", "IOException", JOptionPane.ERROR_MESSAGE);
    }
  }

  // Methods
  public void play() {
    clip.setFramePosition(0);
    clip.loop(0);
    clip.start();
  }

  public void loop() {
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void stop() {
    clip.stop();
  }
}
