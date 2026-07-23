package reports;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import jdk.jfr.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.reports.EmployeePermissionsReportPage;
import pages.transactions.MyTransactionsPage;

public class EmployeePermissionsReportsExportTest extends TestBase {


  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    driver.quit();
  }


  @Test(description = "[7.2.11] تصدير تقرير صلاحيات الموظفين Excel",
      groups = {"Smoke"})
  @Description("[7.2.11] تصدير تقرير صلاحيات الموظفين Excel")
  public void exportEmployeePermissionsReport() {

    LoginPage loginPage = new LoginPage(driver);

    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    EmployeePermissionsReportPage employeePermissionsReportPage =
        myTransactionsPage.getHMComponent()
            .navigateToReportsTab()
            .navigateToEmployeePermissionsReport();

    Validations.verifyThat()
        .object(employeePermissionsReportPage.isResultsGridDisplayed())
        .isTrue()
        .perform();

    employeePermissionsReportPage.clickExportExcel();

    Validations.verifyThat()
        .object(employeePermissionsReportPage
            .waitUntilFileDownloaded(".xls"))
        .isTrue()
        .perform();
  }

}
