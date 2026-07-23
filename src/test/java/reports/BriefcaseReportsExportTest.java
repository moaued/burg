package reports;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import jdk.jfr.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.reports.BriefcaseReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

public class BriefcaseReportsExportTest extends TestBase {

  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    driver.quit();
  }

  @Test(description = "[7.2.12] تصدير تقرير حقيبة العرض PDF & Excel",
      groups = {"Smoke"})
  @Description("[7.2.12] تصدير تقرير حقيبة العرض PDF & Excel")
  public void exportBriefcaseReport() {

    LoginPage loginPage = new LoginPage(driver);

    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    BriefcaseReportPage briefcaseReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToBriefcaseReport();

    String fromDate =
        GeneralOperations.getHijriDateMonthsBefore(9);

    String toDate =
        GeneralOperations.getCurrentHijriDate();

    // Generate Report
    briefcaseReportPage.generateReportForTransactionType(
        fromDate,
        toDate);

    Validations.verifyThat()
        .object(briefcaseReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    Validations.verifyThat()
        .number(briefcaseReportPage.getRowsCount())
        .isGreaterThan(0)
        .perform();

    // PDF Export
    briefcaseReportPage.clickExportPdf();

    Validations.verifyThat()
        .object(briefcaseReportPage.waitUntilFileDownloaded(".pdf"))
        .isTrue()
        .perform();

    // إعادة فتح التقرير لتصدير Excel
    briefcaseReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToBriefcaseReport();

    briefcaseReportPage.generateReportForTransactionType(
        fromDate,
        toDate);

    Validations.verifyThat()
        .object(briefcaseReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // Excel Export
    briefcaseReportPage.clickExportExcel();

    Validations.verifyThat()
        .object(briefcaseReportPage.waitUntilFileDownloaded(".xls"))
        .isTrue()
        .perform();
  }

}
