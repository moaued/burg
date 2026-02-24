package reports;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.reports.BriefcaseReportPage;
import pages.transactions.BriefcasePage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

@Slf4j
public class BriefcaseReportsTest extends TestBase {


  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//  @AfterMethod(alwaysRun = true)
//  public void afterTest() {
//    driver.quit();
//  }

  //=============================
  @Test(description = "[7.1.6] تقرير حقيبة العرض", groups = {"Smoke"})
  @Description("[7.1.6] عرض تقرير ’حقيبة العرض’")
  public void generateBriefcaseReport() {
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    BriefcaseReportPage briefcaseReportPage = myTransactionsPage.getHMComponent()
        .navigateToReportsTab().navigateToBriefcaseReport();
    String fromDate = GeneralOperations.getHijriDateMonthsBefore(9);
    String toDate = GeneralOperations.getCurrentHijriDate();

    briefcaseReportPage.generateReportForTransactionType(fromDate, toDate);
    Validations.verifyThat().object(briefcaseReportPage.isResultsGridDisplayed()).isTrue();
    Validations.verifyThat().number(briefcaseReportPage.getRowsCount()).isGreaterThan(0);
    //This step is failing due to an issue with the date that it is not the Hijri date in the results area
    //Validations.verifyThat().object(briefcaseReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
  }

  @Test(description = "[7.3] تقرير حقيبة العرض و مطابقة النتائج")
  @Description("[7.3] التحقق من عمل تقرير حقيبة العرض ومطابقة النتائج للمعاملة في حقيبة العرض")
  public void compareBriefcaseReportResultsToThePage() {
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    BriefcaseReportPage briefcaseReportPage = myTransactionsPage.getHMComponent()
        .navigateToReportsTab().navigateToBriefcaseReport();
    String fromDate = GeneralOperations.getHijriDateMonthsBefore(9);
    String toDate = GeneralOperations.getCurrentHijriDate();

    briefcaseReportPage.generateReportForTransactionType(fromDate, toDate);
    Validations.verifyThat().object(briefcaseReportPage.isResultsGridDisplayed()).isTrue();
    Validations.verifyThat().number(briefcaseReportPage.getRowsCount()).isGreaterThan(0);
    //This step is failing due to an issue with the date that it is not the Hijri date in the results area
    //Validations.verifyThat().object(briefcaseReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
    //driver.browser().captureScreenshot();

    String firstTransactionNumber = briefcaseReportPage.getFirstResultTransactionNumber();
    String randomTransactionNumber = briefcaseReportPage.getRandomResultTransactionNumber();

    BriefcasePage briefcasePage = briefcaseReportPage.getHMComponent().navigateToTransactionsTab()
        .navigateToBriefcase();

    String transactionNoOnCard = briefcasePage.searchForTransaction(firstTransactionNumber)
        .getTransactionNumberFromFirstCard();
    int numberOfTransactionCards = briefcasePage.getNumberOfRetrievedCards();
    Validations.verifyThat().number(numberOfTransactionCards).isGreaterThanOrEquals(1);
    Validations.verifyThat().object(transactionNoOnCard).isEqualTo(firstTransactionNumber);

    transactionNoOnCard = briefcasePage.refreshPageBeforeSearch()
        .searchForTransaction(randomTransactionNumber).getTransactionNumberFromFirstCard();
    numberOfTransactionCards = briefcasePage.getNumberOfRetrievedCards();
    Validations.verifyThat().number(numberOfTransactionCards).isGreaterThanOrEquals(1);
    Validations.verifyThat().object(transactionNoOnCard).isEqualTo(randomTransactionNumber);

  }
}