package project.dto;

/**
 * Created by janos on 10/03/2018.
 */

public class DotSerail {
    private Id _id;


    String tool_type;
        int DOT_SCR_TRB;
        int DOT_SCR_PBR;
        int DOT_SCR_INB;
        int DOT_SCR_IBH;



    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public String getTool_type() {
        return tool_type;
    }

    public void setTool_type(String tool_type) {
        this.tool_type = tool_type;
    }

    public int getDOT_SCR_TRB() {
        return DOT_SCR_TRB;
    }

    public void setDOT_SCR_TRB(int DOT_SCR_TRB) {
        this.DOT_SCR_TRB = DOT_SCR_TRB;
    }

    public int getDOT_SCR_PBR() {
        return DOT_SCR_PBR;
    }

    public void setDOT_SCR_PBR(int DOT_SCR_PBR) {
        this.DOT_SCR_PBR = DOT_SCR_PBR;
    }

    public int getDOT_SCR_INB() {
        return DOT_SCR_INB;
    }

    public void setDOT_SCR_INB(int DOT_SCR_INB) {
        this.DOT_SCR_INB = DOT_SCR_INB;
    }

    public int getDOT_SCR_IBH() {
        return DOT_SCR_IBH;
    }

    public void setDOT_SCR_IBH(int DOT_SCR_IBH) {
        this.DOT_SCR_IBH = DOT_SCR_IBH;
    }
}
