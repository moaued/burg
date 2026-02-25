package transactions.المعاملة_الداخلية;



import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

import static utils.GeneralOperations.getFileSize;






@Slf4j
public class AddAttachmentToInternalTransactionTest extends TestBase {

  String directory;

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    GeneralOperations.createNewFolderWithDateTimeName("assignmentPaperDownloadFolderPath");
    directory = GeneralOperations.getAbsolutePath();
    openBuragAppWithCustomCapabilities(directory);
  }

  //
//    @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

  //=============================
  @Test(description = "اضافة ملحقات متعددة و طباعة ورقة الاحالة [2.3]")
  @Description("اضافة ملحقات متعددة على المعاملة (من خلال الماسح الضوئي و ايقونة الرفع) ومن ثم حفظ المعاملة وطباعة ورقة الإحالة من الخيار الظاهر في النافذة المنبثقة[2.3]")
  public void addAttachmentToInternalTransaction() {

    SHAFT.TestData.JSON attachmentsData =
        new SHAFT.TestData.JSON("internalTransactionData.json");

    // Login
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();


    // Create Internal Transaction
    InternalTransactionDraftPage internalTransactionDraftPage =
        myTransactionsPage.getTransactionsOperationsComponent()
            .addNewOutboundInternalTransaction()
            .addNewInternalTransaction();

    internalTransactionDraftPage.saveInternalTransaction();
    String transactionNumber =
        internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    // Search for transaction
    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));

    // Edit transaction & go to attachments
    internalTransactionDraftPage = internalTransactionDraftPage.editFirstInTransaction().goToAttachmentsTab();
    int numberOfAttachmentsBefore = internalTransactionDraftPage.getNumberOfAttachmentsInGrid();


    internalTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
        , attachmentsData.getTestData("attachment1.location") , attachmentsData.getTestData("attachment1.validity"));
    int numberOfAttachmentsAfterFirstAttachment = internalTransactionDraftPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat().number(numberOfAttachmentsAfterFirstAttachment).isGreaterThan(numberOfAttachmentsBefore);

    internalTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment2.type")
        , attachmentsData.getTestData("attachment2.location") , attachmentsData.getTestData("attachment2.validity"));
    int numberOfAttachmentsAfterSecondAttachment = internalTransactionDraftPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat().number(numberOfAttachmentsAfterSecondAttachment).isGreaterThan(numberOfAttachmentsAfterFirstAttachment);

    internalTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment3.type")
        , attachmentsData.getTestData("attachment3.location") , attachmentsData.getTestData("attachment3.validity"));
    int numberOfAttachmentsAfterThirdAttachment = internalTransactionDraftPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat().number(numberOfAttachmentsAfterThirdAttachment).isGreaterThan(numberOfAttachmentsAfterSecondAttachment);

    internalTransactionDraftPage.saveModifiedTransaction().printAssignmentPaperFromConfirmation();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    internalTransactionDraftPage.addFileToTransactionLetter(attachmentsData.getTestData("transactionLetter"));

    internalTransactionDraftPage.saveModifiedTransaction3();
    myTransactionsPage = internalTransactionDraftPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent().searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
    int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

    Validations.assertThat().number(numberOfAttachmentsOnCard).isEqualTo(numberOfAttachmentsAfterThirdAttachment);
    double fileSize = getFileSize(directory+"/Download.pdf");
    //The created file size is greater than 150 KB which is the average file size
    Validations.verifyThat().number(fileSize).isGreaterThan(150);
  }

}
