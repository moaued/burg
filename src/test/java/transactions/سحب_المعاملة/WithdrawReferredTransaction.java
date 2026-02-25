package transactions.سحب_المعاملة;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import components.TransactionsOperationsComponent;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.SentTransactionsPage;

public class WithdrawReferredTransaction extends TestBase {


  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//  @AfterMethod
//  public void afterTest() {
//    driver.quit();
//  }

  //======================

  @Test(description = "التحقق من إمكانية سحب المعاملة قبل استلامها وعودتها إلى معاملاتي[12.1]")
  @Description("سحب المعاملة المرسلة (الوارد العام) من خلال سلة المعاملات المرسلة قبل استلامها من الجهة المرسل لها[12.1]")
  public void verifyWithdrawTransactionBeforeReceiving() {
    SHAFT.TestData.JSON followupData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionIndividual();
    inTransactionDraftPage.expandAttachmentSection();
    inTransactionDraftPage.addAttachment(followupData.getTestData("attachment1.type")
        , followupData.getTestData("attachment1.location"), followupData.getTestData("attachment1.validity"));

    inTransactionDraftPage.saveInTransaction();
    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
    int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

    myTransactionsPage = myTransactionsPage.selectFirstTransaction().navigateToDirectReferral()
        .addNewReferral( followupData.getTestData("referral1.orgUnitNum"),
            followupData.getTestData("referral1.actionType"),
            followupData.getTestData("referral1.deliveryMethod"));

    // ⃣ الانتقال إلى صندوق المعاملات المرسلة
    SentTransactionsPage sentTransactionsPage =
        myTransactionsPage.navigateToSentTransactions();

//    البحث عن المعاملة في سلة المعاملات المرسلة
    TransactionsOperationsComponent operations =
        myTransactionsPage.getTransactionsOperationsComponent();
    operations.searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));

    // ⃣ سحب المعاملة قبل استلامها
    sentTransactionsPage
        .withdrawTransaction();

    // ⃣ الرجوع إلى صفحة معاملاتي ثم البحث عن المعاملة
    myTransactionsPage.navigateToMyTransaction();

    // ⃣ التحقق من ظهور المعاملة في معاملاتي
    boolean isReturned =
        myTransactionsPage
            .isTransactionPresent(transactionNumber);

    Validations.verifyThat()
        .object(isReturned)
        .isTrue()
        .withCustomReportMessage(
            "المعاملة لم تعد إلى معاملاتي بعد السحب"
        )
        .perform();

  }

}
