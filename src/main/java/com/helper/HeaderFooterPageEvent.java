package com.helper;


import java.io.IOException;
import java.net.URL;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {

    public void onStartPage(PdfWriter writer, Document document) {
    
    	 URL resource = HeaderFooterPageEvent.class.getResource("/logo-7.png");
    	  Rectangle rect = writer.getBoxSize("rectangle");
        Image image;
        try {
            image = Image.getInstance(resource);
          image.scalePercent(50);
            image.setAbsolutePosition(  rect.getRight() / 2, rect.getTop());
            writer.getDirectContent().addImage(image, true);
        } catch (IOException | DocumentException e) {
           e.printStackTrace();
        }

        ColumnText.showTextAligned(writer.getDirectContent(),
                Element.ALIGN_CENTER, new Phrase(""),
                rect.getRight() / 2, rect.getTop(), 0);
    }

  /*  public void onEndPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("http://www.xxxx-your_example.com/"), 110, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
    }*/

}