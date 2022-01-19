package backend;

import java.io.Serializable;

public class loginInfo implements Serializable{
    private String userName;
    private String password;

    public loginInfo(String userName, String password){
        this.userName = userName;
        this.password = password;

    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
    
    public String toString() {
    	return "" + userName + ", " + password;
    }
}
