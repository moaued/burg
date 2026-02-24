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

public class ImagesBasketReferral extends TestBase {

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


  @Test(description = "إرسال نسخة جديدة من خلال ورقة الإحالة مع مرفقات مختلفة والتحقق من وصولها- سلة الصور [14.3]")
  @Description("بعد الدخول على النسخة من ايقونة العرض، التحقق من إمكانية ارسال نسخة جديدة لادارة أخرى من خلال ورقة الإحالة مع تحديد مرفقات مختلفة لكل نسخة واضافة ملاحظة على الإحالة، والتحقق من وصولها للإدارة المرسل لها بحسب ما تم ارساله[14.3]")
  public void sendImageCopyUsingReferralSheet() {


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


    inTransactionDraftPage.selectOrgUnittosendcopy2(attachmentsData
        .getTestData("copy3.orgUnitNum"), attachmentsData.getTestData("copy3.copyReason")
    );

    inTransactionDraftPage.changeDepartmentafteraddInternalCopies(attachmentsData.getTestData("copy3.orgUnitName"));
    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
    myTransactionsPage.printFirstTransaction();
    String transactionCopyNumber3 = myTransactionsPage.navigateToTransactionsCopies().getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();
    Validations.verifyThat().object(transactionCopyNumber3).isEqualTo(transactionNumber);


  }
}
