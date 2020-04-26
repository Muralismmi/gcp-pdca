package com.helper;


import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This class is a work around for working with iText 5.x in Spring.
 * The code here is almost identical to the AbstractPdfView class. 
 *
 */
public abstract class AbstractITextPdfView extends AbstractView {

	public AbstractITextPdfView() {
		setContentType("application/pdf");
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
		
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// IE workaround: write into byte array first.
		ByteArrayOutputStream baos = createTemporaryOutputStream();
		String ID		 	 =   (String) model.get("WorkPermitId");
		Date date = new Date();
	    String DATE_FORMAT = "DD/MM/YYYY";
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		// Apply preferences and build metadata.
		Document document = newDocument();
		String fileName = "PDCA "+ID+"_"+sdf.format(date)+".pdf";
		PdfWriter writer = newWriter(document, baos);
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);

		// Build PDF document.
		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();
		response.addHeader("Content-Disposition", "attachment; filename="+fileName);
		// Flush to HTTP response.
		writeToResponse(response, baos);
	}

	protected Document newDocument() {
		return new Document(PageSize.A4);
	}
	
	protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
		return PdfWriter.getInstance(document, os);
	}
	
	protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
			throws DocumentException {

		writer.setViewerPreferences(getViewerPreferences());
	}
	
	protected int getViewerPreferences() {
		return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
	}
	
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
	}
	
	protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception;	
}