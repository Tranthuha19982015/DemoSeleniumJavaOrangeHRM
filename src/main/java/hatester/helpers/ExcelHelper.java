package hatester.helpers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ExcelHelper {
    private Workbook wb; //Đại diện cho toàn bộ file Excel (có thể chứa nhiều sheet).
    private Sheet sh; //Đại diện cho một sheet cụ thể trong workbook.
    //private Cell cell; //Đại diện cho một ô (cell) trong sheet.
    private CellStyle defaultCellStyle; //Dùng để định dạng style mặc định cho cell (căn giữa, màu nền, font, …).
    private String excelFilePath; //Lưu đường dẫn file Excel để ghi ngược lại khi sửa dữ liệu.
    private Map<String, Integer> columns = new HashMap<>(); //Map lưu tên cột → vị trí cột (index). Dựa vào hàng đầu tiên (header).

    public void setExcelFile(String excelPath, String sheetName) {
        try {
            //Tạo một đối tượng File đại diện cho đường dẫn file Excel (excelPath).
            //Chỉ là tham chiếu đường dẫn, chưa mở file thật.
            File f = new File(excelPath);

            //Kiểm tra file có tồn tại trong ổ cứng không.
            if (!f.exists()) {
                //Nếu lỗi do input không hợp lệ (file ko tồn tại, sheet ko tồn tại) → dùng IllegalArgumentException.
                throw new IllegalArgumentException("File doesn't exist: " + excelPath);
            }

            //Nếu file tồn tại → tạo FileInputStream để mở file Excel ra đọc.
            try (FileInputStream fis = new FileInputStream(f)) {

                //Từ fis (luồng đọc file), Apache POI sẽ tạo ra một Workbook object trong bộ nhớ.
                // Workbook là bản sao file Excel để thao tác mà không ảnh hưởng trực tiếp đến file gốc.
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

                //Duyệt qua tất cả các ô (cell) trong dòng header.
                //Lưu vào Map<String, Integer> columns
                headerRow.forEach(cell -> {
                    if (cell != null && cell.getCellType() == CellType.STRING) {
                        columns.put(cell.getStringCellValue().trim(), cell.getColumnIndex());
                    }
                });

                // Tạo style
                defaultCellStyle = wb.createCellStyle();
                defaultCellStyle.setFillPattern(FillPatternType.NO_FILL);
                defaultCellStyle.setAlignment(HorizontalAlignment.CENTER);
                defaultCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while opening Excel file: " + e.getMessage(), e);
        }
    }

    private void validateWorkbook() {
        if (wb == null || sh == null)
            throw new IllegalStateException("Workbook or sheet not initialized. Call setExcelFile() first.");
    }

    //Lấy dữ liệu từ cell theo vị trí cột và dòng.
    public String getCellData(int columnIndex, int rowIndex) {
        validateWorkbook();
        Row row = sh.getRow(rowIndex);
        if (row == null) {
            return ""; // Row không tồn tại
        }

        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return ""; // Cell không tồn tại
        }

        try {
            switch (cell.getCellType()) { //kiểm tra kiểu dữ liệu của ô
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

    //Lấy dữ liệu từ cell theo tên cột và dòng.
    public String getCellData(String columnName, int rowIndex) {
        validateWorkbook();
        Integer columnIndex = columns.get(columnName);
        if (columnIndex == null) {
            throw new IllegalArgumentException("Column '" + columnName + "' not found in sheet.");
        }
        return getCellData(columnIndex, rowIndex);

    }

    private void setCellData(String text, int columnIndex, int rowIndex) {
        validateWorkbook();

        try {
            Row row = sh.getRow(rowIndex);
            if (row == null) {
                row = sh.createRow(rowIndex);
            }

            Cell cell = row.getCell(columnIndex);
            if (cell == null) {
                cell = row.createCell(columnIndex);
            }

            // Set giá trị cho cell
            cell.setCellValue(text != null ? text : "");

            cell.setCellStyle(defaultCellStyle);

            try (FileOutputStream fos = new FileOutputStream(excelFilePath)) {
                wb.write(fos);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to set cell data at row " + rowIndex + ", column " + columnIndex, e);
        }
    }

    public void setCellData(String text, String columnName, int rowIndex) {
        Integer columnIndex = columns.get(columnName);
        if (columnIndex == null) {
            throw new IllegalArgumentException("Column '" + columnName + "' not found in sheet.");
        }
        setCellData(text, columnIndex, rowIndex);
    }

    public void closeWorkbook() {
        try {
            if (wb != null) {
                wb.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while opening Excel file: " + e.getMessage(), e);
        }
    }

    //Đọc toàn bộ dữ liệu (trừ dòng tiêu đề) trong file Excel → trả về Object[][]
    public Object[][] getExcelData(String filePath, String sheetName) {
        Object[][] data = null;
        try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {

            // load the sheet
            Sheet sh = workbook.getSheet(sheetName);
            if (sh == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in file: " + filePath);
            }

            // load the row
            Row row = sh.getRow(0);
            if (row == null) {
                throw new IllegalStateException("Header row (row 0) is missing in sheet: " + sheetName);
            }

            int noOfRows = sh.getPhysicalNumberOfRows(); //số hàng có dữ liệu thực tế (kể cả header)
            if (noOfRows < 2) {  //Nếu nhỏ hơn 2 → chỉ có header, chưa có dữ liệu test nào.
                throw new IllegalStateException("Sheet '" + sheetName + "' has no data rows.");
            }

            int noOfCols = row.getLastCellNum(); //Số lượng cột trong dòng tiêu đề

            System.out.println(noOfRows + " - " + noOfCols);

            Cell cell;
            data = new Object[noOfRows - 1][noOfCols];  //Mỗi dòng dữ liệu (bỏ dòng header) là 1 hàng trong data

            for (int i = 1; i < noOfRows; i++) {
                for (int j = 0; j < noOfCols; j++) {
                    row = sh.getRow(i);
                    cell = row.getCell(j);  //lấy ô tại vị trí cột j trong dòng i

                    switch (cell.getCellType()) {
                        case STRING:
                            data[i - 1][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                data[i - 1][j] = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                            } else {
                                // Nếu là số nguyên thì ép kiểu về long, nếu là số thực thì để nguyên
                                double numericValue = cell.getNumericCellValue();
                                if (numericValue == Math.floor(numericValue)) {  //Math.floor(x) = làm tròn xuống giá trị nguyên gần nhất ≤ x
                                    data[i - 1][j] = String.valueOf((long) numericValue); // Số nguyên
                                } else {
                                    data[i - 1][j] = String.valueOf(numericValue); // Số thực
                                }
                            }
                            break;
                        case BOOLEAN:
                            data[i - 1][j] = Boolean.toString(cell.getBooleanCellValue()); //HOẶC dùng String.valueOf(cell.getBooleanCellValue());
                            break;
                        case BLANK:
                            data[i - 1][j] = "";
                            break;
                        default:
                            data[i - 1][j] = "";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("The exception is:" + e.getMessage());
            throw new RuntimeException(e);
        }
        return data;  //Trả mảng Object[][] để DataProvider hoặc test script dùng
    }
}
