package frontend.src;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

import backend.*;
import serverClient.*;


public class MenuScreen {
    
    private JFrame activeWindow = new JFrame("Logout");
    private JPanel backPanel, logoutPanel;
    private MainScreen mainScreen;
    private JButton backButton, logoutButton;
    private Client client;

    //Background
    private Color backgroundColor = new Color(0x7678ed);

    //Button variables
    private Color buttonColor = new Color(0xffb703);
    private Border buttonBorder = new LineBorder(new Color(0xffcb69), 2);
    private Font buttonFont = new Font("Serif", Font.BOLD | Font.ITALIC, 20);
    

    public MenuScreen(MainScreen mainScreen, Client client) {
        this.mainScreen = mainScreen;
        createActiveUI();
        this.client = client;
    }

    public void createActiveUI() {
        activeWindow.setSize(400, 400);
        activeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //needed for .setBounds with JPanel to work properly
        activeWindow.setLayout(null);

        //set background
        activeWindow.getContentPane().setBackground(backgroundColor);

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
                activeWindow.setVisible(false);
                // create UI for main menu
                mainScreen.createUI();
                mainScreen.updateList();
            }
        });
        backPanel.add(backButton);

        logoutPanel = new JPanel();
        logoutPanel.setBounds(150,175,100,50);
        logoutPanel.setBackground(backgroundColor);

        logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(100,50));
        logoutButton.setBackground(buttonColor);
        logoutButton.setBorder(buttonBorder);
        logoutButton.setFont(buttonFont);
        logoutButton.setOpaque(true);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//System.out.println(client);
                activeWindow.dispose();
                mainScreen.getWindow().dispose();

                //update data for character in database
                new Logout(mainScreen.getCharacter(), client);

                new LoginScreen(client);


            }
        });
        logoutPanel.add(logoutButton);

        centerWindow(activeWindow);

        activeWindow.getContentPane().add(logoutPanel);
        activeWindow.getContentPane().add(backPanel);
        activeWindow.setVisible(true);
    }

    //center window of screen
    public void centerWindow(JFrame window) {
        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - 400) / 2);
        int y = (int) ((dimension.getHeight() - 400) / 2);
        window.setLocation(x, y);

    }
}