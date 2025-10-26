package com.example;

// --- TestNG Imports (REPLACES JUnit imports) ---
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert; // Use TestNG's assertion class

// --- Existing Selenium/Utility Imports ---
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager; 
import java.io.File;
import java.time.Duration;

public class SumWebTest {

private WebDriver driver; 
private WebDriverWait wait; 

@BeforeTest // REPLACED @BeforeEach with @BeforeTest
 public void setup() {
 // ULTIMATE ENVIRONMENT FIX: Use WebDriverManager
 WebDriverManager.chromedriver().setup();

ChromeOptions options = new ChromeOptions();
 options.addArguments("--headless");
 options.addArguments("--disable-gpu");
 options.addArguments("--no-sandbox");
 options.addArguments("--disable-dev-shm-usage");

driver = new ChromeDriver(options);
 wait = new WebDriverWait(driver, Duration.ofSeconds(10));
}

 @Test // SAME @Test annotation, but it's now TestNG's
public void testSumWebPage() {
 // Construct the file path to sum.html using the resource directory
 File htmlFile = new File("src/test/resources/sum.html");
 String url = "file:///" + htmlFile.getAbsolutePath().replace("\\", "/");
 driver.get(url);

 // Input the numbers (No wait needed here)
 driver.findElement(By.id("num1")).sendKeys("5");
driver.findElement(By.id("num2")).sendKeys("10");

 // Wait until the sumButton is present and clickable
        WebElement sumButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sumButton")));
 // Click the 'Sum' button
 sumButton.click();

// Wait for the result to be visible and get the text
WebElement resultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
 
// Assert the result is correct - Changed to TestNG's Assert.assertEquals
 Assert.assertEquals(resultElement.getText(), "15");
}

 @AfterTest // REPLACED @AfterEach with @AfterTest
public void tearDown() {
if (driver != null) {
 driver.quit();
 }
 }
}