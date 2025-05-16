//package com.voteSphere.controller;
//
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.URL;
//import java.sql.*;
//
//import com.voteSphere.model.User;
//import com.voteSphere.service.UserService;
//import com.voteSphere.util.ImageRetrievalHandler;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/DownloadUserPDF")
//public class UserPdfDownloadServlet extends HttpServlet {
//
//    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
//    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
//    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        String voterId = "1";
//
//        try {
//            // Get user data from database
//            User user = getUserFromDatabase(voterId);
//
//            // Create PDF document
//            Document document = new Document(PageSize.A4);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            PdfWriter.getInstance(document, baos);
//
//            document.open();
//
//            // Add title
//            Paragraph title = new Paragraph("User Information Details", TITLE_FONT);
//            title.setAlignment(Element.ALIGN_CENTER);
//            title.setSpacingAfter(20f);
//            document.add(title);
//
//            // Add user photo
//            addImageToDocument(document, user.getProfileImage(), "Profile Photo", 150, 150);
//
//            // Create user information table
//            PdfPTable table = new PdfPTable(2);
//            table.setWidthPercentage(100);
//            table.setSpacingBefore(20f);
//            table.setSpacingAfter(20f);
//
//            // Table headers
//            addTableHeader(table, "Field");
//            addTableHeader(table, "Value");
//
//            // Add user data to table
//            addUserDataToTable(table, "First Name", user.getFirstName());
//            addUserDataToTable(table, "Last Name", user.getLastName());
//            addUserDataToTable(table, "Voter ID", user.getVoterId());
//            addUserDataToTable(table, "Email", user.getEmail());
//            addUserDataToTable(table, "Phone", user.getPhoneNumber());
//            addUserDataToTable(table, "Date of Birth", user.getDob().toString());
//            addUserDataToTable(table, "Gender", user.getGender());
//            addUserDataToTable(table, "Temporary Address", user.getTemporaryAddress());
//            addUserDataToTable(table, "Permanent Address", user.getPermanentAddress());
//            addUserDataToTable(table, "Account Status", user.getIsVerified() ? "Verified" : "Not Verified");
//
//            document.add(table);
//
//            // Add document images section
//            Paragraph imagesTitle = new Paragraph("Document Images", TITLE_FONT);
//            imagesTitle.setAlignment(Element.ALIGN_CENTER);
//            imagesTitle.setSpacingBefore(30f);
//            imagesTitle.setSpacingAfter(20f);
//            document.add(imagesTitle);
//
//            // Create images table (2 columns)
//            PdfPTable imagesTable = new PdfPTable(2);
//            imagesTable.setWidthPercentage(100);
//            imagesTable.setSpacingAfter(30f);
//
//            // Add images to the table
//            addImageToTable(imagesTable, user.getImageHoldingCitizenship(), "Citizenship Holding Photo", 200, 150);
//            addImageToTable(imagesTable, user.getVoterCardFront(), "Voter Card Front", 200, 150);
//            addImageToTable(imagesTable, user.getVoterCardBack(), "Voter Card Back", 200, 150);
//            addImageToTable(imagesTable, user.getCitizenshipFront(), "Citizenship Front", 200, 150);
//            addImageToTable(imagesTable, user.getCitizenshipBack(), "Citizenship Back", 200, 150);
//            addImageToTable(imagesTable, user.getThumbPrint(), "Thumb Print", 200, 150);
//
//            document.add(imagesTable);
//
//            // Add footer
//            Paragraph footer = new Paragraph("Generated on: " + new java.util.Date(), NORMAL_FONT);
//            footer.setAlignment(Element.ALIGN_RIGHT);
//            document.add(footer);
//
//            document.close();
//
//            // Set response headers for PDF download
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "attachment; filename=\"UserInfo_"+voterId+".pdf\"");
//            response.setContentLength(baos.size());
//
//            // Write PDF to response
//            OutputStream os = response.getOutputStream();
//            baos.writeTo(os);
//            os.flush();
//            os.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF");
//        }
//    }
//
//    private User getUserFromDatabase(String userId) throws SQLException {
//
//        User user = UserService.getUserById(Integer.parseInt(userId));
//        // Query database and populate user object
//        return user;
//    }
//
//    private void addTableHeader(PdfPTable table, String header) {
//        PdfPCell cell = new PdfPCell();
//        cell.setBackgroundColor(BaseColor.DARK_GRAY);
//        cell.setPadding(5);
//        cell.setPhrase(new Phrase(header, HEADER_FONT));
//        table.addCell(cell);
//    }
//
//    private void addUserDataToTable(PdfPTable table, String field, String value) {
//        if (value == null) value = "N/A";
//
//        table.addCell(createCell(field, true));
//        table.addCell(createCell(value, false));
//    }
//
//    private PdfPCell createCell(String content, boolean isHeader) {
//        PdfPCell cell = new PdfPCell(new Phrase(content, NORMAL_FONT));
//        cell.setPadding(5);
//        if (isHeader) {
//            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//        }
//        return cell;
//    }
//
//    private void addImageToDocument(Document document, String imagePath, String caption, float width, float height)
//            throws DocumentException, IOException {
//        try {
//            Image img = Image.getInstance(new URL(imagePath));
//            img.scaleToFit(width, height);
//            img.setAlignment(Image.MIDDLE);
//
//            ImageRetrievalHandler.sendImage("/",imagePath,);
//
//
//            Paragraph p = new Paragraph(caption, NORMAL_FONT);
//            p.setAlignment(Element.ALIGN_CENTER);
//            p.setSpacingAfter(10f);
//
//            document.add(p);
//            document.add(img);
//        } catch (Exception e) {
//            document.add(new Paragraph("Could not load image: " + caption, NORMAL_FONT));
//        }
//    }
//
//    private void addImageToTable(PdfPTable table, String imagePath, String caption, float width, float height) {
//        PdfPCell cell = new PdfPCell();
//        cell.setPadding(5);
//        cell.setBorder(Rectangle.BOX);
//
//        try {
//            Image img = Image.getInstance(new URL(imagePath));
//            img.scaleToFit(width, height);
//
//            Paragraph p = new Paragraph(caption, NORMAL_FONT);
//            p.setAlignment(Element.ALIGN_CENTER);
//
//            cell.addElement(p);
//            cell.addElement(img);
//        } catch (Exception e) {
//            cell.addElement(new Paragraph("Could not load image", NORMAL_FONT));
//        }
//
//        table.addCell(cell);
//    }
//}
//
