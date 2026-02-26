package transactions.المعاملة_الداخلية;

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
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;


@Slf4j
public class PrintQrInternalTransactionTest extends TestBase {

  String directory;

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    GeneralOperations.createNewFolderWithDateTimeName("paperReferralDownloadFolderPath");
    directory = GeneralOperations.getAbsolutePath();
    openBuragAppWithCustomCapabilities(directory);
  }

/*  @AfterMethod
  public void afterTest() {
    driver.quit();
  }*/

  @Test(description = "طباعة باركود بانواعه (الكتروني + ملصق + A4) للمعاملات الداخلية[2.6]", groups = {"Smoke"})
  @Description("طباعة باركود بانواعه (الكتروني + ملصق + A4) لمعاملة داخلية[2.6]")
  public void printQrForInternalTransaction() {

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

    // Add attachment
    internalTransactionDraftPage.expandAttachmentSection();
    int numberBefore =
        internalTransactionDraftPage.getNumberOfAttachmentsInGrid();

    internalTransactionDraftPage.addAttachment(
        attachmentsData.getTestData("attachment1.type"),
        attachmentsData.getTestData("attachment1.location"),
        attachmentsData.getTestData("attachment1.validity")
    );

    int numberAfter =
        internalTransactionDraftPage.getNumberOfAttachmentsInGrid();

    Validations.verifyThat()
        .number(numberAfter)
        .isGreaterThan(numberBefore);

    // Save transaction
    internalTransactionDraftPage.saveInternalTransaction();
    String transactionNumber =
        internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    // Search transaction
    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(
                transactionNumber,
                new MyTransactionsPage(driver)
            );

    // Edit transaction

    internalTransactionDraftPage =
        internalTransactionDraftPage.editFirstInTransaction();

//       internalTransactionDraftPage.saveModifiedTransaction5();


    // Print Barcode Sticker
    internalTransactionDraftPage.printBarcodeSticker2().saveModifiedTransaction5();

//    internalTransactionDraftPage.printBarcodeSticker();

    Validations.verifyThat()
        .file(directory, "Barcode.pdf")
        .exists();

    double fileSize = getFileSize(directory + "/Barcode.pdf");
    Validations.verifyThat()
        .number(fileSize)
        .isGreaterThan(10);

    // Print QR / A4
    internalTransactionDraftPage.printQrPaperFromConfirmation();

    Validations.verifyThat()
        .number(internalTransactionDraftPage.getNumberOfBrowserWindows())
        .isGreaterThan(1);

    Validations.assertThat()
        .object(internalTransactionDraftPage.isPrintDialogueDisplayed())
        .isTrue();
  }



}
