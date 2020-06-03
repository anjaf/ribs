package uk.ac.ebi.biostudies.integration.boot;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.biostudies.integration.IntegrationTestSuite;

import java.io.File;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FrontEndTest {
    private static WebDriver webDriver;

    @LocalServerPort
    int randomPort;

    @BeforeClass
    public static void setup() throws InterruptedException {
        String path = "F:\\java\\integration-test\\src\\test\\resources\\geckodriver.exe";
        System.setProperty("webdriver.gecko.driver", new File(path).getAbsolutePath());
        FirefoxOptions options = new FirefoxOptions();
//        options.addArguments("--headless");
        webDriver = new FirefoxDriver(options);
    }

    @Test
    public void homeResponse() {
        webDriver.get("http://localhost:"+randomPort+"/biostudies");
        WebDriverWait wait = new WebDriverWait(webDriver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#fileCount")));
        assertEquals("BioStudies < EMBL-EBI", webDriver.getTitle());

    }

    @AfterClass
    public static void destroy(){
        if (webDriver!=null) webDriver.quit();
    }



}
