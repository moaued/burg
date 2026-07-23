package reports;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.reports.TasksReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

public class TasksReportsExportTest extends TestBase {


  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    driver.quit();
  }

  @Test(description = "[7.2.7] تصدير تقرير المهام PDF & Excel",
      groups = {"Smoke"})
  @Description("[7.2.7] تصدير تقرير المهام PDF & Excel")
  public void exportTasksReport() {

    SHAFT.TestData.JSON reportData =
        new SHAFT.TestData.JSON("tasksReports.json");

    LoginPage loginPage = new LoginPage(driver);

    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    TasksReportPage tasksReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateTasksReport();

    String fromDate =
        GeneralOperations.getHijriDateMonthsBefore(9);

    String toDate =
        GeneralOperations.getCurrentHijriDate();

    // Generate Report
    tasksReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("tasksReport1.type"));

    Validations.verifyThat()
        .object(tasksReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // PDF Export
    tasksReportPage.clickExportPdf();

    Validations.verifyThat()
        .object(tasksReportPage.waitUntilFileDownloaded(".pdf"))
        .isTrue()
        .perform();

    // إعادة فتح الصفحة لتصدير Excel
    tasksReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateTasksReport();

    tasksReportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("tasksReport1.type"));

    Validations.verifyThat()
        .object(tasksReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // Excel Export
    tasksReportPage.clickExportExcel();

    Validations.verifyThat()
        .object(tasksReportPage.waitUntilFileDownloaded(".xls"))
        .isTrue()
        .perform();
  }

}
