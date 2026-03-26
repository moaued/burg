package transactions.المعاملة_الداخلية;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.qameta.allure.Description;




public class AddInternalTransactionTest extends TestBase {


  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    driver.quit();
  }

//    =============================

  @Test(description ="إضافة معاملة داخلية[2.1]" , groups = {"Daily"})
  @Description("انشاء معاملة داخلية مع اضافة 3 مرفقات بصلاحيات مختلفة مع إضافة ملف على اصل خطاب المعاملة، واضافة بيانات صاحب علاقة وتحديد خيار ارسال رسالة نصية والتحقق من وصول الرسالة[2.1]")
  public void addInternalTransaction() {

    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InternalTransactionDraftPage internalTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewOutboundInternalTransaction()
        .addNewInternalTransaction().addRelevantperson().Returntobasicdata();
    String transactionSubject = internalTransactionDraftPage.getModifiedTransactionDescription();

    internalTransactionDraftPage.expandAttachmentSection();
    int numberOfAttachmentsBefore = internalTransactionDraftPage.getNumberOfAttachmentsInGrid();
    internalTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
            , attachmentsData.getTestData("attachment1.location"),
            attachmentsData.getTestData("attachment1.validity"));

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

    internalTransactionDraftPage.addFileToTransactionLetter(attachmentsData.getTestData("transactionLetter"));
        internalTransactionDraftPage.saveInTransaction();

        String transactionNumber = internalTransactionDraftPage.getTransactionNumberFromConfirmation();
        myTransactionsPage = internalTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent().searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));

        String transactionNumberOnCard = myTransactionsPage.getFirstTransactionNumber();
        String transactionSubjectOnCard = myTransactionsPage.getFirstTransactionDescription();
        int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

        Validations.assertThat().object(transactionNumberOnCard).isEqualTo(transactionNumber);
        Validations.assertThat().object(transactionSubjectOnCard).isEqualTo(transactionSubject);
        Validations.assertThat().number(numberOfAttachmentsOnCard).isEqualTo(numberOfAttachmentsAfterThirdAttachment);

  }

}