package Idlegamefiles;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ProjectEVO extends JFrame {
    // non graphical variables
    private int points = 0;
    private int clicker = 1;
    private int clickerPrice = 20;

    // graphical variables
    int numberOfColumns = 5;

    Container container;

    JLabel EVOLabel;
    JButton increasePointsButton;

    JLabel clickerLabel;
    JButton increaseClickerButton;

    // buildings
    Building osmosis;
    boolean osmosisUnlocked;

    Building robot;
    boolean robotUnlocked;

    Building factory;
    boolean factoryUnlocked;

    public ProjectEVO() {
        container = getContentPane();
        container.setLayout(new GridLayout(5, 3));

        osmosis = new Building("Osmosis (Auto Growth)", 0, 1, 20);
        osmosisUnlocked = false;

        robot = new Building("Auto Growth II", 0, 5, 100);
        robotUnlocked = false;

        factory = new Building("Auto Growth III", 0, 10, 200);
        factoryUnlocked = false;

        // produce points by clicking
        EVOLabel = new JLabel("EVO points: " + points);
        increasePointsButton = new JButton("Increase EVO points");
        increasePointsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                points += clicker;
            }
        });

        // improve clicking production rate
        clickerLabel = new JLabel("Clicker Level: " + clicker); //show amounts of clicker levels purchased
        increaseClickerButton = new JButton("Improve Clicker (Costs: " + clickerPrice + ")");
        increaseClickerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                increaseClicker();
            }

            private void increaseClicker() {
                if(points >= clickerPrice) {
                    clicker++; //increment clicker level
                    points -= clickerPrice;
                    clickerPrice *= 2; //price of clicker upgrades increases exponentially
                    JOptionPane.showMessageDialog(null, "You have improved your clicker!");
                } else {
                    JOptionPane.showMessageDialog(null, "Not enough money!");
                }
            }
        });

        java.util.Timer actualizeProgress = new java.util.Timer();
        actualizeProgress.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                EVOLabel.setText("EVO Points: " + points);
                clickerLabel.setText("Clicker Level: " + clicker);
                increaseClickerButton.setText("Improve Clicker (Costs: " + clickerPrice + ")");
            }
        }, 0, 25);

        java.util.Timer getMoreBuildings = new java.util.Timer(); 
        getMoreBuildings.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (osmosisUnlocked == false && clicker >= 2) {
                    osmosis.unlock();
                    osmosisUnlocked = true;
                }
                if (robotUnlocked == false && osmosis.getLevel() >= 2) {
                    robot.unlock();
                    robotUnlocked = true;
                }         
                if (factoryUnlocked == false && robot.getLevel() >= 2) {
                    factory.unlock();
                    factoryUnlocked = true;
                }
            }
        }, 0, 2000);

        java.util.Timer produceWithBuildings = new java.util.Timer();
        produceWithBuildings.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                points += osmosis.getProductionRate() + robot.getProductionRate() + factory.getProductionRate();
            }
        }, 0, 1000);

        container.add(EVOLabel);
        container.add(increasePointsButton);
        container.add(new JLabel("")); // blank label
        container.add(clickerLabel);
        container.add(increaseClickerButton);
    }

    public class Building {
        // non graphical variables
        private String name;
        private int level;
        private int productionRate;
        private int costs;

        // graphical variables
        JLabel label;
        JButton button;

        public Building(String name, int level, int productionRate, int costs) {
            // non graphical variables
            this.name = name;
            this.level = level;
            this.productionRate = productionRate;
            this.costs = costs;

            // graphical variables
            label = new JLabel();
            button = new JButton();
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    improve();
                }
            });
        }

        public int getLevel() {
            return level;
        }

        public void unlock() {
            numberOfColumns += 3;
            container.setLayout(new GridLayout(numberOfColumns, 1));
            container.add(new JLabel(""));
            container.add(label);
            container.add(button);
            setSize(210, getHeight() + 120);
            actualize();
        }

        public void improve() {
            if(points >= costs) {
                level++;
                points -= costs;
                costs *= 2;
                JOptionPane.showMessageDialog(null, "You have improved " + name + "!");
            } else {
                JOptionPane.showMessageDialog(null, "Not enough minerals!");
            }
            actualize();
        }

        public int getProductionRate() {
            return productionRate * level;
        }

        public void actualize() {
            label.setText(name + " Prod. Rate: " + getProductionRate());
            button.setText("Improve (costs: " + costs + ")");
        }
    }

    public static void main(String[] args) {
        ProjectEVO ProjectEVO = new ProjectEVO();
        ProjectEVO.setTitle("PROJECT EVO");
        ProjectEVO.setSize(210, 200);
        ProjectEVO.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ProjectEVO.setVisible(true);
    }
}