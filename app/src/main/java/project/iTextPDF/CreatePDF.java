package project.iTextPDF;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Header;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import project.deepwateroiltools_001.R;

/**
 * Created by janos on 03/03/2018.
 */

public class CreatePDF {
    String text, filename;
    Context context;
    public static final String IMAGE1 = "drawable/logo.png";

    public CreatePDF(Context context, String text, String filename){
        this.text = text;
        this.context = context;
        this.filename = filename;
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
}
