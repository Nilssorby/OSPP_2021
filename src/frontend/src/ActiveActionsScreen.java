package frontend.src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class ActiveActionsScreen {
    private JFrame activeWindow = new JFrame("Active Actions");
    private Color backgroundColor = new Color(0x7678ed);
    private Color buttonColor = new Color(0xffb703);
    private Border buttonBorder = new LineBorder(new Color(0xffcb69), 2);
    private Font buttonFont = new Font("Serif", Font.BOLD | Font.ITALIC, 20);
    private JPanel backPanel;
    private JPanel gamesPanel;
    private MainScreen mainScreen;
    private ActiveActionsScreen classRef = this;
    private backend.Character character;
    private JButton backButton;
    private JButton game1Button;
    private JButton game2Button;
    private JButton game3Button;
    private JButton game4Button;

    public ActiveActionsScreen(MainScreen mainScreen, backend.Character character) {
        this.mainScreen = mainScreen;
        this.character = character;
        createActiveUI();
    }

    public int getWindowHeight() {
        return mainScreen.getWindowHeight();
    }

    public int getWindowWidth() {
        return mainScreen.getWindowWidth();
    }

    public JFrame getWindow() {
        return activeWindow;
    }

    public void setFrameVisible() {
        activeWindow.setVisible(true);
    }

    public void createActiveUI() {
        mainScreen.getWindow().setVisible(false);
        activeWindow.setSize(mainScreen.getWindowWidth(), mainScreen.getWindowHeight());
        activeWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //needed for .setBounds with JPanel to work properly
        activeWindow.setLayout(null);

        // (newly added) add EVO icon to the window
        ImageIcon image = new ImageIcon("./src/frontend/src/images/EVOPointsResized.png"); // create an Image
        activeWindow.setIconImage(image.getImage()); // change icon of frame

        //set background
        activeWindow.getContentPane().setBackground(this.backgroundColor);

        //center window of screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - mainScreen.getWindowWidth()) / 2);
        int y = (int) ((dimension.getHeight() - mainScreen.getWindowHeight()) / 2);

        backPanel = new JPanel();
        backPanel.setBounds(5,0,100,50);
        backPanel.setBackground(this.backgroundColor);

        backButton = new JButton("Back");
        backButton.setBorder(buttonBorder);
        backButton.setBackground(buttonColor);
        backButton.setOpaque(true);
        backButton.setFont(buttonFont);
        backButton.setPreferredSize(new Dimension(100,50));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeWindow.setVisible(false);
                // create UI for main menu
                mainScreen.createUI();
                mainScreen.updateList();
            }
        });
        backPanel.add(backButton);

        gamesPanel = new JPanel();
        gamesPanel.setBounds(5,150,mainScreen.getWindowWidth(),500);
        gamesPanel.setBackground(backgroundColor);

        game1Button = new JButton("Memory");
        game1Button.setPreferredSize(new Dimension(150,50));
        game1Button.setOpaque(true);
        game1Button.setBackground(buttonColor);
        game1Button.setBorder(buttonBorder);
        game1Button.setFont(buttonFont);
        game1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartMiniGame("Memory");
            }
        });
        gamesPanel.add(game1Button);

        game2Button = new JButton("Reaction");
        game2Button.setPreferredSize(new Dimension(150,50));
        game2Button.setOpaque(true);
        game2Button.setBackground(buttonColor);
        game2Button.setBorder(buttonBorder);
        game2Button.setFont(buttonFont);
        game2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartMiniGame("Reaction");
            }
        });
        gamesPanel.add(game2Button);

        game3Button = new JButton("Chimp Test");
        game3Button.setPreferredSize(new Dimension(150,50));
        game3Button.setOpaque(true);
        game3Button.setBackground(buttonColor);
        game3Button.setBorder(buttonBorder);
        game3Button.setFont(buttonFont);
        game3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartMiniGame("ChimpTest");
            }
        });
        gamesPanel.add(game3Button);

        game4Button = new JButton("Strength");
        game4Button.setPreferredSize(new Dimension(150,50));
        game4Button.setOpaque(true);
        game4Button.setBackground(buttonColor);
        game4Button.setBorder(buttonBorder);
        game4Button.setFont(buttonFont);
        game4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartMiniGame("Clicker");
            }
        });
        gamesPanel.add(game4Button);


        activeWindow.getContentPane().add(backPanel);
        activeWindow.getContentPane().add(gamesPanel);

        activeWindow.setLocation(x, y);
        activeWindow.setVisible(true);

    }

    void StartMiniGame(String id) {
        switch(id) {
            case "Memory":
                new Memory(classRef, character);
                break;
            case "Reaction":
                new Reaction(classRef, character);
                break;
            case "ChimpTest":
                new ChimpTest(classRef, character);
                break;
            case "Clicker":
                new Clicker(classRef, character);
                break;
        }
    }
}