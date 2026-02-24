package transactions.صادر_عام;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.transactions.OutTransactionsPage;
import pages.transactions.OutTransactionDraftPage;
import pages.transactions.MyTransactionsPage;

@Slf4j
public class ModifyGeneralOutgoingTest extends TestBase {

    @BeforeMethod
    public void beforeTest() {
        testData = new SHAFT.TestData.JSON("appData.json");
        openBuragApp();
    }

    @AfterMethod
    public void afterTest() {
        driver.quit();
    }

    //=============================
    @Test(description = "تعديل صادر عام [3.2]")
    @Description("تعديل صادر عام [3.2]")
    public void modifyOutgoingTransaction() {
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        OutTransactionsPage outTransactionsPage = myTransactionsPage.navigateToOutTransactions();
        OutTransactionDraftPage outTransactionDraftPage = outTransactionsPage.navigateToExportedTransactions().tabOnEditFirstOutTransaction();
        String transactionDraftNumber = outTransactionDraftPage.getOutTransactionNumber();
        outTransactionDraftPage.modifyTransactionSubject();
        String transactionDraftSubject = outTransactionDraftPage.getTransactionSubject();
        outTransactionsPage = outTransactionDraftPage.navigateToTransactionsPage().navigateToExportedTransactions().searchForTransactionWithId(transactionDraftNumber);
        String transactionNumber = outTransactionsPage.getFirstTransactionNumber();
        String transactionSubject = outTransactionsPage.getFirstTransactionDescription();
        Validations.assertThat().object(transactionNumber).equals(transactionDraftNumber);
        Validations.assertThat().object(transactionSubject).equals(transactionDraftSubject);
    }
}
