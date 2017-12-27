package project.deepwateroiltools.HTTP;

import java.util.Date;

/**
 * Created by janos on 21/12/2017.
 */

public class User {
    private Id _id;
    private int userId;
    private String user;
    private Boolean isActive;
    private long lastLogin;

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public int getUserId(){return userId;}

    public void setUserId(int uid){this.userId = uid;}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Boolean getIsActive() {return isActive;}

    public void setIsActive(Boolean b) {this.isActive = b;}

    public long getLastLogin(){ return lastLogin;}

    public void setLastLogin(long l){this.lastLogin =l;}
}
