package com.voteSphere.controller;

import com.voteSphere.dao.ElectionDao;
import com.voteSphere.dao.VoteDao;
import com.voteSphere.model.User;
import com.voteSphere.service.ElectionService;
import com.voteSphere.service.UserService;
import com.voteSphere.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.PieChart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/voteChart")
public class VoteChartServlet extends HttpServlet {

//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws IOException {
//
//        // Get election ID from query parameter
//        int electionId = Integer.parseInt(request.getParameter("electionId"));
//        String electionName = ElectionDao.getElectionNameById(electionId);
//
//
//        byte[] excelData = ExcelUtil.generateExcelElectionVoteReport(electionId);
//        if (excelData == null) {
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Could not generate Excel");
//            return;
//        }
//
//        // Get or generate your PNGs here (you can generate or read from disk)
//        Map<String, byte[]> pngFiles = new HashMap<>();
//
//        pngFiles.put(ValidationUtil.sanitizeName(electionName)+"_"+ ValidationUtil.getTimestamp()+"_pieChart.png", chartToByteArray(ChartUtil.createElectionVoteStatsPieChart(electionId)));
//        pngFiles.put(ValidationUtil.sanitizeName(electionName)+"_"+ ValidationUtil.getTimestamp()+"_genderDonut.png", chartToByteArray(ChartUtil.createElectionGenderDonutChart(electionId)));
//        pngFiles.put(ValidationUtil.sanitizeName(electionName)+"_"+ ValidationUtil.getTimestamp()+"_ageBarChart.png", chartToByteArray(ChartUtil.createElectionVotersAgeBarChart(electionId)));
//
//
//        byte[] zipData = ZipUtil.createZipWithExcelAndPngs(excelData, pngFiles,electionName);
//
//        response.setContentType("application/zip");
//        response.setHeader("Content-Disposition", "attachment; filename="+ ValidationUtil.sanitizeName(electionName)+"_"+ ValidationUtil.getTimestamp()+".zip");
//        response.setContentLength(zipData.length);
//
//        try (OutputStream out = response.getOutputStream()) {
//            out.write(zipData);
//            out.flush();
//        }
//    }


@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = UserService.getUserById(2); // however you get the user
        byte[] pdfBytes = PdfUtil.generatePdfReportForVerifiedUser(getServletContext(), user);

        if (pdfBytes != null) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"verified-user-details.pdf\"");
            response.setContentLength(pdfBytes.length);
            response.getOutputStream().write(pdfBytes);
            response.getOutputStream().flush();
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "PDF generation failed.");
        }
    }


    public static byte[] chartToByteArray(PieChart chart) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapEncoder.saveBitmap(chart, baos, BitmapEncoder.BitmapFormat.PNG);
        return baos.toByteArray();
    }


    public static byte[] chartToByteArray(CategoryChart chart) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BitmapEncoder.saveBitmap(chart, baos, BitmapEncoder.BitmapFormat.PNG);
        return baos.toByteArray();
    }


}

