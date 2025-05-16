package com.voteSphere.util;

import com.voteSphere.dao.ElectionDao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static byte[] createZipWithExcelAndPngs(
            byte[] excelData,
            Map<String, byte[]> pngImages,String electionName
    ) throws IOException {

        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOut = new ZipOutputStream(byteOutStream)) {

            // Add Excel file
            ZipEntry excelEntry = new ZipEntry(electionName+ValidationUtil.getTimestamp()+"_vote_report.xlsx");
            zipOut.putNextEntry(excelEntry);
            zipOut.write(excelData);
            zipOut.closeEntry();

            // Add PNG files
            for (Map.Entry<String, byte[]> entry : pngImages.entrySet()) {
                ZipEntry pngEntry = new ZipEntry(entry.getKey());
                zipOut.putNextEntry(pngEntry);
                zipOut.write(entry.getValue());
                zipOut.closeEntry();
            }
        }

        return byteOutStream.toByteArray();
    }
}
