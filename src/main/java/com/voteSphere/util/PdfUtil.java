package com.voteSphere.util;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.voteSphere.config.AppConfig;
import com.voteSphere.model.User;
import jakarta.servlet.ServletContext;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PdfUtil {

    public static String VERIFIED_USER_DETAIL_PDF_TEMPLATE ="verified-user-detail.html";

    public static String loadPdfTemplate(ServletContext context, String templateName) throws IOException {
        try (InputStream is = context.getResourceAsStream("/WEB-INF/pages/pdf-templates/" + templateName)) {
            if (is == null) {
                throw new FileNotFoundException("Template not found: " + templateName);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }


    public static byte[] generatePdfReportForVerifiedUser(ServletContext context, User user) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // Read the HTML template
            String html = loadPdfTemplate(context, VERIFIED_USER_DETAIL_PDF_TEMPLATE);

            String baseUrl = AppConfig.get("IMAGE_BASE_URL");
            // Replace placeholders with actual data
            html = html
                    .replace("{{VOTER_ID}}", user.getVoterId())
                    .replace("{{FIRST_NAME}}", user.getFirstName())
                    .replace("{{LAST_NAME}}", user.getLastName())
                    .replace("{{NOTIFICATION_EMAIL}}", user.getEmail())
                    .replace("{{PHONE_NUMBER}}", user.getPhoneNumber())
                    .replace("{{REPORT_NAME}}", "Validated Voter Detail")
                    .replace("{{DOB}}", String.valueOf(ValidationUtil.convertTimestampToDateOnly(user.getDob())))
                    .replace("{{GENDER}}", ValidationUtil.capitalize(user.getGender()))
                    .replace("{{TEMPORARY_ADDRESS}}", user.getTemporaryAddress())
                    .replace("{{PERMANENT_ADDRESS}}", user.getPermanentAddress())
                    .replace("{{CURRENT_DATE}}", ValidationUtil.getTimestamp())
                    .replace("{{IS_VERIFIED}}", user.getIsVerified() ? "VERIFIED" : "PENDING")
                    .replace("{{LOGO}}", baseUrl+"logo.png")
                    .replace("{{PROFILE_IMAGE_URL}}", baseUrl+user.getProfileImage())
                    .replace("{{IMAGE_HOLDING_CITIZENSHIP_URL}}", baseUrl+user.getImageHoldingCitizenship())
                    .replace("{{THUMB_PRINT_URL}}",baseUrl+ user.getThumbPrint())
                    .replace("{{VOTER_CARD_FRONT_URL}}",baseUrl+ user.getVoterCardFront())
                    .replace("{{VOTER_CARD_BACK_URL}}",baseUrl+ user.getVoterCardBack())
                    .replace("{{CITIZENSHIP_FRONT_URL}}",baseUrl+ user.getCitizenshipFront())
                    .replace("{{CITIZENSHIP_BACK_URL}}",baseUrl+ user.getCitizenshipBack());

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.toStream(outputStream);

            builder.withHtmlContent(html, null); // htmlString contains your HTML


            builder.run();

            return outputStream.toByteArray();

        }catch (Exception e) {
                e.printStackTrace();
                return null;
        }
    }

}
