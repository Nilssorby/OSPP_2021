package backend;

import java.util.ArrayList;
import java.util.Random;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.Math;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

public class Combat {

    //Background
    private Color backgroundColor = new Color(0x7678ed);

    private Image dummy;

    private Timer timer;

    private int rangedAttack, magicAttack, stabAttack, defenceAttack, damageReduction, HP;

    // BORDER THAT CAN BE USED FOR JCOMPONTENS
    Border border = BorderFactory.createLineBorder(Color.black, 1);
   
    public Combat(Character character) {
        rangedAttack = character.getAttributes().getLevel("ranged") + character.getAttributes().getLevel("dexterity");
        magicAttack = character.getAttributes().getLevel("magic") + character.getAttributes().getLevel("intelligence");
        stabAttack = character.getAttributes().getLevel("attack") + character.getAttributes().getLevel("strength");
        defenceAttack = character.getAttributes().getLevel("defence") + character.getAttributes().getLevel("attack");
        damageReduction = character.getAttributes().getLevel("defence");
        HP = character.getHealthPoints(); ///used as enemy health temporarily
        
    }

    //Maybe add constructor for setting integers to all levels => adding monsters/bosses with set values for these

    // dmg reduction is not used yet
     public int dmgTaken(String id) {
        switch(id) {
            case "rangedAttack":
                return getHit("rangedAttack") * (1 - (damageReduction / 4));
            case "magicAttack":
                return getHit("magicAttack") * (1 - (damageReduction / 4));
            case "stabAttack":
                return getHit("stabAttack") * (1 - (damageReduction / 4));
            case "defenceAttack":
                return getHit("defenceAttack") * (1 - (damageReduction / 4));
            default:
                return 0;
        }
    }

    public double dmgTriangle(String enemy, String style){
        switch(style){
            case "rangedAttack":
                
                return dmgTriangleRanged(enemy);
            case "magicAttack":
               
                return dmgTriangleMagic(enemy);
            case "meleeAttack":
          
                return dmgTriangleMelee(enemy);
            default:
                return 1.00;
        }
        
    }

    //Multiplier for Ranged weapon style 
    public double dmgTriangleRanged(String enemy) {
        switch(enemy) {
            case "Ranged Enemy":
                return 1.00;
            case "Magic Enemy":
                return 1.25;
            case "Melee Enemy":
                return 0.95;
            default:
                return 1.00;
        }
    }
    //multipliers against different types of enemies
    public double dmgTriangleMelee(String id) {
        switch(id) {
            case "Ranged Enemy":
                System.out.println("Enemy is weak against your weapon type");
                return 1.25;
            case "Magic Enemy":
                System.out.println("Enemy is weak against your weapon type");
                return 0.50;
            case "Melee Enemy":
                return 1.00;
            default:
                return 1.00;
        }
    }
    //multipliers against different types of enemies
    public double dmgTriangleMagic(String id) {
        switch(id) {
            case "Ranged Enemy":
                System.out.println("Enemy is weak against your weapoon type");
                return 0.85;
            case "Magic Enemy":
                return 1.00;
            case "Melee Enemy":
                System.out.println("Enemy is weak against your weapoon type");
                return 1.25;
            default:
                return 1.00;
        }
    }

    // Output what attack style is being used.
    public void showStyle(String id) {
        switch(id) {
            case "rangedAttack":
                System.out.println("Current weapon style: Ranged");
                break;
            case "magicAttack":
                System.out.println("Current weapon style: Magic");
                break;
            case "stabAttack":
                System.out.println("Current weapon style: Melee");
                break;
            case "defenceAttack":
                System.out.println("Current weapon style: Defense");
                break;
            default:
                System.out.println("");
        }
    }

    // Output what attack style is being used.
    public void showEnemy(String id) {
        switch(id) {
            case "Ranged Enemy":
                System.out.println("You are fighting a ranged archer");
                break;
            case "magicAttack":
                System.out.println("You are fighting a wizard");
                break;
            case "stabAttack":
                System.out.println("You are fighting a rogue bandit");
                break;
            default:
                System.out.println("");
        }
    }

    // Aux func: Calculates the maxhit dmg
    public int getMaxHit(String id) {
        switch(id) {
            case "rangedAttack":
                return rangedAttack * 2;
            case "magicAttack":
                return magicAttack * 2;
            case "stabAttack":
                return stabAttack * 2;
            case "defenceAttack":
                return defenceAttack * 2;
            default:
                return 0;
        }
    }

    // Get damage after calculating for maxhit + randomness
    public int getHit(String id) {
        Random rand = new Random();
        int upperbound, hit_random;
        switch(id) {
            case "rangedAttack":
                //set max hit
                upperbound = getMaxHit(id) + 2;
                //randomize hit from 0 to max hit and then round to nearest integer
                hit_random = Math.round(rand.nextInt(upperbound));
                return hit_random;

            case "magicAttack":
                //set max hit
                upperbound = getMaxHit(id) + 2;
                //randomize hit from 0 to max hit and then round to nearest integer
                hit_random = Math.round(rand.nextInt(upperbound));
                return hit_random;

            case "stabAttack":
                //set max hit
                upperbound = getMaxHit(id) + 2;
                //randomize hit from 0 to max hit and then round to nearest integer
                hit_random = Math.round(rand.nextInt(upperbound));
                return hit_random;

            case "defenceAttack":
                //set max hit
                upperbound = getMaxHit(id) + 2;
                //randomize hit from 0 to max hit and then round to nearest integer
                hit_random = Math.round(rand.nextInt(upperbound));
                return hit_random;

            default:
                return 0;
        }
    }

    

    // Initiates the combat (starting with a timer)
    public void startCombat(String enemyType, String style) {

        showStyle(style);
        showEnemy(enemyType); 
        double weaponMultiplier = dmgTriangle(enemyType, style);
        

        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(0 >= HP){
                    System.out.println("Enemy defeated!");
                    timer.stop();
                    return;
                } 

                int dmg = getHit(style) * (int) weaponMultiplier;
                

                System.out.println("Enemy HP: " + HP);

                System.out.println("Incoming dmg: " + dmg);
                System.out.println("OOF \n");

                //Calculates amount of health lost after taking into account dmgreduction.
                HP = (HP - dmg); 

                if(0 >= HP ){
                    System.out.println("Enemy defeated!");
                    timer.stop();
                    
                    return;
                }        
                         
                }
            });

            System.out.println("Starting combat(timer) \n");
            timer.start();

            // Creates the frame for the attack button
        JFrame f = new JFrame();
        f.setBounds(420,420,400,400);
        f.setBackground(backgroundColor);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - f.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - f.getHeight()) / 2);
        f.setLocation(x, y);

        JButton b = new JButton("Attack!");
        b.setPreferredSize(new Dimension(150,159));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(0 >= HP){
                    System.out.println("Enemy defeated!");
                    timer.stop();
                    f.dispose();
                    return;
                } 

                int dmg = getHit(style) * (int) weaponMultiplier;
                

                System.out.println("Enemy HP: " + HP);

                System.out.println("Incoming dmg: " + dmg);
                System.out.println("OOF \n");

                //Calculates amount of health lost after taking into account dmgreduction.
                HP = (HP - dmg); 

                if(0 >= HP ){
                    System.out.println("Enemy defeated!");
                    timer.stop();
                    f.dispose();
                    return;
                }        
               // timer.stop();
              // f.dispose();
            }
        });    

        b.setBackground(null);
        b.setBorder(null);
        b.setFocusPainted(false);

        /*
        try {
            dummy = ImageIO.read(getClass().getResource("images/Training_Dummy.png"));
            b.setIcon(new ImageIcon(dummy));
        } catch (Exception exc) {
            System.out.println(exc);
        }
        */

        f.getContentPane().add(b);
        f.setVisible(true);
    }


    //Makes the frame for the weapon style selection
    public void selectWeapon(Combat combat) {

        String enemyType = battleInfo(randomizeEnemy()); 

        

        
       
        JFrame stylesFrame = new JFrame();
        stylesFrame.setBounds(420,420,500,500);
        stylesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - stylesFrame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - stylesFrame.getHeight()) / 2);
        stylesFrame.setLocation(x, y);

        JLabel text = new JLabel("You are fighting a " + enemyType + " enemy! (They're weak against " + triangleInfo(enemyType) + ")");
        text.setBounds(75,0,500,500);
        stylesFrame.add(text);

        System.out.println("Choose a weapon style");

        JLabel text2 = new JLabel("Choose a weapon style.");
        text2.setBounds(175,-100,500,500);
        stylesFrame.add(text2);

        /*
        JLabel j = new JLabel("You are facing a magician (weak against ranged)");
        j.setBounds(50,50,500,500);
        stylesFrame.add(j);

        JLabel j2 = new JLabel("You are facing a magician (weak against ranged)");
        j2.setBounds(50,50,500,500);
        stylesFrame.add(j2);

        JLabel j3 = new JLabel("You are facing a magician (weak against ranged)");
        j3.setBounds(50,50,500,500);
        stylesFrame.add(j3);
        */
        
        JPanel stylesPanel = new JPanel();
        stylesPanel.setBounds(420,420, 500, 500);
        stylesPanel.setBackground(this.backgroundColor);
        stylesPanel.setBorder(border);
        
        /*
        JButton defenseButton = new JButton("Shield Stance");
        defenseButton.setPreferredSize(new Dimension(150,50));
        defenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            combat.startCombat(enemyType, "defenceAttack");         
            stylesFrame.dispose();
            }
        });
        stylesPanel.add(defenseButton);
        */

        JButton rangedButton = new JButton("Ranged Stance");
        rangedButton.setPreferredSize(new Dimension(150,50));
        rangedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            combat.startCombat(enemyType, "rangedAttack");         
            stylesFrame.dispose();
            }
        });
        stylesPanel.add(rangedButton);
        
        JButton stabButton = new JButton("Melee Stance");
        stabButton.setPreferredSize(new Dimension(150,50));
        stabButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            combat.startCombat(enemyType, "stabAttack");         
            stylesFrame.dispose();
            }
        });
        stylesPanel.add(stabButton);

        JButton magicButton = new JButton("Magic Attack");
        magicButton.setPreferredSize(new Dimension(150,50));
        magicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            combat.startCombat(enemyType,"magicAttack");         
            stylesFrame.dispose();
            }
        });
        stylesPanel.add(magicButton);
    
        // ADD ALL PANELS USED TO IDLEWINDOW JFRAME
        stylesFrame.getContentPane().add(stylesPanel);
        

        // SHOW JFRAME
        stylesFrame.setVisible(true);
    }

    /*
    //Makes the frame for the weapon style selection
    public void selectEnemyType(Combat combat, String attackStyle) {
       
        JFrame enemyFrame = new JFrame();
        enemyFrame.setBounds(420,420,500,500);
        enemyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //JLabel j = new JLabel("You are facing a magician (weak against ranged)");
        //j.setBounds(50,50,500,500);
        //stylesFrame.add(j);

        JPanel enemyPanel = new JPanel();
        enemyPanel.setBounds(420,420, 500, 500);
        enemyPanel.setBackground(this.backgroundColor);
        enemyPanel.setBorder(border);
        
        
        JButton rangedEnemyButton = new JButton("Ranged Enemy");
        rangedEnemyButton.setPreferredSize(new Dimension(150,50));
        rangedEnemyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            combat.startCombat("RangedEnemy",attackStyle);         
            enemyFrame.dispose();
            }
        });
        enemyPanel.add(rangedEnemyButton);
        
        JButton stabEnemyButton = new JButton("Melee Enemy");
        stabEnemyButton.setPreferredSize(new Dimension(150,50));
        stabEnemyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            combat.startCombat("MeleeEnemy", attackStyle);         
            enemyFrame.dispose();
            }
        });
        enemyPanel.add(stabEnemyButton);

        JButton magicEnemyButton = new JButton("Magic Enemy");
        magicEnemyButton.setPreferredSize(new Dimension(150,50));
        magicEnemyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            combat.startCombat("MagicEnemy", attackStyle);         
            enemyFrame.dispose();
            }
        });
        enemyPanel.add(magicEnemyButton);
    
        // ADD ALL PANELS USED TO IDLEWINDOW JFRAME
        enemyFrame.getContentPane().add(enemyPanel);

        // SHOW JFRAME
        enemyFrame.setVisible(true);
    }
*/
    public String randomizeEnemy(){
        String [] arr = {"Ranged", "Melee", "Magic"};
        Random random = new Random();

        int select = random.nextInt(arr.length);

        //System.out.println("You are fighting... " + arr[select]);

        return arr[select];
    }

    public String battleInfo(String id) {
        switch(id) {
            case "Ranged":
                System.out.println("You are facing an archer (weak against melee)");
                return "Ranged";
            case "Magic":
                System.out.println("You are facing a magic wizard (weak against ranged)");
                return "Magic";
            case "Melee":
                System.out.println("You are facing a rogue bandit (weak against magic)");
                return "Melee";
            default:
                return "Null";
        }
    }

    public String triangleInfo(String id) {
        switch(id) {
            case "Ranged":
                /// Weak against
                return "Melee";
            case "Magic":
                /// Weak against
                return "Ranged";
            case "Melee":
                /// Weak against
                return "Magic";
            default:
                return "Null";
        }
    }
    


    static public void main(String[] args) {
        Character character = new Character();
        Combat combat = new Combat(character);

        System.out.println("Player base max hit: " + combat.getMaxHit("defenceAttack"));
        System.out.println("Current enemy health: " + combat.HP);

        combat.selectWeapon(combat);

        //combat.startCombat("defenceAttack");
    }
    
}