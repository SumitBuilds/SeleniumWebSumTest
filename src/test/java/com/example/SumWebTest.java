package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class SumWebTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        // Set the path to the chromedriver executable (if needed, but usually automatically found)
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run Chrome in headless mode (no visible browser window)
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox"); // Important for Jenkins/Linux environments
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testSumWebPage() {
        // Construct the file path to sum.html using the resource directory
        File htmlFile = new File("src/test/resources/sum.html");
        String url = "file:///" + htmlFile.getAbsolutePath().replace("\\", "/");
        driver.get(url);

        // Input the numbers
        driver.findElement(By.id("num1")).sendKeys("5");
        driver.findElement(By.id("num2")).sendKeys("10");

        // Click the 'Sum' button
        driver.findElement(By.id("sumButton")).click();

        // Wait for the result to be visible and get the text
        WebElement resultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
        
        // Assert the result is correct
        Assertions.assertEquals("15", resultElement.getText());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}