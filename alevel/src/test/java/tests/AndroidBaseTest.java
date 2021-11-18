package tests;

import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class AndroidBaseTest {

    public static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";//System.getProperty("runType");

    //Remote URL
    /*public static final String USERNAME = "oauth-kotikimurkotiki888-85a5a";
    public static final String ACCESS_KEY = "e384b3d9-e41f-4f05-b41d-3529fe756f53";
    public static final String APPIUM_URL = "https://"+USERNAME+":" + ACCESS_KEY + "@ondemand.eu-central-1.saucelabs.com:443"+"/wd/hub";
*/
    public static final String ANDROID_PLATFORM_NAME = "Android";
    public static final String APP_PATH = "/Users/AKliui/Downloads/alevel/src/main/resources/instagram.apk";
    protected static AndroidDriver driver;

    Logger log = Logger.getLogger(AndroidBaseTest.class.getName());

    @BeforeClass
    public void setUp() {
        driver = initDriver();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.closeApp();
            driver.quit();
        }
    }

    @BeforeMethod
    public void setTestApp() {
        driver.resetApp();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(ITestResult result){
        if (!result.isSuccess()){
            makeScreenshot();
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    private AndroidDriver initDriver() {
        try {
            driver = new AndroidDriver(new URL(APPIUM_URL), getLocalCapabilities() /*getSauceLabsCapabilities()*/);
            log.info("Appium url: " + APPIUM_URL);
        } catch (Exception ex) {
            throw new RuntimeException("Appium driver could not be initialized." + ex.getMessage());
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        return driver;
    }

    private DesiredCapabilities getLocalCapabilities() {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("device", "Android");
        capabilities.setCapability("deviceName", "Nexus_6");
        capabilities.setCapability("platformName", ANDROID_PLATFORM_NAME);
        capabilities.setCapability("app", APP_PATH);
        capabilities.setCapability("autoLaunch", "false");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("appPackage", "com.instagram.android");
        capabilities.setCapability("appActivity", "com.instagram.mainactivity.MainActivity");
        // long timeout is needed for debugging purposes
        capabilities.setCapability("newCommandTimeout", 600);
        capabilities.setCapability("unicodeKeyboard", "true");
        capabilities.setCapability("resetKeyboard", "true");
        return capabilities;
    }

    private DesiredCapabilities getSauceLabsCapabilities() {

        DesiredCapabilities capabilities = DesiredCapabilities.android();
        capabilities.setCapability("appiumVersion", "1.9.1");
        capabilities.setCapability("deviceName", "Galaxy S6 GoogleAPI Emulator");
        capabilities.setCapability("platformVersion", "8.1");
        capabilities.setCapability("platformName", ANDROID_PLATFORM_NAME);
        capabilities.setCapability("app", "storage:c824dd91-f92a-4550-b1ae-91d0ac9fb5aa");
        capabilities.setCapability("browserName", "");
        capabilities.setCapability("autoLaunch", "false");
        capabilities.setCapability("noReset", "true");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appPackage", "com.instagram.android");
        capabilities.setCapability("appActivity", "com.instagram.mainactivity.MainActivity");
        return capabilities;
    }
}
