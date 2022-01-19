package frontend.src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;
import java.util.*;

public class ChimpTest {
    private ActiveActionsScreen activeActionsScreen;
    private backend.Character character;
    private JFrame activeWindow = new JFrame("Chimp Test");

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
    private JButton[] buttonList;
    private int currentRound;
    private int currentNumber;
    private int strikes;

    //Round Complete
    private Timer timer;
    private JPanel roundResultPanel;
    private JLabel roundResultText;

    //Endscreen
    private JPanel endScreen;
    private JLabel endText;
    private JButton restartButton;

    /**
     * Constructor for the Chimp Test minigame.
     * Used when creating object from another class
     * @param actionsScreen the previous screen
     * @param character a character object that will get the rewards for completing the minigame
     */
    public ChimpTest(ActiveActionsScreen actionsScreen, backend.Character character) {
        this.activeActionsScreen = actionsScreen;
        this.character = character;
        currentRound = 1;
        strikes = 0;
        startChimpTest();
    }

    /**
     * Constructor for the Chimp Test minigame when started from this class, used for levelling intelligence.
     * Can be started with a custom amount of strikes and set the starting round.
     * @param actionsScreen the previous screen
     * @param character a charcter object that will get the rewards for completing the minigame
     * @param currRound an int specifying the starting round
     * @param strikes an int spcifying the amount of strikes the player has
     */
    public ChimpTest(ActiveActionsScreen actionsScreen, backend.Character character, int currRound, int strikes){
        this.activeActionsScreen = actionsScreen;
        this.character = character;
        this.strikes = strikes;
        currentRound = currRound;
        startChimpTest();
    }

    /**
    * Is used for initializiing all the visuals and buttons. All the game logic is included in the buttons
    */
    public void startChimpTest() {
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

        newRound();
    
        activeWindow.setLocation(x, y);
        activeWindow.setVisible(true);
    }

    /**
     * Used when the minigame should end. Shows gameover screen and restart button
     * Increases the players EVO points and intelligence based on performance
     */
    private void endGame() {
        //Calculate Evo point gain
        int evoGain = character.getAttributes().getLevel("total") * currentRound * 5;
        int xpGain = character.getAttributes().getLevel("intelligence") * currentRound * 3;
        endText.setFont(new Font("Serif", Font.PLAIN, 20));
        endText.setBorder(new EmptyBorder(75,0,75,0));
        endScreen.setVisible(true);
        gamePanel.setVisible(false);
        character.setEVOPoints(evoGain);
        character.getAttributes().setXp("intelligence", character.getAttributes().getLevel("intelligence") * currentRound); 
           
        if(character.getAttributes().getXp("intelligence") >= character.getAttributes().levelThreshold("intelligence")) {
            character.getAttributes().convertXpToLevels();
            endText.setText("<html>You gained " + evoGain + " EVO Points <br> You gained " + xpGain + 
            " Intelligence XP <br> Your Intelligence is now level " + character.getAttributes().getLevel("intelligence") + "</html>");
        } else {
            endText.setText("<html>You gained " + evoGain + " EVO Points <br> You gained " + xpGain + " Intelligence XP</html>");
        }
    }

    /**
     * Enables all buttons in a list of buttons and removes their text
     */
    private void closeAll() {
        for (JButton jButton : buttonList) {
            jButton.setEnabled(true);
            jButton.setText("");
        }
    }

    /**
     * Increases strike by 1 and if the player has 3 or more strikes it will end the game.
     * If not it will start a new round.
     */
    private void strike(){
        strikes += 1;
        roundResultPanel.setVisible(true);
        roundResultText.setText("Strike!");

        for (JButton jButton : buttonList) {
            jButton.setBackground(Color.pink);
            jButton.setEnabled(false);
        }

        timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                if(strikes >= 3) {
                    endGame();
                }
                else {
                    newRound();
                }
            }
        });
        timer.start();
    }

    /**
     * Initializes the back button
     */
    private void initBackbutton() {
        backPanel = new JPanel();
        backPanel.setBounds(5,0,100,50);
        backPanel.setBackground(backgroundColor);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100,50));
        backButton.setBackground(buttonColor);
        backButton.setBorder(buttonBorder);
        backButton.setFont(buttonFont);
        backButton.setOpaque(true);
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
                new ChimpTest(activeActionsScreen, character);
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
     * Initializes the result display
     */
    private void initResultDisplay() {
        roundResultPanel = new JPanel();
        roundResultPanel.setBounds(150, 150, 300, 50);
        roundResultPanel.setVisible(false);
        roundResultPanel.setBackground(backgroundColor);
        roundResultText = new JLabel("");
        roundResultText.setFont(new Font("Serif", Font.PLAIN, 20));
        roundResultPanel.add(roundResultText);
        
        activeWindow.getContentPane().add(roundResultPanel);
    }

    /**
     * Initializes the game display
     */
    private void initGameDisplay() {
        gamePanel = new JPanel();
        gamePanel.setBounds(175,200,250,400);
        gamePanel.setBackground(backgroundColor);

        timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                currentRound += 1;
                newRound();
            }
        });
      
        buttonList = new JButton[16];
        currentNumber = 1;
        for (int i = 0; i < 16; i++) {
            buttonList[i] = new JButton("");
            buttonList[i].setBackground(buttonColor);
            buttonList[i].setBorder(buttonBorder);
            buttonList[i].setFont(buttonFont);
            buttonList[i].setOpaque(true);
            buttonList[i].setPreferredSize(new Dimension(50,50));
            if(currentNumber <= currentRound+3) {
                buttonList[i].putClientProperty("number", currentNumber);
                buttonList[i].putClientProperty("numbered", true);
                buttonList[i].setText(currentNumber + "");
                buttonList[i].setOpaque(true);
                currentNumber += 1;
            }
            else {
                buttonList[i].putClientProperty("numbered", false);
            }
            buttonList[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(isNumbered((JButton)e.getSource())) {
                        if(isCurrentNumber(currentNumber, (JButton)e.getSource())) {
                            if(currentNumber == 1) closeAll();
                            ((JButton)e.getSource()).setText((int)((JButton)e.getSource()).getClientProperty("number")+"");
                            ((JButton)e.getSource()).setEnabled(false);
                            currentNumber += 1;

                            if(currentNumber > currentRound + 3) {
                                roundResultPanel.setVisible(true);
                                roundResultText.setText("Good Job!");
                                for (JButton jButton : buttonList) {
                                    jButton.setBackground(new Color(168, 255, 130));
                                    jButton.setEnabled(false);
                                }
                                timer.start();
                            }
                        } else {
                            strike();
                        }
                    } else {
                        strike();
                    }
                    updateInfo();
                }
            });
        }
        //Randomize location of the buttons
        Collections.shuffle(Arrays.asList(buttonList));
        
        //Add buttons to JPanel
        for (int i = 0; i < 16; i++) {
            gamePanel.add(buttonList[i]);
        }
        currentNumber = 1;
        activeWindow.getContentPane().add(gamePanel);
    }

    /**
     * Checks if a button is numbered
     * @param button the button
     * @return true if button is numbered
     */
    private boolean isNumbered(JButton button) {
        return (Boolean)button.getClientProperty("numbered") == true;
    }

    /**
     * Checks if buttons number is equal to the current number
     * @param currNumber the current number
     * @param button the button
     * @return true if buttons number is equal to the current number
     */
    private boolean isCurrentNumber(int currNumber, JButton button) {
        return (int)button.getClientProperty("number") == currNumber;
    }

    /**
     * Starts a new round
     */
    private void newRound(){
        //Remove any components in the window
        activeWindow.getContentPane().removeAll();

        initBackbutton();
        initEndscreen();
        initInfoDisplay();
        initResultDisplay();
        initGameDisplay();

        //Add JPanels to JFrame

        activeWindow.revalidate();
        activeWindow.repaint();
    }

    /**
     * Updates the info panel with the current information
     */
    private void updateInfo() {
        infoText.setText("<html>Current strikes: " + strikes + "/3<br/> Current round: " + currentRound + "</html>");
    }
}