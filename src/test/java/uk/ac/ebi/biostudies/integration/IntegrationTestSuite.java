package uk.ac.ebi.biostudies.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.ebi.biostudies.integration.utils.IntegrationTestProperties;
import java.io.File;


/**
 * Created by ehsan on 29/06/2017.
 */


@Suite.SuiteClasses({
        IndexTest.class,
        DetailTest.class,
        AuthTest.class,
        SearchTest.class
})

@RunWith(Suite.class)
public class IntegrationTestSuite {

    public static WebDriver webDriver;




    @Autowired
    IntegrationTestProperties integrationTestProperties;


    @BeforeClass
    public static void setup() throws Throwable {
        String path = "geckodriver";
        System.setProperty("webdriver.gecko.driver", new File(path).getAbsolutePath());
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        webDriver = new FirefoxDriver(options);
    }

    @AfterClass
    public static void destroy(){
        webDriver.close();
    }

}
