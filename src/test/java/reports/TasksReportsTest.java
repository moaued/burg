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
import pages.reports.TasksReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

@Slf4j
public class TasksReportsTest extends TestBase {



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
    @Test(description = "[7.1.3] تقرير المهام")
    @Description("[7.1.3] تقرير المهام")
    public void generateTasksReport() {
        SHAFT.TestData.JSON reportData = new SHAFT.TestData.JSON("tasksReports.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        TasksReportPage tasksReportPage = myTransactionsPage.getHMComponent().navigateToReportsTab().navigateTasksReport();
        String fromDate = GeneralOperations.getHijriDateMonthsBefore(9);
        String toDate = GeneralOperations.getCurrentHijriDate();

        tasksReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("tasksReport1.type"));
        Validations.verifyThat().object(tasksReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(tasksReportPage.getResultsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(tasksReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(tasksReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("tasksReport1.type"));

        tasksReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("tasksReport2.type"));
        Validations.verifyThat().object(tasksReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(tasksReportPage.getResultsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(tasksReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(tasksReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("tasksReport2.type"));

        tasksReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("tasksReport3.type"));
        Validations.verifyThat().object(tasksReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(tasksReportPage.getResultsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(tasksReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(tasksReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("tasksReport3.type"));

    }
}