package project.dto.service;

import java.util.List;

/**
 * Created by janos on 10/02/2018.
 */

public class ProcedureInput extends ProcedureSlide {
    String inputTo;
    List<String> inputTypes;
    List<String> inputList;

    public String getInputTo() {
        return inputTo;
    }

    public void setInputTo(String inputTo) {
        this.inputTo = inputTo;
    }

    public List<String> getInputTypes() {
        return inputTypes;
    }

    public void setInputTypes(List<String> inputTypes) {
        this.inputTypes = inputTypes;
    }

    public List<String> getInputList() {
        return inputList;
    }

    public void setInputList(List<String> inputList) {
        this.inputList = inputList;
    }


}
