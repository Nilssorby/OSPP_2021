package frontend.src;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import serverClient.*;
import backend.Character;
import backend.Login;
import backend.Register;
import database.databaseHandler;

public class LoginScreen {

    private JFrame window, registerWindow;
    private Color backgroundColor = new Color(0x7678ed);
    private JPanel passwordTextPanel, loginPanel, userNameTextPanel;
    private JLabel usernameText, passwordText;
    private JButton loginButton, registerButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel header;
    private JLabel errorText;
    private Client client;
    private int Failed;
    public int loginFailed = 1, registeredFailed = 2;

    //private Character character;

    private int windowWidth = 400, windowHeight = 400;

    public LoginScreen(Client client) {
        this.Failed = 0;
        createLoginUI();
        this.client = client;
    }

    public LoginScreen(Client client, int Failed) {
        this.Failed = Failed;
        createLoginUI();
        this.client = client;
    }

    private void createLoginUI() {
        window = new JFrame("Project Evo");
        window.setSize(windowWidth, windowHeight);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(this.backgroundColor);
        window.setLayout(null);

        centerWindow(window);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0xffcdb2));
        headerPanel.setBounds(0, 0, 400, 50);
        this.header = new JLabel("Welcome to Project EVO!");
        this.header.setBounds(75, 25, 300, 100);
        this.header.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 25));
        headerPanel.add(this.header);

        if(Failed == loginFailed){
            errorText = new JLabel("Wrong username or password!");
            errorText.setBounds(85, 60, 300, 100);
            Color textColor = new Color(120);
            textColor = Color.RED;
            errorText.setForeground(textColor);
            errorText.setFont(new Font("Serif", Font.BOLD, 15));
        } else if(Failed == registeredFailed) {
            errorText = new JLabel("Username already taken!");
            errorText.setBounds(85, 60, 300, 100);
            Color textColor = new Color(120);
            textColor = Color.RED;
            errorText.setForeground(textColor);
            errorText.setFont(new Font("Serif", Font.BOLD, 15));
        }

        userNameTextPanel = new JPanel();
        userNameTextPanel.setBounds(40, 145, 100, 25);
        userNameTextPanel.setBackground(this.backgroundColor);

        passwordTextPanel = new JPanel();
        passwordTextPanel.setBounds(40,175,100,25);

        usernameText = new JLabel("Username");
        passwordText = new JLabel("Password");

        userNameTextPanel.add(usernameText);
        passwordTextPanel.add(passwordText);
        passwordTextPanel.setBackground(this.backgroundColor);

        loginPanel = new JPanel();
        loginPanel.setBackground(this.backgroundColor);
        loginPanel.setBounds(125,142,150,60);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(150,25));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(150,25));

        loginPanel.add(usernameField);
        loginPanel.add(passwordField);
        
        loginButton = new JButton("Login");
        Border loginBorder = new LineBorder(new Color(0xffcb69), 3);
        loginButton.setBorder(loginBorder);
        loginButton.setBackground(new Color(0xf1dca7));
        loginButton.setOpaque(true);
        loginButton.setBounds(150, 205, 100, 30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLogin(usernameField.getText(), new String(passwordField.getPassword()));
            }
        });

        registerButton = new JButton("Register");
        Border registerBorder = new LineBorder(new Color(0xffcb69), 3);
        registerButton.setBorder(registerBorder);
        registerButton.setBackground(new Color(0xf1dca7));
        registerButton.setOpaque(true);
        registerButton.setBounds(150, 240, 100, 30);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Register(usernameField.getText(), new String(passwordField.getPassword()), client);
                window.setVisible(false);

            }
        });


        if(Failed == loginFailed || Failed == registeredFailed){
            window.getContentPane().add(errorText);
        }

        window.getContentPane().add(headerPanel);
        window.getContentPane().add(loginButton);
        window.getContentPane().add(registerButton);
        window.getContentPane().add(loginPanel);
        window.getContentPane().add(userNameTextPanel);
        window.getContentPane().add(passwordTextPanel);
        window.setVisible(true);
    }



    public void checkLogin(String username, String password) {
            new Login(username, password, client);
            window.setVisible(false);
    }

    //center window of screen
    public void centerWindow(JFrame window) {
        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - windowWidth) / 2);
        int y = (int) ((dimension.getHeight() - windowHeight) / 2);
        window.setLocation(x, y);

    }
    
}