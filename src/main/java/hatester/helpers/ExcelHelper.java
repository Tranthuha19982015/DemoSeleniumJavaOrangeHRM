package hatester.helpers;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExcelHelper {
    private FileInputStream fis;
    private FileOutputStream fileOut;
    private Workbook wb;
    private Sheet sh;
    private Cell cell;
    private Row row;
    private CellStyle cellstyle;
    private Color mycolor;
    private String excelFilePath;
    private Map<String, Integer> columns = new HashMap<>();

    public void setExcelFile(String excelPath, String sheetName) {
        try {
            File f = new File(excelPath);

            if (!f.exists()) {
                throw new RuntimeException("File doesn't exist." + excelPath);
            }

            try (FileInputStream fis = new FileInputStream(excelPath)) {
                wb = WorkbookFactory.create(fis);
                sh = wb.getSheet(sheetName);

                if (sh == null) {
                    throw new RuntimeException("Sheet name doesn't exist." + sheetName);
                }

                this.excelFilePath = excelPath;
                // Đọc header row (row 0) và lưu tên cột vào map
                Row headerRow = sh.getRow(0);
                if (headerRow == null) {
                    throw new RuntimeException("Sheet không có header row.");
                }

                //adding all the column header names to the map 'columns'
                headerRow.forEach(cell -> {
                    columns.put(cell.getStringCellValue(), cell.getColumnIndex());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi mở file Excel: " + e.getMessage(), e);
        }
    }
}
