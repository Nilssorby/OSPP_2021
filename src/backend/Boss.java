package backend;

import java.io.Serializable;

public class Boss implements Serializable{
    private int healthPoints, rangedAttack, magicAttack, stabAttack, defenceAttack, damageReduction;
    private String bossName, strongAgainst, weakAgainst;


    public Boss(){
        bossName = "Standard Boss";
        strongAgainst = "nothing";
        weakAgainst = "nothing";
        healthPoints = 1000;
        rangedAttack = 1;
        magicAttack = 1;
        stabAttack = 1;
        defenceAttack = 1;
        damageReduction = 1;
    }

    public Boss(String bossName, int healthPoints, int rangedAttack, int magicAttack, int stabAttack, int defenceAttack, int damageReduction, String strongAgainst, String weakAgainst){
        this.bossName = bossName;
        this.healthPoints = healthPoints;
        this.rangedAttack = rangedAttack;
        this.magicAttack = magicAttack;
        this.stabAttack = stabAttack;
        this.defenceAttack = defenceAttack;
        this.damageReduction = damageReduction;
        this.strongAgainst = strongAgainst;
        this.weakAgainst = weakAgainst;
    }

    public String getBossName(){
        return bossName;
    }

    public String getStrongAgainst(){
        return strongAgainst;
    }

    public String getWeakAgainst(){
        return weakAgainst;
    }

    public int getHealthPoints(){
        return healthPoints;
    }

    public int getRangedAttack(){
        return rangedAttack;
    }

    public int getMagicAttack(){
        return magicAttack;
    }

    public int getStabAttack(){
        return stabAttack;
    }

    public int getDefenceAttack(){
        return defenceAttack;
    }

    public int getDamageReduction(){
        return damageReduction;
    }

    public void setBossName(String bossName){
        this.bossName = bossName;
    }

    public void setStrongAgainst(String strongAgainst){
        this.strongAgainst = strongAgainst;
    }

    public void setWeakAgainst(String weakAgainst){
        this.weakAgainst = weakAgainst;
    }

    public void setHealthPoints(int healthPoints){
        this.healthPoints = healthPoints;
    }

    public void setRangedAttack(int rangedAttack){
        this.rangedAttack = rangedAttack;
    }

    public void setMagicAttack(int magicAttack){
        this.magicAttack = magicAttack;
    }

    public void setStabAttack(int stabAttack){
        this.stabAttack = stabAttack;
    }

    public void setDefenceAttack(int defenceAttack){
        this.defenceAttack = defenceAttack;
    }

    public void setDamageReduction(int damageReduction){
        this.damageReduction = damageReduction;
    }

    public String wellcomeMessage(){
        return "I AM THE MIGHTY " + this.bossName + " FEAR MY WRATH!";
    }

}
