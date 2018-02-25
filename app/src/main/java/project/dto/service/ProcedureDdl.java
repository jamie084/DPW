package project.dto.service;

import java.util.List;

/**
 * Created by janos on 25/02/2018.
 */

public class ProcedureDdl extends ProcedureSlide {
    List<String> labels;
    List<List<String>> elements;
    String ddlType;

    public String getDdlType() {
        return ddlType;
    }

    public void setDdlType(String ddlType) {
        this.ddlType = ddlType;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<List<String>> getElements() {
        return elements;
    }

    public void setElements(List<List<String>> elements) {
        this.elements = elements;
    }
}
