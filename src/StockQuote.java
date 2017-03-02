import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StockQuote {

	public static ArrayList<String> symbols = new ArrayList<>();
	
    public static ArrayList<String> stockSymbols = new ArrayList<>();
    public static ArrayList<String> stockValues = new ArrayList<>();
	
	public static void main(String[] args)
	{
		GetPriceForEachSymbol();
	}
	
	public static void GetPrice(String symbol) throws IOException{
	{
		URL url = new URL("https://www.google.com/finance?q=" + symbol);
		URLConnection urlConn = url.openConnection();
		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());		
		BufferedReader buff = new BufferedReader(inStream);
		
		String price = ("No Price Found");
		String line = buff.readLine();
		
		while(line != null)
		{
			if(line.contains("[\"" + symbol + "\","))
			{
				int target = line.indexOf("[\"" + symbol + "\",");
				int deci = line.indexOf(".", target);
				int start = deci;	
				
				while(!Character.isDigit(line.charAt(start + 1)))
				{
					//System.out.println("Not a digit");
					deci = line.indexOf(".", deci + 1);
					start = deci;
				}
				
				while(line.charAt(start) != '\"')
				{
					start--;
				}				
				
				price = line.substring(start + 1, deci + 3);
				
				// Add to lists (same index)
				stockSymbols.add(symbol);
				stockValues.add(price);
			}
						
			line = buff.readLine();
		}
		System.out.println(symbol + ": " + price);
		
		
		// Create Excel Spreadsheet Here	
		XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("Stock Market Quotes");	
	    
	    // Create Row with title and dates here
	    
        int rowCount = 1;
        int columnCount = 0;                        
        int valueColumnCount = 1;
	    
        for (String stock : stockSymbols) 
        {        	
        	Row row = sheet.createRow(rowCount++);
        	Cell cell = row.createCell(columnCount);
            cell.setCellValue(stock);
            
            Cell cell2 = row.createCell(valueColumnCount);
            cell2.setCellValue(stockValues.get(stockSymbols.indexOf(stock)));           
        }

        try (FileOutputStream outputStream = new FileOutputStream("StockMarket.xlsx", false)) {
            workbook.write(outputStream);
            workbook.close();                        
        }
	}}
	
	public static void GetPriceForEachSymbol()
	{		
		for(String listedSymbol : symbols)
		{
			try {
				GetPrice(listedSymbol);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
