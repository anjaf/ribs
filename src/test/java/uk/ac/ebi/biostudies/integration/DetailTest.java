package uk.ac.ebi.biostudies.integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.biostudies.integration.utils.IntegProps;
import uk.ac.ebi.biostudies.integration.utils.IntegrationConfig;


import java.util.List;
import java.util.Scanner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IntegrationConfig.class})
public class DetailTest {

    protected static WebDriver driver;
    @Autowired
    IntegProps integProps;

    @BeforeClass
    public static void setUpBeforeClass() {
        driver = IntegrationTestSuite.driver;
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testFileCount() {
        String baseUrl = integProps.getBaseUrl();
        // store file and link count on the search page
        driver.get(baseUrl + "studies/?sortBy=files&sortOrder=descending");
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".release-files")));
        String fileCountText = driver.findElement(By.cssSelector(".release-files")).getAttribute("innerText").trim();
        int fileCount = Integer.parseInt(fileCountText.substring(0, fileCountText.indexOf(" ")));
        String location = driver.findElements(By.cssSelector(".title a")).get(0).getAttribute("href");
        driver.navigate().to(location);
//        String source = driver.getPageSource();
        WebDriverWait wait2 = new WebDriverWait(driver, 50);
        wait2.until(ExpectedConditions.presenceOfElementLocated(By.id("file-list_info")));
//        wait2.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("file-list_info"))));
        String filesCountOnDetails = driver.findElement(By.id("file-list_info")).getAttribute("innerText").trim();
        assertEquals( "Showing 1 to "+(fileCount<5?fileCount:5)+" of "+(fileCount)+" entries", filesCountOnDetails.replaceAll(",",""));
    }


//    @Test
    public void testLinkCount() {
        String baseUrl = integProps.getBaseUrl();
        // store file and link count on the search page
        driver.get(baseUrl + "studies/?sortBy=links&sortOrder=descending");
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".release-links")));
        String linkCountText = driver.findElement(By.cssSelector(".release-links")).getAttribute("innerText").trim();
        int expectedLinkCount = Integer.parseInt(linkCountText.substring(0, linkCountText.indexOf(" ")));
        String location = driver.findElements(By.cssSelector(".title a")).get(0).getAttribute("href");
        driver.navigate().to(location);
        wait = new WebDriverWait(driver, 50);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".dataTables_info")));
        List<WebElement> elements = driver.findElements(By.cssSelector(".dataTables_info"));
        int actualLinkCount =0;

        for(WebElement we: elements) {
            if(!we.getAttribute("id").contains("file")) {
                Scanner scanner = new Scanner(we.getText());
                int links = 0;
                while (scanner.hasNext()) {
                    if(scanner.hasNextInt()) {
                        links = scanner.nextInt();
                    } else {
                        scanner.next();
                    }
                }
                actualLinkCount+= links;
            }
        }
        assertEquals(expectedLinkCount, actualLinkCount);
    }

    @Test
    public void testTitle() {
        String baseUrl = integProps.getBaseUrl();
        driver.get(baseUrl + "/studies?query=cancer");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".title a")));
        WebElement secondLink = driver.findElements(By.cssSelector(".title a")).get(1);
        String expectedTitle = secondLink.getText();
        driver.navigate().to(secondLink.getAttribute("href"));
        wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#orcid-title")));
        assertEquals(expectedTitle, driver.findElement(By.cssSelector("#orcid-title")).getAttribute("innerText").trim());
    }

    /* Not working with HtmlUnitDriver
    @Test
    public void testFileFilter() {
        driver.get(baseUrl + "/studies/");
        new Select(driver.findElement(By.id("studies-browse-sorter"))).selectByVisibleText("Files");
        driver.findElement(By.cssSelector(".browse-study-title a")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#file-list_filter input")));
        driver.findElement(By.cssSelector("#file-list_filter input")).click();
        driver.findElement(By.cssSelector("#file-list_filter input")).sendKeys("pdf");
        assertEquals("Showing 1 to 4 of 4 entries (filtered from 35 total entries)",
                driver.findElement(By.cssSelector("#file-list_info")).getText());
    }
    */

//    @Test  There is a bug in phantomJS that suppose some visible areas as invisible so I disabled this test
    public void testDownloadSelection() {
        String baseUrl = integProps.getBaseUrl();
        driver.get(baseUrl + "studies/?sortBy=files&sortOrder=descending");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".release-files")));
        String fileCountText = driver.findElements(By.cssSelector(".release-files")).get(1).getAttribute("innerText").trim();
        int fileCount = Integer.parseInt(fileCountText.substring(0, fileCountText.indexOf(" ")));
        List<WebElement> studies = driver.findElements(By.cssSelector(".title a"));
        String location = studies.get(1).getAttribute("href");
        driver.navigate().to(location);
        wait = new WebDriverWait(driver, 25);
        driver.manage().window().setSize(new Dimension(1280, 1024));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("select-all-files")));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("select-all-files")));
        List<WebElement> elements = driver.findElements(By.cssSelector(".file-check-box input"));
        for(WebElement elem:elements)
            elem.click();
//        driver.findElement(By.id("select-all-files")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("download-selected-files")));
        assertEquals("Download all "+fileCount, driver.findElement(By.id("download-selected-files")).getAttribute("innerText").trim());
    }

}
