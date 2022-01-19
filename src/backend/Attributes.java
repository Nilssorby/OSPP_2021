package backend;

import java.io.Serializable;

import java.util.*;

public class Attributes implements Serializable{
    private int eatingLevel, miningLevel, huntingLevel, woodcuttingLevel, totalLevel, attackLevel, rangedLevel, defenceLevel, firemakingLevel,
                intLevel, dexLevel, strLevel, eatingXp, miningXp, woodcuttingXp, huntingXp, attackXp, rangedXp, defenceXp, firemakingXp, intXp, dexXp, strXp; 

    public Attributes() {
        eatingLevel = 1;
        miningLevel = 1;
        huntingLevel = 1;
        attackLevel = 1;
        rangedLevel = 1;
        defenceLevel = 1;
        woodcuttingLevel = 1;
        firemakingLevel = 1;
        intLevel = 1;
        dexLevel = 1;
        strLevel = 1;
        totalLevel = 11;

        eatingXp = 0;
        miningXp = 0;
        woodcuttingXp = 0;
        huntingXp = 0;
        attackXp = 0;
        rangedXp = 0;
        defenceXp = 0;
        firemakingXp = 0;
        intXp = 0;
        dexXp = 0;
        strXp = 0;
    }

    public int getXp(String id) {
        switch (id) {
            case "eating":
                return eatingXp;
            case "mining":
                return miningXp;
            case "woodcutting":
                return woodcuttingXp;
            case "hunting":
                return huntingXp;
            case "total":
                return totalLevel;
            case "attack":
                return attackXp;
            case "ranged":
                return rangedXp;
            case "defence":
                return defenceXp;
            case "firemaking":
                return firemakingXp;
            case "intelligence":
                return intXp;
            case "dexterity":
                return dexXp;
            case "strength":
                return strXp;
            default:
                return 0;
        }
    }

    public long levelThreshold(String id) {
        // System.out.println(Math.round(0.04 * Math.pow(getLevel(id),3) + 0.8 *
        // Math.pow(getLevel(id),2) + 2 * getLevel(id)));

        //Generation 1 Pokemon method
        return Math.round((4 * (Math.pow(getLevel(id),3))) / 5);

        // Disgea method
        //return Math.round(0.04 * Math.pow(getLevel(id), 3) + 0.8 * Math.pow(getLevel(id), 2) + 2 * getLevel(id));
    }

    public void convertXpToLevels() {
        HashMap<Integer, String> map = new HashMap<>();

        map.put(eatingXp, "eating");
        map.put(miningXp, "mining");
        map.put(woodcuttingXp, "woodcutting");
        map.put(huntingXp, "hunting");
        map.put(attackXp, "attack");
        map.put(rangedXp, "ranged");
        map.put(defenceXp, "defence");
        map.put(firemakingXp, "firemaking");
        map.put(intXp, "intelligence");
        map.put(dexXp, "dexterity");
        map.put(strXp, "strength");

        for (int id : map.keySet()) {
            while (id > levelThreshold(map.get(id))) {
                checkLevelup(id, map.get(id));
            }
        }
    }

    public void checkLevelup(int xp, String id) {
        if (xp >= levelThreshold(id)) {
            switch (id) {
                case "eating":
                    eatingLevel += 1;
                    totalLevel += 1;
                    System.out.println("Eating level is now: " + eatingLevel);
                    break;
                case "mining":
                    miningLevel += 1;
                    totalLevel += 1;
                    System.out.println("Mining level is now: " + miningLevel);
                    break;
                case "woodcutting":
                    woodcuttingLevel += 1;
                    totalLevel += 1;
                    System.out.println("Woodcutting level is now: " + woodcuttingLevel);
                    break;
                case "hunting":
                    huntingLevel += 1;
                    totalLevel += 1;
                    System.out.println("Hunting level is now: " + huntingLevel);
                    break;
                case "attack":
                    attackLevel += 1;
                    totalLevel += 1;
                    System.out.println("Attack level is now: " + attackLevel);
                    break;
                case "ranged":
                    rangedLevel += 1;
                    totalLevel += 1;
                    System.out.println("Ranged level is now: " + rangedLevel);
                    break;
                case "defence":
                    defenceLevel += 1;
                    totalLevel += 1;
                    System.out.println("Defence level is now: " + defenceLevel);
                    break;
                case "firemaking":
                    firemakingLevel += 1;
                    totalLevel += 1;
                    System.out.println("Firemaking level is now: " + firemakingLevel);
                    break;
                case "intelligence":
                    intLevel += 1;
                    totalLevel += 1;
                    System.out.println("Intelligence level is now: " + intLevel);
                    break;
                case "dexterity":
                    dexLevel += 1;
                    totalLevel += 1;
                    System.out.println("Dexterity level is now: " + dexLevel);
                    break;
                case "strength":
                    strLevel += 1;
                    totalLevel += 1;
                    System.out.println("Strength level is now: " + strLevel);
                    break;
            }
        }
    }

    public int waitingTime(int level) {
        return level * 1000 * 2;
    }

    public int getLevel(String id) {
        switch (id) {
            case "eating":
                return eatingLevel;

            case "mining":
                return miningLevel;

            case "woodcutting":
                return woodcuttingLevel;

            case "hunting":
                return huntingLevel;

            case "attack":
                return attackLevel;

            case "ranged":
                return rangedLevel;

            case "defence":
                return defenceLevel;

            case "firemaking":
                return firemakingLevel;

            case "intelligence":
                return intLevel;
                
            case "dexterity":
                return dexLevel;
                
            case "strength":
                return strLevel;
            
            case "total":
                return totalLevel;

            default:
                return 0;
        }
    }

    public void setLevel(String id, int level) {
        switch (id) {
            case "eating":
                eatingLevel = level;
                break;

            case "mining":
                miningLevel = level;
                break;

            case "woodcutting":
                woodcuttingLevel = level;
                break;

            case "hunting":
                huntingLevel = level;
                break;

            case "attack":
                attackLevel = level;
                break;

            case "ranged":
                rangedLevel = level;
                break;

            case "defence":
                defenceLevel = level;
                break;

            case "firemaking":
                firemakingLevel = level;
                break;

            case "intelligence":
                intLevel = level;
                break;

            case "dexterity":
                dexLevel = level;
                break;

            case "strength":
                strLevel = level;
                break;

            case "total":
                totalLevel = level;
                break;

            default:
                break;
        }
    }

    public String[] showAllLevels() {
        String[] list = { "Eating: " + eatingLevel, "Hunting: " + huntingLevel, "Woodcutting: " + woodcuttingLevel,
                "Firemaking: " + firemakingLevel, "Mining: " + miningLevel, "Attack: " + attackLevel,
                "Ranged: " + rangedLevel, "Defence: " + defenceLevel, "Intelligence: " + intLevel, "Dexterity: " + dexLevel, "Strength: " + strLevel, "Total level: " + totalLevel };
        return list;
    }

    public String[] showAllXp() {
        String[] list = { "EatingXp: " + eatingXp, "HuntingXp: " + huntingXp, "WoodcuttingXp: " + woodcuttingXp,
                "FiremakingXp: " + firemakingXp, "MiningXp: " + miningXp, "AttackXp: " + attackXp,
                "RangedXp: " + rangedXp, "DefenceXp: " + defenceXp, "IntXp: " + intXp, "DexXp: " + dexXp, "StrXp: " + strXp };
        return list;
    }

    public void setXp(String id, int xp) {
        switch (id) {
            case "eating":
                eatingXp += xp;
                break;
            case "hunting":
                huntingXp += xp;
                break;
            case "woodcutting":
                woodcuttingXp += xp;
                break;
            case "firemaking":
                firemakingXp += xp;
                break;
            case "attack":
                attackXp += xp;
                break;
            case "ranged":
                rangedXp += xp;
                break;
            case "defence":
                defenceXp += xp;
                break;
            case "mining":
                miningXp += xp;
                break;
            case "intelligence":
                intXp += xp;
                break;
            case "dexterity":
                dexXp += xp;
                break;
            case "strength":
                strXp += xp;
                break;
        }
    }

}
