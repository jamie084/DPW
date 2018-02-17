package project.dto.service;

import java.util.List;

/**
 * Created by janos on 10/02/2018.
 */

public class ProcedureInput extends ProcedureSlide {
    List<String> labelList;
    List<String> inputType;

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public List<String> getInputType() {
        return inputType;
    }

    public void setInputType(List<String> inputType) {
        this.inputType = inputType;
    }
}
