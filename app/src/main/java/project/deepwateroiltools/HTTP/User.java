package project.deepwateroiltools.HTTP;

import java.util.Date;

/**
 * Created by janos on 21/12/2017.
 */

public class User {
    private Id _id;
    private String user;
    private String password;
    private Boolean isActive;
    private Boolean isAdmin;
    private long lastLogin;
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public long getLastLogin(){ return lastLogin;}

    public void setLastLogin(long l){this.lastLogin =l;}
}
