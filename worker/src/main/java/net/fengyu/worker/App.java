package net.fengyu.worker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Duration;
import java.util.List;

/**
 * Hello world!
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        //driver.get("https://www.selenium.dev/selenium/web/web-form.html");
        driver.get("https://www.baidu.com");
        String title = driver.getTitle();
        System.out.println(title);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        WebElement textBox = driver.findElement(By.id("kw"));
        WebElement submitButton = driver.findElement(By.id("su"));

        List<WebElement> links = driver.findElements(By.linkText(("新闻")));
        links.forEach(webElement -> {
            System.out.println("文本：" + webElement.getText());
        });

        textBox.sendKeys("道哥");

        Thread.sleep(2000);
        submitButton.click();
        Thread.sleep(10000);
        driver.quit();

        //WebElement submitButton = driver.findElement(By.cssSelector("button"));
    }
}
