package reports;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.reports.FollowUpReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

public class FollowupReportsExportTest extends TestBase {


  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    driver.quit();
  }

  @Test(description = "[7.2.8] تصدير تقرير المتابعة PDF & Excel",
      groups = {"Smoke"})
  @Description("[7.2.8] تصدير تقرير المتابعة PDF & Excel")
  public void exportFollowUpReport() {

    SHAFT.TestData.JSON reportData =
        new SHAFT.TestData.JSON("followUpReports.json");

    LoginPage loginPage = new LoginPage(driver);

    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    FollowUpReportPage followUpReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToFollowUpReport();

    String fromDate =
        GeneralOperations.getHijriDateMonthsBefore(6);

    String toDate =
        GeneralOperations.getCurrentHijriDate();

    // Generate Report
    followUpReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("followUpReport1.type"));

    Validations.verifyThat()
        .object(followUpReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // PDF Export
    followUpReportPage.clickExportPdf();

    Validations.verifyThat()
        .object(followUpReportPage.waitUntilFileDownloaded(".pdf"))
        .isTrue()
        .perform();

    // إعادة تحميل التقرير لتصدير Excel
    followUpReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToFollowUpReport();

    followUpReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("followUpReport1.type"));

    Validations.verifyThat()
        .object(followUpReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // Excel Export
    followUpReportPage.clickExportExcel();

    Validations.verifyThat()
        .object(followUpReportPage.waitUntilFileDownloaded(".xls"))
        .isTrue()
        .perform();

    // إعادة فتح التقرير لتصدير Word
    // إعادة تحميل التقرير لتصدير Excel
    followUpReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToFollowUpReport();

    followUpReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("followUpReport1.type"));

    Validations.verifyThat()
        .object(followUpReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // Word Export
    followUpReportPage.clickExportWord();

    Validations.verifyThat()
        .object(followUpReportPage
            .waitUntilFileDownloaded(".doc"))
        .isTrue()
        .perform();



  }

}
