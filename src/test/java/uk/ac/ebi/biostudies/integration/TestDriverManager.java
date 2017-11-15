package uk.ac.ebi.biostudies.integration;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.biostudies.integration.utils.IntegProps;
import uk.ac.ebi.biostudies.integration.utils.IntegrationConfig;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by ehsan on 22/06/2017.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IntegrationConfig.class})
public class TestDriverManager {

    private static WebDriver driver;

    @Autowired
    IntegProps integProps;

    @BeforeClass
    public static void setUpBeforeClass() {
        driver = IntegrationTestSuite.driver;
    }


    @Test
    public void navigation(){
        System.out.println(integProps.getBaseUrl());
        driver.navigate().to(integProps.getBaseUrl());
        WebDriverWait wdw = new WebDriverWait(driver, 10);
        wdw.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                String result = d.findElement(By.id("projectCount")).getText();
                return result.length()>0 ;
            }
        });
        String prjCount = IntegrationTestSuite.driver.findElement(By.id("projectCount")).getText();
        int count = Integer.valueOf(prjCount);
        assertThat(count, greaterThan(0));
    }
}
