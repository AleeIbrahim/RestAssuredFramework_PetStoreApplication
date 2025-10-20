/**
 * ExcelUtility - A comprehensive utility class for reading from and writing to Excel files (.xlsx format)
 * This class provides wrapper methods around Apache POI functionality for easier Excel file manipulation
 * 
 * Primary Features:
 * - Read row and cell counts from Excel sheets
 * - Read cell data with automatic type formatting
 * - Write data to specific cells with automatic file/sheet/row creation
 * - Handle both existing and new Excel files
 * 
 */
package api.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;


public class ExcelUtility {

	public FileInputStream fi;   // Input stream object for reading Excel files
	public FileOutputStream fo;  // Output stream object for writing Excel files
	
	// Apache POI objects for Excel file manipulation
	public XSSFWorkbook workbook; // Represents the entire Excel workbook
	public XSSFSheet sheet;       // Represents a single worksheet within the workbook
	public XSSFRow row;           // Represents a row within a worksheet
	public XSSFCell cell;         // Represents a cell within a row
	public CellStyle style;       // Represents cell formatting styles
	
	String path; // File path to the Excel file

	/**
	 * Constructor - Initializes the ExcelUtility with a specific file path
	 * 
	 * @param path The file system path to the Excel file (.xlsx)
	 */
	public ExcelUtility(String path) 
	{
		this.path = path;
	}

	/**
	 * Gets the total number of rows in a specified worksheet
	 * Note: Returns the last row number (0-based index), so actual row count = lastRowNum + 1
	 * 
	 * @throws IOException If file cannot be read or found
	 */
	public int getRowCount(String sheetName) throws IOException {
		// Open file input stream and create workbook instance
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		
		// Get the specified worksheet
		sheet = workbook.getSheet(sheetName);
		
		// Get the last row number (0-based index)
		int rowcount = sheet.getLastRowNum();
		
		// Clean up resources
		workbook.close();
		fi.close();
		
		return rowcount;
	}

	/**
	 * Gets the number of cells in a specific row of a worksheet
	 * Note: Returns the cell count (1-based), so for cells A-E, returns 5
	 * 
	 * @param sheetName The name of the worksheet to examine
	 * @param rownum The row index (0-based) to count cells from
	 * @return int The number of cells in the specified row
	 * @throws IOException If file cannot be read or found
	 */
	public int getCellCount(String sheetName, int rownum) throws IOException 
	{
		// Open file input stream and create workbook instance
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		
		// Get the specified worksheet and row
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		
		// Get the number of cells in the row (1-based count)
		int cellcount = row.getLastCellNum();
		
		// Clean up resources
		workbook.close();
		fi.close();
		
		return cellcount;
	}

	/**
	 * Reads data from a specific cell and returns it as a formatted String
	 * Handles all cell types (numbers, dates, formulas, text) and converts them to String
	 * Returns empty string if cell is null or any error occurs
	 * 
	 * @param sheetName The name of the worksheet containing the cell
	 * @param rownum The row index (0-based) of the cell
	 * @param column The column index (0-based) of the cell
	 * @return String The formatted cell value as String, or empty string if error
	 * @throws IOException If file cannot be read or found
	 */
	public String getCellData(String sheetName, int rownum, int column) throws IOException 
	{
		// Open file input stream and create workbook instance
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);
		
		// Get the specified worksheet, row, and cell
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(column);

		// DataFormatter handles all cell types and returns formatted String representation
		DataFormatter formatter = new DataFormatter();
		String data;
		
		try {
			// Format cell value - works for numbers, dates, formulas, etc.
			data = formatter.formatCellValue(cell);
		} catch (Exception e) 
		{
			// Return empty string if cell is null or any error occurs during formatting
			data = "";
		}
		
		// Clean up resources
		workbook.close();
		fi.close();
		
		return data;
	}

	/**
	 * Writes data to a specific cell in the Excel file
	 * Automatically creates the file, worksheet, row, or cell if they don't exist
	 * 
	 * @param sheetName The name of the worksheet to write to
	 * @param rownum The row index (0-based) to write to
	 * @param column The column index (0-based) to write to
	 * @param data The String data to write to the cell
	 * @throws IOException If file operations fail
	 */
	public void setCellData(String sheetName, int rownum, int column, String data) throws IOException {
		// Check if the Excel file exists
		File xlfile = new File(path);
		
		// Create new Excel file if it doesn't exist
		if (!xlfile.exists()) 
		{
			workbook = new XSSFWorkbook();
			fo = new FileOutputStream(path);
			workbook.write(fo);
		}

		// Open the existing or newly created file
		fi = new FileInputStream(path);
		workbook = new XSSFWorkbook(fi);

		// Create worksheet if it doesn't exist
		if (workbook.getSheetIndex(sheetName) == -1) 
		{
			workbook.createSheet(sheetName);
		}
		sheet = workbook.getSheet(sheetName);

		// Create row if it doesn't exist
		if (sheet.getRow(rownum) == null) 
		{
			sheet.createRow(rownum);
		}
		row = sheet.getRow(rownum);

		// Create cell and set the data value
		cell = row.createCell(column);
		cell.setCellValue(data);

		// Write changes back to the file
		fo = new FileOutputStream(path);
		workbook.write(fo);
		
		// Clean up all resources
		workbook.close();
		fi.close();
		fo.close();
	}
}




