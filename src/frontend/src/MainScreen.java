package frontend.src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import serverClient.*;

import backend.Character;
//import jdk.internal.org.jline.utils.Colors;

public class MainScreen {

    private final int windowHeight = 800;
    private final int windowWidth = 600;
    private JFrame window;
    private Color backgroundColor = new Color(0x7678ed); 
    private MainScreen classRef = this;
    private Character character;
    private JList<String> statList;
    private JPanel menuPanel;
    private JButton menuButton;
    private JButton chattButton;
    private Client client;
    
    public JFrame getWindow() {
        return window;
    }

    public MainScreen(Character character, Client client) {
        this.client = client;
        this.character = character;
        // this.client = client;
        window = new JFrame("Project EVO");
        createUI();
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public Character getCharacter() {
        return character;
    }

    public void createUI() {
        window.getContentPane().removeAll();
        // create window
        window.setSize(windowWidth, windowHeight);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);

        // set background
        window.getContentPane().setBackground(this.backgroundColor);

        // (newly added) add EVO icon to the game
        ImageIcon image = new ImageIcon("./src/frontend/src/images/EVOPointsResized.png"); // create an Image
        window.setIconImage(image.getImage()); // change icon of frame

        // center window of screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - windowWidth) / 2);
        int y = (int) ((dimension.getHeight() - windowHeight) / 2);
        window.setLocation(x, y);

        menuPanel = new JPanel();
        menuPanel.setBackground(this.backgroundColor);
        menuPanel.setBounds(5, 0, 100, 60);

        // create button for going to menuscreen
        menuButton = new JButton("Menu");
        Border menuButtonBorder = new LineBorder(new Color(0xffcb69), 2);
        menuButton.setBorder(menuButtonBorder);
        menuButton.setOpaque(true);
        menuButton.setBackground(new Color(0xffb703));
        menuButton.setMargin(new Insets(0,0,0,0));
        menuButton.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
        menuButton.setPreferredSize(new Dimension(100, 50));
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuScreen(classRef, client);
            }
        });
        menuPanel.add(menuButton);

        JPanel chattPanel = new JPanel();
        chattPanel.setBackground(this.backgroundColor);
        chattPanel.setBounds(105, 0, 100, 60);

        // create button for going to chatscreen
        chattButton = new JButton("Chatt");
        Border chattButtonBorder = new LineBorder(new Color(0xffcb69), 2);
        chattButton.setBorder(chattButtonBorder);
        chattButton.setOpaque(true);
        chattButton.setBackground(new Color(0xffb703));
        chattButton.setMargin(new Insets(0,0,0,0));
        chattButton.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
        chattButton.setPreferredSize(new Dimension(100, 50));
        chattButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChattScreen(character,client);
            }
        });
        chattPanel.add(chattButton);

        // (Newly added) create panel for your cell
        JPanel cellOrganismPanel = new JPanel();
        //cellOrganismPanel.setBackground(new Color(0xffb703));//backgroundColor);
        cellOrganismPanel.setBackground(backgroundColor);
        cellOrganismPanel.setBounds(100, 225, 400, 270);
        window.getContentPane().add(cellOrganismPanel);

           // resizeing the image
           ImageIcon imageCell = new ImageIcon("./src/frontend/src/images/Cell1Transparent.png"); // create an Image
           Image i = imageCell.getImage(); // transform it
           Image newimg = i.getScaledInstance(300, 150, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
           ImageIcon imageCellResized = new ImageIcon(newimg); // transform it back
   
           JLabel cellLabel = new JLabel();
           cellLabel.setSize(200, 150);
           cellLabel.setIcon(imageCellResized);
     
           // second cell image
           ImageIcon imageCell2 = new ImageIcon("./src/frontend/src/images/Cell2Transparent.png"); // create an Image
           Image i2 = imageCell2.getImage(); // transform it
           Image newimg2 = i2.getScaledInstance(350, 150, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
           ImageIcon imageCellResized2 = new ImageIcon(newimg2); // transform it back
   
           JLabel cellLabel2 = new JLabel();
           cellLabel2.setSize(200, 150);
           cellLabel2.setIcon(imageCellResized2);
    
           // third cell image
           ImageIcon imageCell3 = new ImageIcon("./src/frontend/src/images/Cell3Transparent.png"); // create an Image
           Image i3 = imageCell3.getImage(); // transform it
           Image newimg3 = i3.getScaledInstance(400, 150, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
           ImageIcon imageCellResized3 = new ImageIcon(newimg3); // transform it back
   
           JLabel cellLabel3 = new JLabel();
           cellLabel3.setSize(200, 150);
           cellLabel3.setIcon(imageCellResized3);

           // fish image
           ImageIcon imageFish = new ImageIcon("./src/frontend/src/images/Fish.png"); // create an Image
           Image ifish = imageFish.getImage(); // transform it
           Image newfishimg = ifish.getScaledInstance(275, 125, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
           ImageIcon imageFishResized = new ImageIcon(newfishimg); // transform it back
   
           //JLabel fishLabel = new JLabel(imageFishResized, JLabel.CENTER);

           JLabel fishLabel = new JLabel();
           fishLabel.setSize(200, 150);
           fishLabel.setIcon(imageFishResized);

           // lizard image
           ImageIcon imageLizard = new ImageIcon("./src/frontend/src/images/Lizard.png"); // create an Image
           Image ilizard = imageLizard.getImage(); // transform it
           Image newlizardimg = ilizard.getScaledInstance(275, 125, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
           ImageIcon imageLizardResized = new ImageIcon(newlizardimg); // transform it back
   
           //JLabel lizardLabel = new JLabel(imageLizardResized, JLabel.CENTER);

           JLabel lizardLabel = new JLabel();
           lizardLabel.setSize(200, 150);
           lizardLabel.setIcon(imageLizardResized);

           // ape image
           ImageIcon imageApe = new ImageIcon("./src/frontend/src/images/Ape.png"); // create an Image
           Image iape = imageApe.getImage(); // transform it
           Image newapeimg = iape.getScaledInstance(250, 150, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
           ImageIcon imageApeResized = new ImageIcon(newapeimg); // transform it back
   
           //JLabel apeLabel = new JLabel(imageApeResized, JLabel.CENTER);

           JLabel apeLabel = new JLabel();
           apeLabel.setSize(200, 150);
           apeLabel.setIcon(imageApeResized);

           // caveman image
           /*
           ImageIcon imageCaveman = new ImageIcon("./src/frontend/src/images/Caveman.png"); // create an Image
           Image icaveman = imageCaveman.getImage(); // transform it
           Image newcavemanimg = icaveman.getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
           ImageIcon imageCavemanResized = new ImageIcon(newcavemanimg); // transform it back
   
           //JLabel cavemanLabel = new JLabel(imageCavemanResized, JLabel.CENTER);

           JLabel cavemanLabel = new JLabel();
           cavemanLabel.setSize(300,250);
           cavemanLabel.setIcon(imageCavemanResized); */

           // human image
           ImageIcon imageHuman = new ImageIcon("./src/frontend/src/images/Human.png"); // create an Image
           Image ihuman = imageHuman.getImage(); // transform it
           Image newhumanimg = ihuman.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
           ImageIcon imageHumanResized = new ImageIcon(newhumanimg); // transform it back
   
           //JLabel humanLabel = new JLabel(imageHumanResized, JLabel.CENTER);

           JLabel humanLabel = new JLabel();
           humanLabel.setSize(300,250);
           humanLabel.setIcon(imageHumanResized);
            

        // changes the character image at certain levels (every 2 eating levels)
        int eatingLevel = character.getAttributes().getLevel("eating");
        //int huntingLevel = character.getAttributes().getLevel("hunting");
        
        // (every 2 eating levels)
        if(eatingLevel >= 1 && eatingLevel < 2) {cellOrganismPanel.add(cellLabel); }
        if(eatingLevel >= 2 && eatingLevel < 3) {cellOrganismPanel.add(cellLabel2); }
        if(eatingLevel >= 3 && eatingLevel < 4) {cellOrganismPanel.add(cellLabel3); }
        if(eatingLevel >= 4 && eatingLevel < 5) {cellOrganismPanel.add(fishLabel); }
        if(eatingLevel >= 5 && eatingLevel < 6) {cellOrganismPanel.add(lizardLabel); }
        if(eatingLevel >= 6 && eatingLevel < 7) {cellOrganismPanel.add(apeLabel); }
        //if(eatingLevel >= 7 && eatingLevel < 8) {cellOrganismPanel.add(cavemanLabel); }
        if(eatingLevel >= 7 && eatingLevel < 100) {cellOrganismPanel.add(humanLabel); 
        } 


        // create the panel for idleActions
        JPanel idleActionsPanel = new JPanel();
        idleActionsPanel.setBackground(this.backgroundColor);
        idleActionsPanel.setBounds(50, 500, 250, 60);
        window.getContentPane().add(idleActionsPanel);

        // create the panel for activeActions
        JPanel activeActionsPanel = new JPanel();
        activeActionsPanel.setBackground(this.backgroundColor);
        activeActionsPanel.setBounds(300, 500, 250, 60);
        window.getContentPane().add(activeActionsPanel);

        // add button into idleActions panel
        JButton idleActionsButton = new JButton("Idle Actions");
        Border idleButtonBorder = new LineBorder(new Color(0xffcb69), 2);
        idleActionsButton.setBorder(idleButtonBorder);
        idleActionsButton.setOpaque(true);
        idleActionsButton.setBackground(new Color(0xffb703));
        idleActionsButton.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
        idleActionsButton.setPreferredSize(new Dimension(250, 50));
        idleActionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new IdleActionsScreen(classRef, character);
                
                // change window to idle actions window
            }
        });
        idleActionsPanel.add(idleActionsButton);
        
        // add button for activeActions or "mini-games"
        JButton activeActionsButton = new JButton("Active actions");
        Border activeButtonBorder = new LineBorder(new Color(0xffcb69), 2);
        activeActionsButton.setBorder(activeButtonBorder);
        activeActionsButton.setOpaque(true);
        activeActionsButton.setBackground(new Color(0xffb703));
        activeActionsButton.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
        activeActionsButton.setPreferredSize(new Dimension(250, 50));
        activeActionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new ActiveActionsScreen(classRef, character);
                //change window to active actions window

            }
        });
        activeActionsPanel.add(activeActionsButton);

        JPanel statPanel = new JPanel();
        statPanel.setBounds(400, 0, 200, 300);
        statPanel.setBackground(this.backgroundColor);

        statList = new JList<String>();
        statList.setBackground(this.backgroundColor);
        updateList();

        statPanel.add(statList);
        window.getContentPane().add(statPanel);
        window.getContentPane().add(menuPanel);
        window.getContentPane().add(chattPanel);

        window.setVisible(true);
    }

    public void updateList() {
        statList.setListData(character.getAttributes().showAllLevels());
    }

}
