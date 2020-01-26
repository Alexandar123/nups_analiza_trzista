package com.analyze.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import it.grabz.grabzit.GrabzItClient;
import it.grabz.grabzit.GrabzItFile;
import it.grabz.grabzit.enums.ImageFormat;
import it.grabz.grabzit.parameters.ImageOptions;

public class Test {
	private static int      DISPLAY_NUMBER  = 99;
    private static String   XVFB            = "/usr/bin/Xvfb";
    private static String   XVFB_COMMAND    = XVFB + " :" + DISPLAY_NUMBER;
    private static String   URL             = "http://www.google.com/";
    private static String   RESULT_FILENAME = "/tmp/screenshot.png";

	public static void main(String args[]) throws Exception 
    { 
		GrabzItClient grabzIt = new GrabzItClient("YjMwNjRmNDg0YTI2NDFmYmE4MjZkMTE0ZTQxZGUxYjc=", "EE0/Pz8/axxqPz8/P3RyP10/P1caUTYgPxM/Pz9VUz8=");

		ImageOptions options = new ImageOptions();
		options.setBrowserHeight(768);
		options.setBrowserWidth(1024);
		
		
		options.setFormat(ImageFormat.JPG);

		grabzIt.URLToImage("https://www.webnekretnine.net/oglas-1780828-nekretnina-Prodaja+stanova-Beograd-Srbija.html", options);
		//Then call the Save or SaveTo method
		grabzIt.SaveTo("C:\\ttt\\img.jpg");
		System.out.println("Gotovo");
		/*Process p = Runtime.getRuntime().exec(XVFB_COMMAND);
        FirefoxBinary firefox = new FirefoxBinary();
        firefox.setEnvironmentProperty("DISPLAY", ":" + DISPLAY_NUMBER);
        
        WebDriver driver = new FirefoxDriver(firefox, null);
        driver.get("https://www.webnekretnine.net/oglas-1772920-nekretnina-Prodaja+stanova-Beograd-Srbija.html");
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("C:\\ttt\\img.jpg"));*/
	}
}
