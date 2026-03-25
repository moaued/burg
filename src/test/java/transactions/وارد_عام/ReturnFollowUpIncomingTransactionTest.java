package transactions.وارد_عام;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.TransactionsFollowupPage;

public class ReturnFollowUpIncomingTransactionTest extends TestBase {

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

  @Test(description = "التحقق من إعادة المعاملة الوارد من الإدارة المطلوب المتابعة عليها إلى الإدارة الطالبة للمتابعة[1.12]")
  @Description("إعادة المعاملة من الإدارة المطلوب المتابعة عليها الى الادارة الطالبة للمتابعة مع التحقق من ظهور بطاقة المعاملة في تبويبة المتابعات المعادة في سلة المتابعة[1.12]")
  public void verifyReturnFollowUpForIncomingTransaction() {


    // Test Data
    SHAFT.TestData.JSON followupData = new SHAFT.TestData.JSON("inTransactionDraftData.json");

//    SHAFT.TestData.JSON followupData =
//        new SHAFT.TestData.JSON("internalTransactionData.json");

    // Login
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    // Create Incoming Transaction
    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionDestination();
    inTransactionDraftPage.expandAttachmentSection();
    inTransactionDraftPage.addAttachment(
          followupData.getTestData("attachment2.type")
        , followupData.getTestData("attachment2.location")
        , followupData.getTestData("attachment2.validity"));

    inTransactionDraftPage.saveInTransaction();
    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
    int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

    // Add Follow-up via Direct Referral
    myTransactionsPage = myTransactionsPage.selectFirstTransaction().navigateToDirectReferral()
        .addNewReferral(
            followupData.getTestData("referral2.orgUnitNum"),
            followupData.getTestData("referral2.actionType"),
            followupData.getTestData("referral2.deliveryMethod"));

    // 5️⃣ تغيير الإدارة إلى الإدارة المطلوب المتابعة عليها
    myTransactionsPage =
        myTransactionsPage.getSystemAdminComponent()
            .changeDepartment(
                followupData.getTestData("referral2.orgUnitName")
            );

    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab();

    // رفض المعاملة
    myTransactionsPage .rejectFirstTransaction(
        followupData.getTestData("referral2.rejectReason")
    );


    // Switch to follow-up department
    myTransactionsPage =
        myTransactionsPage.getSystemAdminComponent()
            .changeDepartment(followupData.getTestData("referral2.followUpUnit"));

    // Verify Follow-up
    TransactionsFollowupPage transactionsFollowupPage =
        myTransactionsPage.navigateToTransactionFollowup();

//    Switch to returned follow-up tab
    transactionsFollowupPage =
        transactionsFollowupPage.navigateToReturnedFollowUpsTab()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(
                transactionNumber,
                new TransactionsFollowupPage(driver)
            );

    String followupTransactionNumber =
        transactionsFollowupPage.getFirstTransactionNumber();
    // Assertions
    Validations.verifyThat()
        .object(followupTransactionNumber)
        .isEqualTo(transactionNumber);
    
  }


  }
