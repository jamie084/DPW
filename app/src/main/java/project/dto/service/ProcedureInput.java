package project.dto.service;

import java.util.List;

/**
 * Created by janos on 10/02/2018.
 */

public class ProcedureInput extends ProcedureSlide {
    List<String> labelList;
    List<Integer> inputType;

    public List<String> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<String> labelList) {
        this.labelList = labelList;
    }

    public List<Integer> getInputType() {
        return inputType;
    }

    public void setInputType(List<Integer> inputType) {
        this.inputType = inputType;
    }
}
