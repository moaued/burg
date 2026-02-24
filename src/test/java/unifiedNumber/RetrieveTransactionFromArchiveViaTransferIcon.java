package unifiedNumber;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.unifiedNumber.UnifiedNumberPage;

public class RetrieveTransactionFromArchiveViaTransferIcon extends TestBase {

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

  @Test(description = "التحقق من إمكانية إخراج المعاملة من سلة الحفظ وفتحها في التعديل عبر أيقونة التحويل[9.11]")
  @Description("التحقق من إمكانية اخراج المعاملة من سلة الحفظ و فتح المعاملة في التعديل من خلال ايقونة التحويل اذا كانت المعاملة الأصل محفوظة من نفس المستخدم في سلة الحفظ[9.11]")
  public void retrieveTransactionFromArchive() {


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

//    نقل المعاملة إلى سلة الحفظ
    myTransactionsPage.moveTransactionToArchive();

    boolean notFound =
        myTransactionsPage.isTransactionNotPresent(transactionNumber);

    Validations.verifyThat()
        .object(notFound)
        .isTrue();

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
