package reports;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import jdk.jfr.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.reports.FollowUpDetailsReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

public class FollowupDetailsReportsExportTest extends TestBase {


  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    driver.quit();
  }

  @Test(description = "[7.2.9] تصدير تقرير المتابعة التفصيلي PDF & Excel",
      groups = {"Smoke"})
  @Description("[7.2.9] تصدير تقرير المتابعة التفصيلي PDF & Excel")
  public void exportFollowUpDetailsReport() {

    SHAFT.TestData.JSON reportData =
        new SHAFT.TestData.JSON("followUpReports.json");

    LoginPage loginPage = new LoginPage(driver);

    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    FollowUpDetailsReportPage followUpDetailsReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToFollowUpDetailsReport();

    String fromDate =
        GeneralOperations.getHijriDateMonthsBefore(9);

    String toDate =
        GeneralOperations.getCurrentHijriDate();

    // Generate Report
    followUpDetailsReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("followUpReport1.type"));

    Validations.verifyThat()
        .object(followUpDetailsReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // PDF Export
    followUpDetailsReportPage.clickExportPdf();

    Validations.verifyThat()
        .object(followUpDetailsReportPage
            .waitUntilFileDownloaded(".pdf"))
        .isTrue()
        .perform();

    // إعادة فتح التقرير لتصدير Excel
    followUpDetailsReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToFollowUpDetailsReport();

    followUpDetailsReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("followUpReport1.type"));

    Validations.verifyThat()
        .object(followUpDetailsReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // Excel Export
    followUpDetailsReportPage.clickExportExcel();

    Validations.verifyThat()
        .object(followUpDetailsReportPage
            .waitUntilFileDownloaded(".xls"))
        .isTrue()
        .perform();

    // إعادة فتح التقرير لتصدير Word
    followUpDetailsReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToFollowUpDetailsReport();

    followUpDetailsReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("followUpReport1.type"));

    Validations.verifyThat()
        .object(followUpDetailsReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // Word Export
    followUpDetailsReportPage.clickExportWord();

    Validations.verifyThat()
        .object(followUpDetailsReportPage
            .waitUntilFileDownloaded(".doc"))
        .isTrue()
        .perform();

  }

}
