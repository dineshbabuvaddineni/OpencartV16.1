package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	// Data Provider1
	@DataProvider(name="LoginData1")
	public String[][] getData() throws IOException
	{
		String path=".\\testData\\OpencartLoginData.xlsx";
		ExcelUtility xlutil=new ExcelUtility(path); 
		
		int totalrows =xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellcount("Sheet1", 1);
		
		String logindata[][] = new String[totalrows][totalcols];
		
		for(int i=1;i<=totalrows;i++) {
			for(int j=0;j<totalcols;j++) // i is rows and j is columns 
			{
				logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j); //1,0
				
			}
		}
	return logindata; //returning two dimension array
	}
	
	//DataProvider 2
	//Data Provider 3
	//DataProvider 4
	

}
