package com.analyze;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import ru.yandex.qatools.ashot.shooting.ShootingStrategy;

@Configuration
public class WebPageScreenShotTaker {
	static int screenshotNum = 0;
	static WebDriver driver = null;
	
	@Value("${screenshot.directory}")
	private static String directory;
	
	public static void main(String[] args) {
		System.out.println("Dir: " + WebPageScreenShotTaker.screenShot("Fdf"));
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
		Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		byte[] imageInByte = toByteArrayAutoClosable(screenshot.getImage(), "jpg");

		ImageIO.write(screenshot.getImage(),"PNG",new File("C:\\Users\\agordic\\Documents\\workspace-spring-tool-suite-4-4.2.1.RELEASE\\NUPS_analiza_trzista\\new.png"));
		System.out.println("Took Screenshot for " + site + " and saved as " + "site" + screenshotNum + ".png");
		return imageInByte;
	}

	public static void destroy() {
		System.out.println("Closing browser...");
		driver.close();
	}
	
	private static byte[] toByteArrayAutoClosable(BufferedImage image, String type) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()){
        	
            ImageIO.write(image, type, out);
            return out.toByteArray();
        }
    }
}
