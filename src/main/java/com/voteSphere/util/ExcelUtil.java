package com.voteSphere.util;

import com.voteSphere.config.DBConnectionManager;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExcelUtil {

    public static String SQL_QUERY_FOR_ELECTION_INFO = "SELECT \n" +
            "    -- Votes table\n" +
            "    v.vote_id,\n" +
            "    v.user_id AS voted_user_id,\n" +
            "    v.election_id AS voted_election_id,\n" +
            "    v.party_id AS voted_party_id,\n" +
            "    v.voted_at,\n" +
            "    v.ip,\n" +
            "\n" +
            "    -- Users table\n" +
            "    u.user_id,\n" +
            "    u.first_name,\n" +
            "    u.last_name,\n" +
            "    u.voter_id,\n" +
            "    u.notification_email,\n" +
            "    u.phone_number,\n" +
            "    u.profile_image,\n" +
            "    u.image_holding_citizenship,\n" +
            "    u.voter_card_front,\n" +
            "    u.voter_card_back,\n" +
            "    u.citizenship_front,\n" +
            "    u.citizenship_back,\n" +
            "    u.thumb_print,\n" +
            "    u.dob,\n" +
            "    u.gender,\n" +
            "    u.temporary_address,\n" +
            "    u.permanent_address,\n" +
            "    u.role,\n" +
            "    u.is_verified,\n" +
            "    u.is_email_verified,\n" +
            "    u.created_at AS user_created_at,\n" +
            "\n" +
            "    -- Elections table\n" +
            "    e.election_id,\n" +
            "    e.name AS election_name,\n" +
            "    e.type AS election_type,\n" +
            "    e.cover_image AS election_cover_image,\n" +
            "    e.date AS election_date,\n" +
            "    e.start_time,\n" +
            "    e.end_time,\n" +
            "\n" +
            "    -- Parties table\n" +
            "    p.party_id,\n" +
            "    p.name AS party_name,\n" +
            "    p.leader_name,\n" +
            "    p.founder_name,\n" +
            "    p.symbol_image,\n" +
            "    p.cover_image AS party_cover_image,\n" +
            "    p.description AS party_description\n" +
            "\n" +
            "FROM votes v\n" +
            "JOIN users u ON v.user_id = u.user_id\n" +
            "JOIN elections e ON v.election_id = e.election_id\n" +
            "JOIN parties p ON v.party_id = p.party_id\n" +
            "WHERE v.election_id = ?;\n";

    public static byte[] generateExcelElectionVoteReport(int electionId) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             Connection conn = DBConnectionManager.establishConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_QUERY_FOR_ELECTION_INFO)) {

            XSSFSheet sheet = workbook.createSheet("Election Vote Report");

            stmt.setInt(1, electionId);
            var resultSet = stmt.executeQuery();
            var metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                Cell cell = headerRow.createCell(i - 1);
                cell.setCellValue(metaData.getColumnLabel(i));
            }

            int rowIndex = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowIndex++);
                for (int i = 1; i <= columnCount; i++) {
                    Cell cell = row.createCell(i - 1);
                    Object value = resultSet.getObject(i);
                    if (value != null) {
                        if (value instanceof Integer) {
                            cell.setCellValue((Integer) value);
                        } else if (value instanceof Boolean) {
                            cell.setCellValue((Boolean) value);
                        } else {
                            cell.setCellValue(value.toString());
                        }
                    }
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
