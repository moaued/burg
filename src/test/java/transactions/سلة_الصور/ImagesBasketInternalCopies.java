package transactions.سلة_الصور;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.TransactionCopiesDetailsPage;
import pages.transactions.TransactionsCopiesPage;

public class ImagesBasketInternalCopies extends TestBase {

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//    @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

// =========================
// =========================

@Test(description = "[14.2]بعد الدخول على النسخة من ايقونة العرض، التحقق من إمكانية إضافة نسخة جديدة لادارة من خلال تبيوبة النسخ الالكترونية الداخلية مع إضافة عنوان ومرفقات و ارشفة خاصة بها، والتحقق من وصولها للإدارة المرسل له[14.2]")
@Description("بعد الدخول على النسخة من ايقونة العرض، التحقق من إمكانية إضافة نسخة جديدة لادارة من خلال تبيوبة النسخ الالكترونية الداخلية مع إضافة عنوان ومرفقات و ارشفة خاصة بها، والتحقق من وصولها للإدارة المرسل له[14.2]")
public void sendImageCopyUsingInternalCopies() {


      SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionIndividual();
//    //Add 3 Attachments
    inTransactionDraftPage.expandAttachmentSection();
    inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
        , attachmentsData.getTestData("attachment1.location") , attachmentsData.getTestData("attachment1.validity"));
    inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment2.type")
        , attachmentsData.getTestData("attachment2.location") , attachmentsData.getTestData("attachment2.validity"));
    inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment3.type")
        , attachmentsData.getTestData("attachment3.location") , attachmentsData.getTestData("attachment3.validity"));

    inTransactionDraftPage.saveInTransaction();
    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
    inTransactionDraftPage = inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver)).editFirstInTransaction();

    inTransactionDraftPage.addInternalCopies(attachmentsData
        .getTestData("copy1.orgUnitNum"), attachmentsData.getTestData("copy1.copyReason"), 1);

//
    String copyType1 = inTransactionDraftPage.getAttachmentCopyDescription().getFirst();


    //Validate the first copy after switching to its department
    myTransactionsPage = inTransactionDraftPage.saveModifiedTransaction().goBackToMyTransactionPage().getSystemAdminComponent()
        .changeDepartment(attachmentsData.getTestData("copy1.orgUnitName"));
    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
    myTransactionsPage.printFirstTransaction();
    TransactionsCopiesPage transactionsCopiesPage = myTransactionsPage.navigateToTransactionsCopies();
    String transactionCopyNumber = transactionsCopiesPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();

    Validations.verifyThat().object(transactionCopyNumber).isEqualTo(transactionNumber);

    TransactionCopiesDetailsPage transactionCopiesDetailsPage = transactionsCopiesPage.goToTransactionCopyDetails();
    String transactionNumberFromCopy1 = transactionCopiesDetailsPage.getTransactionNumber();
    boolean isAttachmentTypePresent1 = transactionCopiesDetailsPage
        .confirmExistenceOfSpecificContentInTableOfCopies(copyType1);
//
    Validations.verifyThat().object(transactionNumberFromCopy1).isEqualTo(transactionNumber);
    Validations.verifyThat().object(isAttachmentTypePresent1).isTrue();



    inTransactionDraftPage.addInternalCopies2(attachmentsData
        .getTestData("copy2.orgUnitNum"), attachmentsData.getTestData("copy2.copyReason"),attachmentsData.getTestData("transactionLetter")
    );



inTransactionDraftPage.changeDepartmentafteraddInternalCopies(attachmentsData.getTestData("copy2.orgUnitName"));
    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
    myTransactionsPage.printFirstTransaction();
        String transactionCopyNumber3 = myTransactionsPage.navigateToTransactionsCopies().getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();
    Validations.verifyThat().object(transactionCopyNumber3).isEqualTo(transactionNumber);
}

}
