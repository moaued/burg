package transactions.سحب_المعاملة;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.AdminActionsPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.OutTransactionDraftPage;

public class WithdrawTransactionAndReassignToAnotherUserTest extends TestBase {

  private String transactionNumber;
  private String editedSubject;

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

  @Test(description ="تحويل المعاملة المستلمة لدى مستخدم آخر في نفس الإدارة إلى مستخدم آخر داخل نفس الإدارة[12.3.1]" )
  @Description("التحقق من إمكانية سحب وتعديل معاملة داخلية ثم إعادة إحالتها إلى مستخدم آخر داخل نفس الإدارة وظهورها في معاملاتي[12.3.1]")
  public void verifyWithdrawTransactionAndReassignToAnotherUser() {

    // Test Data
    SHAFT.TestData.JSON followupData =
        new SHAFT.TestData.JSON("internalTransactionData.json");

    // Login
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // =============================
    // Create Internal Transaction
    // =============================
    InternalTransactionDraftPage internalTransactionDraftPage =
        myTransactionsPage.getTransactionsOperationsComponent()
            .addNewOutboundInternalTransaction()
            .addNewInternalTransaction();

    internalTransactionDraftPage.expandAttachmentSection();
    internalTransactionDraftPage.addAttachment(
        followupData.getTestData("attachment1.type"),
        followupData.getTestData("attachment1.location"),
        followupData.getTestData("attachment1.validity")
    );

    internalTransactionDraftPage.saveInternalTransaction();
    String transactionNumber =
        internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    // =============================
    // Search for transaction
    // =============================
    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));

    int numberOfAttachmentsOnCard =
        myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

    // =============================
    // Add Follow-up via Direct Referral
    // =============================
    myTransactionsPage =
        myTransactionsPage.selectFirstTransaction()
            .navigateToDirectReferral()
            .addNewReferral3(
                followupData.getTestData("referral1.orgUnitNum"),
                followupData.getTestData("referral1.employeeName"),
                followupData.getTestData("referral1.actionType"),
                followupData.getTestData("referral1.deliveryMethod")
            );
//    تسجيل الخروج
    loginPage.logoutToTheApp();

//    تسجيل الدخول بالمستخدم المستلم
    MyTransactionsPage user2Home = loginPage.loginToTheApp2();


    // Switch to follow-up department
    myTransactionsPage =
        myTransactionsPage.getSystemAdminComponent()
            .changeDepartment(followupData.getTestData("referral1.orgUnitName"));
    // 4️⃣ Navigate to Admin Actions
    AdminActionsPage adminActionsPage = user2Home.navigateToAdminActions();

    // 5️⃣ Search for Transaction
    adminActionsPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, adminActionsPage);



    internalTransactionDraftPage = myTransactionsPage.editFirstInTransaction4().modifyInTransactionSubject3()
        .saveInternalTransaction();
    String transactionNumber1 =
        internalTransactionDraftPage.getTransactionNumberFromConfirmation();
//    String transactionSubject = internalTransactionDraftPage.getModifiedTransactionDescription();

    myTransactionsPage = internalTransactionDraftPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent().searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));

    String transactionNumberOnCard = myTransactionsPage.getFirstTransactionNumber();
    String transactionSubjectOnCard = myTransactionsPage.getFirstTransactionDescription();

    Validations.assertThat().object(transactionNumberOnCard).equals(transactionNumber);




/*

    // Withdraw & Edit
    // =============================
    InTransactionDraftPage editPage =
        adminActionsPage.clickWithdrawAndEdit();

    // =============================
    // Modify Transaction
    // =============================
    editPage.modifyTransactionSubject();

    // =============================
    // Save Transaction
    // =============================
    editPage.saveInTransaction();

    // =============================
    // Back to My Transactions
    // =============================
    myTransactionsPage =
        editPage.goBackToMyTransactionPage();

*/






   /* // 6️⃣ Withdraw + Open for Edit
    OutTransactionDraftPage editPage =
        adminActionsPage.withdrawAndEditTransaction();

    String draftNumber = editPage.getOutTransactionNumber();

    Validations.assertThat()
        .object(draftNumber.trim())
        .isEqualTo(transactionNumber.trim());

    // 7️⃣ Modify Subject + Save
    editPage.modifyTransactionSubject();
    editedSubject = editPage.getTransactionSubject();

    editPage.saveModifiedTransaction();

    // 8️⃣ Navigate to Outgoing Transactions

    editPage
        .navigateToTransactionsPage();  // رجوع للهوم
    myTransactionsPage = myTransactionsPage.navigateToMyTransactiontest();
*/


//    myTransactionsPage.navigateToMyTransaction();

//     Search & Validate
//    String actualNumber = outPage.getFirstTransactionNumber();
//    String actualSubject = outPage.getFirstTransactionDescription();
//
//    Validations.assertThat()
//        .object(actualNumber.trim())
//        .isEqualTo(draftNumber.trim());
//
//    Validations.assertThat()
//        .object(actualSubject)
//        .isEqualTo(editedSubject);





  }



}
