package com.analyze;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
public class WebPageScreenShotTaker {
	static int screenshotNum = 0;
	static WebDriver driver = null;
	@Value("${screenshot.directory}")
	private static String directory;
	public static void main(String[] args) {
		System.out.println("Dir: " + WebPageScreenShotTaker.directory);
	}
	public static byte[] screenShot(String url) {
		byte[] byteImage = null;
		initDriver();
		WebPageScreenShotTaker ws = new WebPageScreenShotTaker();
		try {
			byteImage = ws.capture(url);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		destroy();
		return byteImage;
	}

	public static void initDriver() {
		System.setProperty("webdriver.chrome.driver", "C:\\ttt\\driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().setPosition(new Point(-2000, 0));
	}

	public byte[] capture(String site) throws IOException {
		screenshotNum++;
		driver.get(site);
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		byte[] b = Files.readAllBytes(scrFile.toPath());
		FileUtils.copyFile(scrFile, new File(directory + screenshotNum + ".png"));
		System.out.println("Took Screenshot for " + site + " and saved as " + "site" + screenshotNum + ".png");
		return b;
	}

	public static void destroy() {
		System.out.println("Closing browser...");
		driver.close();
	}
}
