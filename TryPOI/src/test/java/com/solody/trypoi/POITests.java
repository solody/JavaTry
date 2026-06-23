package com.solody.trypoi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.*;

public class POITests {
    @Test
    public void testResizeImage() {
        try (OutputStream fileOut = new FileOutputStream("workbook.xlsx");
             Workbook wb = new XSSFWorkbook();
             InputStream is = new FileInputStream("project-header.png")) {

            byte[] bytes = IOUtils.toByteArray(is);
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);

            CreationHelper helper = wb.getCreationHelper();
            //create sheet
            Sheet sheet = wb.createSheet();

            Row row = sheet.createRow(0);
            row.setHeightInPoints(100);    // 100 pt tall

            sheet.setColumnWidth(0, 25 * 256); // 25 characters wide

            // Create the drawing patriarch.  This is the top level container for all shapes.
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(0);
            anchor.setRow1(0);
            anchor.setCol2(0);
            anchor.setRow2(0);
            anchor.setDx1(0);
            anchor.setDy1(0);
            anchor.setDx2(Units.columnWidthToEMU(sheet.getColumnWidth(0)));
            anchor.setDy2(Units.pixelToEMU(Units.pointsToPixel(row.getHeightInPoints())));
            anchor.setAnchorType(ClientAnchor.AnchorType.MOVE_AND_RESIZE);

            drawing.createPicture(anchor, pictureIdx);
            //save workbook
            wb.write(fileOut);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
