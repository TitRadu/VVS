package com.example.vvsdemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumTests {

    @LocalServerPort
    private int localPort;

    private String serverUrl;
    private WebDriver webDriver;


    @BeforeAll
    public static void init(){
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    public void initServerUrl(){
        this.serverUrl = "http://localhost:" + localPort;
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    @Test
    public void testWhenFilterPiecesHappyFlow() throws InterruptedException {
        String affordablePriceValue = "500";
        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);

        Thread.sleep(5000);

        By affordablePriceInput = By.id("affordablePriceInput");
        wait.until(presenceOfElementLocated(affordablePriceInput));
        webDriver.findElement(affordablePriceInput).sendKeys(affordablePriceValue);

        Thread.sleep(5000);

        By listFilterButton = By.id("listFilterButton");
        wait.until(elementToBeClickable(listFilterButton));
        webDriver.findElement(listFilterButton).click();

        assertEquals("", webDriver.findElement(By.id("priceWarning")).getText());

    }

    @Test
    public void testWhenFilterPiecesWithEmptyPrice() throws InterruptedException {
        String warningMessage = "Please introduce a price!";

        String affordablePriceValue = "";
        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);

        Thread.sleep(5000);

        By affordablePriceInput = By.id("affordablePriceInput");
        wait.until(presenceOfElementLocated(affordablePriceInput));
        webDriver.findElement(affordablePriceInput).sendKeys(affordablePriceValue);

        Thread.sleep(5000);

        By listFilterButton = By.id("listFilterButton");
        wait.until(elementToBeClickable(listFilterButton));
        webDriver.findElement(listFilterButton).click();

        assertEquals(warningMessage, webDriver.findElement(By.id("priceWarning")).getText());

    }

    @Test
    public void testWhenFilterPiecesWithNegativePrice() throws InterruptedException {
        String warningMessage = "Please introduce a price higher than 0!";

        String affordablePriceValue = "-100";
        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);

        Thread.sleep(5000);

        By affordablePriceInput = By.id("affordablePriceInput");
        wait.until(presenceOfElementLocated(affordablePriceInput));
        webDriver.findElement(affordablePriceInput).sendKeys(affordablePriceValue);

        Thread.sleep(5000);

        By listFilterButton = By.id("listFilterButton");
        wait.until(elementToBeClickable(listFilterButton));
        webDriver.findElement(listFilterButton).click();

        assertEquals(warningMessage, webDriver.findElement(By.id("priceWarning")).getText());

    }

    @Test
    public void testWhenFilterPiecesWithStringPrice() throws InterruptedException {
        String warningMessage = "Please introduce a valid price!";

        String affordablePriceValue = "aaa";
        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);

        Thread.sleep(5000);

        By affordablePriceInput = By.id("affordablePriceInput");
        wait.until(presenceOfElementLocated(affordablePriceInput));
        webDriver.findElement(affordablePriceInput).sendKeys(affordablePriceValue);

        Thread.sleep(5000);

        By listFilterButton = By.id("listFilterButton");
        wait.until(elementToBeClickable(listFilterButton));
        webDriver.findElement(listFilterButton).click();

        assertEquals(warningMessage, webDriver.findElement(By.id("priceWarning")).getText());

    }

    @Test
    public void testWhenAddPieceWithHappyFlow() throws InterruptedException {
        String pieceNameValue = "test";
        String producerValue = "test";
        String priceValue = "50.50";

        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);

        Thread.sleep(5000);

        By newPieceHref = By.id("newPieceHref");
        wait.until(elementToBeClickable(newPieceHref ));
        webDriver.findElement(newPieceHref).click();

        Thread.sleep(5000);

        By pieceNameInput = By.id("pieceNameInput");
        wait.until(presenceOfElementLocated(pieceNameInput));
        webDriver.findElement(pieceNameInput).sendKeys(pieceNameValue);

        Thread.sleep(5000);

        By producerInput = By.id("producerInput");
        wait.until(presenceOfElementLocated(producerInput));
        webDriver.findElement(producerInput).sendKeys(producerValue);

        Thread.sleep(5000);

        By priceInput = By.id("priceInput");
        wait.until(presenceOfElementLocated(priceInput));
        webDriver.findElement(priceInput).sendKeys(priceValue);

        Thread.sleep(5000);

        By submitButton = By.id("submitButton");
        wait.until(elementToBeClickable(submitButton));
        webDriver.findElement(submitButton).click();

        assertEquals(serverUrl + "/", webDriver.getCurrentUrl());

    }

    @Test
    public void testWhenAddPieceWithEmptyInputs() throws InterruptedException {
        String warningMessagePieceName = "Please introduce a piece name!";
        String warningMessageProducer = "Please introduce a producer!";
        String warningMessagePrice = "Please introduce a price!";

        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);

        Thread.sleep(5000);

        By newPieceHref = By.id("newPieceHref");
        wait.until(elementToBeClickable(newPieceHref ));
        webDriver.findElement(newPieceHref).click();

        Thread.sleep(5000);

        By submitButton = By.id("submitButton");
        wait.until(elementToBeClickable(submitButton));
        webDriver.findElement(submitButton).click();

        assertEquals(warningMessagePieceName, webDriver.findElement(By.id("pieceNameWarning")).getText());
        assertEquals(warningMessageProducer, webDriver.findElement(By.id("producerWarning")).getText());
        assertEquals(warningMessagePrice, webDriver.findElement(By.id("priceWarning")).getText());

    }

    @Test
    public void testWhenAddPieceWithNegativePrice() throws InterruptedException {
        String warningMessagePrice = "Please introduce a price higher than 0!";
        String priceValue = "-60.5";

        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);

        Thread.sleep(5000);

        By newPieceHref = By.id("newPieceHref");
        wait.until(elementToBeClickable(newPieceHref ));
        webDriver.findElement(newPieceHref).click();

        Thread.sleep(5000);

        By priceInput = By.id("priceInput");
        wait.until(presenceOfElementLocated(priceInput));
        webDriver.findElement(priceInput).sendKeys(priceValue);

        Thread.sleep(5000);

        By submitButton = By.id("submitButton");
        wait.until(elementToBeClickable(submitButton));
        webDriver.findElement(submitButton).click();

        assertEquals(warningMessagePrice, webDriver.findElement(By.id("priceWarning")).getText());

    }

    @Test
    public void testWhenAddPieceWithStringPrice() throws InterruptedException {
        String warningMessagePrice = "Please introduce a valid price!";
        String priceValue = "aaa";

        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);

        Thread.sleep(5000);

        By newPieceHref = By.id("newPieceHref");
        wait.until(elementToBeClickable(newPieceHref ));
        webDriver.findElement(newPieceHref).click();

        Thread.sleep(5000);

        By priceInput = By.id("priceInput");
        wait.until(presenceOfElementLocated(priceInput));
        webDriver.findElement(priceInput).sendKeys(priceValue);

        Thread.sleep(5000);

        By submitButton = By.id("submitButton");
        wait.until(elementToBeClickable(submitButton));
        webDriver.findElement(submitButton).click();

        assertEquals(warningMessagePrice, webDriver.findElement(By.id("priceWarning")).getText());

    }

}
