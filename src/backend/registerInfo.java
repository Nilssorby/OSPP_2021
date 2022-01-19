package backend;

import java.io.Serializable;

public class registerInfo implements Serializable{
    private String userName;
    private String password;

    public registerInfo(String userName, String password){
        this.userName = userName;
        this.password = password;

    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
