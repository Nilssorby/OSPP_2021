package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import backend.Boss;
import backend.Character;

public class databaseHandler {

    public static String url = "jdbc:mysql://localhost:3306/sonoo";
    public static String uname = "root";
    public static String password = "12345678";

    public static void main(String[] args) throws SQLException {
        databaseHandler db = new databaseHandler();
        db.connectToDatabase();
        //Boss Chad = new Boss("Chad", 999, 3, 2, 100, 50, 25, "ranged", "magic");
        //Boss Chad = db.getBoss("Chad");
        //db.addBoss(Chad);
        //db.deleteBoss("Chad");
        
        //Character user = new Character();
        //user.setuserName("nils");
        //db.addCharacter(user);
        //user = db.getCharacter(user.getUsername());
        //getUsers();
        //db.deleteUser("nils");
        //getUsers();
    }

    // Connects to database, if no database exists it creates a new one.
    public void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (!checkTable("users")) {
            createUserTable();
        }
        if (!checkTable("bosses")) {
            createBossesTable();
        }
    }

    // Prints all users from database.
    public static void getUsers() {
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("select * from users");

            while (result.next()) {
                String tempString = "";
                for (int i = 1; i <= 16; i++) {

                    tempString += (result.getString(i) + " ");
                }
                System.out.println(tempString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // return Character containing entries for character attributes
    public Character getCharacter(String userName) {
        Character userCharacter = new Character();
        System.out.println("----XP BEFORE UPDATE--------");
        String[] list1 = userCharacter.getAttributes().showAllXp();
        for (int i = 0; i < list1.length; i++) {
            System.out.println(list1[i]);
        }
        // Attributes userAttributes = new Attributes();
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("select * from users where userName = \"" + userName + "\"");

            while (result.next()) {
                userCharacter.getAttributes().setXp("mining", result.getInt(6));
                System.out.println("setting miningXP to: " + result.getInt(6));
                userCharacter.getAttributes().setXp("eating", result.getInt(7));
                System.out.println("setting eating to: " + result.getInt(7));
                userCharacter.getAttributes().setXp("woodcutting", result.getInt(8));
                userCharacter.getAttributes().setXp("hunting", result.getInt(9));
                userCharacter.getAttributes().setXp("attack", result.getInt(10));
                userCharacter.getAttributes().setXp("ranged", result.getInt(11));
                userCharacter.getAttributes().setXp("defence", result.getInt(12));
                userCharacter.getAttributes().setXp("firemaking", result.getInt(13));
                userCharacter.getAttributes().setXp("intelligence", result.getInt(14));
                userCharacter.getAttributes().setXp("dexterity", result.getInt(15));
                userCharacter.getAttributes().setXp("strength", result.getInt(16));

                // set all levels based on xp
                userCharacter.getAttributes().convertXpToLevels();

                System.out.println("----XP AFTER UPDATE--------");
                String[] list2 = userCharacter.getAttributes().showAllXp();
                for (int i = 0; i < list2.length; i++) {
                    System.out.println(list2[i]);
                }

                // update stats for character
                userCharacter.setuserName(result.getString(1));
                userCharacter.setPassword(result.getString(2));
                userCharacter.setEVOPoints(result.getInt(3));
                userCharacter.setHealthPoints(result.getInt(4));
                userCharacter.setEnergyPoints(result.getInt(5));
                // userCharacter.setAttributes(userAttributes);
            }

            // update attributes for character

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        System.out.println("---------UPDATED USER---------");
        userCharacter.printCharacter();
        return userCharacter;
    }

    // return Character containing entries for character attributes
    public Boss getBoss(String bossName) {
        Boss newBoss = new Boss();

        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("select * from bosses where bossName = \"" + bossName + "\"");

            while (result.next()) {
                // update stats for character
                newBoss.setBossName(result.getString(1));
                newBoss.setStrongAgainst(result.getString(2));
                newBoss.setWeakAgainst(result.getString(3));
                newBoss.setHealthPoints(result.getInt(4));
                newBoss.setRangedAttack(result.getInt(5));
                newBoss.setMagicAttack(result.getInt(6));
                newBoss.setStabAttack(result.getInt(7));
                newBoss.setDefenceAttack(result.getInt(8));
                newBoss.setDamageReduction(result.getInt(9));

            }

            // update attributes for character

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return newBoss;
    }

    public void addCharacter(Character user) {
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO users VALUES (" + "\"" + user.getUsername() + "\"" + ", " + "\""
                    + user.getPassword() + "\"" + ", " + user.getEVOPoints() + ", " + user.getHealthPoints() + ", "
                    + user.getEnergyPoints() + ", " + user.getAttributes().getXp("mining") + ", "
                    + user.getAttributes().getXp("eating") + ", " + user.getAttributes().getXp("woodcutting") + ", "
                    + user.getAttributes().getXp("hunting") + ", " 
                    + user.getAttributes().getXp("attack") + ", " 
                    + user.getAttributes().getXp("ranged") + ", "
                    + user.getAttributes().getXp("defence") + ", "
                    + user.getAttributes().getXp("firemaking") + ", "
                    + user.getAttributes().getXp("intelligence") + ", "
                    + user.getAttributes().getXp("dexterity") + ", "
                    + user.getAttributes().getXp("strength") + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBoss(Boss boss) {
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            statement.executeUpdate("INSERT INTO bosses VALUES (" + "\"" + boss.getBossName() + "\"" + ", "
                    + "\"" + boss.getStrongAgainst() + "\"" + ", " + "\"" + boss.getWeakAgainst() + "\"" + " ," + "\"" 
                    + boss.getHealthPoints() + "\"" + ", " + "\"" + boss.getRangedAttack() + "\"" + ", " + "\"" + boss.getMagicAttack() + "\"" 
                    + ", " + "\"" + boss.getStabAttack() + "\"" + ", " + "\"" + boss.getDefenceAttack() + "\"" + ", " + "\"" + boss.getDamageReduction() + "\"" + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
    
    // Deletes user with a given userName from the table.
    public void deleteUser(String userName) {
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            statement.executeUpdate("DELETE FROM users WHERE userName = \"" + userName + "\"");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Deletes boss with a given bossName from the table.
    public void deleteBoss(String bossName) {
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            statement.executeUpdate("DELETE FROM bosses WHERE bossName = \"" + bossName + "\"");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    

    // Creates a new userstable.
    public void createUserTable() {
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE users (userName varchar (20), userPassword varchar(100), evoPoints INTEGER, healthPoints INTEGER, energyPoints INTEGER, miningXP INTEGER, eatingXP INTEGER, woodcuttingXP INTEGER, huntingXP INTEGER, attackXP INTEGER, rangedXP INTEGER, defenceXP INTEGER, firemakingXP INTEGER, intelligence INTEGER, dexterity INTEGER, strength INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Creates a new bossestable.
    public void createBossesTable() {
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE bosses (bossName varchar (20), strongAgainst varchar(20), weakAgainst varchar(20), healthPoints INTEGER, rangedAttack INTEGER, magicAttack INTEGER, stabAttack INTEGER, defenceAttack INTEGER, damageReduction INTEGER)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Checks if there exists a user table.
    public boolean checkTable(String tableName) {
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            statement.executeQuery("select * from " + tableName + ";");

        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    // update given Character with data from database
    public void updateCharacter(Character character) {
        deleteUser(character.getUsername());
        addCharacter(character);
    }

    // checks if a there exists a character with the given userName in the database;
    public boolean checkCharacter(String userName) {
        try {
            Connection con = DriverManager.getConnection(url, uname, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("select * from users where userName = \"" + userName + "\"");
            if (result.next()) {
                String tempString = result.getString(1);
                if (userName.equals(tempString)) {
                    return true;
                }
                return false;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("user not found in database");
            return false;
        }
    }

    // retrieves character if it exists in database
    // returns: character retrieved from database
    // new character if it does not exist in database
    // null if character exists but wrong password
    public Character loginCharacter(String userName, String password) {
        if (checkCharacter(userName)) {
            System.out.println("-------user found in database---------");
            Character userCharacter = getCharacter(userName);
            if (password.equals(userCharacter.getPassword())) {
                return userCharacter;
            }
            return null;
        }
        return null;
    }

    public Character registerCharacter(String userName, String password){
        if(checkCharacter(userName)){
            return null;
        }
        System.out.println("Creating new user with userName: " + userName + " and password: " + password);
        Character newUser = new Character();
        newUser.setuserName(userName);
        newUser.setPassword(password);
        addCharacter(newUser);
        return newUser;
    }
}