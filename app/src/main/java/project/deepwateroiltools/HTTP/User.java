package project.deepwateroiltools.HTTP;

/**
 * Created by janos on 21/12/2017.
 */

public class User {
    private Id _id;
    private String user;

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
}
