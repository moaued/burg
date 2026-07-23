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
import pages.reports.TransactionsReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

//@Slf4j

public class TransactionReportsExportTest extends TestBase {

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

  @Test(description = "[7.2.1] تصدير تقرير المعاملات PDF & Excel",
      groups = {"Smoke"})
  @Description("[7.2.1] تصدير تقرير المعاملات PDF & Excel")
  public void exportTransactionReport() {

    SHAFT.TestData.JSON reportData =
        new SHAFT.TestData.JSON("transactionsReports.json");

    LoginPage loginPage = new LoginPage(driver);

    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    TransactionsReportPage transactionsReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab();

    String fromDate =
        GeneralOperations.getHijriDateWeeksBefore(24);

    String toDate =
        GeneralOperations.getCurrentHijriDate();

    transactionsReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("transactionReport1.type"));

    Validations.verifyThat()
        .object(transactionsReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // PDF Export

    transactionsReportPage.clickExportPdf();

    Validations.verifyThat()
        .object(transactionsReportPage.waitUntilFileDownloaded(".pdf"))
        .isTrue()
        .perform();

    driver.browser().navigateBack();

    // Excel Export
     transactionsReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab();

     fromDate =
        GeneralOperations.getHijriDateWeeksBefore(24);

     toDate =
        GeneralOperations.getCurrentHijriDate();

    transactionsReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("transactionReport1.type"));

    Validations.verifyThat()
        .object(transactionsReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // إعادة الدخول للصفحة بعد التصدير
    transactionsReportPage.clickExportExcel();

 /*   Validations.verifyThat()
        .object(transactionsReportPage
            .isExcelDownloaded("TransactionReport.xlsx"))
        .isTrue()
        .perform();*/

    Validations.verifyThat()
        .object(transactionsReportPage.waitUntilFileDownloaded(".xls"))
        .isTrue()
        .perform();
  }

}
