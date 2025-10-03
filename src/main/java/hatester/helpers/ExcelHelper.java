package hatester.helpers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ExcelHelper {
    private Workbook wb;
    private Sheet sh;
    private Cell cell;
    private CellStyle defaultCellStyle;
    private String excelFilePath;
    private Map<String, Integer> columns = new HashMap<>();

    public void setExcelFile(String excelPath, String sheetName) {
        try {
            //Tạo một File object đại diện cho đường dẫn file Excel (excelPath).
            //Chỉ là tham chiếu đường dẫn, chưa mở file thật.
            File f = new File(excelPath);

            //Kiểm tra file có tồn tại trong ổ cứng không.
            if (!f.exists()) {
                //Nếu lỗi do input không hợp lệ (file ko tồn tại, sheet ko tồn tại) → dùng IllegalArgumentException.
                throw new IllegalArgumentException("File doesn't exist." + excelPath);
            }

            //Nếu file tồn tại → tạo FileInputStream để mở file Excel ra đọc.
            try (FileInputStream fis = new FileInputStream(f)) {

                //Từ fis (luồng đọc file), Apache POI sẽ tạo ra một Workbook object trong bộ nhớ.
                // Workbook là bản sao file Excel để bạn thao tác mà không ảnh hưởng trực tiếp đến file gốc.
                wb = WorkbookFactory.create(fis);

                sh = wb.getSheet(sheetName);  //Tìm sheet theo tên (sheetName) trong workbook vừa load.

                if (sh == null) {
                    throw new IllegalArgumentException("Sheet '" + sheetName + "' does not exist in file: " + excelPath);
                }

                this.excelFilePath = excelPath; // lưu lại đường dẫn file để ghi ra

                // Lấy dòng đầu tiên trong sheet (index = 0)
                Row headerRow = sh.getRow(0);
                if (headerRow == null) {
                    throw new IllegalArgumentException("Sheet '" + sheetName + "' does not have a header row (row 0).");
                }

                //Duyệt qua tất cả các cell trong dòng header.
                //Lưu vào Map<String, Integer> columns
                headerRow.forEach(cell -> {
                    if (cell != null && cell.getCellType() == CellType.STRING) {
                        columns.put(cell.getStringCellValue().trim(), cell.getColumnIndex());
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while opening Excel file: " + e.getMessage(), e);
        }
    }

    public String getCellData(int columnIndex, int rowIndex) {
        Row row = sh.getRow(rowIndex);
        if (row == null) {
            return ""; // Row không tồn tại
        }

        cell = row.getCell(columnIndex);
        if (cell == null) {
            return ""; // Cell không tồn tại
        }

        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                    } else {
                        // Nếu là số nguyên thì ép kiểu về long, nếu là số thực thì để nguyên
                        double numericValue = cell.getNumericCellValue();
                        if (numericValue == Math.floor(numericValue)) {  //Math.floor(x) = làm tròn xuống giá trị nguyên gần nhất ≤ x
                            return String.valueOf((long) numericValue); // Số nguyên
                        } else {
                            return String.valueOf(numericValue); // Số thực
                        }
                    }
                case BOOLEAN:
                    return Boolean.toString(cell.getBooleanCellValue()); //HOẶC dùng String.valueOf(cell.getBooleanCellValue());
                case BLANK:
                    return "";
                default:
                    return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getCellData(String columnName, int rowIndex) {
        Integer columnIndex = columns.get(columnName);
        if (columnIndex == null) {
            throw new IllegalArgumentException("Column '" + columnName + "' not found in sheet.");
        }
        return getCellData(columnIndex, rowIndex);

    }

    public void setCellData(String text, int columnIndex, int rowIndex) {
        try {
            Row row = sh.getRow(rowIndex);
            if (row == null) {
                row = sh.createRow(rowIndex);
            }

            cell = row.getCell(columnIndex);
            if (cell == null) {
                cell = row.createCell(columnIndex);
            }

            // Set giá trị cho cell
            cell.setCellValue(text != null ? text : "");

            // Tạo style một lần duy nhất (lazy initialization)
            if (defaultCellStyle == null) {
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setFillPattern(FillPatternType.NO_FILL);
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                defaultCellStyle = style;
            }

            cell.setCellStyle(defaultCellStyle);

            // Ghi workbook ra file (try-with-resources)
            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                wb.write(fos);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to set cell data at row " + rowIndex + ", column " + columnIndex, e);
        }
    }

    public void setCellData(String text, String columnName, int rowIndex) {
        try {
            Row row = sh.getRow(rowIndex);
            if (row == null) {
                row = sh.createRow(rowIndex);
            }

            cell = row.getCell(columns.get(columnName));
            if (cell == null) {
                cell = row.createCell(columns.get(columnName));
            }

            cell.setCellValue(text != null ? text : "");

            if (defaultCellStyle == null) {
                XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
                style.setFillPattern(FillPatternType.NO_FILL);
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setVerticalAlignment(VerticalAlignment.CENTER);
                defaultCellStyle = style;
            }

            cell.setCellStyle(defaultCellStyle);

            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                wb.write(fos);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to set cell data at row " + rowIndex + ", column " + columnName, e);
        }
    }
}
