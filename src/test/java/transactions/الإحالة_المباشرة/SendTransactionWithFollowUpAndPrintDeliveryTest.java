package transactions.الإحالة_المباشرة;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.TransactionsFollowupPage;

public class SendTransactionWithFollowUpAndPrintDeliveryTest extends TestBase {


  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//  @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

  //=============================

  @Test(description = "ارسال معاملة مع طلب متابعة وطباعة بيان التسليم[13.1]")
  @Description("تحديد عدة معاملات من سلة معاملاتي و ارسالها إلى إدارة أخرى مع طلب المتابعة عليها و طباعة بيان التسليم، والتحقق من ان كل معاملة مرسلة لها رقم بيان تسليم، والتحقق من وصول طلبات المتابعة ونسخة من المعاملة المرسلة إلى إدارة المتابعة المعرفة في الهيكل التنظيمي[13.1]")
  public void verifySendTransactionWithFollowUpAndPrintDelivery() {


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
            .addNewReferral2(
                followupData.getTestData("referral2.orgUnitNum"),
                followupData.getTestData("referral2.actionType"),
                followupData.getTestData("referral2.deliveryMethod")
            );


// حفظ التبويب الأساسي
// =============================
    String mainWindow =
        driver.getDriver().getWindowHandle();

// =============================
// التحقق من فتح تبويب جديد
// =============================
    int numberOfWindows =
        driver.browser().getWindowHandles().size();

    Validations.verifyThat()
        .number(numberOfWindows)
        .isGreaterThan(1)
        .withCustomReportMessage(
            "لم يتم فتح تبويب بيان التسليم"
        )
        .perform();

// =============================
// الانتقال إلى تبويب بيان التسليم
// =============================
    for (String windowHandle :
        driver.browser().getWindowHandles()) {

      if (!windowHandle.equals(mainWindow)) {

        driver.browser().switchToWindow(windowHandle);

        break;
      }
    }

// =============================
// التحقق من أن صفحة بيان التسليم مفتوحة
// =============================
    Validations.verifyThat()
        .object(driver.browser().getCurrentURL())
        .isNotNull()
        .perform();

// =============================
// الرجوع للتبويب الأساسي
// =============================
    driver.browser().switchToWindow(mainWindow);


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
