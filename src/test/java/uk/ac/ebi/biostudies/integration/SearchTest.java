package uk.ac.ebi.biostudies.integration;

import org.apache.commons.lang.time.DateUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.biostudies.integration.utils.IntegProps;
import uk.ac.ebi.biostudies.integration.utils.IntegrationConfig;

import java.text.SimpleDateFormat;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IntegrationConfig.class})
public class SearchTest {

    @Autowired
    IntegProps integProps;
    protected static WebDriver driver;

    @BeforeClass
    public static void setUpBeforeClass() {
        driver = IntegrationTestSuite.driver;
//        driver = new HtmlUnitDriver( BrowserVersion.FIREFOX_38 , true);
//        baseUrl = new BSInterfaceTestApplication().getPreferences().getString("bs.test.uk.ac.ebi.biostudies.integration.server.url");
        //driver.get(baseUrl + "/admin/reload-xml");
    }


    @Test
    public void testPageStats() {
        driver.get(integProps.getBaseUrl() + "/studies");
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".result-count")));
        String pages = driver.findElement(By.cssSelector(".result-count")).getAttribute("innerText");
        assertTrue(pages.contains("Showing"));
    }

    // Does not work with <input type="search"...
    //@Test
    public void testAutoComplete() throws Exception{
        driver.get(integProps.getBaseUrl() + "/studies/");
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 120);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".result-count")));
        WebElement searchBox = driver.findElement (By.cssSelector("#query"));
        searchBox.click();
        searchBox.sendKeys("dna");
        wait.until(ExpectedConditions.visibilityOfElementLocated (By.cssSelector(".ac_inner")));
        List<WebElement> we = driver.findElements(By.cssSelector(".ac_inner li"));
        if(!we.get(3).getText().startsWith("DNA")) {
            Thread.sleep(60000);
            searchBox.click();
            searchBox.sendKeys("dna");
            we = driver.findElements(By.cssSelector(".ac_inner li"));
        }
        assertTrue(we.get(3).getText().startsWith("DNA"));
    }

//    @Test
//    public void testAccessionAscendingSort() throws Exception{
//        driver.get(integProps.getBaseUrl() + "/studies?query=cancer");
//        new Select(driver.findElement(By.id("sort-by"))).selectByVisibleText("Relevance");
//        testSort(".browse-study-accession");
//    }

//    @Test
//    public void testAccessionDescendingSort() throws Exception{
//        driver.get(integProps.getBaseUrl() + "/studies/search.html?query=cancer");
//        new Select(driver.findElement(By.id("studies-browse-sorter"))).selectByVisibleText("Accession");
//        driver.findElement(By.cssSelector(".studies-browse-sort-order-left")).click();
//        testSort(".browse-study-accession", true);
//    }

    private void testSort(String cssSelector) {
        testSort(cssSelector, false);
    }

    private void testSort(String cssSelector, boolean isDescending) {
        List<WebElement> list = driver.findElements(By.cssSelector(cssSelector));
        String [] values = new String[list.size()];
        for(int i=0; i < values.length; i++) {
            values[i] = list.get(i).getText().toLowerCase().trim();
        }
        String [] unsortedValues = values.clone();
        if (isDescending) {
            Arrays.sort(values, Collections.reverseOrder());
        } else {
            Arrays.sort(values);
        }
        assertArrayEquals(values, unsortedValues);
    }

//    @Test
//    public void testTitleAscendingSort() throws Exception{
//        driver.get(integProps.getBaseUrl() + "/studies/search.html?query=cancer");
//        new Select(driver.findElement(By.id("studies-browse-sorter"))).selectByVisibleText("Title");
//        testSort(".browse-study-title a");
//    }

//    @Test
//    public void testTitleDescendingSort() throws Exception{
//        driver.get(integProps.getBaseUrl() + "/studies/search.html?query=cancer");
//        new Select(driver.findElement(By.id("studies-browse-sorter"))).selectByVisibleText("Title");
//        driver.findElement(By.cssSelector(".studies-browse-sort-order-left")).click();
//        testSort(".browse-study-title a", true);
//    }

//    @Test
//    public void testAuthorsAscendingSort() throws Exception{
//        driver.get(integProps.getBaseUrl() + "/studies/search.html?query=cancer");
//        new Select(driver.findElement(By.id("studies-browse-sorter"))).selectByVisibleText("Authors");
//        testSort(".browse-study-title + div.search-authors");
//    }

//    @Test
//    public void testAuthorsDescendingSort() throws Exception{
//        driver.get(integProps.getBaseUrl() + "/studies/search.html?query=cancer");
//        new Select(driver.findElement(By.id("studies-browse-sorter"))).selectByVisibleText("Authors");
//        driver.findElement(By.cssSelector(".studies-browse-sort-order-left")).click();
//        testSort(".browse-study-title + div.search-authors", true);
//    }


    @Test
    public void testFilesDescendingSort() throws Exception{
        driver.get(integProps.getBaseUrl() + "/studies?query=cancer");
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sort-by")));
        new Select(driver.findElement(By.cssSelector("#sort-by"))).selectByVisibleText("Files");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".release-files")));
        List<WebElement> list = driver.findElements(By.cssSelector(".release-files"));
        Integer [] values = new Integer[list.size()];
        for(int i=0; i < values.length; i++) {
            values[i] = Integer.parseInt(list.get(i).getAttribute("innerText").trim().split(" ")[0]);
        }
        Integer [] unsortedValues = values.clone();
        Arrays.sort(values, Collections.reverseOrder());
        assertArrayEquals(values, unsortedValues);
    }

    @Test(expected = TimeoutException.class)
    public void testFilesAscendingSort() throws Exception{
        driver.get(integProps.getBaseUrl() + "/studies?query=cancer");
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sort-by")));
        new Select(driver.findElement(By.cssSelector("#sort-by"))).selectByVisibleText("Files");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sort-asc")));
        driver.findElement(By.cssSelector("#sort-asc")).click();
        WebDriverWait wait2 = new WebDriverWait(IntegrationTestSuite.driver, 2);
        wait2.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".release-files")));
        List<WebElement> list = driver.findElements(By.cssSelector(".release-files"));
        Integer [] values = new Integer[list.size()];
        for(int i=0; i < values.length; i++) {
            values[i] = Integer.parseInt(list.get(i).getText().trim().split(" ")[0]);
        }
        Integer [] unsortedValues = values.clone();
        assertArrayEquals(values, unsortedValues);
        throw new TimeoutException("Test finished successfully");
    }

    @Test
    public void testLinksDescendingSort() throws Exception{
        driver.get(integProps.getBaseUrl() + "/studies?query=cancer");
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sort-by")));
        new Select(driver.findElement(By.cssSelector("#sort-by"))).selectByVisibleText("Links");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".release-links")));
        List<WebElement> list = driver.findElements(By.cssSelector(".release-links"));
        Integer [] values = new Integer[list.size()];
        for(int i=0; i < values.length; i++) {
            values[i] = Integer.parseInt(list.get(i).getText().trim().split(" ")[0]);
        }
        Integer [] unsortedValues = values.clone();
        Arrays.sort(values, Collections.reverseOrder());
        assertArrayEquals(values, unsortedValues);
    }

    @Test(expected = TimeoutException.class)
    public void testLinksAscendingSort() throws Exception{
        driver.get(integProps.getBaseUrl() + "/studies?query=cancer");
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 4);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sort-by")));
        new Select(driver.findElement(By.cssSelector("#sort-by"))).selectByVisibleText("Links");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sort-asc")));
        driver.findElement(By.cssSelector("#sort-asc")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".release-links")));
        List<WebElement> list = driver.findElements(By.cssSelector(".release-links"));
        Integer [] values = new Integer[list.size()];
        for(int i=0; i < values.length; i++) {
            values[i] = Integer.parseInt(list.get(i).getText().trim().split(" ")[0]);
        }
        Integer [] unsortedValues = values.clone();
        assertArrayEquals(values, unsortedValues);
        throw new TimeoutException("Test finished successfully");
    }

    @Test
    public void testReleasedDescendingSort() throws Exception{
        driver.get(integProps.getBaseUrl() + "/studies?query=cancer");
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 4);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sort-by")));
        new Select(driver.findElement(By.cssSelector("#sort-by"))).selectByVisibleText("Released");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".release-date")));
        List<WebElement> list = driver.findElements(By.cssSelector(".release-date"));
        Date [] values = new Date[list.size()];
        SimpleDateFormat formatter1 = new SimpleDateFormat("d MMM yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("MMM d, yyyy");        for(int i=0; i < values.length; i++) {
            String date = list.get(i).getAttribute("innerText").trim();
            if (date.equalsIgnoreCase("today")) {
                values[i] = Calendar.getInstance().getTime();
            } else if (date.equalsIgnoreCase("yesterday")) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                values[i] = cal.getTime();
            } else if (date.equalsIgnoreCase("tomorrow")) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                values[i] = cal.getTime();
            } else {
                try{
                    values[i] = formatter1.parse(date);
                }catch (Exception ex){
                    values[i] = formatter2.parse(date);
                }
            }
        }
        Date [] unsortedValues = values.clone();
        Arrays.sort(values, Collections.reverseOrder());
        for (int i=0; i< unsortedValues.length; i++) {
            assertTrue(DateUtils.isSameDay(values[i],unsortedValues[i]));
        }
    }


    @Test
    public void testReleasedAscendingSort() throws Exception{
        driver.get(integProps.getBaseUrl() + "/studies?query=cancer");
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sort-by")));
        new Select(driver.findElement(By.cssSelector("#sort-by"))).selectByVisibleText("Released");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#sort-asc")));
        driver.findElement(By.cssSelector("#sort-asc")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".release-date")));
        List<WebElement> list = driver.findElements(By.cssSelector(".release-date"));
        Date [] values = new Date[list.size()];
        SimpleDateFormat formatter1 = new SimpleDateFormat("d MMM yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("MMM d, yyyy");

        for(int i=0; i < values.length; i++) {
            String date = list.get(i).getAttribute("innerText").trim();
            if (date.equalsIgnoreCase("today")) {
                values[i] = new Date();
            } else if (date.equalsIgnoreCase("yesterday")) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -1);
                values[i] = cal.getTime();
            } else if (date.equalsIgnoreCase("tomorrow")) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 1);
                values[i] = cal.getTime();
            } else {
                try{
                values[i] = formatter1.parse(date);
                }catch (Exception ex){
                    values[i] = formatter2.parse(date);
                }
            }
        }
        Date [] unsortedValues = values.clone();
        for (int i=0; i< unsortedValues.length; i++) {
            assertTrue(DateUtils.isSameDay(values[i],unsortedValues[i]));
        }
    }


    @Test
    public void testPaging() throws Exception{
        driver.manage().window().setSize(new Dimension(1280, 1024));
        driver.get(integProps.getBaseUrl() + "/studies");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("2")));
        driver.findElement(By.linkText("2")).click();
        driver.manage().window().setSize(new Dimension(1280, 1024));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".result-count")));
        String pages = driver.findElement(By.cssSelector(".result-count")).getAttribute("innerText").trim();
        assertTrue(pages.startsWith("(Showing"));
        String accession  = driver.findElement(By.cssSelector(".accession")).getAttribute("innerText").trim();
        driver.findElement(By.cssSelector(".title a")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#orcid-accession")));
        assertEquals(accession, driver.findElement(By.cssSelector("#orcid-accession")).getAttribute("innerText").trim());
    }

}
