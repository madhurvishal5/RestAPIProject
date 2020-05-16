package com.api.rootLib;


import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Root extends APIConfig {
	public  static ExtentReports extent;
	public  static ExtentTest extentTest;
	public APIConfig config = new APIConfig();

	@BeforeSuite
	public void setReport() {
		extent=generateExtentReport();
	}
	@AfterMethod
	public void getResultStatus(ITestResult result) throws InterruptedException
	{
		if(result.getStatus()==ITestResult.FAILURE){
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS "+result.getName()); 
			extentTest.log(LogStatus.FAIL, "TEST CASE FAILED IS "+result.getThrowable()); 
		}
		else if(result.getStatus()==ITestResult.SKIP){
			extentTest.log(LogStatus.SKIP, "Test Case SKIPPED IS " + result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS){
			extentTest.log(LogStatus.PASS, "Test Case PASSED IS " + result.getName());
		}
	}
	
	@AfterSuite
	public void closeApplicationAndEndReport() {
		extent.endTest(extentTest);
		extent.flush();
	}
}
