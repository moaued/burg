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
import pages.reports.FollowUpReportPage;
import pages.reports.TransactionsReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

@Slf4j
public class FollowupReportsTest extends TestBase {



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
    @Test(description = "[7.1.4] تقرير المتابعة", groups = {"Smoke"})
    @Description("[7.1.4] تقرير المتابعة")
    public void generateFollowUpReport() {
        SHAFT.TestData.JSON reportData = new SHAFT.TestData.JSON("followUpReports.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        FollowUpReportPage followUpReportPage = myTransactionsPage.getHMComponent().navigateToReportsTab().navigateToFollowUpReport();
        String fromDate = GeneralOperations.getHijriDateMonthsBefore(6);
        String toDate = GeneralOperations.getCurrentHijriDate();

        followUpReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("followUpReport1.type"));
        Validations.verifyThat().object(followUpReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(followUpReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(followUpReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(followUpReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("followUpReport1.type"));

        followUpReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("followUpReport2.type"));
        Validations.verifyThat().object(followUpReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(followUpReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(followUpReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(followUpReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("followUpReport2.type"));

        followUpReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("followUpReport3.type"));
        Validations.verifyThat().object(followUpReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(followUpReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(followUpReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(followUpReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("followUpReport3.type"));

    }
}