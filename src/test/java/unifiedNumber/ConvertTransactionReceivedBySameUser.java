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

public class ConvertTransactionReceivedBySameUser extends TestBase {

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//    @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

  //======================

  @Test(description = "التحقق من إمكانية فتح المعاملة في التعديل من خلال ايقونة التحويل اذا كانت المعاملة الأصل باستلام نفس المستخدم في سلة معاملاتي[9.10]")
  @Description("التحقق من إمكانية استلام معاملة وفتحها في التعديل من خلال ايقونة التحويل اذا كانت المعاملة الاصل باستلام نفس المستخدم في سلة معاملاتي[9.10]")
  public void testConvertTransaction() {

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

    myTransactionsPage = myTransactionsPage.selectFirstTransaction().navigateToDirectReferral()
        .addNewReferral( followupData.getTestData("referral2.orgUnitNum"),
            followupData.getTestData("referral2.actionType"),
            followupData.getTestData("referral2.deliveryMethod"));
    myTransactionsPage.getSystemAdminComponent().changeDepartment(followupData.getTestData("referral2.orgUnitName"));

    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab();
        myTransactionsPage.printFirstTransaction();

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
