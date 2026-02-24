package transactions.وارد_عام;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.transactions.InTransactionDraftPage;
import pages.LoginPage;
import pages.transactions.MyTransactionsPage;

public class AddGeneralIncomingTransactionTest extends TestBase {
    @BeforeMethod
    public void beforeTest() {
        testData = new SHAFT.TestData.JSON("appData.json");
        openBuragApp();
    }
//
//    @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

////    =============================
//    @Test(description = "[1.1] اضافة وارد جهات")
//    @Description("[1.1] انشاء وارد عام من نوع جهات مع اضافة 3 مرفقات بصلاحيات مختلفة مع إضافة ملف على اصل خطاب المعاملة، واضافة بيانات صاحب علاقة وتحديد خيار ارسال رسالة نصية والتحقق من وصول الرسالة عند حفظ المعاملة")
//    public void addIncomingTransaction() {
//        SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
//        LoginPage loginPage = new LoginPage(driver);
//        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
//        InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
//                .addNewIncomingTransaction().addInGeneralTransactionDestination();
//        String transactionSubject = inTransactionDraftPage.getModifiedTransactionDescription();
//
//        inTransactionDraftPage.expandAttachmentSection();
//        int numberOfAttachmentsBefore = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
//        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
//                , attachmentsData.getTestData("attachment1.location") , attachmentsData.getTestData("attachment1.validity"));
//        int numberOfAttachmentsAfterFirstAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
//        Validations.verifyThat().number(numberOfAttachmentsAfterFirstAttachment).isGreaterThan(numberOfAttachmentsBefore);
//
//        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment2.type")
//                , attachmentsData.getTestData("attachment2.location") , attachmentsData.getTestData("attachment2.validity"));
//        int numberOfAttachmentsAfterSecondAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
//        Validations.verifyThat().number(numberOfAttachmentsAfterSecondAttachment).isGreaterThan(numberOfAttachmentsAfterFirstAttachment);
//
//        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment3.type")
//                , attachmentsData.getTestData("attachment3.location") , attachmentsData.getTestData("attachment3.validity"));
//        int numberOfAttachmentsAfterThirdAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
//        Validations.verifyThat().number(numberOfAttachmentsAfterThirdAttachment).isGreaterThan(numberOfAttachmentsAfterSecondAttachment);
//
//        inTransactionDraftPage.addFileToTransactionLetter(attachmentsData.getTestData("transactionLetter"));
//
//
//        inTransactionDraftPage.saveInTransaction();
//        String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
//        myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage()
//                .getTransactionsOperationsComponent().searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
//        String transactionNumberOnCard = myTransactionsPage.getFirstTransactionNumber();
//        String transactionSubjectOnCard = myTransactionsPage.getFirstTransactionDescription();
//        int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();
//
//        Validations.assertThat().object(transactionNumberOnCard).isEqualTo(transactionNumber);
//        Validations.assertThat().object(transactionSubjectOnCard).isEqualTo(transactionSubject);
//        Validations.assertThat().number(numberOfAttachmentsOnCard).isEqualTo(numberOfAttachmentsAfterThirdAttachment);
//    }

//    =============================
    @Test(description = "[1.2] اضافة وارد أفراد")
    @Description("[1.2] انشاء وارد عام من نوع أفراد مع اضافة 3 مرفقات بصلاحيات مختلفة مع إضافة ملف على اصل خطاب المعاملة، واضافة بيانات صاحب علاقة وتحديد خيار ارسال رسالة نصية والتحقق من وصول الرسالة عند حفظ المعاملة")
    public void addIndividualIncomingTransaction() {
        SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
            .addNewIncomingTransaction().addInGeneralTransactionIndividual();
        String transactionSubject = inTransactionDraftPage.getModifiedTransactionDescription();

        inTransactionDraftPage.expandAttachmentSection();
        int numberOfAttachmentsBefore = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
            , attachmentsData.getTestData("attachment1.location") , attachmentsData.getTestData("attachment1.validity"));
        int numberOfAttachmentsAfterFirstAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
        Validations.verifyThat().number(numberOfAttachmentsAfterFirstAttachment).isGreaterThan(numberOfAttachmentsBefore);

        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment2.type")
            , attachmentsData.getTestData("attachment2.location") , attachmentsData.getTestData("attachment2.validity"));
        int numberOfAttachmentsAfterSecondAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
        Validations.verifyThat().number(numberOfAttachmentsAfterSecondAttachment).isGreaterThan(numberOfAttachmentsAfterFirstAttachment);

        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment3.type")
            , attachmentsData.getTestData("attachment3.location") , attachmentsData.getTestData("attachment3.validity"));
        int numberOfAttachmentsAfterThirdAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
        Validations.verifyThat().number(numberOfAttachmentsAfterThirdAttachment).isGreaterThan(numberOfAttachmentsAfterSecondAttachment);

        inTransactionDraftPage.addFileToTransactionLetter(attachmentsData.getTestData("transactionLetter"));
//
//
//        inTransactionDraftPage.saveInTransaction();
//        String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
//        myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage()
//            .getTransactionsOperationsComponent().searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
//        String transactionNumberOnCard = myTransactionsPage.getFirstTransactionNumber();
//        String transactionSubjectOnCard = myTransactionsPage.getFirstTransactionDescription();
//        int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();
//
//        Validations.assertThat().object(transactionNumberOnCard).isEqualTo(transactionNumber);
//        Validations.assertThat().object(transactionSubjectOnCard).isEqualTo(transactionSubject);
//        Validations.assertThat().number(numberOfAttachmentsOnCard).isEqualTo(numberOfAttachmentsAfterThirdAttachment);
    }

}
