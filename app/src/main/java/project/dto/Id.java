package project.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by janos on 21/12/2017.
 */

public class Id {
    @SerializedName("$oid")
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
