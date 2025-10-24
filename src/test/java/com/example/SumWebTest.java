package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class SumWebTest {
    private WebDriver driver;

    @Before
    public void setUp() {
        // Options for running in headless mode, which is typical for Jenkins [cite: 83-84]
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new"); 
        options.addArguments("--allow-file-access-from-files"); // Access local HTML [cite: 85]
        driver = new ChromeDriver(options);
    }

    @Test
    public void testSum() throws InterruptedException {
        // NOTE: The file path must match the location where Jenkins will checkout the project. [cite: 88]
        String url = "file:///C:/ProgramData/Jenkins/.jenkins/workspace/SeleniumWebSumTest/src/test/resources/sum.html"; 
        driver.get(url); [cite: 89]

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("num1"))); [cite: 90]

        // Locate elements
        WebElement num1 = driver.findElement(By.id("num1")); [cite: 91]
        WebElement num2 = driver.findElement(By.id("num2")); [cite: 92]
        WebElement calcBtn = driver.findElement(By.id("calcBtn")); [cite: 92]
        WebElement result = driver.findElement(By.id("result")); [cite: 92]

        // Perform actions: input numbers and click
        num1.sendKeys("10"); [cite: 93]
        num2.sendKeys("20"); [cite: 96]
        calcBtn.click(); [cite: 97]

        Thread.sleep(1000); // wait for JS to update [cite: 98]

        // Verify result
        String output = result.getText().trim(); [cite: 99]
        System.out.println("Output: " + output); [cite: 100]
        assertEquals("Sum=30", output); [cite: 101]
    }

    @After
    public void tearDown() {
        // Clean up: close the browser [cite: 103]
        if (driver != null) driver.quit(); [cite: 104]
    }
}