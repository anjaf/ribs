package uk.ac.ebi.biostudies.integration;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.biostudies.integration.utils.IntegProps;
import uk.ac.ebi.biostudies.integration.utils.IntegrationConfig;
import uk.ac.ebi.biostudies.integration.utils.TestUtils;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IntegrationConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IndexTest {

    @Autowired
    IntegProps integProps;

    protected static WebDriver driver;
    protected static String baseUrl;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception{
        driver = IntegrationTestSuite.driver;
//        driver = new HtmlUnitDriver();
//        ((HtmlUnitDriver)driver).setJavascriptEnabled(true);
//        baseUrl = new BSInterfaceTestApplication().getPreferences().getString("bs.test.uk.ac.ebi.biostudies.integration.server.url");
    }


    @Test
    public void BTestIndexing() throws Exception {
        TestUtils.login(integProps.getBaseUrl(), integProps.getUsername(), integProps.getPassword());
        IntegrationTestSuite.driver.navigate().to(integProps.getBaseUrl()+"api/v1/index/reload/testJson.json");
        Thread.sleep(30000);
        assertTrue(IntegrationTestSuite.driver.getPageSource().contains("Indexing started"));
        TestUtils.validIndexIsloaded(integProps.getBaseUrl());
        TestUtils.logout(integProps.getBaseUrl());
    }
    @Test
    public void DUpdateDocument() throws Exception{
        TestUtils.login(integProps.getBaseUrl(), integProps.getUsername(), integProps.getPassword());
        IntegrationTestSuite.driver.navigate().to(integProps.getBaseUrl()+"api/v1/index/reload/smallJson.json");
        Thread.sleep(10000);
        IntegrationTestSuite.driver.navigate().to(integProps.getBaseUrl()+"studies?query=S-EPMC3343805");
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".accession")));
        assertTrue(IntegrationTestSuite.driver.findElement(By.cssSelector(".accession")).getAttribute("innerText").contains("S-EPMC3343805"));
        TestUtils.logout(integProps.getBaseUrl());
    }

    @Test
    public void AClearIndex(){
        TestUtils.login(integProps.getBaseUrl(), integProps.getUsername(), integProps.getPassword());
        IntegrationTestSuite.driver.navigate().to(integProps.getBaseUrl()+"api/v1/index/clear");
        assertTrue(IntegrationTestSuite.driver.getPageSource().contains("Index empty"));
        TestUtils.logout(integProps.getBaseUrl());
    }



    @Test
    public void ETestDeleteDocument() throws Exception{
        TestUtils.login(integProps.getBaseUrl(), integProps.getUsername(), integProps.getPassword());
        IntegrationTestSuite.driver.navigate().to(integProps.getBaseUrl()+"api/v1/index/delete/S-EPMC3343805");
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 20);
        IntegrationTestSuite.driver.navigate().to(integProps.getBaseUrl()+"studies?query=S-EPMC3343805");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".alert")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#renderedContent")));
        assertTrue(IntegrationTestSuite.driver.findElement(By.cssSelector("#renderedContent")).getAttribute("innerText").contains("no results"));
        TestUtils.logout(integProps.getBaseUrl());
    }

}
