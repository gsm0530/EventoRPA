package com.evento.eventorpa;

import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventoSelenium {

    public static WebElement webElement;
    private WebDriver mdriver;
    private WebDriverWait mwait;
    private static String mstrurl = "https://ghb8302.ulog.kr";

    public EventoSelenium() throws MalformedURLException {

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

            int startNum = 415700;
            JavascriptExecutor js = (JavascriptExecutor) mdriver;
            Thread.sleep(2000);
            mwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".menus")));

            for (int i = 0 ; i < 5000; i++) {

                mdriver.get(mstrurl + "/" + String.valueOf(startNum));
                startNum++;

                Thread.sleep(500);

                String strcheck = mdriver.findElement(By.cssSelector(".desc")).getText();

                if( strcheck.contains("삭제된")) {
                    System.out.println(i + "Times. Error...");
                    continue;
                }

                Thread.sleep(3000);
                js.executeScript("window.scrollBy(0,99999)", "");
                System.out.println(i + "Times. Successc!!!   LastNum : " + startNum);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

}
