package project.dto.service;

/**
 * Created by janos on 08/02/2018.
 */

public abstract class ProcedureSlide {
    private int procId;
    private String title;

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
