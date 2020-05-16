package com.api.rootLib;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Properties;
import org.json.simple.JSONObject;
import com.relevantcodes.extentreports.ExtentReports;
import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class APIConfig {

	Properties pobj = new Properties();
	Response resp;
	String[] KeyValue;

	public String getPropertyObject(String key){
		try{
			FileInputStream file = new FileInputStream("./apiList.properties");
			pobj.load(file);
		}catch(Exception e){
			e.printStackTrace();
		}
		return pobj.getProperty(key);
	}

	public Response executeRequest(String apiData){
		String arr[]=apiData.split(";");
		if(arr[0].equals("get"))
		{
			resp=given()
					.param("userId", "7")
					.when()
					.get(arr[1]);
		}
		else if (arr[0].equals("post")) {
			String postobj=createJsonObject(arr[2]);
			resp=given()
					.contentType(ContentType.JSON)
					.body(postobj)
					.when()
					.post(arr[1]);
		}
		else if (arr[0].equals("patch")) {
			String postobj=createJsonObject(arr[2]);
			resp=given()
					.contentType(ContentType.JSON)
					.body(postobj)
					.when()
					.patch(arr[1]);
		}
		else if (arr[0].equals("put")) {
			String postobj=createJsonObject(arr[2]);
			resp=given()
					.contentType(ContentType.JSON)
					.body(postobj)
					.when()
					.put(arr[1]);
		}
		else if (arr[0].equals("delete")) {
			resp=delete(arr[1]);
		}
		return resp;

	}

	public String createJsonObject(String data) {
		JSONObject jobj = new JSONObject();
		String arrJsonData[] =data.split(",");
		for(int i=0;i<arrJsonData.length;i++){
			KeyValue=arrJsonData[i].split(":");
			jobj.put(KeyValue[0], KeyValue[1]);
		}
		return jobj.toJSONString();
	}
	
	public static ExtentReports generateExtentReport() {
		ExtentReports extent =null;
		try {
			Date d = new Date();
			String currentDate = d.toString().replaceAll(":","_");
			extent=new ExtentReports(System.getProperty("user.dir")+"/ExtentReports/"+"Rest Assured Report" +"_"+ currentDate + ".html",false);
			extent.loadConfig(new File(System.getProperty("user.dir")+"./ReportsConfig.xml")); 
			extent.addSystemInfo("Rest-Assured Version", "3.3.0").addSystemInfo("User Name", "Vishal Madhur");
		}catch(Exception e) {
		}
		return extent;
	}
}
