package project.dto.service;

import java.util.ArrayList;
import java.util.HashSet;

import project.dto.Id;

/**
 * Created by janos on 08/02/2018.
 */

public class ProcedureSlide {
    private Id _id;
    private int procId;
    private String type;
    private String title;
    private int childId;

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getProcId() {
        return procId;
    }

    public void setProcId(int procId) {
        this.procId = procId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
