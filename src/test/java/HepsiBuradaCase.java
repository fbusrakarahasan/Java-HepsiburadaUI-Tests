import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class HepsiBuradaCase {
    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeClass
    public void Setup(){
        driver = new ChromeDriver();
        driver.get("https://www.hepsiburada.com/");
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver , Duration.ofSeconds(15));
    }

    @Test
    public void SuccessfulAddToBasket() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='onetrust-button-group']/button[2]")));
        WebElement cookieAccepted = driver.findElement(By.xpath("//div[@id='onetrust-button-group']/button[2]"));
        cookieAccepted.click();

        WebElement searchBox = driver.findElement(By.xpath("//div[@role='combobox']"));
        searchBox.click();
        searchBox.sendKeys("ayakkabı");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'SearchBoxOld')]//div[text()='ARA']")));
        WebElement searchButton = driver.findElement(By.xpath("//div[contains(@class,'SearchBoxOld')]//div[text()='ARA']"));
        searchButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'searchResultSummaryBar')]/h1")));

        WebElement choosingShoes = driver.findElement(By.xpath("(//li[contains(@class,'productListContent')]/div/a)[5]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);" , choosingShoes);
        choosingShoes.click();

        for (String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product-name")));

        WebElement addToCart = driver.findElement(By.id("addToCart"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);" , addToCart);
        addToCart.click();

        wait.until(ExpectedConditions.textToBe(By.xpath("//span[contains(@class,'checkoutui-ProductOnBasketHeader')]"), "Ürün sepetinizde" ));
        WebElement productOnBasket = driver.findElement(By.xpath("//span[contains(@class,'checkoutui-ProductOnBasketHeader')]"));
        String productText = productOnBasket.getText();
        Assert.assertEquals(productText,"Ürün sepetinizde");
    }

    @AfterClass
    public void DriverClose(){
        driver.close();

        for (String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        driver.close(); }
}