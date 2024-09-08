package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import tesBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter; //UI of the report
	public ExtentReports extent; //populate common information on the report(tester name, Os used, browsername, project name , module name, environment name)
	public ExtentTest test; //creating test case entries in the report and update status of the test methods.
	String repName;
	public void onStart(ITestContext testContext) {
			/*SimpleDateFormat df = new SimpleDateFormat("YYYY.MM.dd.HH.mm.ss");
			Date dt= new Date();
			 String currentdatetimestamp=df.format(dt);*/
		
			String timeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //time stamp
			repName="Test-Report-" + timeStamp + ".html";
			sparkReporter = new ExtentSparkReporter(".//reports//"+ repName);//specify location of the report
			
			sparkReporter.config().setDocumentTitle("Opencart Automation Report"); //Title of report
			sparkReporter.config().setReportName("Opencart Functional Testing"); //Name of the report
			sparkReporter.config().setTheme(Theme.DARK);
			
			extent=new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Application", "Opencart");
			extent.setSystemInfo("Module", "Admin");
			extent.setSystemInfo("Sub Module", "Customers");
			extent.setSystemInfo("User Name",System.getProperty("user.name"));
			extent.setSystemInfo("Environment", "QA");
			
			String os=testContext.getCurrentXmlTest().getParameter("os");
			extent.setSystemInfo("Operating System", os);
			
			String browser=testContext.getCurrentXmlTest().getParameter("browser");
			extent.setSystemInfo("Browser", browser);
			
			List<String> includedGroups=testContext.getCurrentXmlTest().getIncludedGroups();
			if(!includedGroups.isEmpty()) {
				extent.setSystemInfo("Groups", includedGroups.toString());
			}
	}
	
	public void onTestSuccess(ITestResult result) {
	    test = extent.createTest(result.getTestClass().getName()); //create a new entry in the report
	    test.assignCategory(result.getMethod().getGroups()); //to display groups in report
	    test.log(Status.PASS,result.getName()+"got successfully executed"); //update status pass/fail/skip
	    
	  }
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName()); //create a new entry in the report
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		
	    test.log(Status.FAIL,result.getName() + "got Failed"); //update status pass/fail/skip
	    test.log(Status.INFO,result.getThrowable().getMessage());  //returns the main error.
	    
	    try {
	    	String imgPath=new BaseClass().captureScreen(result.getName());
	    	test.addScreenCaptureFromPath(imgPath);
	    }catch(Exception e1) {
	    	e1.printStackTrace();
	    }
	    
	  }
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName()); //create a new entry in the report
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.SKIP, result.getName()+"got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	 public void onFinish(ITestContext context) {
		    extent.flush(); // it will write all the above logs into report
		    
		    String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
		    File extentReport= new File(pathOfExtentReport);
		    
		    try {
		    	Desktop.getDesktop().browse(extentReport.toURI());
		    }catch(IOException e) {
		    	e.printStackTrace();
		    }
		    
		  }
}
	
