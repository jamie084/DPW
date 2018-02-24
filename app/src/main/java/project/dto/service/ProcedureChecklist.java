package project.dto.service;

import java.util.List;

/**
 * Created by janos on 24/02/2018.
 */

public class ProcedureChecklist extends ProcedureSlide {
    List<String> listItems;

    public List<String> getListItems() {
        return listItems;
    }

    public void setListItems(List<String> listItems) {
        this.listItems = listItems;
    }
}
