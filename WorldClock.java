package Pages;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import Base.Base;

public class WorldClock extends Base {
	By email = By.xpath("//input[@type='email']");
	By next = By.xpath("//input[@type='submit']");
	By pcall = By.xpath("//*[@data-value='TwoWayVoiceMobile']"); // "//*[@id=\"idDiv_SAOTCS_Proofs\"]/div[2]/div/div/div[2]/div"
	By pass = By.name("passwd");
	By acc = By.id("user-name");
	By yes = By.xpath("//input[@value='Yes']");
	By d = By.xpath("//span[@class='date ng-binding']");
	By t = By.xpath("//span[@class='time flex align-items-baseline justify-content-center ng-binding']");
	By c = By.xpath("//span[@class='location ng-binding']");
	By sliderx = By.xpath("//div[@class='slider-handle min-slider-handle round']");

	//
	public void login() {
		logger = report.createTest("Login into Becognizant.");
		try {
			wait(20, email);
			driver.findElement(email).sendKeys(prop.getProperty("userEmail")); // Enter user details   prop.getProperty("userEmail")
			driver.findElement(next).click();
			wait(20, pass);
			// driver.findElement(pass).sendKeys(); //Enter password
			// prop.getProperty("password")
			driver.findElement(next).click();
			Thread.sleep(1000);
			reportPass("Email and Password Verified sucessfully");
			wait(20, pcall);
			driver.findElement(pcall).click();
			wait(120, yes); // Verify by Phone call
			driver.findElement(yes).click();
			// Verify Title
			if (driver.getTitle().contains("Be.Cognizant"))
				// Pass
				System.out.println("Page title contains Be.Cognizant");
			else
				// Fail
				System.out.println("Page title doesn't contains Be.Cognizant");
			String name = driver.findElement(acc).getText();
			System.out.println("The name for the Acoount is: " + name);
			ScreenShot("Account");
			reportPass("Be.Cognizant Page is reached sucessfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	// To get current time in different locations
	public void CurrentTime() throws InterruptedException {
		logger = report.createTest("Get the different date and time of different location for my local time");
		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0,2000)");
			ScreenShot("WorldClock");
			file = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\Data.xlsx");
			workbook = new XSSFWorkbook();
			sh = workbook.createSheet("Dates"); // Create sheet in workbook
			List<WebElement> country = driver.findElements(c);
			List<WebElement> time = driver.findElements(t); // Stores in list
			List<WebElement> date = driver.findElements(d);
			System.out.println("The current time and Dates for diff location is: ");
			System.out.println("\n");
			for (int i = 1; i < 4; i++) {
				sh.createRow(i).createCell(1).setCellValue(country.get(i).getText());
				sh.getRow(i).createCell(4).setCellValue(time.get(i).getText());
				sh.getRow(i).createCell(6).setCellValue(date.get(i).getText());
				System.out.println(country.get(i).getText());
				System.out.println(time.get(i).getText());
				System.out.println(date.get(i).getText());
				System.out.println("\n");
			}
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM YYYY h:mm a"); // Prints date and time in this
																						// project
			LocalDateTime dt = LocalDateTime.now();
			System.out.println("Current Date Time: " + dtf.format(dt));
			LocalDateTime NJ = dt.minusHours(10).minusMinutes(30);
			System.out.println("TEANECK, NJ(ET) :" + dtf.format(NJ));
			LocalDateTime London = dt.minusHours(5).minusMinutes(30);
			System.out.println("LONDON, UK(BST) :" + dtf.format(London));
			System.out.println("\n");
			reportPass("Date and times are obtained of different locations for my local time.");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void slideBar() throws InterruptedException {

		WebElement slider = driver.findElement(sliderx);

		Actions move = new Actions(driver); // To See time in different locations after sliding action performed
		Action action = (Action) move.dragAndDropBy(slider, 90, 0).build(); // Adds 4.5 hours time for all locations
		action.perform();
		Thread.sleep(2000);
	}

	// Prints date and time after increasing timezones in sheet
	public void DiffTime() throws InterruptedException {
		logger = report.createTest("Get the different date and time of different location by changing local time");
		try {
			ScreenShot("WorldClock1");
			List<WebElement> country = driver.findElements(c);
			List<WebElement> time = driver.findElements(t);
			List<WebElement> date = driver.findElements(d);
			System.out.println("The time and Dates for diff location by changing local time is: ");
			System.out.println("\n");
			for (int i = 1; i < 4; i++) {
				sh.createRow(i + 5).createCell(1).setCellValue(country.get(i).getText());
				sh.getRow(i + 5).createCell(4).setCellValue(time.get(i).getText());
				sh.getRow(i + 5).createCell(6).setCellValue(date.get(i).getText());
				System.out.println(country.get(i).getText());
				System.out.println(time.get(i).getText());
				System.out.println(date.get(i).getText());
				System.out.println("\n");
			}
			reportPass("Date and times are obtained of different locations by changing local time.");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

}
