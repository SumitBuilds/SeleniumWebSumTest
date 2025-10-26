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
import io.github.bonigarcia.wdm.WebDriverManager; 
import java.io.File;
import java.time.Duration;

public class SumWebTest {

    // --- THESE TWO LINES ARE CRUCIAL AND MUST BE HERE! ---
    private WebDriver driver; 
    private WebDriverWait wait; 
    // -----------------------------------------------------------

    @BeforeEach
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

    @Test
    public void testSumWebPage() {
        // Construct the file path to sum.html using the resource directory
        File htmlFile = new File("src/test/resources/sum.html");
        String url = "file:///" + htmlFile.getAbsolutePath().replace("\\", "/");
        driver.get(url);

        // Input the numbers (No wait needed here)
        driver.findElement(By.id("num1")).sendKeys("5");
        driver.findElement(By.id("num2")).sendKeys("10");

        // --- FINAL FIX: Wait until the sumButton is present and clickable ---
        WebElement sumButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("sumButton")));

        // Click the 'Sum' button
        sumButton.click();

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