package frontend.src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

public class Clicker {
    private ActiveActionsScreen activeActionsScreen;
    private backend.Character character;
    private JFrame activeWindow = new JFrame("Strength Training");

    //Background
    private Color backgroundColor = new Color(0x7678ed);

    //Button variables
    private Color buttonColor = new Color(0xffb703);
    private Border buttonBorder = new LineBorder(new Color(0xffcb69), 2);
    private Font buttonFont = new Font("Serif", Font.BOLD | Font.ITALIC, 20);
    private Border clickerBorder = new LineBorder(backgroundColor);
    //Back Button
    private JPanel backPanel;
    private JButton backButton;

    //Information display
    private JLabel infoText;
    private JPanel infoDisplay;

    //Start screen
    private JPanel startPanel;
    private JButton startButton;
    private JLabel startText;
    private Timer startCountdown;
    private int countdown;

    //Game Variables
    private JPanel gamePanel;
    private JButton clickButton;
    private int clicks;
    private Timer gameDuration;
    private int duration;
    private Image dummy;

    //Endscreen
    private JPanel endScreen;
    private JLabel endText;
    private JButton restartButton;
    private Timer restartButtonTimer;

    /**
     * Constructor for the Chimp Test minigame.
     * Used when creating object from another class
     * @param actionsScreen the previous screen
     * @param character a character object that will get the rewards for completing the minigame
     */
    public Clicker(ActiveActionsScreen actionsScreen, backend.Character character) {
        this.activeActionsScreen = actionsScreen;
        this.character = character;
        startClicker();
    }

    /**
    * Starts the game
    */
    public void startClicker() {
        activeActionsScreen.getWindow().setVisible(false);
        activeWindow.setSize(activeActionsScreen.getWindowWidth(), activeActionsScreen.getWindowHeight());
        activeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //needed for .setBounds with JPanel to work properly
        activeWindow.setLayout(null);

        //set background
        activeWindow.getContentPane().setBackground(backgroundColor);

        //center window of screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - activeActionsScreen.getWindowWidth()) / 2);
        int y = (int) ((dimension.getHeight() - activeActionsScreen.getWindowHeight()) / 2);

        //Remove any components in the window
        activeWindow.getContentPane().removeAll();

        initBackbutton();
        initEndscreen();
        initInfoDisplay();
        initStartDisplay();
        initGameDisplay();

        activeWindow.setLocation(x, y);
        activeWindow.setVisible(true);
    }

    /**
     * Used when the minigame should end. Shows gameover screen and restart button
     */
    private void endGame() {
        //Calculate Evo point gain
        int evoGain = character.getAttributes().getLevel("total") * clicks / 2;
        int xpGain = character.getAttributes().getLevel("strength") * clicks / 5;
        character.getAttributes().setXp("strength", xpGain);
        if(character.getAttributes().getXp("strength") >= character.getAttributes().levelThreshold("strength")) {
            character.getAttributes().convertXpToLevels();
            endText.setText("<html>You gained " + evoGain + " EVO Points <br> You gained " + xpGain + 
            " Strength XP <br> Your strength is now level " + character.getAttributes().getLevel("strength") + "</html>");
        } else {
            endText.setText("<html>You gained " + evoGain + " EVO Points <br> You gained " + xpGain + " strength XP</html>");
        }
        endText.setFont(new Font("Serif", Font.PLAIN, 20));
        endText.setBorder(new EmptyBorder(75,0,75,0));
        restartButton.setVisible(false);
        restartButtonTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartButton.setVisible(true);
                restartButtonTimer.stop();
            }
        });
        restartButtonTimer.start();
        endScreen.setVisible(true);
        gamePanel.setVisible(false);;
    }

    /**
     * Initializes the back button
     */
    private void initBackbutton() {
        backPanel = new JPanel();
        backPanel.setBounds(5,0,100,50);
        backPanel.setBackground(backgroundColor);

        //The actual button
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100,50));
        backButton.setBackground(buttonColor);
        backButton.setBorder(buttonBorder);
        backButton.setFont(buttonFont);
        backButton.setOpaque(true);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (String i : character.getAttributes().showAllLevels()) {
                    System.out.println(i);
                }
                activeWindow.dispose();
                activeActionsScreen.setFrameVisible();
            }
        });
        backPanel.add(backButton);

        activeWindow.getContentPane().add(backPanel);
    }

    /**
     * Initializes the endscreen
     */
    private void initEndscreen() {
        endScreen = new JPanel();
        endScreen.setBounds(activeActionsScreen.getWindowWidth()/2-150, 150, 300, 300);
        endScreen.setBackground(Color.pink);
        endScreen.setVisible(false);

        endText = new JLabel();
        endScreen.add(endText);

        restartButton = new JButton("Play Again");
        restartButton.setPreferredSize(new Dimension(100,50));
        restartButton.setBackground(buttonColor);
        restartButton.setBorder(buttonBorder);
        restartButton.setFont(buttonFont);
        restartButton.setOpaque(true);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeWindow.dispose();
                new Clicker(activeActionsScreen, character);
            }
        });
        endScreen.add(restartButton);
        
        activeWindow.getContentPane().add(endScreen);
    }

    /**
     * Initializes the info display
     */
    private void initInfoDisplay() {
        infoDisplay = new JPanel();
        infoDisplay.setBounds(activeActionsScreen.getWindowWidth()-150,10,120,50);
        infoDisplay.setBackground(backgroundColor);
        infoText = new JLabel();
        updateInfo();
        infoDisplay.add(infoText);
        
        activeWindow.getContentPane().add(infoDisplay);
    }

    /**
     * Initializes the start screen
     */
    private void initStartDisplay() {
        startPanel = new JPanel();
        startPanel.setBounds(175,200,250,400);
        startPanel.setBackground(backgroundColor);
        startText = new JLabel();
        startText.setText("Hit the dummy!");
        startText.setFont(new Font("Serif", Font.PLAIN, 20));
        startText.setBorder(new EmptyBorder(75,0,50,0));
        startButton = new JButton("Press when ready!");
        startButton.setBackground(buttonColor);
        startButton.setBorder(buttonBorder);
        startButton.setFont(buttonFont);
        startButton.setOpaque(true);
        startButton.setPreferredSize(new Dimension(200,100));
        countdown = 3;
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);
                startButton.setText(countdown + "");
                startCountdown = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(countdown <= 0) {
                            startCountdown.stop();
                            gamePanel.setVisible(true);
                            startPanel.setVisible(false);
                            gameDuration.start();
                        } else {
                            countdown -= 1;
                            startButton.setText(countdown + "");
                        }
                    }
                });
                startCountdown.start();
            }
        });
        startPanel.add(startText);
        startPanel.add(startButton);
        
        activeWindow.getContentPane().add(startPanel);
    }

    /**
     * Initializes the game display
     */
    private void initGameDisplay() {
        gamePanel = new JPanel();
        gamePanel.setBounds(175,200,250,400);
        gamePanel.setBackground(backgroundColor);
        clickButton = new JButton();
        clickButton.setPreferredSize(new Dimension(400,300));
        clickButton.setOpaque(true);
        clickButton.setBorder(clickerBorder);
        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clicks += 1;
                updateInfo();
            }
        });
        clickButton.setBackground(null);
        //clickButton.setBorder(null);
        clickButton.setFocusPainted(false);

        try {
            dummy = ImageIO.read(getClass().getResource("images/Training_Dummy.png"));
            clickButton.setIcon(new ImageIcon(dummy));
        } catch (Exception e) {
            System.out.println(e);
        }

        gamePanel.add(clickButton);
        gamePanel.setVisible(false);
        duration = 10;
        gameDuration = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                duration -= 1;
                if(duration <= 0) {
                    gameDuration.stop();
                    endGame();
                }
            }
        });

        activeWindow.getContentPane().add(gamePanel);
    }

    /**
     * Updates the info panel with the current information
     */
    private void updateInfo() {
        //infoText.setText("<html> " + clicks + " </html>");
    }
}