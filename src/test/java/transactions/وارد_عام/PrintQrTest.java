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
public class PrintQrTest extends TestBase {

    String directory;

    @BeforeMethod
    public void beforeTest() {
        testData = new SHAFT.TestData.JSON("appData.json");
        GeneralOperations.createNewFolderWithDateTimeName("paperReferralDownloadFolderPath");
        directory = GeneralOperations.getAbsolutePath();
        openBuragAppWithCustomCapabilities(directory);
    }

    @AfterMethod
    public void afterTest() {
        driver.quit();
    }

// -------------------------------


//  @Test(description = "طباعة باركود بانواعه (الكتروني + ملصق + A4) لكل نوع من انواع المعاملة الواردة[1.10]" , groups = {"Smoke"})
//  @Description("طباعة باركود بانواعه (الكتروني + ملصق + A4) ل لمعاملة الوردةافراد")
//  public void printQrforIndividuals(){
//    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
//    LoginPage loginPage = new LoginPage(driver);
//    MyTransactionsPage myTransactionsPage;
//    myTransactionsPage = loginPage.loginToTheApp();
//
//    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
//        .addNewIncomingTransaction().addInGeneralTransactionIndividual();
//
//    inTransactionDraftPage.expandAttachmentSection();
//    int numberOfAttachmentsBefore = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
//    inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
//        , attachmentsData.getTestData("attachment1.location") , attachmentsData.getTestData("attachment1.validity"));
//    int numberOfAttachmentsAfterFirstAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
//    Validations.verifyThat().number(numberOfAttachmentsAfterFirstAttachment).isGreaterThan(numberOfAttachmentsBefore);
//
//    inTransactionDraftPage.saveInTransaction();
//    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
//
//    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage()
//        .getTransactionsOperationsComponent().searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
//
//    inTransactionDraftPage = myTransactionsPage.editFirstInTransaction();
//    inTransactionDraftPage.saveModifiedTransaction();
//    inTransactionDraftPage.printBarcodeSticker();
//    Validations.verifyThat().file(directory, "Barcode.pdf").exists();
//    //& the file size is greater than 10KB (which is the average file size)
//    double fileSize = getFileSize(directory+"/Barcode.pdf");
//    Validations.verifyThat().number(fileSize).isGreaterThan(10);
//    inTransactionDraftPage.printQrPaperFromConfirmation();
//
//
//    //The created file size is greater than 150 KB which is the average file size
//    Validations.verifyThat().number( inTransactionDraftPage.getNumberOfBrowserWindows()).isGreaterThan(1);
//    //Confirm also the contents of the print dialogue
//    Validations.assertThat().object( inTransactionDraftPage.isPrintDialogueDisplayed()).isTrue();
//
//
//  }
}