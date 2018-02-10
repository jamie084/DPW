package project.dto.service;

/**
 * Created by janos on 08/02/2018.
 */

public class ProcedureImg extends ProcedureSlide{
    private String imgURL;
    private String imgLocalName;

  //  public ProcedureImg() {
 //       super();
   // }
    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getImgLocalName() {
        return imgLocalName;
    }

    public void setImgLocalName(String imgLocalName) {
        this.imgLocalName = imgLocalName;
    }

}
