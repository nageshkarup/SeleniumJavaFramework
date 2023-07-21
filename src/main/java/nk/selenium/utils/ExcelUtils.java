package nk.selenium.utils;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtils {



    //Please delete all the rows which does not have any data before using this function
    public static Object[][] getDataHashMap(String excelPath, String sheetName) {
        FileInputStream fis;
        XSSFWorkbook workbook;
        Sheet sheet;

        Object[][] data = null;

        try {

            File file = new File(excelPath);

            if (!file.exists()) {
                try {
                    Log.info("File Excel path not found.");
                    throw new RuntimeException("File Excel path not found.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            fis = new FileInputStream(excelPath);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);

            int rows = sheet.getLastRowNum();
            System.out.println("row count: "+rows);
            int columns = sheet.getRow(0).getLastCellNum();
            System.out.println("column count: "+columns);

            data = new Object[rows][1];
            System.out.println(data.length+" : "+data[0].length);
            Map<String, String> table;

            for (int row = 1; row <= rows; row++) {
                table = new HashMap<>();
                for (int colNum = 0; colNum < columns; colNum++) {
                    table.put(formatCell(sheet.getRow(0).getCell(colNum)), formatCell(sheet.getRow(row).getCell(colNum)));
                }
                data[row - 1][0] = table;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            Log.error(e.getMessage());
        }
        return data;
    }

    private static String formatCell(Cell cell) {
        DataFormatter format = new DataFormatter();
        return format.formatCellValue(cell);
    }
}
