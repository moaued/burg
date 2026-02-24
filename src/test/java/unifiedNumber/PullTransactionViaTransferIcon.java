package unifiedNumber;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.unifiedNumber.UnifiedNumberPage;

public class PullTransactionViaTransferIcon extends TestBase {

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//  @AfterMethod
//  public void afterTest() {
//    driver.quit();
//  }

  @Test(description = "التحقق من إمكانية سحب المعاملة وفتحها في التعديل من خلال ايقونة التحويل اذا كانت المعاملة الاصل باستلام مستخدم آخر في نفس الادارة[9.9]")
  @Description("التحقق من إمكانية سحب المعاملة وفتحها في التعديل من خلال ايقونة التحويل اذا كانت المعاملة الاصل باستلام مستخدم آخر في نفس الادارة[9.9]")
  public void pullTransactionUsingTransferIcon() {
//     تسجيل الدخول بالمستخدم المنشئ

    SHAFT.TestData.JSON followupData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionDestination();
    inTransactionDraftPage.expandAttachmentSection();
    inTransactionDraftPage.addAttachment(followupData.getTestData("attachment2.type")
        , followupData.getTestData("attachment2.location"), followupData.getTestData("attachment2.validity"));

    inTransactionDraftPage.saveInTransaction();
    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
    int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

//    تسجيل الخروج
    loginPage.logoutToTheApp();

//    تسجيل الدخول بالمستخدم المستلم
    MyTransactionsPage myTransactionsPage2 = loginPage.loginToTheApp2();

// الذهاب الى الرقم الموحد
    UnifiedNumberPage unifiedNumberPage = myTransactionsPage.getHMComponent()
        .navigateToUnifiedNumberTab().searchForTransactionWithNumber(transactionNumber);
    Validations.verifyThat()
        .object(unifiedNumberPage.confirmValueExistenceInResultsGrid(transactionNumber)).isTrue();

    // التعديل من ايقونة التحويل
    InTransactionDraftPage InTransactionDraftPage =
        unifiedNumberPage.clickTransferIcon()
            .saveModifiedTransaction();

    String newTransferNumber =
        InTransactionDraftPage.getTransactionNumberFromConfirmation();

    // التحقق في معاملاتي (الوارد الجديد يظهر في معاملاتي)
    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));


    boolean isCreated =
        myTransactionsPage
            .isTransactionPresent(newTransferNumber);
    Validations.verifyThat().object(isCreated).isTrue();


  }

}
