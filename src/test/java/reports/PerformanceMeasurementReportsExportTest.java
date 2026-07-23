package reports;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.reports.PerformanceMeasurementReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

public class PerformanceMeasurementReportsExportTest extends TestBase {


  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    driver.quit();
  }

  @Test(description = "[7.2.2] تصدير تقرير قياس أداء الإدارات PDF",
      groups = {"Smoke"})
  @Description("[7.2.2] تصدير تقرير قياس أداء الإدارات PDF")
  public void exportDepartmentsPerformanceReport() {

    SHAFT.TestData.JSON reportData =
        new SHAFT.TestData.JSON("PerformanceMeasurementReports.json");

    LoginPage loginPage = new LoginPage(driver);

    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    PerformanceMeasurementReportPage reportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToPerformanceMeasurementReport();

    String fromDate =
        GeneralOperations.getHijriDateMonthsBefore(9);

    String toDate =
        GeneralOperations.getCurrentHijriDate();

    // Generate Report
    reportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("PerformanceMeasurementReports1.type"));

    Validations.verifyThat()
        .object(reportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // PDF Export
    reportPage.clickExport();

    Validations.verifyThat()
        .object(reportPage.waitUntilFileDownloaded(".pdf"))
        .isTrue()
        .perform();


  }



  @Test(description = "[7.2.3] تصدير تقرير قياس أداء الموظفين PDF",
      groups = {"Smoke"})
  @Description("[7.2.3] تصدير تقرير قياس أداء الموظفين PDF")
  public void exportEmployeesPerformanceReport() {

    SHAFT.TestData.JSON reportData =
        new SHAFT.TestData.JSON("PerformanceMeasurementReports.json");

    LoginPage loginPage = new LoginPage(driver);

    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    PerformanceMeasurementReportPage reportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToPerformanceMeasurementReport();

    String fromDate =
        GeneralOperations.getHijriDateMonthsBefore(9);

    String toDate =
        GeneralOperations.getCurrentHijriDate();

    // Generate Report
    reportPage.generateReportForTransactionType(
        fromDate,
        toDate,
        reportData.getTestData("PerformanceMeasurementReports2.type"));

    Validations.verifyThat()
        .object(reportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    // PDF Export
    reportPage.clickExportPdf();

    Validations.verifyThat()
        .object(reportPage.waitUntilFileDownloaded(".pdf"))
        .isTrue()
        .perform();


  }

}
