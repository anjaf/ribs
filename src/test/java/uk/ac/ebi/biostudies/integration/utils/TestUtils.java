package uk.ac.ebi.biostudies.integration.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.biostudies.integration.IntegrationTestSuite;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by awais on 18/08/2015.
 */
public class TestUtils {


    public static void login(String url, String username, String password){
        IntegrationTestSuite.driver.navigate().to(url);
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#studyCount")));
        IntegrationTestSuite.driver.findElement(By.cssSelector("#login-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#user-field")));
        IntegrationTestSuite.driver.findElement(By.cssSelector("#user-field")).sendKeys(username);
        IntegrationTestSuite.driver.findElement(By.cssSelector("#pass-field")).sendKeys(password);
        IntegrationTestSuite.driver.findElement(By.cssSelector("input[type='submit'].submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#logout-button")));
        assertEquals("Logout "+ username, IntegrationTestSuite.driver.findElement(By.cssSelector("#logout-button")).getAttribute("innerText").trim());
    }

    public static void logout(String url){
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 10);
        IntegrationTestSuite.driver.navigate().to(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#logout-button")));
        IntegrationTestSuite.driver.findElement(By.cssSelector("#logout-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#login-button")));
        assertEquals("Login", IntegrationTestSuite.driver.findElement(By.cssSelector("#login-button")).getAttribute("innerText").trim());
    }

    public static void validIndexIsloaded(String baseUrl){
        IntegrationTestSuite.driver.navigate().to(baseUrl);
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#projectCount")));
        String prjCount = IntegrationTestSuite.driver.findElement(By.id("projectCount")).getText();
        int count = Integer.valueOf(prjCount);
        assertThat(count, greaterThan(0));
    }

    public static void indexBigFile(){

    }

}
