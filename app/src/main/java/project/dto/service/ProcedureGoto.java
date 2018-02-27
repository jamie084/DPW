package project.dto.service;

/**
 * Created by janos on 27/02/2018.
 */

public class ProcedureGoto extends ProcedureSlide {
    int altChildId;
    String btnLabel;

    public String getBtnLabel() {
        return btnLabel;
    }

    public void setBtnLabel(String btnLabel) {
        this.btnLabel = btnLabel;
    }

    public int getAltChildId() {
        return altChildId;
    }

    public void setAltChildId(int altChildId) {
        this.altChildId = altChildId;
    }
}
