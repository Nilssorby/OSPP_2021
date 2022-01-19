package backend;

import java.io.Serializable;

public class Equipment implements Serializable{

    private int miningPickaxe;
    private int woodcuttingAxe;
    private int huntingRifle;
    private int attackWeapon;
    private int defenceArmour;
    private int rangedBow;
    private int firemaking;

    public Equipment() {
        miningPickaxe = 1;
        woodcuttingAxe = 1;
        huntingRifle = 1;
        attackWeapon = 1;
        defenceArmour = 1;
        rangedBow = 1;
        firemaking = 1;
    }

    public int getFiremaking() {
        return firemaking;
    }

    public void setFiremaking(int fire) {
        this.firemaking = fire;
    }

    public int getBow() {
        return rangedBow;
    }

    public void setBow(int bow) {
        this.rangedBow = bow;
    }

    public int getArmour() {
        return defenceArmour;
    }

    public void setArmour(int armour) {
        this.defenceArmour = armour;
    }

    public int getWeapon() {
        return attackWeapon;
    }

    public void setWeapon(int weapon) {
        this.attackWeapon = weapon;
    }

    public int getHuntingRifle() {
        return huntingRifle;
    }

    public void setHuntingRifle(int huntingRifle) {
        this.huntingRifle = huntingRifle;
    }

    public int getWoodcuttingAxe() {
        return woodcuttingAxe;
    }

    public void setWoodcuttingAxe(int woodcuttingAxe) {
        this.woodcuttingAxe = woodcuttingAxe;
    }

    public int getMiningPickaxe() {
        return miningPickaxe;
    }

    public void setMiningPickaxe(int miningPickaxe) {
        this.miningPickaxe = miningPickaxe;
    }

    public int costOfUpgrade(String item) {
        switch(item) {
            case "mining":
                return getMiningPickaxe() * 500;
            case "woodcutting":
                return getWoodcuttingAxe() * 500;
            case "hunting":
                return getHuntingRifle() * 500;
            case "attack":
                return getWeapon() * 500;
            case "defence":
                return getArmour() * 500;
            case "ranged":
                return getBow() * 500;
            case "firemaking":
                return getFiremaking() * 500;
            default:
                return 0;
        }
    }

    public String[] showAllEquipmentCost() {
        String[] list = {"Upgrade mining: " + costOfUpgrade("mining"),
                         "Upgrade hunting: " + costOfUpgrade("hunting"),
                         "Upgrade woodcutting: " + costOfUpgrade("woodcutting"),
                         "Upgrade firemaking: " + costOfUpgrade("firemaking"),
                         "Upgrade attack: " + costOfUpgrade("attack"),
                         "Upgrade defence: " + costOfUpgrade("defence"),
                         "Upgrade ranged: " + costOfUpgrade("ranged"),
                        };
        return list;
    }

    public String[] showAllEquipmentLevels() {
        String[] list = {"Mining (level " + miningPickaxe + ")",
                         "Hunting (level " + huntingRifle + ")",
                         "Woodcutting (level " + woodcuttingAxe + ")",
                         "Firemaking (level " + firemaking + ")",
                         "Atttack (level " + attackWeapon + ")",
                         "Defence (level " + defenceArmour + ")",
                         "Ranged (level " + rangedBow + ")"};
        return list;
    }
    
}
