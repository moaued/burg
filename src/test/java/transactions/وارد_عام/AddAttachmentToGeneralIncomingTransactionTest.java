package transactions.وارد_عام;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.transactions.InTransactionDraftPage;
import pages.LoginPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

import static utils.GeneralOperations.getFileSize;

@Slf4j
public class AddAttachmentToGeneralIncomingTransactionTest extends TestBase {

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
    @Test(description = "اضافة ملحقات متعددة و طباعة ورقة الاحالة [1.5]")
    @Description("اضافة ملحقات متعددة على المعاملة (من خلال الماسح الضوئي و ايقونة الرفع) ومن ثم حفظ المعاملة وطباعة ورقة الإحالة من الخيار الظاهر في النافذة المنبثقة  [1.5]")
    public void addAttachmentToTransaction() {
        SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

        InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
            .addNewIncomingTransaction().addInGeneralTransactionIndividual();
        inTransactionDraftPage.saveInTransaction();
        String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();

        myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent().searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));

         inTransactionDraftPage = myTransactionsPage.editFirstInTransaction().goToAttachmentsTab();
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

        inTransactionDraftPage.saveModifiedTransaction().printAssignmentPaperFromConfirmation();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        inTransactionDraftPage.saveModifiedTransaction2();
        myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent().searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
        int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

        Validations.assertThat().number(numberOfAttachmentsOnCard).isEqualTo(numberOfAttachmentsAfterThirdAttachment);
        double fileSize = getFileSize(directory+"/Download.pdf");
        //The created file size is greater than 150 KB which is the average file size
        Validations.verifyThat().number(fileSize).isGreaterThan(150);

    }
}