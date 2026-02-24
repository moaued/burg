package transactions.مسودة_صادر;

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
import pages.transactions.MyTransactionsPage;
import pages.transactions.OutgoingDraftTransactionPage;
import utils.GeneralOperations;

@Slf4j
public class PrintQrForOutgoingDraftTest extends TestBase {

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

  // ===============================

  @Test(description = "طباعة باركود بانواعه (الكتروني + ملصق + A4) لمعاملة مسودة صادر[4.5]", groups = {"Smoke"})
  @Description("طباعة باركود بانواعه لمعاملة مسودة صادر مع التحقق من ملفات الطباعة[4.5]")
  public void printQrForOutgoingDraft() {

    SHAFT.TestData.JSON attachmentsData =
        new SHAFT.TestData.JSON("outTransactionDraftData.json");

    // Login
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // Create Outgoing Draft Transaction
    OutgoingDraftTransactionPage OutgoingDraftTransactionPage =
        myTransactionsPage.getTransactionsOperationsComponent()
            .addNewOutgoingDraftransaction()
            .Returntobasicdata();

    // Add Attachment
    OutgoingDraftTransactionPage.expandAttachmentSection();
    int attachmentsBefore = OutgoingDraftTransactionPage.getNumberOfAttachmentsInGrid();

    OutgoingDraftTransactionPage.addAttachment(
        attachmentsData.getTestData("attachment1.type"),
        attachmentsData.getTestData("attachment1.location"),
        attachmentsData.getTestData("attachment1.validity")
    );

    int attachmentsAfter = OutgoingDraftTransactionPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat()
        .number(attachmentsAfter)
        .isGreaterThan(attachmentsBefore);

    // Save Transaction
    OutgoingDraftTransactionPage.saveOutgoingDraftTransaction();
    String transactionNumber =
        OutgoingDraftTransactionPage.getTransactionNumberFromConfirmation();

    // Search & Edit
    myTransactionsPage = OutgoingDraftTransactionPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));

    OutgoingDraftTransactionPage = myTransactionsPage.editFirstInTransaction3();

    // Print Barcode Sticker
    OutgoingDraftTransactionPage.saveModifiedTransaction();
    OutgoingDraftTransactionPage.printBarcodeSticker();

    Validations.verifyThat()
        .file(directory, "Barcode.pdf")
        .exists();

    double fileSize = getFileSize(directory + "/Barcode.pdf");
    Validations.verifyThat()
        .number(fileSize)
        .isGreaterThan(10);

    // Print QR / A4
    OutgoingDraftTransactionPage.printQrPaperFromConfirmation();

    Validations.verifyThat()
        .number(OutgoingDraftTransactionPage.getNumberOfBrowserWindows())
        .isGreaterThan(1);

    Validations.assertThat()
        .object(OutgoingDraftTransactionPage.isPrintDialogueDisplayed())
        .isTrue();
  }

}
