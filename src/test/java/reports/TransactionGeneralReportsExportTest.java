package reports;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.reports.TransactionsGeneralReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

public class TransactionGeneralReportsExportTest extends TestBase {

  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    driver.quit();
  }

  //=============================
  @Test(description = "[7.2.2] تصدير تقرير المعاملات الشامل PDF & Excel",
      groups = {"Smoke"})
  @Description("[7.2.2] تصدير تقرير المعاملات الشامل PDF & Excel")
  public void exportGeneralTransactionReport() {

    SHAFT.TestData.JSON reportData =
        new SHAFT.TestData.JSON("transactionsReports.json");

    LoginPage loginPage = new LoginPage(driver);

    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    TransactionsGeneralReportPage transactionsGeneralReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToTransactionsGeneralReport();

    String fromDate =
        GeneralOperations.getHijriDateWeeksBefore(24);

    String toDate =
        GeneralOperations.getCurrentHijriDate();

    transactionsGeneralReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("generalTransactions.report1.type"));

    Validations.verifyThat()
        .object(transactionsGeneralReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // PDF Export
    transactionsGeneralReportPage.clickExportPdf();

    Validations.verifyThat()
        .object(transactionsGeneralReportPage.waitUntilFileDownloaded(".pdf"))
        .isTrue()
        .perform();

    // إعادة الدخول للصفحة بعد التصدير
    transactionsGeneralReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToTransactionsGeneralReport();

    transactionsGeneralReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("generalTransactions.report1.type"));

    Validations.verifyThat()
        .object(transactionsGeneralReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // Excel Export
    transactionsGeneralReportPage.clickExportExcel();

    Validations.verifyThat()
        .object(transactionsGeneralReportPage.waitUntilFileDownloaded(".xls"))
        .isTrue()
        .perform();
  }

}
