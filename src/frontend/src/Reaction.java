package frontend.src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import javax.swing.border.*;

public class Reaction {
    private ActiveActionsScreen activeActionsScreen;
    private JFrame activeWindow = new JFrame("Reaction");

    //Background
    private Color backgroundColor = new Color(0x7678ed);

    //Button variables
    private Color buttonColor = new Color(0xffb703);
    private Border buttonBorder = new LineBorder(new Color(0xffcb69), 2);
    private Font buttonFont = new Font("Serif", Font.BOLD | Font.ITALIC, 20);
    
    //back button
    private JPanel backPanel;
    private JButton backButton;

    private JPanel gamePanel;
    private JButton startButton;
    private JButton reactButton;
    private JButton waitButton;
    private backend.Character character;

    //Information display
    private JLabel infoText;
    private JPanel infoDisplay;
    //private JLabel bestTime;

    //Endscreen
    private JPanel endScreen;
    private JLabel endText;
    private JButton restartButton;

    //Game variables
    private long startTime, stopTime, fastest, latest;
    private int tries;
    private javax.swing.Timer timer;
    private boolean state = true;
    private int EVOpoints = 0, dexterityXP = 0;

    public Reaction(ActiveActionsScreen actionsScreen, backend.Character character) {
        this.activeActionsScreen = actionsScreen;
        this.character = character;
        startReaction();
    }

    public void startReaction() {
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

        //back button
        backPanel = new JPanel();
        backPanel.setBounds(5,0,100,50);
        backPanel.setBackground(backgroundColor);

        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100,50));
        backButton.setBackground(buttonColor);
        backButton.setBorder(buttonBorder);
        backButton.setOpaque(true);
        backButton.setFont(buttonFont);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeWindow.setVisible(false);
                activeActionsScreen.setFrameVisible();
                character.setEVOPoints(EVOpoints);
                character.getAttributes().setXp("dexterity", dexterityXP);
                character.getAttributes().convertXpToLevels();
            }
        });
        backPanel.add(backButton);

        //end screen
        endScreen = new JPanel();
        endScreen.setBounds(activeActionsScreen.getWindowWidth()/2-150, 150, 300, 300);
        endScreen.setBackground(Color.orange);
        endScreen.setVisible(false);

        endText = new JLabel();
        endScreen.add(endText);

        restartButton = new JButton("Play Again");
        restartButton.setPreferredSize(new Dimension(100,50));
        restartButton.setOpaque(true);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeWindow.setVisible(false);
                new Memory(activeActionsScreen, character);
            }
        });
        endScreen.add(restartButton);

        //Info display
        infoDisplay = new JPanel();
        infoDisplay.setBounds(150,10,250,100);
        infoDisplay.setBackground(backgroundColor);
        infoText = new JLabel();
        //bestTime = new JLabel();
        updateInfo();
        infoDisplay.add(infoText);
        //infoDisplay.add(bestTime);

        //Reaction logic
        gamePanel = new JPanel();
        gamePanel.setBounds(0,100,600,525);
        gamePanel.setOpaque(true);
        gamePanel.setBackground(backgroundColor);
        tries = 0;
        fastest = 0;
        startButton = new JButton("Click to start");
        startButton.setPreferredSize(new Dimension(600, 500));
        startButton.setBackground(buttonColor);
        startButton.setBorder(buttonBorder);
        startButton.setOpaque(true);
        startButton.setFont(buttonFont);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
                startButton.setVisible(false);
                waitButton.setVisible(true);
                
            }
        });

        waitButton = new JButton("Wait");
        waitButton.setVisible(false);
        waitButton.setBackground(Color.red);
        waitButton.setBorder(buttonBorder);
        waitButton.setPreferredSize(new Dimension(600,500));
        waitButton.setOpaque(true);
        waitButton.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 30));
        waitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                System.out.println("Stop. timer restarted.");
                timer.start();

            }
        });

        reactButton = new JButton("Click!");
        reactButton.setVisible(false);
        reactButton.setBackground(Color.green);
        reactButton.setBorder(buttonBorder);
        reactButton.setOpaque(true);
        reactButton.setPreferredSize(new Dimension(600,500));
        reactButton.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 80));
        reactButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopTime = System.currentTimeMillis();
                latest = latestTime();
                checkFastestTime(latest);
                tries++;
                //gain evopoints and agility xp
                Gain();
                updateInfo();
                waitButton.setVisible(true);
                reactButton.setVisible(false);
                
                
                timer.start();
            }
        });


        timer = new javax.swing.Timer(timeIntervall(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                waitButton.setVisible(false);
                reactButton.setVisible(true);
                timer.stop();
                startTime = System.currentTimeMillis();
            }
        });

        //add button to panel
        gamePanel.add(waitButton);
        gamePanel.add(reactButton);
        gamePanel.add(startButton);

        //Add JPanels to JFrame
        activeWindow.getContentPane().add(backPanel);
        activeWindow.getContentPane().add(infoDisplay);
        activeWindow.getContentPane().add(endScreen);
        activeWindow.getContentPane().add(gamePanel);
    
        activeWindow.setLocation(x, y);
        activeWindow.setVisible(true);

    }

    private void Gain() {
        //Calculate Evo point gain
        int evoGain = Math.round(character.getAttributes().getLevel("total") * 1000 / latest);

        EVOpoints += evoGain;

        int dexterityGain = Math.round(character.getAttributes().getLevel("dexterity") * 1000 / latest);

        dexterityXP += dexterityGain;
        
        System.out.println("Gained EVO points: " + evoGain);
        System.out.println("Gained agility xp: " + dexterityGain);
        
    }

    private long checkFastestTime(long time) {
        if (state) {
            fastest = time;
            state = false;
        } else if (time < fastest) {
            fastest = time;
        }
        return fastest;
    }

    private long latestTime() {
        return (latest = stopTime - startTime);
    }

    private int timeIntervall() {
        Random rand = new Random();
        int upper_bound = 4000;
        System.out.println(""+ (2000 + (Math.round(rand.nextInt(upper_bound)))));
        return (2000 + (Math.round(rand.nextInt(upper_bound))));
    }
    
    //Update info text
    private void updateInfo() {
        infoText.setText("<html>Current tries: " + tries + "<br/> Fastest try in ms: " + fastest + "<br/> DexterityXp gained: " + dexterityXP + "<br/> Gained EVO points: " + Math.round(EVOpoints) + "</html>");
    }
}