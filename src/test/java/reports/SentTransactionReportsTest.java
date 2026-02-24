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
import pages.reports.SentTransactionsReportPage;
import pages.reports.TransactionsReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

@Slf4j
public class SentTransactionReportsTest extends TestBase {



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
    @Test(description = "[7.1.7] تقرير المعاملات المرسلة", groups = {"Smoke"})
    @Description("[7.1.7] تقرير المعاملات المرسلة")
    public void generateTransactionReport() {
        SHAFT.TestData.JSON reportData = new SHAFT.TestData.JSON("transactionsReports.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        SentTransactionsReportPage sentTransactionsReportPage = myTransactionsPage.getHMComponent()
                .navigateToReportsTab().navigateToSentTransactionReport();
        String fromDate = GeneralOperations.getHijriDateWeeksBefore(19);
        String toDate = GeneralOperations.getCurrentHijriDate();

        sentTransactionsReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("transactionReport1.type"));
        Validations.verifyThat().object(sentTransactionsReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(sentTransactionsReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(sentTransactionsReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(sentTransactionsReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("transactionReport1.type"));

        sentTransactionsReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("transactionReport2.type"));
        Validations.verifyThat().object(sentTransactionsReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(sentTransactionsReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(sentTransactionsReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(sentTransactionsReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("transactionReport2.type"));

        sentTransactionsReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("transactionReport3.type"));
        Validations.verifyThat().object(sentTransactionsReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(sentTransactionsReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(sentTransactionsReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(sentTransactionsReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("transactionReport3.type"));

    }
}