package reports;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.reports.SentTransactionsReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

public class SentTransactionReportsExportTest extends TestBase {


  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    driver.quit();
  }

  @Test(description = "[7.2.10] تصدير تقرير المعاملات المرسلة PDF & Excel",
      groups = {"Smoke"})
  @Description("[7.2.10] تصدير تقرير المعاملات المرسلة PDF & Excel")
  public void exportSentTransactionReport() {

    SHAFT.TestData.JSON reportData =
        new SHAFT.TestData.JSON("transactionsReports.json");

    LoginPage loginPage = new LoginPage(driver);

    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    SentTransactionsReportPage sentTransactionsReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToSentTransactionReport();

    String fromDate =
        GeneralOperations.getHijriDateWeeksBefore(19);

    String toDate =
        GeneralOperations.getCurrentHijriDate();

    // Generate Report
    sentTransactionsReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("transactionReport1.type"));

    Validations.verifyThat()
        .object(sentTransactionsReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // PDF Export
    sentTransactionsReportPage.clickExportPdf();

    Validations.verifyThat()
        .object(sentTransactionsReportPage
            .waitUntilFileDownloaded(".pdf"))
        .isTrue()
        .perform();

    // إعادة تحميل التقرير لتصدير Excel
    sentTransactionsReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToSentTransactionReport();

    sentTransactionsReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("transactionReport1.type"));

    Validations.verifyThat()
        .object(sentTransactionsReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // Excel Export
    sentTransactionsReportPage.clickExportExcel();

    Validations.verifyThat()
        .object(sentTransactionsReportPage
            .waitUntilFileDownloaded(".xls"))
        .isTrue()
        .perform();
  }

}
