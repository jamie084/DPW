package project.dto;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by janos on 19/02/2018.
 */

public class SeaCure_job {
    private Id _id;
    private String _user_id;
    private int _jobID;
    private int savedId;
    private String toolType;
    private int tool_serial;
    private String region; //locator????
    private long startDate;
    private long finishDate;
    private String clientOperator;
    private int po_reference;
    private int torque_SCR_TRB;
    private int sn_in_DOT_SCR_TRB;
    private int sn_in_DOT_SCR_PBR;
    private int sn_in_DOT_SCR_INB;
    private int sn_in_DOT_SCR_IBH;
    private int sn_out_DOT_SCR_TRB;
    private int sn_out_DOT_SCR_PBR;
    private int sn_out_DOT_SCR_INB;
    private int sn_out_DOT_SCR_IBH;
    private ArrayList<HashMap> photos_local_name;
    private boolean isORingReplaced;
    private boolean isUpperBearingRingReplaced;
    private boolean isMiddelBearingRingReplaced;
    private boolean isStubAcmeRingReplaced;
    private boolean isLowerBearingRingReplaced;
    private int wo_oRing;
    private int wo_upperBearingRing;
    private int wo_middleBearingRing;
    private int wo_lowerBearingRing;
    private int wo_STUB_acme_O_ring;
    private boolean pin_thread_damage;
    private boolean pin_thread_repaired;
    private boolean box_thread_damage;
    private boolean box_thread_repaired;
    private int new_m8_grub_screws;
    private String loctite_lot_number; //could be int???
    private String defect_desc;
    private int torque_SCR_TRB_end;
    private List<Integer> visited;
    private List<String> notes = new ArrayList<String>();

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public String get_user_id() {
        return _user_id;
    }

    public void set_user_id(String _user_id) {
        this._user_id = _user_id;
    }

    public int get_jobID() {
        return _jobID;
    }

    public void set_jobID(int _jobID) {
        this._jobID = _jobID;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String toolType) {
        this.toolType = toolType;
    }

    public int getTool_serial() {
        return tool_serial;
    }

    public void setTool_serial(int tool_serial) {
        this.tool_serial = tool_serial;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(long finishDate) {
        this.finishDate = finishDate;
    }

    public String getClientOperator() {
        return clientOperator;
    }

    public void setClientOperator(String clientOperator) {
        this.clientOperator = clientOperator;
    }

    public int getPo_reference() {
        return po_reference;
    }

    public void setPo_reference(int po_reference) {
        this.po_reference = po_reference;
    }

    public int getTorque_SCR_TRB() {
        return torque_SCR_TRB;
    }

    public void setTorque_SCR_TRB(int torque_SCR_TRB) {
        this.torque_SCR_TRB = torque_SCR_TRB;
    }

    public int getSn_in_DOT_SCR_TRB() {
        return sn_in_DOT_SCR_TRB;
    }

    public void setSn_in_DOT_SCR_TRB(int sn_in_DOT_SCR_TRB) {
        this.sn_in_DOT_SCR_TRB = sn_in_DOT_SCR_TRB;
    }

    public int getSn_in_DOT_SCR_PBR() {
        return sn_in_DOT_SCR_PBR;
    }

    public void setSn_in_DOT_SCR_PBR(int sn_in_DOT_SCR_PBR) {
        this.sn_in_DOT_SCR_PBR = sn_in_DOT_SCR_PBR;
    }

    public int getSn_in_DOT_SCR_INB() {
        return sn_in_DOT_SCR_INB;
    }

    public void setSn_in_DOT_SCR_INB(int sn_in_DOT_SCR_INB) {
        this.sn_in_DOT_SCR_INB = sn_in_DOT_SCR_INB;
    }

    public int getSn_in_DOT_SCR_IBH() {
        return sn_in_DOT_SCR_IBH;
    }

    public void setSn_in_DOT_SCR_IBH(int sn_in_DOT_SCR_IBH) {
        this.sn_in_DOT_SCR_IBH = sn_in_DOT_SCR_IBH;
    }

    public int getSn_out_DOT_SCR_TRB() {
        return sn_out_DOT_SCR_TRB;
    }

    public void setSn_out_DOT_SCR_TRB(int sn_out_DOT_SCR_TRB) {
        this.sn_out_DOT_SCR_TRB = sn_out_DOT_SCR_TRB;
    }

    public int getSn_out_DOT_SCR_PBR() {
        return sn_out_DOT_SCR_PBR;
    }

    public void setSn_out_DOT_SCR_PBR(int sn_out_DOT_SCR_PBR) {
        this.sn_out_DOT_SCR_PBR = sn_out_DOT_SCR_PBR;
    }

    public int getSn_out_DOT_SCR_INB() {
        return sn_out_DOT_SCR_INB;
    }

    public void setSn_out_DOT_SCR_INB(int sn_out_DOT_SCR_INB) {
        this.sn_out_DOT_SCR_INB = sn_out_DOT_SCR_INB;
    }

    public int getSn_out_DOT_SCR_IBH() {
        return sn_out_DOT_SCR_IBH;
    }

    public void setSn_out_DOT_SCR_IBH(int sn_out_DOT_SCR_IBH) {
        this.sn_out_DOT_SCR_IBH = sn_out_DOT_SCR_IBH;
    }

    public ArrayList<HashMap> getPhotos_local_name() {
        return photos_local_name;
    }

    public void setPhotos_local_name(ArrayList<HashMap> photos_local_name) {
        this.photos_local_name = photos_local_name;
    }

    public int getWo_oRing() {
        return wo_oRing;
    }

    public void setWo_oRing(int wo_oRing) {
        this.wo_oRing = wo_oRing;
    }

    public int getWo_upperBearingRing() {
        return wo_upperBearingRing;
    }

    public void setWo_upperBearingRing(int wo_upperBearingRing) {
        this.wo_upperBearingRing = wo_upperBearingRing;
    }

    public int getWo_middleBearingRing() {
        return wo_middleBearingRing;
    }

    public void setWo_middleBearingRing(int wo_middleBearingRing) {
        this.wo_middleBearingRing = wo_middleBearingRing;
    }

    public int getWo_lowerBearingRing() {
        return wo_lowerBearingRing;
    }

    public void setWo_lowerBearingRing(int wo_lowerBearingRing) {
        this.wo_lowerBearingRing = wo_lowerBearingRing;
    }

    public int getWo_STUB_acme_O_ring() {
        return wo_STUB_acme_O_ring;
    }

    public void setWo_STUB_acme_O_ring(int wo_STUB_acme_O_ring) {
        this.wo_STUB_acme_O_ring = wo_STUB_acme_O_ring;
    }

    public boolean isPin_thread_damage() {
        return pin_thread_damage;
    }

    public void setPin_thread_damage(boolean pin_thread_damage) {
        this.pin_thread_damage = pin_thread_damage;
    }

    public boolean isPin_thread_repaired() {
        return pin_thread_repaired;
    }

    public void setPin_thread_repaired(boolean pin_thread_repaired) {
        this.pin_thread_repaired = pin_thread_repaired;
    }

    public boolean isBox_thread_damage() {
        return box_thread_damage;
    }

    public void setBox_thread_damage(boolean box_thread_damage) {
        this.box_thread_damage = box_thread_damage;
    }

    public boolean isBox_thread_repaired() {
        return box_thread_repaired;
    }

    public void setBox_thread_repaired(boolean box_thread_repaired) {
        this.box_thread_repaired = box_thread_repaired;
    }

    public int getNew_m8_grub_screws() {
        return new_m8_grub_screws;
    }

    public void setNew_m8_grub_screws(int new_m8_grub_screws) {
        this.new_m8_grub_screws = new_m8_grub_screws;
    }

    public String getLoctite_lot_number() {
        return loctite_lot_number;
    }

    public void setLoctite_lot_number(String loctite_lot_number) {
        this.loctite_lot_number = loctite_lot_number;
    }

    public String getDefect_desc() {
        return defect_desc;
    }

    public void setDefect_desc(String defect_desc) {
        this.defect_desc = defect_desc;
    }

    public int getTorque_SCR_TRB_end() {
        return torque_SCR_TRB_end;
    }

    public void setTorque_SCR_TRB_end(int torque_SCR_TRB_end) {
        this.torque_SCR_TRB_end = torque_SCR_TRB_end;
    }

    public boolean isORingReplaced() {
        return isORingReplaced;
    }

    public void setORingReplaced(boolean ORingReplaced) {
        isORingReplaced = ORingReplaced;
    }

    public boolean isUpperBearingRingReplaced() {
        return isUpperBearingRingReplaced;
    }

    public void setUpperBearingRingReplaced(boolean upperBearingRingReplaced) {
        isUpperBearingRingReplaced = upperBearingRingReplaced;
    }

    public boolean isMiddelBearingRingReplaced() {
        return isMiddelBearingRingReplaced;
    }

    public void setMiddelBearingRingReplaced(boolean middelBearingRingReplaced) {
        isMiddelBearingRingReplaced = middelBearingRingReplaced;
    }

    public boolean isStubAcmeRingReplaced() {
        return isStubAcmeRingReplaced;
    }

    public void setStubAcmeRingReplaced(boolean stubAcmeRingReplaced) {
        this.isStubAcmeRingReplaced = stubAcmeRingReplaced;
    }

    public boolean isLowerBearingRingReplaced() {
        return isLowerBearingRingReplaced;
    }

    public void setLowerBearingRingReplaced(boolean lowerBearingRingReplaced) {
        isLowerBearingRingReplaced = lowerBearingRingReplaced;
    }

    public int getSavedId() {
        return savedId;
    }

    public void setSavedId(int savedId) {
        this.savedId = savedId;
    }

    public List<Integer> getVisited() {
        return visited;
    }

    public void setVisited(ArrayList<Integer> visited) {
        this.visited = visited;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void addNote(String note){
        getNotes().add(note);
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public String getFormattedNotes(){
        String result = "";
        for (int i=0; i<getNotes().size();i++){
            result += getNotes().get(i).toString() + "\n";
        }
        return  result;
    }


    @Override
    public String toString(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String result="";
        result += "Start Date: " + formatter.format(new Date(this.getStartDate()))+ "\n";
        result += "Finish Date: " + formatter.format(new Date(this.getFinishDate()))+ "\n";
        result += "Tool Type: " + this.getToolType() + "\n";
        result += "Client Operator: "  + this.clientOperator + "\n";
        result += "PO reference number: " + this.po_reference + "\n";
        result += "Torque SCR_TRB: " + this.torque_SCR_TRB + "\n";

        result += "Seacure Serial: "        + this.tool_serial + "\n";
        result += "DOT-SCR-TRB Serial in: " + this.sn_in_DOT_SCR_TRB + "\n";
        result += "DOT-SCR-PBR Serial in: " + this.sn_in_DOT_SCR_PBR + "\n";
        result += "DOT-SCR-INB Serial in: " + this.sn_in_DOT_SCR_INB + "\n";
        result += "DOT-SCR-IBH Serial in: " + this.sn_in_DOT_SCR_IBH + "\n";

        result += "O-Ring replacement work order: "                 + this.wo_oRing + "\n";
        result += "Upper Bearing Ring replacement work order: "     + this.wo_upperBearingRing + "\n";
        result += "Middle Bearing Ring replacement work order: "    + this.wo_middleBearingRing + "\n";
        result += "Lower Bearing Ring replacement work order: "     + this.wo_lowerBearingRing + "\n";
        result += "STUB Acme O-Ring replacement work order: "       + this.wo_STUB_acme_O_ring + "\n";
        result += "Saved state: "                                   + this.savedId + "\n";
        result += "Notes: "                                         + this.getFormattedNotes() + "\n";
        result += "UserID: " + this.get_user_id();
        return result;
    }
}
