package frontend.src;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import backend.Character;

public class UpgradeScreen {
    private JFrame f;
    private Color backgroundColor = new Color(0x7678ed); 
    private JPanel upgrades, backPanel, evoPanel;
    private JLabel evoLabel;
    private JButton upgradeHunting, upgradeMining, upgradeWoodcutting, upgradeAttack, upgradeDefence, upgradeFiremaking, upgradeRanged;

    public UpgradeScreen(int windowWidth, int windowHeight, Character character, IdleActionsScreen idleActionsScreen) {
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(windowWidth, windowHeight);
        f.setLayout(null);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - windowWidth) / 2);
        int y = (int) ((dimension.getHeight() - windowHeight) / 2);
        f.setLocation(x, y);

        backPanel = new JPanel();
        backPanel.setBounds(5, 0, 100, 50);
        backPanel.setBackground(this.backgroundColor);

        // button for going back to idle screen
        JButton backButton = new JButton("Back");
        customizeButton(backButton);
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setVisible(false);
                idleActionsScreen.showWindow();
            }
        });
        backPanel.add(backButton);

        evoPanel = new JPanel(new BorderLayout());
        evoPanel.setBounds(110, 0, 150, 50);
        evoPanel.setBackground(this.backgroundColor);

        // label for EVO-Points
        evoLabel = new JLabel("EVO Points: ");
        evoLabel.setText("EVO Points: " + character.getEVOPoints());
        evoPanel.add(evoLabel);

        upgrades = new JPanel();
        upgrades.setBounds(150, 75, 300, 600);
        upgrades.setBackground(this.backgroundColor);

        // button for upgrade huntingRifle
        upgradeHunting = new JButton("Hunting level: " + (character.getEquipment().getHuntingRifle() + 1) + " (Cost : "
                + character.getEquipment().costOfUpgrade("hunting") + ")");
        customizeButton(upgradeHunting);
        upgradeHunting.setPreferredSize(new Dimension(300, 50));
        upgradeHunting.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                character.levelUpItem("hunting", character.getEVOPoints());
                upgradeHunting.setText("Hunting level: " + (character.getEquipment().getHuntingRifle() + 1) + " (Cost : "
                        + character.getEquipment().costOfUpgrade("hunting") + ")");
                evoLabel.setText("EVO Points: " + character.getEVOPoints());
            }
        });

        // button for upgrade woodcuttingAxe
        upgradeWoodcutting = new JButton("Woodcutting level: " + (character.getEquipment().getWoodcuttingAxe() + 1)
                + " (Cost : " + character.getEquipment().costOfUpgrade("woodcutting") + ")");
        customizeButton(upgradeWoodcutting);
        upgradeWoodcutting.setPreferredSize(new Dimension(300, 50));
        upgradeWoodcutting.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                character.levelUpItem("woodcutting", character.getEVOPoints());
                upgradeWoodcutting.setText("Woodcutting level: " + (character.getEquipment().getWoodcuttingAxe() + 1)
                        + " (Cost : " + character.getEquipment().costOfUpgrade("woodcutting") + ")");
                evoLabel.setText("EVO Points: " + character.getEVOPoints());
            }
        });

        // button for upgrade miningPickaxe
        upgradeMining = new JButton("Mining level: " + (character.getEquipment().getMiningPickaxe() + 1) + " (Cost : "
                + character.getEquipment().costOfUpgrade("mining") + ")");
        customizeButton(upgradeMining);
        upgradeMining.setPreferredSize(new Dimension(300, 50));
        upgradeMining.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                character.levelUpItem("mining", character.getEVOPoints());
                upgradeMining.setText("Mining level: " + (character.getEquipment().getMiningPickaxe() + 1)
                        + " (Cost : " + character.getEquipment().costOfUpgrade("mining") + ")");
                evoLabel.setText("EVO Points: " + character.getEVOPoints());
            }
        });

        // button for upgrade miningPickaxe
        upgradeAttack = new JButton("Attack level: " + (character.getEquipment().getWeapon() + 1) + " (Cost : "
                + character.getEquipment().costOfUpgrade("attack") + ")");
        customizeButton(upgradeAttack);
        upgradeAttack.setPreferredSize(new Dimension(300, 50));
        upgradeAttack.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                character.levelUpItem("attack", character.getEVOPoints());
                upgradeAttack.setText("Attack level: " + (character.getEquipment().getWeapon() + 1)
                        + " (Cost : " + character.getEquipment().costOfUpgrade("attack") + ")");
                evoLabel.setText("EVO Points: " + character.getEVOPoints());
            }
        });

        // button for upgrade miningPickaxe
        upgradeDefence = new JButton("Defence level: " + (character.getEquipment().getArmour() + 1) + " (Cost : "
                + character.getEquipment().costOfUpgrade("defence") + ")");
        customizeButton(upgradeDefence);
        upgradeDefence.setPreferredSize(new Dimension(300, 50));
        upgradeDefence.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                character.levelUpItem("defence", character.getEVOPoints());
                upgradeDefence.setText("Defence level: " + (character.getEquipment().getMiningPickaxe() + 1)
                        + " (Cost : " + character.getEquipment().costOfUpgrade("defence") + ")");
                evoLabel.setText("EVO Points: " + character.getEVOPoints());
            }
        });

        // button for upgrade miningPickaxe
        upgradeRanged = new JButton("Ranged level: " + (character.getEquipment().getBow() + 1) + " (Cost : "
                + character.getEquipment().costOfUpgrade("ranged") + ")");
        customizeButton(upgradeRanged);
        upgradeRanged.setPreferredSize(new Dimension(300, 50));
        upgradeRanged.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                character.levelUpItem("ranged", character.getEVOPoints());
                upgradeRanged.setText("Ranged level: " + (character.getEquipment().getBow() + 1)
                        + " (Cost : " + character.getEquipment().costOfUpgrade("ranged") + ")");
                evoLabel.setText("EVO Points: " + character.getEVOPoints());
            }
        });

        // button for upgrade miningPickaxe
        upgradeFiremaking = new JButton("Firemaking level: " + (character.getEquipment().getFiremaking() + 1) + " (Cost : "
                + character.getEquipment().costOfUpgrade("firemaking") + ")");
        customizeButton(upgradeFiremaking);
        upgradeFiremaking.setPreferredSize(new Dimension(300, 50));
        upgradeFiremaking.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                character.levelUpItem("firemaking", character.getEVOPoints());
                upgradeFiremaking.setText("Firemaking level: " + (character.getEquipment().getFiremaking() + 1)
                        + " (Cost : " + character.getEquipment().costOfUpgrade("firemaking") + ")");
                evoLabel.setText("EVO Points: " + character.getEVOPoints());
            }
        });

        upgrades.add(upgradeMining);
        upgrades.add(upgradeWoodcutting);
        upgrades.add(upgradeHunting);
        upgrades.add(upgradeAttack);
        upgrades.add(upgradeDefence);
        upgrades.add(upgradeFiremaking);
        upgrades.add(upgradeRanged);

        f.add(evoPanel);
        f.add(backPanel);
        f.add(upgrades);

        f.getContentPane().setBackground(this.backgroundColor);
        f.setVisible(true);

    }

    public void customizeButton(JButton button) {
        Border buttonBorder = new LineBorder(new Color(0xffcb69), 2);
        button.setBorder(buttonBorder);
        button.setOpaque(true);
        button.setBackground(new Color(0xffb703));
        button.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 20));
    }

}