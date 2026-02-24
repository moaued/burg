import com.shaft.driver.SHAFT;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.unifiedNumber.UnifiedNumberPage;
import pages.transactions.MyTransactionsPage;
import base.TestBase;

@Slf4j
public class TrialTestClass extends TestBase {

    @BeforeTest
    public void beforeTest() {
        testData = new SHAFT.TestData.JSON("appData.json");
        openBuragApp();
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }

    //=============================
    @Test(description="تعديل صادر عام [3.2]")
    @Description("تعديل صادر عام [3.2]")
    public void modifyIncomingTransaction() {
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        UnifiedNumberPage unifiedNumberPage = myTransactionsPage.getHMComponent().navigateToUnifiedNumberTab();
       /* TransactionCopiesDetailsPage transactionCopiesDetailsPage= myTransactionsPage.navigateToTransactionsCopies()
                .goToTransactionCopyDetails();
        myTransactionsPage = transactionCopiesDetailsPage.navigateToMyTransactions()
                .getSystemAdminComponent().changeDepartment("مكتب الوزير");
        driver.browser().captureScreenshot();
        transactionCopiesDetailsPage= myTransactionsPage.navigateToTransactionsCopies()
                .goToTransactionCopyDetails();
        myTransactionsPage = transactionCopiesDetailsPage.navigateToMyTransactions()
                .getSystemAdminComponent().changeDepartment("وكالة البيئة");
        driver.browser().captureScreenshot();
        transactionCopiesDetailsPage= myTransactionsPage.navigateToTransactionsCopies()
                .goToTransactionCopyDetails();
        myTransactionsPage = transactionCopiesDetailsPage.navigateToMyTransactions()
                .getSystemAdminComponent().changeDepartment("مكتب نائب الوزير");
        driver.browser().captureScreenshot();*/
    }
}
