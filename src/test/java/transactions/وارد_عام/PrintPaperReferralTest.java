package transactions.وارد_عام;

import static utils.GeneralOperations.getFileSize;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

@Slf4j
public class PrintPaperReferralTest extends TestBase {

    String directory;

    @BeforeMethod
    public void beforeTest() {
        testData = new SHAFT.TestData.JSON("appData.json");
        GeneralOperations.createNewFolderWithDateTimeName("paperReferralDownloadFolderPath");
        directory = GeneralOperations.getAbsolutePath();
        openBuragAppWithCustomCapabilities(directory);
    }
//
//    @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

//    //=============================
//    @Test(description = "طباعة الإحالة الورقية [1.8]")
//    @Description("التحقق من إمكانية طباعة الإحالة الورقية من التعديل على المعاملة واختيار ايقونة الإحالة الورقية  [1.8]")
//    public void addAttachmentToTransaction() {
//        SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
//        LoginPage loginPage = new LoginPage(driver);
//        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
//
//        InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
//            .addNewIncomingTransaction().addInGeneralTransactionIndividual();
//
//        inTransactionDraftPage.expandAttachmentSection();
//        int numberOfAttachmentsBefore = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
//        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
//            , attachmentsData.getTestData("attachment1.location") , attachmentsData.getTestData("attachment1.validity"));
//        int numberOfAttachmentsAfterFirstAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
//        Validations.verifyThat().number(numberOfAttachmentsAfterFirstAttachment).isGreaterThan(numberOfAttachmentsBefore);
//
//        inTransactionDraftPage.saveInTransaction();
//        String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
//
//        myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage()
//            .getTransactionsOperationsComponent().searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
//
//        inTransactionDraftPage = myTransactionsPage.editFirstInTransaction();
//
//        inTransactionDraftPage.saveModifiedTransaction().printAssignmentPaperFromConfirmation();
//
//        double fileSize = getFileSize(directory+"/Download.pdf");
//        //The created file size is greater than 150 KB which is the average file size
//        Validations.verifyThat().number(fileSize).isGreaterThan(150);
//
//    }
}