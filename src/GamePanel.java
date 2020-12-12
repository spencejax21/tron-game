import acm.graphics.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static acm.util.JTFTools.pause;

public class GamePanel extends GCanvas{

    private final int HEIGHT = 600;
    private final int WIDTH = 850;
    private double player1X;
    private double player1Y;
    private double player2X;
    private double player2Y;
    private GImage player1;
    private GImage player2;
    private int player1Direction = 1; //1 is up, 2 is right, 3 is down 4 is left
    private int player2Direction = 1;
    private Color magenta, cyan, blue, pinktrail;
    private int gameState; //1 = player 1 won, 2 = player 2 won, 3 = tie
    private AudioInputStream in = AudioSystem.getAudioInputStream(new File("8bitexplosion.wav"));
    private Clip clip = AudioSystem.getClip();
    private boolean keysActive = false;
    private volatile boolean playGame = false;

    public GamePanel() throws IOException, UnsupportedAudioFileException, LineUnavailableException {

    }

    public void run() throws IOException, LineUnavailableException, AWTException {

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

        setup();
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(keysActive) {
                    if (e.getKeyCode() == KeyEvent.VK_A) {

                        if (player1Direction == 1) {
                            player1Direction = 4;
                            player1.setLocation(player1.getX() - player1.getHeight() + 12, player1.getY() + player1.getWidth() + 8);
                        } else if (player1Direction == 2) {
                            player1Direction--;
                            player1.setLocation(player1.getX() - 5, player1.getY() - player1.getHeight() - 6);
                        } else if (player1Direction == 3) {
                            GRect rect1 = new GRect(player1.getX() + 6, player1.getY() + 1, 5, 6);
                            rect1.setFillColor(blue);
                            rect1.setColor(blue);
                            rect1.setFilled(true);
                            add(rect1);
                            player1Direction--;
                            player1.setLocation(player1.getX() + 4, player1.getY() + 1);
                        } else if (player1Direction == 4) {
                            player1Direction--;
                            player1.setLocation(player1.getX() + player1.getWidth() - 6, player1.getY() + 5);
                        }

                    }
                    if (e.getKeyCode() == KeyEvent.VK_D) {

                        if (player1Direction == 1) {
                            player1Direction++;
                            player1.setLocation(player1.getX() + 6, player1.getY() + player1.getWidth() + 5);
                        } else if (player1Direction == 2) {
                            GRect rect1 = new GRect(player1.getX() +2, player1.getY() + 7, 6, 5);
                            rect1.setFillColor(blue);
                            rect1.setColor(blue);
                            rect1.setFilled(true);
                            add(rect1);
                            player1Direction++;
                            player1.setLocation(player1.getX() + 2, player1.getY() + 6);
                        } else if (player1Direction == 3) {
                            player1Direction++;
                            player1.setLocation(player1.getX() - player1.getWidth() - 7, player1.getY() - 4);
                        } else if (player1Direction == 4) {
                            player1Direction = 1;
                            player1.setLocation(player1.getX() + 24, player1.getY() - 25);
                        }

                    }
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {

                        if (player2Direction == 1) {
                            player2Direction = 4;
                            player2.setLocation(player2.getX() - player2.getHeight() + 11, player2.getY() + player2.getWidth() + 8);
                        } else if (player2Direction == 2) {
                            player2Direction--;
                            player2.setLocation(player2.getX() - 5, player2.getY() - player2.getHeight() - 7);
                        } else if (player2Direction == 3) {
                            GRect rect2 = new GRect(player2.getX() + 6, player2.getY() + 1, 5, 6);
                            rect2.setFillColor(pinktrail);
                            rect2.setColor(pinktrail);
                            rect2.setFilled(true);
                            add(rect2);
                            player2Direction--;
                            player2.setLocation(player2.getX() + 4, player2.getY() + 1);

                        } else if (player2Direction == 4) {
                            player2Direction--;
                            player2.setLocation(player2.getX() + player2.getWidth() - 6, player2.getY() + 5);
                        }
                    }
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                        if (player2Direction == 1) {
                            player2Direction++;
                            player2.setLocation(player2.getX() + 5, player2.getY() + player2.getWidth() + 7);
                        } else if (player2Direction == 2) {
                            GRect rect2 = new GRect(player2.getX() +2, player2.getY() + 6, 6, 5);
                            rect2.setFillColor(pinktrail);
                            rect2.setColor(pinktrail);
                            rect2.setFilled(true);
                            add(rect2);
                            player2Direction++;
                            player2.setLocation(player2.getX() + 2, player2.getY() + 5);
                        } else if (player2Direction == 3) {
                            player2Direction++;
                            player2.setLocation(player2.getX() - player2.getWidth() - 7, player2.getY() - 4);
                        } else if (player2Direction == 4) {
                            player2Direction = 1;
                            player2.setLocation(player2.getX() + 25, player2.getY() - 25);
                        }
                    }
                }
            }
        });
        /*GLabel begin = new GLabel("CLICK TO BEGIN", 300,175);
        begin.setFont(retro);
        begin.setColor(Color.WHITE);
        begin.setVisible(true);
        add(begin);*/
        GLabel begin = new GLabel("CLICK TO BEGIN", 320,175);
        begin.setFont(retro);
        begin.setColor(Color.WHITE);
        begin.setVisible(true);
        add(begin);

        addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                playGame = true;
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
        });

        while(!playGame){


        }

        remove(begin);
        play();


    }

    public void setup() throws AWTException {

        magenta = new Color(255,0,191);
        cyan = new Color(24,255,255);
        blue = new Color(98,0,234);
        pinktrail = new Color(250,150,225);

        setVisible(true);
        setBackground(Color.BLACK);

        GRect topBorder = new GRect(WIDTH,10);
        topBorder.setFillColor(cyan);
        topBorder.setFilled(true);
        topBorder.setColor(cyan);
        add(topBorder,0,0);

        GRect leftBorder = new GRect(10,HEIGHT);
        leftBorder.setFillColor(cyan);
        leftBorder.setFilled(true);
        leftBorder.setColor(cyan);
        add(leftBorder,0,0);

        GRect botBorder = new GRect(WIDTH,10);
        botBorder.setFillColor(cyan);
        botBorder.setFilled(true);
        botBorder.setColor(cyan);
        add(botBorder,0,HEIGHT-40);

        GRect rightBorder = new GRect(10,HEIGHT);
        rightBorder.setFillColor(cyan);
        rightBorder.setFilled(true);
        rightBorder.setColor(cyan);
        add(rightBorder,WIDTH-15,0);

        player1 = new GImage("player1cycle1.png",300,250);
        player1X = 300;
        player1Y = 250;
        player1.scale(.75);
        add(player1);

        player2 = new GImage("player2cycle1.png",WIDTH-300,250);
        player2X = WIDTH-300;
        player2Y = 250;
        player2.scale(.75);
        add(player2);


    }
    public void play() throws IOException, LineUnavailableException {

//        GRect trail1 = new GRect(7,0);
//        trail1.setFillColor(blue);
//        trail1.setFilled(true);
//        GRect trail2 = new GRect(7,0);
//        trail2.setFillColor(pinktrail);
//        trail2.setFilled(true);
//        add(trail1,player1.getX()+6,player1.getY()+player1.getHeight());
//        add(trail2,player2.getX()+5,player2.getY()+player2.getHeight());

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

        GLabel countdown = new GLabel("3",430,175);
        countdown.setFont(retro);
        countdown.setColor(Color.WHITE);
        countdown.setVisible(true);
        add(countdown);
        pause(1000);
        countdown.setLabel("2");
        pause(1000);
        countdown.setLabel("1");
        pause(1000);
        remove(countdown);

        keysActive = true;
        while(!isColliding()){
            GRect trail1 = new GRect(5,1.25);
            trail1.setFillColor(blue);
            trail1.setFilled(true);
            trail1.setColor(blue);

            GRect trail2 = new GRect(5,1.25);
            trail2.setFillColor(pinktrail);
            trail2.setFilled(true);
            trail2.setColor(pinktrail);

            if(player1Direction==1) {
                player1.setImage("player1cycle1.png");
                player1.scale(.75);
                player1.move(0, -1.25);
                player1X = player1.getX();
                player1Y = player1.getY();
                add(trail1,player1.getX()+7,player1.getY()+player1.getHeight());

            }
            if(player2Direction==1) {
                player2.setImage("player2cycle1.png");
                player2.scale(.75);
                player2.move(0, -1.25);
                player2X = player2.getX();
                player2Y = player2.getY();
                add(trail2,player2.getX()+6,player2.getY()+player2.getHeight());
            }
            if(player1Direction==2) {
                player1.setImage("player1cycle2.png");
                player1.scale(.75);
                player1.move(1.25, 0);
                player1X = player1.getX();
                player1Y = player1.getY();
                trail1.setSize(1.25,5);
                add(trail1,player1.getX(),player1.getY()+7);
            }
            if(player2Direction==2) {
                player2.setImage("player2cycle2.png");
                player2.scale(.75);
                player2.move(1.25, 0);
                player2X = player2.getX();
                player2X = player2.getX();
                trail2.setSize(1.25 ,5);
                add(trail2,player2.getX(),player2.getY()+6);
            }
            if(player1Direction==3) {
                player1.setImage("player1cycle3.png");
                player1.scale(.75);
                player1.move(0, 1.25);
                player1X = player1.getX();
                player1Y = player1.getY();
                add(trail1,player1.getX()+6,player1.getY());
            }
            if(player2Direction==3) {
                player2.setImage("player2cycle3.png");
                player2.scale(.75);
                player2.move(0, 1.25);
                player2X = player2.getX();
                player2Y = player2.getY();
                add(trail2,player2.getX()+6,player2.getY());
            }
            if(player1Direction==4) {
                player1.setImage("player1cycle4.png");
                player1.scale(.75);
                player1.move(-1.25, 0);
                player1X = player1.getX();
                player1Y = player1.getY();
                trail1.setSize(1.25,5);
                add(trail1,player1.getX()+player1.getWidth(),player1.getY()+6);
            }
            if(player2Direction==4) {
                player2.setImage("player2cycle4.png");
                player2.scale(.75);
                player2.move(-1.25, 0);
                player2X = player2.getX();
                player2Y = player2.getY();
                trail2.setSize(1.25,5);
                add(trail2, player2.getX()+player2.getWidth(), player2.getY()+6);
            }

            pause(5);
        }
        keysActive = false;
        clip.open(in);
        clip.start();
        GLabel endState = new GLabel("",320,175);
        endState.setFont(retro);
        endState.setColor(Color.WHITE);
        endState.setVisible(true);
        if(gameState == 1)
            endState.setLabel("PLAYER 1 WINS");
        else if(gameState == 2)
            endState.setLabel("PLAYER 2 WINS");
        else {
            endState.setLabel("DRAW");
            endState.setLocation(383,175);
        }
        add(endState);

        GLabel restart = new GLabel("RESTARTING...", 325,225);
        restart.setFont(retro);
        restart.setColor(Color.WHITE);
        restart.setVisible(true);
        add(restart);

        pause(1000);
        if(gameState == 1)
            remove(player2);
        else if(gameState == 2)
            remove(player1);
        else {
            remove(player1);
            remove(player2);
        }
        pause(2000);
    }
    public boolean isColliding(){

        boolean player1Died = false;
        boolean player2Died = false;

        if (player1Direction==1 && getElementAt(player1X, player1Y-1) != null && getElementAt(player1X + player1.getWidth(), player1Y-1) != null) {
            player1Died = true;
        }
        if (player2Direction==1 && getElementAt(player2X,player2Y - 1) != null && getElementAt(player2X + player2.getWidth(),player2Y - 1) != null) {
            player2Died = true;
        }
        if (player1Direction==2 && getElementAt(player1X+player1.getWidth()+1, player1Y) != null && getElementAt(player1X+player1.getWidth()+1, player1Y + player1.getHeight()) != null) {
            player1Died = true;
        }
        if (player2Direction==2 && getElementAt(player2X+player2.getWidth()+1,player2Y) != null && getElementAt(player2X+player2.getWidth()+1,player2Y + player2.getHeight()) != null) {
            player2Died = true;
        }
        if (player1Direction==3 && getElementAt(player1X,player1Y+player1.getHeight()+1) != null && getElementAt(player1X+player1.getWidth(),player1Y+player1.getHeight()+1) != null) {
            player1Died = true;
        }
        if (player2Direction==3 && getElementAt(player2X,player2Y+player2.getHeight()+1) != null && getElementAt(player2X+player2.getWidth(),player2Y+player2.getHeight()+1) != null) {
            player2Died = true;
        }
        if (player1Direction==4 && getElementAt(player1X-1,player1Y) != null && getElementAt(player1X-1,player1Y+player1.getHeight()) != null) {
            player1Died = true;
        }
        if (player2Direction==4 && getElementAt(player2X-1,player2Y) != null && getElementAt(player2X-1,player2Y+player2.getHeight()) != null) {
            player2Died = true;
        }
        remove(player1);
        if(getElementAt(player1X + (player1.getWidth())/2, player1Y +(player1.getHeight())/2) != null){
            player1Died = true;
        }
        add(player1);
        remove(player2);
        if(getElementAt(player2X + (player2.getWidth())/2, player2Y +(player2.getHeight())/2) != null){
            player2Died = true;
        }
        add(player2);

        if(player1Died && player2Died) {
            gameState = 3;
            player1.setImage("explosion.png");
            player2.setImage("explosion.png");
            return true;
        }
        else if(player1Died) {
            gameState = 2;
            player1.setImage("explosion.png");
            return true;
        }
        else if(player2Died) {
            gameState = 1;
            player2.setImage("explosion.png");
            return true;
        }
        return false;
    }

}