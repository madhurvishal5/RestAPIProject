package com.api.testScripts;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.api.rootLib.Root;
import com.cedarsoftware.util.io.JsonWriter;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;

public class getAllCountriesTest extends Root {

	@Test
	public void fetchCountries(){
		extentTest = extent.startTest("Fetch All Countries Test");
		String apiData = config.getPropertyObject("getAllCountries");
		Response resp = config.executeRequest(apiData);
		int actualStatusCode = resp.getStatusCode();
		int expectedStatusCode = 200;
		String actualContentType = resp.getContentType();
		String expectedContentType="application/json;charset=utf-8";
		long actualResponseTime = resp.getTime();
		long expectedResponseTime = 5000;
		SoftAssert s = new SoftAssert();
		if(actualStatusCode==expectedStatusCode){
			extentTest.log(LogStatus.PASS, "Staus Code Verified Successfully ----- "+"Staus Code : " + actualStatusCode);
		}
		else{
			extentTest.log(LogStatus.FAIL, "Failed to Verify Staus Code ----- "+"Staus Code : " + actualStatusCode);	
		}
		s.assertEquals(actualStatusCode,expectedStatusCode);
		if(actualContentType.equals(expectedContentType)){
			extentTest.log(LogStatus.PASS, "Content  Type Verified Successfully ----- "+"Content Type: " + actualContentType);
		}
		else{
			extentTest.log(LogStatus.FAIL, "Failed to Verify Content  Type -----"+"Content Type: " + actualContentType );	
		}
		s.assertEquals(actualContentType,expectedContentType);
		if(actualResponseTime < expectedResponseTime){
			extentTest.log(LogStatus.PASS, "Response Time Verified Successfully-----"+"Response Time: " + actualResponseTime +" ms");
		}
		else{
			extentTest.log(LogStatus.FAIL, "Failed to Verify Response Time ----- "+"Response Time: " + actualResponseTime +" ms");	
		}
		s.assertTrue(actualResponseTime<expectedResponseTime);
		String responseBody = resp.asString();
		extentTest.log(LogStatus.INFO, "<pre>" + "Response Body: " + JsonWriter.formatJson(responseBody) + "</pre>");
		resp.then().log().all();
		s.assertAll();
	}
}
