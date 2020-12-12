import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class StartPanel extends JPanel implements MouseListener {

    private final int HEIGHT = 850;
    private final int WIDTH = 600;

    private Color magenta, cyan, blue;
    //clip used for music
    private AudioInputStream in = AudioSystem.getAudioInputStream(new File("derezzed_cut_16_mono_loop.wav"));
    private Clip clip = AudioSystem.getClip();

    private boolean blinkingText = false;
    private boolean blinkingText2 = true;
    private boolean beginGame = false;
    private boolean inTutorial = false;
    //detects whether the user has clicked
    private boolean hasClicked = false;
    private boolean hasClicked2 = false;

    //empty constructor
    public StartPanel() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

    }
    protected void paintComponent(Graphics g){

        magenta = new Color(255,0,191);
        cyan = new Color(24,255,255);
        blue = new Color(98,0,234);

        //background
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, HEIGHT, WIDTH);
        g.setColor(Color.BLUE);

        //tron logo
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("tronfinal-pixilart (cropped).png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(563, 132, Image.SCALE_SMOOTH);
        ImageIcon logo = new ImageIcon(dimg);
        logo.paintIcon(this, g, 130, 130);

        g.setColor(Color.WHITE);

        //creating the font to be used later
        Font retro = null;
        try {
            retro = Font.createFont(Font.TRUETYPE_FONT, new File("Retro Gaming.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(retro);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //start text
        g.setFont(retro);
        if(blinkingText) {
            g.setColor(blue);
            blinkingText=false;
        }
        else{
            g.setColor(Color.BLACK);
            blinkingText=true;
        }
        g.drawString("CLICK TO START", 295, 400); //x: 306 orig
        g.setColor(magenta);
        g.drawString("© 2020 Lauren and Spencer Jackson", 135, 550);

        //only runs once user has clicked
        if(hasClicked){

            g.setColor(Color.BLACK);
            g.fillRect(0,0,HEIGHT, WIDTH);

            g.setColor(Color.WHITE);
            g.drawString("CONTROLS",348,110);
            g.setColor(blue);
            g.drawString("PLAYER 1",180,225);
            g.setColor(magenta);
            g.drawString("PLAYER 2",525,225);
            BufferedImage controlimg = null;
            try {
                controlimg = ImageIO.read(new File("control1-fixed.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Image controldimg = controlimg.getScaledInstance(926, 191, Image.SCALE_SMOOTH);
            ImageIcon controls = new ImageIcon(controldimg);
            controls.paintIcon(this, g, -45, 225);

            ImageIcon blueBike = new ImageIcon("player1cycle1.png");
            blueBike.paintIcon(this,g,230,120);
            ImageIcon pinkBike = new ImageIcon("player2cycle1.png");
            pinkBike.paintIcon(this,g,580,120);

            if(blinkingText2) {
                g.setColor(Color.YELLOW);
                blinkingText2=false;
            }
            else{
                g.setColor(Color.BLACK);
                blinkingText2=true;
            }
            g.drawString("CLICK TO BEGIN GAME",250,460);

        }
        if(beginGame){

            g.setColor(Color.BLACK);
            g.fillRect(0,0,HEIGHT, WIDTH);
        }

    }

    //runs at start until user clicks
    // constantly repaints panel to create blinking button animation
    public void startRun(){

        while(!hasClicked){

            repaint();
            try{
                Thread.sleep(650);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        removeAll();
        repaint();
    }

    public void tutorialScreen(){

        while(!hasClicked2){

            repaint();
            try {
                Thread.sleep(650);
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        removeAll();
        beginGame = true;
        repaint();

    }

    //starts music
    public void startMusic() {

        try {
            clip.open(in);
            clip.loop(100);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //stops music
    public void stopMusic(){

        clip.stop();
    }

    //methods from MouseEvent interface, only one used is mouseClicked
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        //once the user clicks, this boolean becomes true
        hasClicked = true;
        if(inTutorial){
            hasClicked2 = true;
        }
    }
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
    public void setInTutorial(boolean b){

        this.inTutorial = b;
    }
}


