package reports;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import jdk.jfr.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.reports.FollowUpEmployeePerformanceReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

public class FollowUpEmployeePerformanceReportExportTest extends TestBase {


  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    driver.quit();
  }
  @Test(
      description =
          "[7.2.5] تصدير تقرير قياس أداء موظفي المتابعة PDF",
      groups = {"Smoke"})
  @Description(
      "[7.2.5] تصدير تقرير قياس أداء موظفي المتابعة PDF")
  public void exportFollowUpEmployeePerformanceReport() {

    SHAFT.TestData.JSON reportData =
        new SHAFT.TestData.JSON("tasksReports.json");


    LoginPage loginPage =
        new LoginPage(driver);

    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    FollowUpEmployeePerformanceReportPage reportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToFollowUpEmployeePerformanceReport();

    String fromDate =
        GeneralOperations.getHijriDateMonthsBefore(6);

    String toDate =
        GeneralOperations.getCurrentHijriDate();

    reportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("tasksReport1.type"));

    Validations.verifyThat()
        .object(reportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    reportPage.clickExportPdf();

    Validations.verifyThat()
        .object(reportPage
            .waitUntilFileDownloaded(".pdf"))
        .isTrue()
        .perform();
  }

}
