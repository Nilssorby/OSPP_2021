package frontend.src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;

public class Memory {
    private ActiveActionsScreen activeActionsScreen;
    private backend.Character character;
    private JFrame activeWindow = new JFrame("Memory");

    //Background
    private Color backgroundColor = new Color(0x7678ed);

    //Button variables
    private Color buttonColor = new Color(0xffb703);
    private Border buttonBorder = new LineBorder(new Color(0xffcb69), 2);
    private Font buttonFont = new Font("Serif", Font.BOLD | Font.ITALIC, 20);

    //Back Button
    private JPanel backPanel;
    private JButton backButton;

    //Information display
    private JLabel infoText;
    private JPanel infoDisplay;

    //Game Variables
    private JPanel gamePanel;
    private JButton[] memoryButtonList;
    private int guesses = 0;
    private JButton[] chosenButtons;
    private int found;
    private int tries;
    private javax.swing.Timer timer;

    //Endscreen
    private JPanel endScreen;
    private JLabel endText;
    private JButton restartButton;

    /**
     * Constructor for memory minigame.
     * @param actionsScreen the previous screen
     * @param character a character object that will get the rewards for completing the minigame
     */
    public Memory(ActiveActionsScreen actionsScreen, backend.Character character) {
        this.activeActionsScreen = actionsScreen;
        this.character = character;
        startMemory();
    }

    private void initBackbutton() {
        backPanel = new JPanel();
        backPanel.setBounds(5,0,100,50);
        backPanel.setBackground(backgroundColor);

        backButton = new JButton("Back");
        backButton.setBackground(buttonColor);
        backButton.setBorder(buttonBorder);
        backButton.setFont(buttonFont);
        backButton.setOpaque(true);
        backButton.setPreferredSize(new Dimension(100,50));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeWindow.dispose();
                activeActionsScreen.setFrameVisible();
            }
        });
        backPanel.add(backButton);

        activeWindow.getContentPane().add(backPanel);
    }

    private void initEndscreen() {
        endScreen = new JPanel();
        endScreen.setBounds(activeActionsScreen.getWindowWidth()/2-150, 150, 300, 300);
        endScreen.setBackground(Color.orange);
        endScreen.setVisible(false);

        endText = new JLabel();
        endScreen.add(endText);

        restartButton = new JButton("Play Again");
        restartButton.setPreferredSize(new Dimension(100,50));
        restartButton.setBackground(buttonColor);
        restartButton.setOpaque(true);
        restartButton.setBorder(buttonBorder);
        restartButton.setFont(buttonFont);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeWindow.dispose();
                new Memory(activeActionsScreen, character);
            }
        });
        endScreen.add(restartButton);
        
        activeWindow.getContentPane().add(endScreen);
    }

    private void initInfoDisplay() {
        infoDisplay = new JPanel();
        infoDisplay.setBounds(activeActionsScreen.getWindowWidth()-150,10,120,50);
        infoDisplay.setBackground(backgroundColor);
        infoText = new JLabel();
        updateInfo();
        infoDisplay.add(infoText);

        activeWindow.getContentPane().add(infoDisplay);
    }

    private void initGameDisplay() {
        gamePanel = new JPanel();
        gamePanel.setBounds(175,200,250,400);
        gamePanel.setBackground(backgroundColor);
        tries = 0;
        found = 0;
        memoryButtonList = new JButton[16];
        chosenButtons = new JButton[2];

        timer = new javax.swing.Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAll();
                timer.stop();
            }
        });

        for (int i = 0; i < 16; i++) {
            memoryButtonList[i] = new JButton("?");
            memoryButtonList[i].setBackground(buttonColor);
            memoryButtonList[i].setBorder(buttonBorder);
            memoryButtonList[i].setFont(buttonFont);
            memoryButtonList[i].setOpaque(true);
            memoryButtonList[i].setPreferredSize(new Dimension(50,50));
            memoryButtonList[i].putClientProperty("match", i/2);
            memoryButtonList[i].putClientProperty("found", false);
            memoryButtonList[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timer.stop();
                    if(guesses == 2) {
                        closeAll();
                        guesses = 0;
                    }
                    ((JButton)e.getSource()).setText((int)((JButton)e.getSource()).getClientProperty("match")+"");
                    ((JButton)e.getSource()).setEnabled(false);
                    guesses += 1;
                    chosenButtons[guesses-1] = (JButton)e.getSource();
                    if(guesses == 2) {
                        tries += 1;
                        if(chosenButtons[0].getClientProperty("match") == chosenButtons[1].getClientProperty("match")) {
                            chosenButtons[0].putClientProperty("found", true);
                            chosenButtons[1].putClientProperty("found", true);
                            
                            chosenButtons[0].setBackground(new Color(168, 255, 130));
                            chosenButtons[1].setBackground(new Color(168, 255, 130));
                            chosenButtons[0].setOpaque(true);
                            chosenButtons[1].setOpaque(true);
                            found += 1;
                            if(found == 8)
                            {
                                endGame();
                            }
                        }
                        timer.start();
                    }
                    updateInfo();
                }
            });
        }

        //Randomize location of the buttons
        Collections.shuffle(Arrays.asList(memoryButtonList));
        
        //Add buttons to JPanel
        for (int i = 0; i < 16; i++) {
                gamePanel.add(memoryButtonList[i]);
        }
        
        activeWindow.getContentPane().add(gamePanel);
    }


    /**
    * Is used for initializiing all the visuals and buttons. All the game logic is included in the buttons
    */
    public void startMemory() {
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

        initBackbutton();
        initEndscreen();
        initInfoDisplay();
        initGameDisplay();

        activeWindow.setLocation(x, y);
        activeWindow.setVisible(true);
    }

    /**
     * Used when the minigame should end. Shows gameover screen and restart button.
     * Increases the players EVO points  and intelligence based on performance
     */
    private void endGame() {
        //Calculate Evo point gain
        int evoGain = character.getAttributes().getLevel("total") * 30 - (tries / 4) * character.getAttributes().getLevel("total");
        int xpGain = character.getAttributes().getLevel("intelligence") * 10;
        endText.setFont(new Font("Serif", Font.PLAIN, 20));
        endText.setBorder(new EmptyBorder(75,0,75,0));
        endScreen.setVisible(true);
        gamePanel.setVisible(false);
        if(evoGain <= 0) {
            character.setEVOPoints(0);
            endText.setText("<html>You gained " + 0 + " EVO Points <br> You gained " + 0 + " Intelligence XP</html>");
        }
        else {
            character.setEVOPoints(evoGain);
            character.getAttributes().setXp("intelligence", xpGain);
            if(character.getAttributes().getXp("intelligence") >= character.getAttributes().levelThreshold("intelligence")) {
                character.getAttributes().convertXpToLevels();
                endText.setText("<html>You gained " + evoGain + " EVO Points <br> You gained " + xpGain + 
                " Intelligence XP <br> Your Intelligence is now level " + character.getAttributes().getLevel("intelligence") + "</html>");
            } else {
                endText.setText("<html>You gained " + evoGain + " EVO Points <br> You gained " + xpGain + " Intelligence XP</html>");
            }
        }
    }

    /**
     * Enables all buttons in a list of buttons and removes their text
     */
    private void closeAll() {
        for (JButton jButton : memoryButtonList) {
            if ((Boolean)jButton.getClientProperty("found") == false)
            {
                jButton.setEnabled(true);
                jButton.setText("?");
            }
        }
    }

    /**
     * Updates the info panel with the current information
     */
    private void updateInfo() {
        infoText.setText("<html>Current tries: " + tries + "<br/> Current found: " + found + "/8</html>");
    }
}