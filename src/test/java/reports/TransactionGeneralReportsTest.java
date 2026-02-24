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
import pages.reports.TransactionsGeneralReportPage;
import pages.reports.TransactionsReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

@Slf4j
public class TransactionGeneralReportsTest extends TestBase {



    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        testData = new SHAFT.TestData.JSON("appData.json");
        openBuragApp();
    }

//    @AfterMethod(alwaysRun = true)
//    public void afterTest() {
//        driver.quit();
//    }

    //=============================
    @Test(description = "[7.1.8] تقرير المعاملات الشامل", groups = {"Smoke"})
    @Description("[7.1.8] تقرير المعاملات الشامل" + "\n is not working[تقرير المعاملات الشامل] The Report")
    public void generateTransactionsGeneralReport() {
        SHAFT.TestData.JSON reportData = new SHAFT.TestData.JSON("transactionsReports.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        TransactionsGeneralReportPage transactionsGeneralReportPage = myTransactionsPage.getHMComponent()
                .navigateToReportsTab().navigateToTransactionsGeneralReport();
        String fromDate = GeneralOperations.getHijriDateWeeksBefore(24);
        String toDate = GeneralOperations.getCurrentHijriDate();

        transactionsGeneralReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("generalTransactions.report1.type"));
        Validations.verifyThat().object(transactionsGeneralReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(transactionsGeneralReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(transactionsGeneralReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(transactionsGeneralReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("generalTransactions.report1.type"));

        transactionsGeneralReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("generalTransactions.report2.type"));
        Validations.verifyThat().object(transactionsGeneralReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(transactionsGeneralReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(transactionsGeneralReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
/*        Validations.verifyThat().object(transactionsGeneralReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("generalTransactions.report2.type"));*/

        transactionsGeneralReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("generalTransactions.report3.type"));
        Validations.verifyThat().object(transactionsGeneralReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(transactionsGeneralReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(transactionsGeneralReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
/*        Validations.verifyThat().object(transactionsGeneralReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("generalTransactions.report3.type"));*/

    }
}