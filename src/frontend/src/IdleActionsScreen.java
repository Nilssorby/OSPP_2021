package frontend.src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import backend.Character;

public class IdleActionsScreen {
    private JFrame idleWindow;
    private Color backgroundColor = new Color(0x7678ed);
    private Color buttonColor = new Color(0xffb703);
    private Border buttonBorder = new LineBorder(new Color(0xffcb69), 2);
    private Font buttonFont = new Font("Serif", Font.BOLD | Font.ITALIC, 20);
    private IdleActionsScreen classRef = this;
    private MainScreen mainScreen;
    private Character character;
    private Timer timer;
    private JButton backButton, upgradeButton, miningButton, woodcuttingButton, huntingButton, eatButton, doneButton,
            attackButton, rangedButton, defenceButton, firemakingButton;
    private JList<String> equipmentList, allLevels;
    private JPanel EVOPointsPanel, backPanel, idleActions, idleProgress, upgradePanel, combatPanel, combatTextPanel;
    private JLabel EVOPointsLabel, currentSkill, combatLabel;
    private int idleWindowWidth, idleWindowHeight;
    private boolean popUpState1 = true, popUpState2 = true, popUpState3 = true, popUpState4 = true, popUpState5 = true;

    public IdleActionsScreen(MainScreen mainScreen, Character character) {
        this.character = character;
        idleWindow = mainScreen.getWindow();
        this.mainScreen = mainScreen;
        this.createIdleUI(mainScreen);
    }

    // Exception because of sleep in idleMode // ?? what does this mean
    public void createIdleUI(MainScreen mainScreen) {

        // MAKE SURE SHOWN JFRAME IS EMPTY (MIGHT REMOVE THIS IF UNECCESSARY)
        idleWindow.getContentPane().removeAll();

        // (newly added) add EVO icon to the window
        ImageIcon image = new ImageIcon("./src/frontend/src/images/EVOPointsResized.png"); // create an Image
        idleWindow.setIconImage(image.getImage()); // change icon of frame

        // SET SIZE OF JFRAME
        idleWindow.setSize(mainScreen.getWindowWidth(), mainScreen.getWindowHeight());

        // SET DEFAULT CLOSE OPERATION
        idleWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // HACK: needed for .setBounds with JPanel to work properly, otherwise we need
        // to use a LayoutManager(), seemed difficult to get it right
        idleWindow.setLayout(null);

        // sSET BACKGROUND
        idleWindow.getContentPane().setBackground(this.backgroundColor);

        // MAKE SURE JFRAME IS CENTERED ON SCREEN
        centerScreen(mainScreen);

        // PANEL FOR BACKBUTTON
        backPanel = new JPanel();
        backPanel.setBackground(this.backgroundColor);
        backPanel.setBounds(5, 0, 100, 50);

        /// (Newly added) initialize icon for back button
        ImageIcon imageCross = new ImageIcon(getClass().getResource("images/CrossImageTransparent.png")); // create an
                                                                                                          // Image
        Image imageCross2 = imageCross.getImage(); // transform it
        Image newimg = imageCross2.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        ImageIcon imageCrossResized = new ImageIcon(newimg); // transform it back

        // BUTTON FOR GOING BACK TO MAINSCREEN
        backButton = new JButton("Back", imageCrossResized); // Button with text and icon.
        customizeButton(backButton);
        // BUTTON FOR GOING BACK TO MAINSCREEN
        // backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idleWindow.setVisible(false);
                // create UI for main menu
                mainScreen.createUI();
                mainScreen.updateList();
            }
        });
        backPanel.add(backButton);

        // PANEL USED FOR SHOWING EVO POINTS
        EVOPointsPanel = new JPanel(new BorderLayout());
        EVOPointsPanel.setBounds(110, 0, 150, 50);
        EVOPointsPanel.setBackground(this.backgroundColor);

        // LABEL SHOWING EVO POINTS
        EVOPointsLabel = new JLabel();
        EVOPointsLabel.setPreferredSize(new Dimension(150, 50));
        EVOPointsLabel.setText("EVO Points: " + character.getEVOPoints());
        EVOPointsPanel.add(EVOPointsLabel, BorderLayout.CENTER);

        // BORDER THAT CAN BE USED FOR JCOMPONTENS
        Border border = BorderFactory.createLineBorder(Color.black, 1);

        // PANEL USED FOR DISPLAYING IDLE ACTIONS SUCH AS "MINING", "HUNTING" ETC
        idleActions = new JPanel();
        idleActions.setBounds(50, 100, 500, 200);
        idleActions.setBackground(this.backgroundColor);
        idleActions.setBorder(border);
        idleWindow.getContentPane().add(idleActions);

        // PANEL USED FOR SHOWING ALL CURRENT LEVELS
        idleProgress = new JPanel();
        idleProgress.setBackground(this.backgroundColor);
        idleProgress.setBounds(50, 525, 250, 200);

        // SHOW ALL CURRENT LEVELS (HIDES WHAT SKILL IS CURRENTLY BEING IDLED)
        allLevels = new JList<String>(character.getAttributes().showAllLevels());
        allLevels.setBackground(this.backgroundColor);
        idleProgress.add(allLevels);

        // LABEL USED FOR SHOWING WHAT SKILL IS CURRENTLY BEING IDLED (HIDES ALL OTHER
        // LEVELS)
        currentSkill = new JLabel();
        idleProgress.add(currentSkill);

        // EAT IDLE BUTTON
        eatButton = new JButton("Eat");
        customizeButton(eatButton);
        eatButton.setPreferredSize(new Dimension(150, 50));
        eatButton.setEnabled(false);
        eatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableButtons();
                new idlePopUp("eating", character);
            }
        });
        idleActions.add(eatButton);
        
        // HUNTING IDLE BUTTON
        huntingButton = new JButton("Hunting");
        customizeButton(huntingButton);
        huntingButton.setEnabled(false);
        huntingButton.setPreferredSize(new Dimension(150, 50));
        huntingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableButtons();
                new idlePopUp("hunting", character);
            }
        });
        idleActions.add(huntingButton);
        
        // WOODCUTTING IDLE BUTTON
        woodcuttingButton = new JButton("Woodcutting");
        customizeButton(woodcuttingButton);
        woodcuttingButton.setEnabled(false);
        woodcuttingButton.setPreferredSize(new Dimension(150, 50));
        woodcuttingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableButtons();
                new idlePopUp("woodcutting", character);
            }
        });
        idleActions.add(woodcuttingButton);
        
        // FIREMAKING IDLE BUTTON
        firemakingButton = new JButton("Firemaking");
        customizeButton(firemakingButton);
        firemakingButton.setEnabled(false);
        firemakingButton.setPreferredSize(new Dimension(150, 50));
        firemakingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableButtons();
                new idlePopUp("firemaking", character);
            }
        });
        idleActions.add(firemakingButton);
        
        // MINING IDLE BUTTON
        miningButton = new JButton("Mining");
        customizeButton(miningButton);
        miningButton.setPreferredSize(new Dimension(150, 50));
        miningButton.setEnabled(false);
        miningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableButtons();
                new idlePopUp("mining", character);
            }
        });
        idleActions.add(miningButton);

        // PANEL USED FOR PUTTING THE UPGRADE BUTTON
        upgradePanel = new JPanel();
        upgradePanel.setBounds(300, 525, 250, 200);
        upgradePanel.setBackground(this.backgroundColor);

        // SHOW CURRENT EQUIPMENT
        equipmentList = new JList<String>(character.getEquipment().showAllEquipmentLevels());
        equipmentList.setBackground(this.backgroundColor);
        upgradePanel.add(equipmentList);

        // UPGRADE BUTTON
        upgradeButton = new JButton("Upgrade");
        customizeButton(upgradeButton);
        upgradeButton.setEnabled(false);
        upgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idleWindow.setVisible(false);
                new UpgradeScreen(mainScreen.getWindowWidth(), mainScreen.getWindowHeight(), character, classRef);
            }
        });
        upgradePanel.add(upgradeButton);

        // PANEL FOR SHOWING "COMBAT" TEXT
        combatTextPanel = new JPanel();
        combatTextPanel.setBounds(50, 300, 500, 30);
        combatTextPanel.setBackground(this.backgroundColor);

        // LABLE USED FOR SHOWING "COMBAT" TEXT
        combatLabel = new JLabel("Combat");
        combatLabel.setBounds(0, 0, 50, 30);

        combatTextPanel.add(combatLabel);

        // PANEL FOR SHOWING COMBAT IDLE ACTIONS
        combatPanel = new JPanel();
        combatPanel.setBounds(50, 330, 500, 170);
        combatPanel.setBackground(this.backgroundColor);
        combatPanel.setBorder(border);

        // ATTACK IDLE BUTTON
        attackButton = new JButton("Attack");
        customizeButton(attackButton);
        attackButton.setPreferredSize(new Dimension(150, 50));
        attackButton.setEnabled(false);
        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableButtons();
                new idlePopUp("attack", character);
            }
        });
        combatPanel.add(attackButton);

        // RANGED IDLE BUTTON
        rangedButton = new JButton("Ranged");
        customizeButton(rangedButton);
        rangedButton.setPreferredSize(new Dimension(150, 50));
        rangedButton.setEnabled(false);
        rangedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableButtons();
                new idlePopUp("ranged", character);
            }
        });
        combatPanel.add(rangedButton);

        // DEFENCE IDLE BUTTON
        defenceButton = new JButton("Defence");
        customizeButton(defenceButton);
        defenceButton.setPreferredSize(new Dimension(150, 50));
        defenceButton.setEnabled(false);
        defenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disableButtons();
                new idlePopUp("defence", character);
            }
        });
        combatPanel.add(defenceButton);

        // ADD ALL PANELS USED TO IDLEWINDOW JFRAME
        idleWindow.getContentPane().add(combatTextPanel);
        idleWindow.getContentPane().add(combatPanel);
        idleWindow.getContentPane().add(idleProgress);
        idleWindow.getContentPane().add(backPanel);
        idleWindow.getContentPane().add(EVOPointsPanel);
        idleWindow.getContentPane().add(upgradePanel);

        // BEFORE ENABLING BUTTONS, SET THE POPUP STATES TO PREVENT POPUPS DISPLAYING
        // MULTIPLE TIMES
        checkPopUpStates();

        // ENABLE BUTTONS THAT ARE SUPPOSED TO BE ENABLED ACCORDING TO TOTAL LEVEL
        enableButtons(character.getAttributes().getLevel("total"));

        // ?? maybe not needed
        idleWindow.repaint();

        // SHOW JFRAME
        idleWindow.setVisible(true);
    }

    // sets PopUpStates to false if the popup has already been shown once i.e.
    // totalLevel is already greather than the threshold needed for the popup to
    // show
    public void checkPopUpStates() {
        int totalLevel = character.getAttributes().getLevel("total");
        if (totalLevel >= 10) {
            popUpState1 = false;
        }
        ;
        if (totalLevel >= 20) {
            popUpState2 = false;
        }
        ;
        if (totalLevel >= 30) {
            popUpState3 = false;
        }
        ;
        if (totalLevel >= 40) {
            popUpState4 = false;
        }
        ;

    }

    // centers window of screen
    private void centerScreen(MainScreen mainScreen) {

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        idleWindowWidth = (int) ((dimension.getWidth() - mainScreen.getWindowWidth()) / 2);
        idleWindowHeight = (int) ((dimension.getHeight() - mainScreen.getWindowHeight()) / 2);
        idleWindow.setLocation(idleWindowWidth, idleWindowHeight);
    }

    public void customizeButton(JButton button) {
        Border buttonBorder = new LineBorder(new Color(0xffcb69), 2);
        button.setBorder(buttonBorder);
        button.setOpaque(true);
        button.setBackground(new Color(0xffb703));
        button.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
    }

    

    // disables all buttons except "Done" button
    public void disableButtons() {
        eatButton.setEnabled(false);
        upgradeButton.setEnabled(false);
        backButton.setEnabled(false);
        miningButton.setEnabled(false);
        woodcuttingButton.setEnabled(false);
        huntingButton.setEnabled(false);
        attackButton.setEnabled(false);
        rangedButton.setEnabled(false);
        defenceButton.setEnabled(false);

        // doneButton.setVisible(true);

    }

    // enables all buttons
    public void enableButtons(int totalLevel) {
        System.out.println("Your total level is: " + totalLevel);

        if (totalLevel < 10) {
            eatButton.setEnabled(true);
            backButton.setEnabled(true);
        }

        if (totalLevel >= 10) {
            if (popUpState1) {
                new PopUpWindow("Hunting!", mainScreen);
                popUpState1 = false;
            }
            eatButton.setEnabled(true);
            huntingButton.setEnabled(true);
            upgradeButton.setEnabled(true);
            backButton.setEnabled(true);
        }

        if (totalLevel >= 20) {
            if (popUpState2) {
                new PopUpWindow("Combat!", mainScreen);
                popUpState2 = false;
            }
            eatButton.setEnabled(true);
            huntingButton.setEnabled(true);
            attackButton.setEnabled(true);
            rangedButton.setEnabled(true);
            defenceButton.setEnabled(true);
            upgradeButton.setEnabled(true);
            backButton.setEnabled(true);
        }
        if (totalLevel >= 30) {
            if (popUpState3) {
                new PopUpWindow("All buttons enabled!", mainScreen);
                popUpState3 = false;
            }
            popUpState3 = false;
            eatButton.setEnabled(true);
            huntingButton.setEnabled(true);
            woodcuttingButton.setEnabled(true);
            miningButton.setEnabled(true);
            upgradeButton.setEnabled(true);

            backButton.setEnabled(true);
        }

        // doneButton.setVisible(false);

    }

    public void showWindow() {
        EVOPointsLabel.setText("EVO Points: " + character.getEVOPoints());
        allLevels.setListData(character.getAttributes().showAllLevels());
        equipmentList.setListData(character.getEquipment().showAllEquipmentLevels());
        enableButtons(character.getAttributes().getLevel("total"));
        idleWindow.setVisible(true);
    }

    public class PopUpWindow {
        public PopUpWindow(String id, MainScreen mainScreen) {
            JFrame f = new JFrame("Skill unlocked!");
            f.setSize(600, 200);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            

            // MAKE SURE JFRAME IS CENTERED ON SCREEN
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - mainScreen.getWindowWidth()) / 2);
            int y = (int) ((dimension.getHeight() - mainScreen.getWindowHeight()) / 2) + 300;
            f.setLocation(x, y);

            JPanel p = new JPanel();
            p.setBounds(0, 0, 600, 50);
            p.setBackground(backgroundColor);

            JLabel label = new JLabel("You just unlocked: ");
            p.add(label);

            JLabel idLabel = new JLabel(id);
            idLabel.setFont(new Font("Serif", Font.BOLD, 20));
            p.add(idLabel);

            JPanel textPanel = new JPanel();
            textPanel.setBounds(0, 50, 600, 150);
            textPanel.setBackground(backgroundColor);

            JTextArea infoText = new JTextArea();
            infoText.setLineWrap(true);
            infoText.setEditable(false);
            infoText.setOpaque(false);
            infoText.setWrapStyleWord(true);
            infoText.setPreferredSize(new Dimension(575, 200));
            infoText.setForeground(buttonColor);
            infoText.setFont(buttonFont);
            textPanel.add(infoText);

            switch (id) {
                case "Hunting!":
                    infoText.setText(
                            "Congrats! You just unlocked Hunting which is a great source for food!");
                    break;
                case "Combat!":
                    infoText.setText(
                            "Congratulations! You just unlocked combat!");
                    break;
            }

            f.getContentPane().add(textPanel);
            f.getContentPane().add(p);
            f.setVisible(true);
        }
    }

    public class idlePopUp {

        private JProgressBar bar;
        private String idleAction;

        public idlePopUp(String idleAction, Character character) {
            this.idleAction = idleAction;

            // create the JFrame
            JFrame f = new JFrame("Idle " + idleAction);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setSize(600, 200);
            f.setLayout(null);

            f.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    enableButtons(character.getAttributes().getLevel("total"));
                    allLevels.setListData(character.getAttributes().showAllLevels());
                    allLevels.setVisible(true);
                    currentSkill.setText("");
                    timer.stop();
                    f.dispose();
                }
            });

            int startValue = character.getAttributes().getXp(idleAction);
            int maximumValue = (int) character.getAttributes().levelThreshold(idleAction);

            JProgressBar bar = new JProgressBar(0, maximumValue) // lower limit of progressBar
            ; // upper limit of progressBar
            bar.setBounds(0, 0, 600, 50);
            bar.setStringPainted(true);
            bar.setIndeterminate(true);
            bar.setBackground(backgroundColor);
            bar.setForeground(buttonColor);

            f.getContentPane().add(bar);

            // MAKE SURE JFRAME IS CENTERED ON SCREEN
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - mainScreen.getWindowWidth()) / 2);
            int y = (int) ((dimension.getHeight() - mainScreen.getWindowHeight()) / 2) + 300;

            JPanel p = new JPanel();
            p.setBounds(200, 50, 200, 30);
            p.setBackground(backgroundColor);

            JLabel levelUpLabel = new JLabel(
                    "Needed for levelup: " + character.getAttributes().levelThreshold(idleAction));
            levelUpLabel.setFont(new Font("Serif", Font.BOLD, 15));
            p.add(levelUpLabel);

            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (bar.getPercentComplete() == 1.0) {
                        bar.setString("Level up!");
                        character.increaseXp(idleAction);
                        bar.setMinimum(0);
                        bar.setMaximum((int) character.getAttributes().levelThreshold(idleAction));
                        bar.setValue(character.getAttributes().getXp(idleAction));
                        bar.repaint();

                        levelUpLabel
                                .setText("Needed for levelup: " + character.getAttributes().levelThreshold(idleAction));
                        allLevels.setListData(character.getAttributes().showAllLevels());

                        EVOPointsLabel.setText("EVO Points: " + character.getEVOPoints());
                    } else {
                        character.increaseXp(idleAction);
                        EVOPointsLabel.setText("EVO Points: " + character.getEVOPoints());
                        bar.setValue(character.getAttributes().getXp(idleAction));
                        bar.setString("" + bar.getValue());
                    }
                }
            });
            timer.start();

            JPanel p2 = new JPanel();
            p2.setBounds(250, 100, 100, 50);
            p2.setBackground(backgroundColor);

            // DONE BUTTON USED WHEN DONE IDLING AN ACTION
            doneButton = new JButton("Done");
            Border doneButtonBorder = new LineBorder(new Color(0xffcb69), 2);
            doneButton.setBorder(doneButtonBorder);
            doneButton.setBackground(new Color(0xffb703));
            doneButton.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
            doneButton.setPreferredSize(new Dimension(100, 50));
            doneButton.setVisible(true);
            doneButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    enableButtons(character.getAttributes().getLevel("total"));
                    allLevels.setListData(character.getAttributes().showAllLevels());
                    allLevels.setVisible(true);
                    currentSkill.setText("");
                    timer.stop();
                    f.dispose();
                }
            });
            p2.add(doneButton);

            f.getContentPane().setBackground(backgroundColor);
            f.getContentPane().add(p2);
            f.getContentPane().add(p);
            f.setLocation(x, y);
            f.setVisible(true);
        }
    }
}