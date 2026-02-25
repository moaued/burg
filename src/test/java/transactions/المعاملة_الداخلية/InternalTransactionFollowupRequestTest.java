package transactions.المعاملة_الداخلية;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.*;

public class InternalTransactionFollowupRequestTest extends TestBase {

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }


    @AfterMethod
    public void afterTest() {
        driver.quit();
    }

  //======================
  @Test(description = "إضافة طلب متابعة إلى معاملة داخلية من خلال تعديل المعاملة[2.4.1] ")
  @Description("إضافة طلب متابعة إلى معاملة داخلية من خلال تعديل المعاملة[2.4.1] ")
  public void addFollowupRequestToInternalTransaction() {

    SHAFT.TestData.JSON followupData =
        new SHAFT.TestData.JSON("internalTransactionData.json");

    // Login
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // Create Internal Transaction
    InternalTransactionDraftPage internalTransactionDraftPage =
        myTransactionsPage.getTransactionsOperationsComponent()
            .addNewOutboundInternalTransaction()
            .addNewInternalTransaction();

    // Add attachment
    internalTransactionDraftPage.expandAttachmentSection();
    internalTransactionDraftPage.addAttachment(
        followupData.getTestData("attachment1.type"),
        followupData.getTestData("attachment1.location"),
        followupData.getTestData("attachment1.validity")
    );

    // Save transaction
    internalTransactionDraftPage.saveInternalTransaction();
    String transactionNumber =
        internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    // Search for transaction
    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));

    int numberOfAttachmentsOnCard =
        myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

    // Edit transaction & add follow-up
    myTransactionsPage.editFirstInTransaction();

    myTransactionsPage =
        internalTransactionDraftPage.addTransactionFollowup(
                followupData.getTestData("followup1.orgUnitNum"))
            .saveModifiedTransaction()
            .goBackToMyTransactionPage();


    // Change department to follow-up receiver
    myTransactionsPage.getSystemAdminComponent()
        .changeDepartment(followupData.getTestData("followup1.orgUnitName"));

    // Navigate to Follow-up page
    TransactionsFollowupPage transactionsFollowupPage =
        myTransactionsPage.navigateToTransactionFollowup();

    transactionsFollowupPage =
        transactionsFollowupPage.navigateToSentFollowUpTab()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(transactionNumber, transactionsFollowupPage);

    // Validations
    String firstTransactionFollowupNumber =
        transactionsFollowupPage.getFirstTransactionNumber();

    int numberOfAttachmentsOnFollowupCard =
        transactionsFollowupPage.getNumberOfAttachmentsOnFirstCard();

    Validations.verifyThat()
        .object(firstTransactionFollowupNumber)
        .isEqualTo(transactionNumber);

    Validations.verifyThat()
        .number(numberOfAttachmentsOnFollowupCard)
        .isEqualTo(numberOfAttachmentsOnCard);
  }

  @Test(description = "اضافة طلب متابعة الى معاملة داخلية من ايقونة الاحالة المباشرة[2.4.2] ")
  @Description("اضافة طلب متابعة الى معاملة داخلية من ايقونة الاحالة المباشرة[2.4.2] ")
  public void addFollowupRequestFromInternalTransactionCard() {

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
            .addNewReferral(
                followupData.getTestData("referral2.orgUnitNum"),
                followupData.getTestData("referral2.actionType"),
                followupData.getTestData("referral2.deliveryMethod")
            );

    // Switch to follow-up department
    myTransactionsPage =
        myTransactionsPage.getSystemAdminComponent()
        .changeDepartment(followupData.getTestData("referral2.followUpUnit"));

    // =============================
    // Verify Follow-up
    // =============================
    TransactionsFollowupPage transactionsFollowupPage =
        myTransactionsPage.navigateToTransactionFollowup();

    transactionsFollowupPage =
        transactionsFollowupPage.navigateToSentFollowUpTab()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(
                transactionNumber,
                new TransactionsFollowupPage(driver)
            );

    String followupTransactionNumber =
        transactionsFollowupPage.getFirstTransactionNumber();

    int numberOfAttachmentsOnFollowupCard =
        transactionsFollowupPage.getNumberOfAttachmentsOnFirstCard();

    // =============================
    // Assertions
    // =============================
    Validations.verifyThat()
        .object(followupTransactionNumber)
        .isEqualTo(transactionNumber);

    Validations.verifyThat()
        .number(numberOfAttachmentsOnFollowupCard)
        .isEqualTo(numberOfAttachmentsOnCard);
  }

}
