package com.example.vvsdemo;

import com.example.vvsdemo.entities.Piece;
import com.example.vvsdemo.repositories.PiecesRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeleniumTests {
    @Autowired
    private PiecesRepository piecesRepository;

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
        piecesRepository.deleteAll();
        this.serverUrl = "http://localhost:" + localPort;
        this.webDriver = new ChromeDriver();
        this.webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    @AfterEach
    public void teardown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void testWhenFilterPiecesAndAllPieces() throws InterruptedException {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500.25),
                new Piece("Motor Diesel", "Bosch", 550.35),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        String affordablePriceValue = "500.25";
        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);

        By piecesList = By.id("piecesList");
        wait.until(presenceOfElementLocated(piecesList));
        List<WebElement> webElementPiecesList = webDriver.findElements(piecesList);
        int initialSize = webElementPiecesList.size();

        Thread.sleep(5000);

        By affordablePriceInput = By.id("affordablePriceInput");
        wait.until(presenceOfElementLocated(affordablePriceInput));
        webDriver.findElement(affordablePriceInput).sendKeys(affordablePriceValue);

        Thread.sleep(5000);

        By listFilterButton = By.id("listFilterButton");
        wait.until(elementToBeClickable(listFilterButton));
        webDriver.findElement(listFilterButton).click();

        Thread.sleep(5000);

        By listAllButton = By.id("listAllButton");
        wait.until(elementToBeClickable(listAllButton));
        webDriver.findElement(listAllButton).click();

        Thread.sleep(5000);
        webElementPiecesList = webDriver.findElements(piecesList);

        String pieceName1 = webDriver.findElement(By.id("pieceName" + 0)).getText();
        String producer1 = webDriver.findElement(By.id("producer" + 0)).getText();
        String price1 = webDriver.findElement(By.id("price" + 0)).getText();

        String pieceName2 = webDriver.findElement(By.id("pieceName" + 1)).getText();
        String producer2 = webDriver.findElement(By.id("producer" + 1)).getText();
        String price2 = webDriver.findElement(By.id("price" + 1)).getText();

        String pieceName3 = webDriver.findElement(By.id("pieceName" + 2)).getText();
        String producer3 = webDriver.findElement(By.id("producer" + 2)).getText();
        String price3 = webDriver.findElement(By.id("price" + 2)).getText();



        assertEquals("", webDriver.findElement(By.id("priceWarning")).getText());
        assertEquals(initialSize, webElementPiecesList.size());
        assertEquals(pieces.get(0).getPieceName(),pieceName1);
        assertEquals(pieces.get(0).getProducer(),producer1);
        assertEquals(String.valueOf(pieces.get(0).getPrice()),price1);
        assertEquals(pieces.get(1).getPieceName(),pieceName2);
        assertEquals(pieces.get(1).getProducer(),producer2);
        assertEquals(String.valueOf(pieces.get(1).getPrice()),price2);
        assertEquals(pieces.get(2).getPieceName(),pieceName3);
        assertEquals(pieces.get(2).getProducer(),producer3);
        assertEquals("450",price3);


    }

    @Test
    public void testWhenFilterPiecesHappyFlow() throws InterruptedException {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500.25),
                new Piece("Motor Diesel", "Bosch", 550.35),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        String affordablePriceValue = "500.25";
        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);

        By piecesList = By.id("piecesList");
        wait.until(presenceOfElementLocated(piecesList));
        List<WebElement> webElementPiecesList = webDriver.findElements(piecesList);
        int initialSize = webElementPiecesList.size();

        Thread.sleep(5000);

        By affordablePriceInput = By.id("affordablePriceInput");
        wait.until(presenceOfElementLocated(affordablePriceInput));
        webDriver.findElement(affordablePriceInput).sendKeys(affordablePriceValue);

        Thread.sleep(5000);

        By listFilterButton = By.id("listFilterButton");
        wait.until(elementToBeClickable(listFilterButton));
        webDriver.findElement(listFilterButton).click();

        Thread.sleep(5000);
        webElementPiecesList = webDriver.findElements(piecesList);

        String pieceName1 = webDriver.findElement(By.id("pieceName" + 0)).getText();
        String producer1 = webDriver.findElement(By.id("producer" + 0)).getText();
        String price1 = webDriver.findElement(By.id("price" + 0)).getText();

        String pieceName2 = webDriver.findElement(By.id("pieceName" + 1)).getText();
        String producer2 = webDriver.findElement(By.id("producer" + 1)).getText();
        String price2 = webDriver.findElement(By.id("price" + 1)).getText();


        assertEquals("", webDriver.findElement(By.id("priceWarning")).getText());
        assertEquals(initialSize - 1, webElementPiecesList.size());
        assertEquals(pieces.get(0).getPieceName(),pieceName1);
        assertEquals(pieces.get(0).getProducer(),producer1);
        assertEquals(String.valueOf(pieces.get(0).getPrice()),price1);
        assertEquals(pieces.get(2).getPieceName(),pieceName2);
        assertEquals(pieces.get(2).getProducer(),producer2);
        assertEquals("450",price2);

    }

    @Test
    public void testWhenFilterPiecesWithEmptyPrice() throws InterruptedException {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

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
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

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
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

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
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        String pieceNameValue = "test";
        String producerValue = "test";
        String priceValue = "50.5";

        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);

        By piecesList = By.id("piecesList");
        wait.until(presenceOfElementLocated(piecesList));
        List<WebElement> webElementPiecesList = webDriver.findElements(piecesList);
        int initialSize = webElementPiecesList.size();

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
        webElementPiecesList = webDriver.findElements(piecesList);

        Thread.sleep(5000);

        String addedPieceName = webDriver.findElement(By.id("pieceName" + (webElementPiecesList.size() - 1))).getText();
        String addedProducer = webDriver.findElement(By.id("producer" + (webElementPiecesList.size() - 1))).getText();
        String addedPrice = webDriver.findElement(By.id("price" + (webElementPiecesList.size() - 1))).getText();



        assertEquals(serverUrl + "/", webDriver.getCurrentUrl());
        assertEquals(initialSize+1,webElementPiecesList.size());
        assertEquals(pieceNameValue,addedPieceName);
        assertEquals(producerValue,addedProducer);
        assertEquals(priceValue,addedPrice);


    }

    @Test
    public void testWhenAddPieceWithEmptyInputs() throws InterruptedException {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

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
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

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
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

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

    @Test
    public void testWhenRemoveAPiece() throws InterruptedException {
        List<Piece> pieces = Arrays.asList(new Piece("Motor electric", "Ford", 500),
                new Piece("Motor Diesel", "Bosch", 550),
                new Piece("Motor Otto", "General Motors", 450));
        piecesRepository.saveAll(pieces);

        WebDriverWait wait = new WebDriverWait(webDriver,30,1000);
        webDriver.get(serverUrl);

        Thread.sleep(5000);

        By deleteList = By.id("piecesList");
        wait.until(presenceOfElementLocated(deleteList));
        List<WebElement> webElementDeleteList = webDriver.findElements(deleteList);
        int initialSize = webElementDeleteList.size();

        By deleteButton = By.id("1");
        wait.until(elementToBeClickable(deleteButton));
        WebElement deletePieceWebElement = webElementDeleteList.get(1);
        WebElement deleteButtonWebElement = webDriver.findElement(deleteButton);
        deleteButtonWebElement.click();

        Thread.sleep(5000);
        webElementDeleteList = webDriver.findElements(deleteList);

        assertFalse(webElementDeleteList.contains(deletePieceWebElement));
        assertEquals(initialSize-1, webElementDeleteList.size());

    }

}
