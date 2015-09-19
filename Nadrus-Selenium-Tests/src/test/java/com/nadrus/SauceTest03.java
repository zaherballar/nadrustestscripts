package com.nadrus;
import common.Config;

import com.saucelabs.common.SauceOnDemandAuthentication;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import common.Config;

import java.net.URL;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

import com.saucelabs.common.SauceOnDemandSessionIdProvider;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * Demonstrates how to write a JUnit test that runs tests against Sauce Labs using multiple browsers in parallel.
 * <p/>
 * The test also includes the {@link SauceOnDemandTestWatcher} which will invoke the Sauce REST API to mark
 * the test as passed or failed.
 *
 * @author Neil Manvar
 */
@RunWith(ConcurrentParameterized.class)
public class SauceTest03 implements SauceOnDemandSessionIdProvider {
   /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(Config.username, Config.accesskey);

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    @Rule public TestName name = new TestName() {
        public String getMethodName() {
        		return String.format("%s : (%s %s %s %s)", super.getMethodName(), os, browser, version,screenResolution);
        };
    };
    /**
     * Screen resolution for vm
     */
    private String screenResolution;
    /**
     * Represents the browser to be used as part of the test run.
     */
    private String browser;
    /**
     * Represents the operating system to be used as part of the test run.
     */
    private String os;
    /**
     * Represents the version of the browser to be used as part of the test run.
     */
    private String version;
    /**
     * Represents the deviceName of mobile device
     */
    private String deviceName;
    /**
     * Represents the device-orientation of mobile device
     */
    private String deviceOrientation;
    /**
     * Instance variable which contains the Sauce Job Id.
     */
    private String sessionId;

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private WebDriver driver;

    /**
     * Constructs a new instance of the test.  The constructor requires three string parameters, which represent the operating
     * system, version and browser to be used when launching a Sauce VM.  The order of the parameters should be the same
     * as that of the elements within the {@link #browsersStrings()} method.
     * @param os
     * @param version
     * @param browser
     * @param screenResolution 
     * @param deviceName
     * @param deviceOrientation
     */

    public SauceTest03(String os, String version, String browser, String deviceName, String deviceOrientation, String screenResolution) {
        super();
        this.os = os;
        this.version = version;
        this.browser = browser;
        this.deviceName = deviceName;
        this.deviceOrientation = deviceOrientation;
        this.screenResolution = screenResolution;
    }

    /**
     * @return a LinkedList containing String arrays representing the browser combinations the test should be run against. The values
     * in the String array are used as part of the invocation of the test constructor
     */
    @ConcurrentParameterized.Parameters
    public static LinkedList browsersStrings() {
        LinkedList browsers = new LinkedList();
        for(int i=0;i<Config.browsers.length;i++){
        	 browsers.add(Config.browsers[i]);
        }
        return browsers;
    }


    /**
     * Constructs a new {@link RemoteWebDriver} instance which is configured to use the capabilities defined by the {@link #browser},
     * {@link #version} and {@link #os} instance variables, and which is configured to run against ondemand.saucelabs.com, using
     * the username and access key populated by the {@link #authentication} instance.
     *
     * @throws Exception if an error occurs during the creation of the {@link RemoteWebDriver} instance.
     */
    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (browser != null) capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
        if (version != null) capabilities.setCapability(CapabilityType.VERSION, version);
        if (deviceName != null) capabilities.setCapability("deviceName", deviceName);
        if (deviceOrientation != null) capabilities.setCapability("device-orientation", deviceOrientation);
        if (screenResolution != null) capabilities.setCapability("screenResolution", screenResolution);

        capabilities.setCapability(CapabilityType.PLATFORM, os);

        String methodName = name.getMethodName();
        capabilities.setCapability("name", methodName);

        this.driver = new RemoteWebDriver(
                new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey() +
                        "@ondemand.saucelabs.com:80/wd/hub"),
                capabilities);
        this.driver.manage().timeouts().implicitlyWait(Config.TIMEOUT, TimeUnit.SECONDS);
        
        this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

        String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s", this.sessionId, methodName);
        System.out.println(message);
    }

    /**
     * Runs a simple test verifying the UI and title of the belk.com home page.
     * @throws Exception
     */
    @Test
    public void runTestID03_1() throws Exception {
        driver.get("http://dev.nadrus.com/theme/1");
        driver.findElement(By.xpath("//div[5]/div[1]/div/a")).click();
        driver.findElement(By.id("CourseTitles")).click();
        driver.findElement(By.id("CourseTitles")).clear();
        driver.findElement(By.id("CourseTitles")).sendKeys("مايكروسو�?ت");
        driver.findElement(By.xpath("//form[@id='CourseSearchForm']/div/div/button")).click();
        if (!driver.findElement(By.tagName("html")).getText().contains("Windows")) {
            System.out.println("verifyTextPresent failed");
        }
        if (!driver.findElement(By.tagName("html")).getText().contains("Office")) {
            System.out.println("verifyTextPresent failed");
        }
        if (!driver.findElement(By.tagName("html")).getText().contains("Skype")) {
            System.out.println("verifyTextPresent failed");
        }
    }
    
    @Test
    public void runTestID03_2() throws Exception {
        driver.get("http://dev.nadrus.com/theme/1");
        driver.findElement(By.xpath("//div[5]/div[1]/div/a")).click();
        driver.findElement(By.id("CourseTitles")).click();
        driver.findElement(By.id("CourseTitles")).clear();
        driver.findElement(By.id("CourseTitles")).sendKeys("مايكروسو�?ت");
        driver.findElement(By.xpath("//form[@id='CourseSearchForm']/div/div/button")).click();
        if (!driver.findElement(By.tagName("html")).getText().contains("اندرويد")) {
            System.out.println("verifyTextPresent failed");
        }      
    }


    /**
     * Closes the {@link WebDriver} session.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    /**
     *
     * @return the value of the Sauce Job id.
     */
    @Override
    public String getSessionId() {
        return sessionId;
    }
}
