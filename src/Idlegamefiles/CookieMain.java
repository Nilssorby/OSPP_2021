package Idlegamefiles;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class CookieMain {
    
    public static void main(String[] args){

        new CookieMain();
    }

    public CookieMain(){

        createUI();
    }
    public void createUI(){

        JFrame window = new JFrame();
        window.setSize(1200, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);

        

        
        JPanel cookiePanel = new JPanel();
        cookiePanel.setBounds(100, 220, 200, 200);
        cookiePanel.setBackground(Color.black);
        window.add(cookiePanel);

        ImageIcon cookie = new ImageIcon(getClass().getClassLoader().getResource("DittoInHat141x141.png"));
        
        
        JButton cookieButton = new JButton();
        cookieButton.setBackground(Color.black);
        cookieButton.setFocusPainted(false);
        cookieButton.setBorder(null);
        cookieButton.setIcon(cookie);
        cookiePanel.add(cookieButton);

        

        window.setVisible(true);
        
    }
    
}
