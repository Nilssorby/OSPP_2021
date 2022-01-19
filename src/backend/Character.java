package backend;

import java.io.Serializable;

public class Character implements Serializable{
    private Equipment equipment;
    private Attributes attributes;
    private int healthPoints, energyPoints;
    private String userName, userPassword;

    private int EVOPoints;

    public Character() {
        healthPoints = 100;
        energyPoints = 100;
        equipment = new Equipment();
        attributes = new Attributes();
        EVOPoints = 0;
        userName = "test character";
        userPassword = "test";
    }

    public Character(String userName, String userPassword, int healthPoints, int energyPoints, Equipment equipment,
            Attributes attributes, int evoPoints) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.healthPoints = healthPoints;
        this.energyPoints = energyPoints;
        this.equipment = equipment;
        this.attributes = attributes;
        this.EVOPoints = evoPoints;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return userPassword;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public int getEVOPoints() {
        return EVOPoints;
    }

    public void setEVOPoints(int eVOPoints) {
        this.EVOPoints += eVOPoints;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.userPassword = password;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getEnergyPoints() {
        return energyPoints;
    }

    public void setEnergyPoints(int energyPoints) {
        this.energyPoints = energyPoints;
    }

    public void increaseXp(String id) {
        switch (id) {
            case "eating":
                System.out.println(attributes.getXp("eating") + 1 + " (needed for levelup: "
                        + attributes.levelThreshold("eating") + ")");
                attributes.setXp("eating", 1);
                attributes.checkLevelup(attributes.getXp("eating"), "eating");
                setEVOPoints(attributes.getLevel("eating"));
                break;
            case "mining":
                System.out.println(attributes.getXp("mining") + 1 + " (needed for levelup: "
                        + attributes.levelThreshold("mining") + ")");
                attributes.setXp("mining", getEquipment().getMiningPickaxe());
                attributes.checkLevelup(attributes.getXp("mining"), "mining");

                // pleb mode :boring:
                setEVOPoints(getEquipment().getMiningPickaxe());

                // developer mode :cool:
                // setEVOPoints(50);
                break;
            case "woodcutting":
                System.out.println(attributes.getXp("woodcutting") + 1 + " (needed for levelup: "
                        + attributes.levelThreshold("woodcutting") + ")");
                attributes.setXp("woodcutting", getEquipment().getWoodcuttingAxe());
                attributes.checkLevelup(attributes.getXp("woodcutting"), "woodcutting");
                setEVOPoints(getEquipment().getWoodcuttingAxe());
                break;
            case "hunting":
                System.out.println(attributes.getXp("hunting") + 1 + " (needed for levelup: "
                        + attributes.levelThreshold("hunting") + ")");
                attributes.setXp("hunting", getEquipment().getHuntingRifle());
                attributes.checkLevelup(attributes.getXp("hunting"), "hunting");
                setEVOPoints(getEquipment().getHuntingRifle());
                break;
            case "attack":
                System.out.println(attributes.getXp("attack") + 1 + " (needed for levelup: "
                        + attributes.levelThreshold("attack") + ")");
                attributes.setXp("attack", getEquipment().getWeapon());
                attributes.checkLevelup(attributes.getXp("attack"), "attack");
                setEVOPoints(getEquipment().getWeapon());
                break;
            case "ranged":
                System.out.println(attributes.getXp("ranged") + 1 + " (needed for levelup: "
                        + attributes.levelThreshold("ranged") + ")");
                attributes.setXp("ranged", getEquipment().getBow());
                attributes.checkLevelup(attributes.getXp("ranged"), "ranged");
                setEVOPoints(getEquipment().getBow());
                break;
            case "defence":
                System.out.println(attributes.getXp("defence") + 1 + " (needed for levelup: "
                        + attributes.levelThreshold("defence") + ")");
                attributes.setXp("defence", getEquipment().getArmour());
                attributes.checkLevelup(attributes.getXp("defence"), "defence");
                setEVOPoints(getEquipment().getArmour());
                break;
            case "firemaking":
                System.out.println(attributes.getXp("firemaking") + 1 + " (needed for levelup: "
                    + attributes.levelThreshold("firemaking") + ")");
                attributes.setXp("firemaking", getEquipment().getFiremaking());
                attributes.checkLevelup(attributes.getXp("firemaking"), "firemaking");
                setEVOPoints(getEquipment().getFiremaking());
                break;
        }

    }

    public void levelUpItem(String item, int EVOPoints) {
        switch (item) {
            case "mining":
                if (EVOPoints >= equipment.costOfUpgrade(item)) {
                    int cost = equipment.costOfUpgrade(item);
                    System.out.println("Adding " + cost * (-1) + " to current EVO Balance: " + getEVOPoints());
                    equipment.setMiningPickaxe(equipment.getMiningPickaxe() + 1);
                    setEVOPoints(cost * (-1));
                    System.out.println("Current balance is now: " + getEVOPoints());

                } else {
                    System.out.println("Not enough EVO Points!");
                }
                break;
            case "woodcutting":
                if (EVOPoints >= equipment.costOfUpgrade(item)) {
                    int cost = equipment.costOfUpgrade(item);
                    equipment.setWoodcuttingAxe(equipment.getWoodcuttingAxe() + 1);
                    setEVOPoints(cost * (-1));
                } else {
                    System.out.println("Not enough EVO Points!");
                }
                break;
            case "hunting":
                if (EVOPoints >= equipment.costOfUpgrade(item)) {
                    int cost = equipment.costOfUpgrade(item);
                    equipment.setHuntingRifle(equipment.getHuntingRifle() + 1);
                    setEVOPoints(cost * (-1));
                } else {
                    System.out.println("Not enough EVO Points!");
                }
                break;
            case "attack":
                if (EVOPoints >= equipment.costOfUpgrade(item)) {
                    int cost = equipment.costOfUpgrade(item);
                    equipment.setWeapon(equipment.getWeapon() + 1);
                    setEVOPoints(cost * (-1));
                } else {
                    System.out.println("Not enough EVO Points!");
                }
                break;
            case "defence":
                if (EVOPoints >= equipment.costOfUpgrade(item)) {
                    int cost = equipment.costOfUpgrade(item);
                    equipment.setArmour(equipment.getArmour() + 1);
                    setEVOPoints(cost * (-1));
                } else {
                    System.out.println("Not enough EVO Points!");
                }
                break;
            case "ranged":
                if (EVOPoints >= equipment.costOfUpgrade(item)) {
                    int cost = equipment.costOfUpgrade(item);
                    equipment.setBow(equipment.getBow() + 1);
                    setEVOPoints(cost * (-1));
                } else {
                    System.out.println("Not enough EVO Points!");
                }
                break;
            case "firemaking":
                if (EVOPoints >= equipment.costOfUpgrade(item)) {
                    int cost = equipment.costOfUpgrade(item);
                    equipment.setFiremaking(equipment.getFiremaking() + 1);
                    setEVOPoints(cost * (-1));
                } else {
                    System.out.println("Not enough EVO Points!");
                }
                break;
        }
    }

    public void printCharacter() {
        System.out.println("username: " + userName);
        System.out.println("evoPoints: " + getEVOPoints());
        System.out.println("Health: " + getHealthPoints());
        System.out.println("energyPoints: " + getEnergyPoints());
        System.out.println("eatingXp: " + getAttributes().getXp("eating"));
        System.out.println("huntingXp: " + getAttributes().getXp("hunting"));
        System.out.println("woodcuttingXp: " + getAttributes().getXp("woodcutting"));
        System.out.println("miningXp: " + getAttributes().getXp("miningLevel"));
    }
    
    public String toString() {
    	return  "Username:" +userName + " Password:" + userPassword + " Health:" + healthPoints + 
    			" Energy:" + energyPoints + " Equipment:" + equipment + " Attributes:" + attributes + 
    			" EVOPoints:" + EVOPoints;
    }
}
