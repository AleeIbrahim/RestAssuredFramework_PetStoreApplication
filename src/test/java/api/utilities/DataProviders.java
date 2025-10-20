/**
 * DataProviders - A TestNG DataProvider utility class for data-driven testing
 * 
 * This class provides methods to read test data from Excel files and supply it to TestNG test methods.
 * It uses the ExcelUtility class to interact with Excel files and returns data in formats suitable
 * for parameterized testing with TestNG's @DataProvider annotation.
 * 
 * @package api.utilities
 * @version 1.0
 */
package api.utilities;

import java.io.IOException;
import org.testng.annotations.DataProvider;

public class DataProviders {

    /**
     * DataProvider method that reads ALL test data from the specified Excel sheet
     * 
     * This method reads the entire data set from "Sheet1" of the Userdata.xlsx file,
     * excluding the header row, and returns it as a 2D String array. Each inner array
     * represents a row of test data, making it ideal for tests that require multiple
     * parameters per test case.
     * 
     * @return String[][] A 2D array where:
     *         - First dimension: Data rows (excluding header)
     *         - Second dimension: All columns for each row
     * @throws IOException If the Excel file cannot be found, accessed, or read
     * 
     */
	
	String sheetName = "Sheet1";
	
    @DataProvider(name = "Data")
    public String[][] getAllData() throws IOException 
    {
        // Construct the absolute path to the Excel file in the project root directory
        String path = System.getProperty("user.dir") + "//TestData//UserModel_Data.xlsx";
        
        // Initialize ExcelUtility to handle Excel file operations
        ExcelUtility xl = new ExcelUtility(path);

        // Get the total number of rows in Sheet1 (returns 0-based index, so actual data rows = this value)
        int rownum = xl.getRowCount(sheetName);
        
        // Get the number of columns in the first data row (row 1, after header)
        // This assumes all data rows have the same number of columns
        int colcount = xl.getCellCount(sheetName, 1);

        // Initialize 2D array to store all test data
        // Dimensions: [number of data rows] x [number of columns per row]
        String apidata[][] = new String[rownum][colcount];

        // Iterate through all data rows (starting from row 1, skipping header row 0)
        for (int i = 1; i <= rownum; i++) 
        {
            // Iterate through all columns in the current row
            for (int j = 0; j < colcount; j++) 
            {
                // Extract cell data and store in the array
                // i-1 converts Excel row index (1-based) to array index (0-based)
                // j maintains column index (0-based)
                apidata[i - 1][j] = xl.getCellData(sheetName, i, j);
            }
        }
        
        // Return the complete test data set for TestNG data-driven testing
        return apidata;
    }
    
    /**
     * DataProvider method that reads ONLY USERNAMES from a specific column in the Excel sheet
     * 
     * This method extracts data specifically from column index 1 (second column) of "Sheet1",
     * making it ideal for tests that require only username data, such as:
     * - Username validation tests
     * - Login functionality tests
     * - User search tests
     * - Duplicate username checks
     * 
     * @return String[] A 1D array containing only usernames from column index 1
     * @throws IOException If the Excel file cannot be found, accessed, or read
     * 
     */
    @DataProvider(name = "UserNames")
    public String[] getUserNames() throws IOException 
    {
        // Construct the absolute path to the Excel file in the project root directory
        String path = System.getProperty("user.dir") + "//TestData//UserModel_Data.xlsx";
        
        // Initialize ExcelUtility to handle Excel file operations
        ExcelUtility xl = new ExcelUtility(path);

        // Get the total number of rows in Sheet1 (0-based index)
        int rownum = xl.getRowCount(sheetName);

        // Initialize 1D array to store only usernames
        // Size equals the number of data rows (excluding header)
        String apidata[] = new String[rownum];

        // Iterate through all data rows (starting from row 1, skipping header row 0)
        for (int i = 1; i <= rownum; i++) 
        {
            // Extract data from column index 1 (second column) and store in array
            // i-1 converts Excel row index (1-based) to array index (0-based)
            apidata[i - 1] = xl.getCellData(sheetName, i, 1);
        }

        // Return the username array for single-parameter TestNG testing
        return apidata;
    }
}