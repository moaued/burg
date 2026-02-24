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
public class AddGeneralOutgoingTransactionTest extends TestBase {

    @BeforeMethod
    public void beforeTest() {
        testData = new SHAFT.TestData.JSON("appData.json");
        openBuragApp();
    }

    @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

    //=============================

    /**
     * To make sure of the SMS sending, we need to get access to the API responsible for sending the SMS
     As far as I understand, we can check by the transactionId if the SMS is sent or not
     (this is all what we need here, we won't test the SMS sending itself)
     */
    @Test(description = "انشاء صادر عام [3.1]")
    @Description("انشاء صادر عام، واضافة بيانات صاحب علاقة وتحديد خيار ارسال رسالة نصية والتحقق من وصول الرسالة [3.1]")
    public void addOutgoingTransaction() {
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        OutTransactionDraftPage outTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent().addNewGeneralTransaction();
        outTransactionDraftPage.addGeneralTransaction();
        String transactionDraftNumber = outTransactionDraftPage.getTransactionNumberFromConfirmation();
        OutTransactionsPage outTransactionsPage = outTransactionDraftPage.backToOutgoingTransactionPage().navigateToExportedTransactions()
                .getTransactionsOperationsComponent().searchForTransactionWithId(transactionDraftNumber, new OutTransactionsPage(driver));
        String transactionNumber = outTransactionsPage.getFirstTransactionNumber();
        Validations.assertThat().object(transactionDraftNumber).isEqualTo(transactionNumber);
    }
}
