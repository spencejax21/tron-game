import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class TronGame extends JPanel{

    private static final int HEIGHT = 850;
    private static final int WIDTH = 600;
    public static JFrame frame;

    public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException, AWTException {

        TronGame tron = new TronGame();
        frame = new JFrame("TRON");
        ImageIcon img = new ImageIcon("tronwithblacksquare.png");
        frame.setIconImage(img.getImage());
        frame.add(tron);

        frame.setSize(HEIGHT, WIDTH);
        frame.setResizable(false);
        frame.setLocation(200,50);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        StartPanel panel = new StartPanel();
        frame.addMouseListener(panel);
        frame.add(panel);
        frame.setVisible(true);

        panel.startMusic();
        panel.startRun();
        panel.setInTutorial(true);
        panel.tutorialScreen();
        panel.stopMusic();

        while(true) {

            frame.getContentPane().removeAll();
            GamePanel game = new GamePanel();
            frame.add(game);
            frame.repaint();
            frame.revalidate();
            game.run();

        }
    }
    public static Point getFrameLocation(){

        return frame.getLocation();
    }
}
