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
import pages.transactions.MyTransactionsPage;
import pages.transactions.OutTransactionDraftPage;
import pages.transactions.OutTransactionsPage;
import pages.transactions.SentTransactionsPage;

public class WithdrawAndEditOutboundTransactionTest extends TestBase {

  private String transactionNumber;
  private String editedSubject;

  @BeforeMethod
  public void setUp() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod
  public void tearDown() {
    driver.quit();
  }

  @Test(description = "سحب معاملة صادرة من مستخدم آخر وتعديلها[12.4]")
  @Description("إنشاء معاملة بواسطة user1 ثم سحبها بواسطة user2 وتعديلها والتحقق من التعديل [12.4]")
  public void shouldWithdrawAndEditOutboundTransactionFromAnotherUser() {

    // ═════════════════════════════════════════════════════
    // 1️⃣ Login user1 + Create Transaction
    // ═════════════════════════════════════════════════════
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    OutTransactionDraftPage draftPage = myTransactionsPage
        .getTransactionsOperationsComponent()
        .addNewGeneralTransaction();

    draftPage.addGeneralTransaction();

    transactionNumber = draftPage.getTransactionNumberFromConfirmation();

    // تأكد إن الرقم مش فاضي
    Validations.assertThat()
        .object(transactionNumber)
        .isNotNull();

    // ═════════════════════════════════════════════════════
    // 2️⃣ Logout user1
    // ═════════════════════════════════════════════════════
    loginPage.logoutToTheApp();

    // ═════════════════════════════════════════════════════
    // 3️⃣ Login user2
    // ═════════════════════════════════════════════════════
    loginPage = new LoginPage(driver);
    MyTransactionsPage user2Home = loginPage.loginToTheApp2();

    // ═════════════════════════════════════════════════════
    // 4️⃣ Navigate to Admin Actions
    // ═════════════════════════════════════════════════════
    AdminActionsPage adminActionsPage = user2Home.navigateToAdminActions();

    // ═════════════════════════════════════════════════════
    // 5️⃣ Search for Transaction
    // ═════════════════════════════════════════════════════
    adminActionsPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, adminActionsPage);

    // ═════════════════════════════════════════════════════
    // 6️⃣ Withdraw + Open for Edit
    // ═════════════════════════════════════════════════════
    OutTransactionDraftPage editPage =
        adminActionsPage.withdrawAndEditTransaction();

    String draftNumber = editPage.getOutTransactionNumber();

    Validations.assertThat()
        .object(draftNumber.trim())
        .isEqualTo(transactionNumber.trim());

    // ═════════════════════════════════════════════════════
    // 7️⃣ Modify Subject + Save
    // ═════════════════════════════════════════════════════
    editPage.modifyTransactionSubject();
    editedSubject = editPage.getTransactionSubject();

    editPage.saveModifiedTransaction();

    // ═════════════════════════════════════════════════════
    // 8️⃣ Navigate to Outgoing Transactions
    // ═════════════════════════════════════════════════════

     editPage
        .navigateToTransactionsPage();  // رجوع للهوم
    myTransactionsPage = myTransactionsPage.navigateToMyTransactiontest();

    myTransactionsPage.navigateToMyTransaction();

    OutTransactionsPage outPage = myTransactionsPage.navigateToOutTransactions();
    outPage.navigateToExportedTransactions();


    // ═════════════════════════════════════════════════════
    // 9️⃣ Search & Validate
    // ═════════════════════════════════════════════════════
    outPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(draftNumber, outPage);

    String actualNumber = outPage.getFirstTransactionNumber();
    String actualSubject = outPage.getFirstTransactionDescription();

    Validations.assertThat()
        .object(actualNumber.trim())
        .isEqualTo(draftNumber.trim());

    Validations.assertThat()
        .object(actualSubject)
        .isEqualTo(editedSubject);
  }

}
