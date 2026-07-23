package reports;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import jdk.jfr.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.reports.TransactionsFollowUpReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

public class TransactionFollowUpReportsExportTest extends TestBase {

  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    driver.quit();
  }

  @Test(description = "[7.2.6] تصدير تقرير نسب الإنجاز على طلبات المتابعة PDF",
      groups = {"Smoke"})
  @Description("[7.2.6] تصدير تقرير نسب الإنجاز على طلبات المتابعة PDF ")
  public void exportTransactionFollowUpReport() {

    LoginPage loginPage = new LoginPage(driver);

    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    TransactionsFollowUpReportPage transactionsFollowUpReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToTransactionFollowUpReport();

    String fromDate =
        GeneralOperations.getHijriDateMonthsBefore(6);

    String toDate =
        GeneralOperations.getCurrentHijriDate();

    // Generate Report
    transactionsFollowUpReportPage.generateReportForTransactionFollowUp(
        fromDate,
        toDate);

    Validations.verifyThat()
        .number(transactionsFollowUpReportPage.getRowsCount())
        .isGreaterThanOrEquals(0)
        .perform();

    // PDF Export
    transactionsFollowUpReportPage.clickExportPdf();

    Validations.verifyThat()
        .object(transactionsFollowUpReportPage
            .waitUntilFileDownloaded(".pdf"))
        .isTrue()
        .perform();

  }

}