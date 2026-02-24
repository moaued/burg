package reports;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.reports.TransactionsReportPage;
import pages.LoginPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

@Slf4j
public class TransactionReportsTest extends TestBase {



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
    @Test(description = "[7.1.1] تقارير المعاملات", groups = {"Smoke"})
    @Description("[7.1.1] تقارير المعاملات")
    public void generateTransactionReport() {
        SHAFT.TestData.JSON reportData = new SHAFT.TestData.JSON("transactionsReports.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        TransactionsReportPage transactionsReportPage = myTransactionsPage.getHMComponent().navigateToReportsTab();
        String fromDate = GeneralOperations.getHijriDateWeeksBefore(24);
        String toDate = GeneralOperations.getCurrentHijriDate();

        transactionsReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("transactionReport1.type"));
        Validations.verifyThat().object(transactionsReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(transactionsReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(transactionsReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(transactionsReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("transactionReport1.type"));

        transactionsReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("transactionReport2.type"));
        Validations.verifyThat().object(transactionsReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(transactionsReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(transactionsReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(transactionsReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("transactionReport2.type"));

        transactionsReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("transactionReport3.type"));
        Validations.verifyThat().object(transactionsReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(transactionsReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(transactionsReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(transactionsReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("transactionReport3.type"));

    }
}