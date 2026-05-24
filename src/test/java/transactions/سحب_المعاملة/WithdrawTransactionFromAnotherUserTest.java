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

public class WithdrawTransactionFromAnotherUserTest extends TestBase {


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

  @Test(description = "سحب معاملة مرسلة من مستخدم آخر من سلة مرسلة الإدارة[12.2]")
  @Description("التحقق من إمكانية سحب المعاملة (داخلي/وارد) المرسلة من مستخدم آخر قبل استلامها وظهورها في معاملاتي[12.2]")
  public void verifyWithdrawTransactionFromAnotherUser() {

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

//    تسجيل الخروج
    loginPage.logoutToTheApp();

//    تسجيل الدخول بالمستخدم المستلم
    MyTransactionsPage myTransactionsPage2 = loginPage.loginToTheApp2();
    // ⃣ الانتقال إلى صندوق المعاملات المرسلة
    SentTransactionsPage sentTransactionsPage =
        myTransactionsPage.navigateToSentTransactions();

    // ⃣ الانتقال إلى تبويبة مرسلة الادارة
     sentTransactionsPage.switchToSentManagementTab();

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
