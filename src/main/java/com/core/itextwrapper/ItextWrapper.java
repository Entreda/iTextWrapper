package com.core.itextwrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.core.itextwrapper.dto.FileDTO;
import com.core.itextwrapper.dto.PDFDTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author Aniket
 *
 */
public class ItextWrapper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ItextWrapper.class);


  private static final String DATE_FORMAT = "MMMM dd yyyy";

  /**
   * @param pdfDTO
   * @return FileDTO
   * @throws DocumentException
   * @throws IOException
   */
  public FileDTO generatePDF(PDFDTO pdfDTO) throws DocumentException, IOException {

    LOGGER.info("-------------------------------------------------");
    LOGGER.info("Generate PDF Wrapper class method called.");
    LOGGER.info("-------------------------------------------------");
    String filePath =
        "pdftemp" + File.separator + pdfDTO.getReport() + "_" + UUID.randomUUID() + ".pdf";

    File file = new File(filePath);
    if (!file.getParentFile().exists()) {
      file.getParentFile().mkdirs();
    }

    String fileName = "report.pdf";

    Document document = new Document(PageSize.A3.rotate(), 30f, 30f, 60f, 0f);

    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

    document.open();
    ClassLoader classLoader = getClass().getClassLoader();
    BaseFont opensans =
        BaseFont.createFont(classLoader.getResource("OpenSans-Regular.ttf").toString(),
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    Font f = new Font(opensans, 7.0f, Font.BOLD, BaseColor.BLACK);
    Font fnormal = new Font(opensans, 7.0f, Font.NORMAL, BaseColor.BLACK);
    Image img = Image.getInstance(classLoader.getResource("entredaLogo.jpg").toString());
    img.scaleToFit(60, 15);

    document.add(img);
    switch (pdfDTO.getReport()) {
      case "computeReport":
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Cybersecurity Policy Compliance Status Report", f));
        document.add(new Paragraph("WINDOWS & MAC OS", f));
        document.add(new Paragraph(new SimpleDateFormat(DATE_FORMAT).format(new Date()), fnormal));
        fileName = "Cybersecurity_Policy_Compliance_Status_Report_"
            + new SimpleDateFormat("MMddyyyy").format(new Date()) + ".pdf";
        document.add(prepareCompliancePDFTable(pdfDTO.getHeader(), pdfDTO.getData()));

        break;

      case "agingReport":
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Cybersecurity Policy Compliance Aging Report", f));
        document.add(new Paragraph("WINDOWS & MAC OS"));
        document.add(new Paragraph(new SimpleDateFormat(DATE_FORMAT).format(new Date()), fnormal));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph(
            "Failure Report: Aging report for users who have not addressed particular issue(s) over a \""
                + pdfDTO.getDays() + "\"-day period."));
        document.add(new Paragraph("Search Query / Filters enabled:" + pdfDTO.getFilters()));
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        fileName = "Cybersecurity_Policy_Compliance_Aging_Report_" + pdfDTO.getDays() + "_days_"
            + new SimpleDateFormat("MMddyyyy").format(new Date()) + ".pdf";
        document.add(prepareCompliancePDFTable(pdfDTO.getHeader(), pdfDTO.getData()));
        break;
      case "mobileReport":
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Cybersecurity Policy Compliance Status Report", f));
        document.add(new Paragraph("iOS & Android"));
        document.add(new Paragraph(new SimpleDateFormat(DATE_FORMAT).format(new Date()), fnormal));
        document.add(Chunk.NEWLINE);
        fileName = "Cybersecurity_Policy_Compliance_Status_Report_"
            + new SimpleDateFormat("MMddyyyy").format(new Date()) + ".pdf";
        document.add(prepareCompliancePDFTable(pdfDTO.getHeader(), pdfDTO.getData()));

        break;

      case "mobileAgingReport":
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Cybersecurity Policy Compliance Aging Report", f));
        document.add(new Paragraph("iOS & Android"));
        document.add(new Paragraph(new SimpleDateFormat(DATE_FORMAT).format(new Date()), fnormal));
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph(
            "Failure Report: Aging report for users who have not addressed particular issue(s) over a \""
                + pdfDTO.getDays() + "\"-day period."));
        document.add(new Paragraph("Search Query / Filters enabled:" + pdfDTO.getFilters()));
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        fileName = "Cybersecurity_Policy_Compliance_Aging_Report_" + pdfDTO.getDays() + "_days_"
            + new SimpleDateFormat("MMddyyyy").format(new Date()) + ".pdf";
        document.add(prepareCompliancePDFTable(pdfDTO.getHeader(), pdfDTO.getData()));

        break;

      case "summaryReport":
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Enrolled User Summary Report", f));
        document.add(new Paragraph(new SimpleDateFormat(DATE_FORMAT).format(new Date()), fnormal));
        document.add(Chunk.NEWLINE);
        fileName = "Enrolled_User_Summary_Report.pdf";
        document.add(prepareEnrolledPDFTable(pdfDTO.getHeader(), pdfDTO.getData()));
        break;

      case "detailedReport":
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("Enrolled User Detailed Report", f));
        document.add(new Paragraph(new SimpleDateFormat(DATE_FORMAT).format(new Date()), fnormal));
        document.add(Chunk.NEWLINE);
        fileName = "Enrolled_User_Detailed_Report.pdf";
        document.add(prepareEnrolledPDFTable(pdfDTO.getHeader(), pdfDTO.getData()));
        break;

      default:
        break;
    }
    document.close();

    writer.close();

    FileDTO fileDTO = new FileDTO();
    fileDTO.setFile(file);
    fileDTO.setFileName(fileName);
    return fileDTO;

  }

  /**
   * @param header
   * @param pdfData
   * @return PdfTable
   * @throws DocumentException
   * @throws IOException
   */
  private PdfPTable prepareCompliancePDFTable(List<String> header,
      List<Map<String, String>> pdfData) throws DocumentException, IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    BaseFont opensans =
        BaseFont.createFont(classLoader.getResource("OpenSans-Regular.ttf").toString(),
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    Font f = new Font(opensans, 6.0f, Font.BOLD, BaseColor.BLACK);
    Font fnormal = new Font(opensans, 6.0f, Font.NORMAL, BaseColor.BLACK);
    Font fnred = new Font(opensans, 6.0f, Font.NORMAL, BaseColor.RED);
    Font fnorange = new Font(opensans, 6.0f, Font.NORMAL, BaseColor.ORANGE);
    Font fngreen = new Font(opensans, 6.0f, Font.NORMAL, new BaseColor(66, 138, 69));
    PdfPTable table = new PdfPTable(header.size());
    table.setWidthPercentage(100);
    table.setSpacingBefore(10f);
    table.setSpacingAfter(0f);

    int totalHeaderColSpan = 0;
    PdfPCell cell1 = new PdfPCell(new Phrase("User Information", f));
    if (header.get(0).equalsIgnoreCase("Office")) {
      totalHeaderColSpan = totalHeaderColSpan + 5;
      cell1.setColspan(5);
    } else {
      totalHeaderColSpan = totalHeaderColSpan + 4;
      cell1.setColspan(4);
    }
    cell1.setBackgroundColor(new BaseColor(225, 225, 225));
    cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell1);
    PdfPCell cell2 = new PdfPCell(new Phrase("Device Information", f));
    cell2.setColspan(4);
    totalHeaderColSpan = totalHeaderColSpan + 4;
    cell2.setBackgroundColor(new BaseColor(225, 225, 225));
    cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell2);
    PdfPCell cell3 = new PdfPCell(new Phrase("Cybersecurity Policy Compliance Status", f));
    cell3.setColspan(header.size() - totalHeaderColSpan);
    cell3.setBackgroundColor(new BaseColor(225, 225, 225));
    cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(cell3);
    for (int i = 0; i < header.size(); i++) {
      PdfPCell hcell = new PdfPCell(new Phrase(header.get(i), f));
      hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
      if (isValid(header.get(i))) {
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
      }
      hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      table.addCell(hcell);
    }

    for (int i = 0; i < header.size(); i++) {
      PdfPCell bcell = new PdfPCell(new Phrase(" ", f));
      bcell.setBackgroundColor(new BaseColor(147, 155, 154));
      table.addCell(bcell);
    }

    if (pdfData != null && !pdfData.isEmpty()) {

      for (int i = 0; i < pdfData.size(); i++) {
        for (Map.Entry<String, String> obj : pdfData.get(i).entrySet()) {
          if (obj.getValue() != null) {
            PdfPCell cell = new PdfPCell(new Phrase(obj.getValue(), fnormal));
            if (isValid(obj.getKey())) {
              cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            }
            if (obj.getValue().equalsIgnoreCase("PASS")) {
              cell = new PdfPCell(new Phrase(obj.getValue(), fngreen));
              cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            } else if (obj.getValue().equalsIgnoreCase("FAIL")) {
              cell = new PdfPCell(new Phrase(obj.getValue(), fnred));
              cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            } else if (obj.getValue().equalsIgnoreCase("WARNING")) {
              cell = new PdfPCell(new Phrase(obj.getValue(), fnorange));
              cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            }
            table.addCell(cell);
          } else {
            table.addCell("");
          }
        }
      }
    }

    return table;
  }

  /**
   * @param key
   * @return true is given key is valid
   */
  private boolean isValid(String key) {
    boolean isHeaderValid = false;
    if (!key.equalsIgnoreCase("Last Name") && !key.equalsIgnoreCase("First Name")
        && !key.equalsIgnoreCase("User Email") && !key.equalsIgnoreCase("Company Name")
        && !key.equalsIgnoreCase("Device Name") && !key.equalsIgnoreCase("Description")) {
      isHeaderValid = true;
    }

    return isHeaderValid;

  }

  /**
   * @param header
   * @param data
   * @return PdfTable
   * @throws DocumentException
   * @throws IOException
   */
  private PdfPTable prepareEnrolledPDFTable(List<String> header, List<Map<String, String>> data)
      throws DocumentException, IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    BaseFont opensans =
        BaseFont.createFont(classLoader.getResource("OpenSans-Regular.ttf").toString(),
            BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
    Font f = new Font(opensans, 6.0f, Font.BOLD, BaseColor.BLACK);
    Font fnormal = new Font(opensans, 6.0f, Font.NORMAL, BaseColor.BLACK);
    PdfPTable table = new PdfPTable(header.size());
    table.setWidthPercentage(100);
    table.setSpacingBefore(10f);
    table.setSpacingAfter(0f);

    for (int i = 0; i < header.size(); i++) {
      PdfPCell hcell = new PdfPCell(new Phrase(header.get(i), f));
      hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
      hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
      hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      table.addCell(hcell);
    }

    if (data != null && !data.isEmpty()) {
      for (int i = 0; i < data.size(); i++) {
        for (Map.Entry<String, String> obj : data.get(i).entrySet()) {
          if (obj.getValue() != null) {
            PdfPCell cell = new PdfPCell(new Phrase(obj.getValue(), fnormal));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
          } else {
            table.addCell("");
          }
        }
      }
    }
    return table;
  }


}
