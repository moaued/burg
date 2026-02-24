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
import pages.reports.FollowUpDetailsReportPage;
import pages.reports.FollowUpReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

@Slf4j
public class FollowupDetailsReportsTest extends TestBase {



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
    @Test(description = "[7.1.5] تقرير المتابعة التفصيلى", groups = {"Smoke"})
    @Description("[7.1.5] تقرير المتابعة التفصيلى")
    public void generateFollowUpDetailsReport() {
        SHAFT.TestData.JSON reportData = new SHAFT.TestData.JSON("followUpReports.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        FollowUpDetailsReportPage followUpDetailsReportPage = myTransactionsPage.getHMComponent().navigateToReportsTab().navigateToFollowUpDetailsReport();
        String fromDate = GeneralOperations.getHijriDateMonthsBefore(9);
        String toDate = GeneralOperations.getCurrentHijriDate();

        followUpDetailsReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("followUpReport1.type"));
        Validations.verifyThat().object(followUpDetailsReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(followUpDetailsReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(followUpDetailsReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(followUpDetailsReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("followUpReport1.type"));

        followUpDetailsReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("followUpReport2.type"));
        Validations.verifyThat().object(followUpDetailsReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(followUpDetailsReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(followUpDetailsReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(followUpDetailsReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("followUpReport2.type"));

        followUpDetailsReportPage.generateReportForTransactionType(fromDate, toDate, reportData.getTestData("followUpReport3.type"));
        Validations.verifyThat().object(followUpDetailsReportPage.isResultsGridDisplayed()).isTrue();
        Validations.verifyThat().number(followUpDetailsReportPage.getRowsCount()).isGreaterThanOrEquals(0);
        Validations.verifyThat().object(followUpDetailsReportPage.getDateRangeInReport()).contains(fromDate+" - "+toDate);
        Validations.verifyThat().object(followUpDetailsReportPage.getTransactionTypeInReport())
                .contains(reportData.getTestData("followUpReport3.type"));

    }
}