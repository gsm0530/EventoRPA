package com.evento.eventorpa;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminSelenium {

    public static WebElement webElement;
    private WebDriver mdriver;
    private WebDriverWait mwait;
    private static String mstrurl = "https://zzal.blog/partners/login";

    public AdminSelenium() throws MalformedURLException {

        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "C:\\chromedriver.exe";

        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        Map<String, Object> prefs = new HashMap<String, Object>();

        prefs.put("download.default_directory", WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--window-size=1920x1080");

        options.addArguments("disable-gpu");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.20 Safari/537.36");
        //WebDriver mdriver;

        try {
            mdriver = new ChromeDriver(options);
        } catch (Exception e) {
            e.getMessage();
            System.out.println(e.getMessage());
            return;
        }

        mwait = new WebDriverWait(mdriver, 60);

        mdriver.get(mstrurl);
        mdriver.manage().window().maximize();
    }

    public void Start() {

        try {

            JavascriptExecutor js = (JavascriptExecutor) mdriver;
            Thread.sleep(2000);
            mwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".login-form-inner")));

            mdriver.findElement(By.id("form_id")).sendKeys("ghb8302@naver.com");
            mdriver.findElement(By.id("form_pw")).sendKeys("gg05300719!!");
            mdriver.findElement(By.id("zzal_login_button")).click();

            Thread.sleep(2000);

            for (int i = 0 ; i < 5000; i++) {

                mdriver.get(mstrurl);

                mwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".button.save")));

                List<WebElement> mlist =  mdriver.findElements(By.cssSelector(".button.save"));

                for(WebElement webElement : mlist) {
                    if(webElement.isDisplayed()) {

                        js.executeScript("arguments[0].scrollIntoView();",webElement );

                        webElement.click();
                        Thread.sleep(3000);
                    }
                }
                System.out.println("Admin -" + i + "Times. Successc!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

}
