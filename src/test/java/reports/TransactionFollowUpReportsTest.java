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
import pages.reports.TransactionsFollowUpReportPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

@Slf4j
public class TransactionFollowUpReportsTest extends TestBase {



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
    @Test(description = "[7.1.2] نسب الانجاز على طلبات المتابعة", groups = {"Smoke"})
    @Description("[7.1.2] نسب الانجاز على طلبات المتابعة")
    public void generateTransactionFollowUpReport() {
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        TransactionsFollowUpReportPage transactionsFollowUpReportPage =
                myTransactionsPage.getHMComponent().navigateToReportsTab().navigateToTransactionFollowUpReport();
        String fromDate = GeneralOperations.getHijriDateMonthsBefore(6);
        String toDate = GeneralOperations.getCurrentHijriDate();

        transactionsFollowUpReportPage.generateReportForTransactionFollowUp(fromDate, toDate);
        Validations.verifyThat().number(transactionsFollowUpReportPage.getRowsCount()).isGreaterThanOrEquals(0);

    }
}