package project.iTextPDF;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import project.deepwateroiltools_001.R;
import project.dto.SeaCure_job;

/**
 * Created by janos on 03/03/2018.
 */

public class CreatePDF extends AsyncTask<String, Void, String> {
    private String text, filename;
    private Context context;
    private SeaCure_job seaCure_job;
    private ProgressDialog dialog;
    public static final String IMAGE1 = "drawable/logo.png";

    public CreatePDF(Context context, SeaCure_job seaCure_job, String filename){
        this.text = seaCure_job.toString();
        this.context = context;
        this.filename = filename;
        this.seaCure_job = seaCure_job;
        dialog = new ProgressDialog(context, R.style.DialogBoxStyle);
    }
    // Method for creating a pdf file from text, saving it then opening it for display
    public void createandDisplayPdf() {
        String path= "";
        Document doc = new Document();

        try {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, filename + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 2});
            table.addCell(createTextCell("Job report"));
            table.addCell(createImageCell(getLogo()));

            doc.add(table);
            Paragraph p1 = new Paragraph(text);

            p1.setAlignment(Paragraph.ALIGN_LEFT);
            doc.add(p1);

            addImagesToDoc(doc);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }

        viewPdf(filename + ".pdf", path);
    }

    // Method for opening a pdf file in a new intent
    //Params: String filename, String directory
    private void viewPdf(String file, String directory) {

       // File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        File pdfFile = new File(directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);

        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            context.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }

    //get the logo file from the assets and returns an Image object
    //params: None
    //returns: Image
    public Image getLogo(){
        try {
            InputStream ims = context.getAssets().open("logo.png");
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.scaleAbsoluteHeight(110);
            image.scaleAbsoluteWidth(170);
            return image;
        }
        catch (Exception e){
            Log.d("GetLogoIO: " , e.toString());
        }
        return null;
    }

    //create a cell from a parameter Image  with the appropiate formatting
    //param: Image
    //returns: PdfCell
    public static PdfPCell createImageCell(Image img) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell(img, false);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    //create a cell from a parameter text with the appropiate formatting
    //param: String
    //returns: PdfCell
    public static PdfPCell createTextCell(String text) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Font fontbold = FontFactory.getFont("Times-Roman", 30, Font.BOLD);
        Paragraph p = new Paragraph(text, fontbold);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public  void addImagesToDoc(Document doc){
        String defaultPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        try {
            Iterator it = seaCure_job.getPhotos_local_name().entrySet().iterator();

            while (it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                //construct the file path
                String path = defaultPath +   "/" + pair.getKey() + ".jpg";
                File imageFile = new File(path);

                if(imageFile != null){
                    //to catch all file not found exceptions
                    try{
                        FileInputStream mInputStream =  new FileInputStream(imageFile);
                        Bitmap bmp = BitmapFactory.decodeStream(mInputStream);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                        Image image = Image.getInstance(stream.toByteArray());

                        doc.newPage();
                        //reduce image size to fit into the page
                        float width = image.getWidth()/8;
                        float height = image.getHeight()/8;
                        image.scaleAbsoluteHeight(height);
                        image.scaleAbsoluteWidth(width);

                        Paragraph p1 = new Paragraph(filename.toString() + "\n" );
                        p1.setAlignment(Paragraph.ALIGN_LEFT);
                        doc.add(p1);
                        doc.add(image);
                    }
                    catch (Exception ex){

                    }


                } else {
                }
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
        catch (Exception ex){
            Log.d("excepteion", ex.getMessage());
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        createandDisplayPdf();
        return null;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Exporting...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.show();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
      //  processListener.ProcessingIsDone(result);
    }

}
