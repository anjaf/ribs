package uk.ac.ebi.biostudies.integration.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.ac.ebi.biostudies.api.util.HttpTools;
import uk.ac.ebi.biostudies.integration.IntegrationTestSuite;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by awais on 18/08/2015.
 */
public class TestUtils {


    public static void login(String url, String username, String password){
        IntegrationTestSuite.webDriver.navigate().to(url);
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.webDriver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#studyCount")));
        IntegrationTestSuite.webDriver.findElement(By.cssSelector("#login-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#user-field")));
        IntegrationTestSuite.webDriver.findElement(By.cssSelector("#user-field")).sendKeys(username);
        IntegrationTestSuite.webDriver.findElement(By.cssSelector("#pass-field")).sendKeys(password);
        IntegrationTestSuite.webDriver.findElement(By.cssSelector("input[type='submit'].submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#logout-button")));
        String logoutText = IntegrationTestSuite.webDriver.findElement(By.cssSelector("#logout-button")).getAttribute("innerText").trim();
        assertThat(logoutText, startsWith("Logout "));
    }

    public static void logout(String url){
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.webDriver, 20);
        IntegrationTestSuite.webDriver.navigate().to(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#logout-button")));
        IntegrationTestSuite.webDriver.findElement(By.cssSelector("#logout-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#login-button")));
        assertEquals("Login", IntegrationTestSuite.webDriver.findElement(By.cssSelector("#login-button")).getAttribute("innerText").trim());
    }

    public static void validIndexIsloaded(String baseUrl){
        IntegrationTestSuite.webDriver.navigate().to(baseUrl);
        WebDriverWait wait = new WebDriverWait(IntegrationTestSuite.webDriver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#studyCount")));
        String studyCount = IntegrationTestSuite.webDriver.findElement(By.id("studyCount")).getText();
        int count = Integer.valueOf(studyCount.replaceAll("[^0-9]*",""));
        assertThat(count, greaterThan(0));
    }


}
