package com.solarprincess.app;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;

public class BalanceData {

    public double hitPercent;
    public double returnPercent;

    public BalanceData(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new HSSFWorkbook(fis);
        ProcessSheet(workbook.getSheetAt(0));
        workbook.close();
        fis.close();
    }

    private void ProcessSheet(Sheet sheet) {
        hitPercent = sheet.getRow(37).getCell(6).getNumericCellValue();
        returnPercent = sheet.getRow(40).getCell(6).getNumericCellValue();
    }
}