package uk.ac.ebi.biostudies.integration;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import uk.ac.ebi.biostudies.integration.utils.IntegrationTestProperties;

/**
 * Created by ehsan on 29/06/2017.
 */


@Suite.SuiteClasses({
        IndexTest.class,
        DetailTest.class,
        BrowseTest.class,
        SearchTest.class
})


@RunWith(Suite.class)
public class IntegrationTestSuite {

    public static WebDriver driver;

    @Autowired
    IntegrationTestProperties integrationTestProperties;


    @BeforeClass
    public static void setup() throws InterruptedException {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setCapability("phantomjs.binary.path", System.getenv("phantomjs.binary.path") );
        driver = new PhantomJSDriver(capabilities);
    }

    @AfterClass
    public static void destroy(){
        IntegrationTestSuite.driver.quit();
    }

}
