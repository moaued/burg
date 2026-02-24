package transactions.المعاملة_الداخلية;

import static utils.GeneralOperations.getFileSize;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

public class PrintPaperReferralInternalTransactionTest extends TestBase {


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

  //=============================
  @Test(description = "طباعة الإحالة الورقية للمعاملات الداخلية [2.9]")
  @Description("من التعديل على المعاملة يتم طباعة ورقة الإحالة الورقية[2.9]")
  public void printPaperReferralForInternalTransaction() {

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

    // Add Attachment
    internalTransactionDraftPage.expandAttachmentSection();
    int numberOfAttachmentsBefore =
        internalTransactionDraftPage.getNumberOfAttachmentsInGrid();

    internalTransactionDraftPage.addAttachment(
        attachmentsData.getTestData("attachment1.type"),
        attachmentsData.getTestData("attachment1.location"),
        attachmentsData.getTestData("attachment1.validity")
    );

    int numberOfAttachmentsAfter =
        internalTransactionDraftPage.getNumberOfAttachmentsInGrid();

    Validations.verifyThat()
        .number(numberOfAttachmentsAfter)
        .isGreaterThan(numberOfAttachmentsBefore);

    // Save Transaction
    internalTransactionDraftPage.saveInternalTransaction();
    String transactionNumber =
        internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    // Search for transaction
    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(
                transactionNumber,
                new MyTransactionsPage(driver)
            );

    // Edit Transaction
    internalTransactionDraftPage =
        internalTransactionDraftPage.editFirstInTransaction();

    // Save & Print Paper Referral
    internalTransactionDraftPage
        .saveModifiedTransaction()
        .printAssignmentPaperFromConfirmation();

    // Validate downloaded file
    double fileSize = getFileSize(directory + "/Download.pdf");

    Validations.verifyThat()
        .number(fileSize)
        .isGreaterThan(150);
  }

}
