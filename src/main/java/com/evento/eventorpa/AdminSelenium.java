package com.evento.eventorpa;

import com.evento.eventorpa.entity.User;
import com.evento.eventorpa.repository.UserRepository;
import com.evento.eventorpa.service.UserService;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AdminSelenium {

    private static UserService inituserService;

    @Autowired
    UserService userService;

    public static WebElement webElement;
    private WebDriver mdriver;
    private WebDriverWait mwait;
    private static String mstrurl = "https://zzal.blog/partners/login";

    public AdminSelenium()  {
        inituserService = this.userService;
    }

    public void Init() {
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

    public void ADD() {

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
                        Thread.sleep(2000);
                    }
                }
                System.out.println("Admin -" + i + "Times. Successc!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    public void Adminset(String userid) {

        try {

            Thread.sleep(2000);
            mwait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".login-form-inner")));

            mdriver.findElement(By.id("form_id")).sendKeys(userid);
            mdriver.findElement(By.id("form_pw")).sendKeys("gg05300719!!");
            mdriver.findElement(By.id("zzal_login_button")).click();

            Thread.sleep(2000);

            mdriver.get("https://zzal.blog/partners/mystats/analytics");

            Thread.sleep(2000);

            Select select = new Select(mdriver.findElement(By.cssSelector(".datepicker-ranged-preset-selection")));
            select.selectByIndex(3);

            Thread.sleep(2000);

            select = new Select(mdriver.findElement(By.cssSelector(".datepicker-ranged-sort-selection")));
            select.selectByIndex(0);

            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Map GET(String userid,boolean first) {

        Map map = new HashMap();

        try {

            if (!first) {
                mdriver.findElement(By.cssSelector(".button.right")).click();
                Thread.sleep(1000);
            }

            List<WebElement> mlist =  mdriver.findElements(By.cssSelector("#analytics-table-body tr td:nth-child(1)"));

            map.put("list",mlist);
            map.put("bnext",mdriver.findElement(By.cssSelector(".button.right")).isDisplayed());


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        return map;
    }

}
