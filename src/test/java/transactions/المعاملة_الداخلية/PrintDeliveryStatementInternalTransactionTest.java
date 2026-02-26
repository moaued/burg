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
import pages.orgUnitTransactions.OrgUnitRecievalPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;


public class PrintDeliveryStatementInternalTransactionTest extends TestBase {

  String directory;

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    GeneralOperations.createNewFolderWithDateTimeName("deliveryStatementDownloadFolderPath");
    directory = GeneralOperations.getAbsolutePath();
    openBuragAppWithCustomCapabilities(directory);
  }

//  @AfterMethod
//  public void afterTest() {
//    driver.quit();
//  }

  //=============================
  @Test(description = "[2.11] حفظ معاملة داخلية وارسال وطباعة بيان التسليم")
  @Description("[2.11] حفظ المعاملة الداخلية وارسالها من خلال ايقونة ارسال وطباعة بيان التسليم")
  public void modifyInternalTransactionAndSendDeliveryStatement() {

    // Login
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // Create Internal Transaction
    InternalTransactionDraftPage internalTransactionDraftPage =
        myTransactionsPage.getTransactionsOperationsComponent()
            .addNewOutboundInternalTransaction()
            .addNewInternalTransaction();

    // Save transaction
    internalTransactionDraftPage.saveInternalTransaction();
    String transactionNumber =
        internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    // Search for transaction
    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(transactionNumber, myTransactionsPage);

    String transactionSubject =
        myTransactionsPage.getFirstTransactionDescription();

    // Edit transaction
    internalTransactionDraftPage =
        internalTransactionDraftPage.editFirstInTransaction();

        internalTransactionDraftPage.saveModifiedTransaction();

    // Send & Print Delivery Statement
    internalTransactionDraftPage
        .sendAndPrintDeliveryStatementForModifiedInTransaction();
//        .saveModifiedTransaction4();
//        .printDeliveryStatementForAddedInTransaction();

    // Switch to receiving department
    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage()
            .getSystemAdminComponent()
            .changeDepartment(
                testData.getTestData("internalTransaction.receivingDepartment"));

    // Navigate to Org Unit Transactions
    OrgUnitRecievalPage orgUnitRecievalPage =
        myTransactionsPage.getHMComponent()
            .navigateToOrgUnitTransactionsTab();

    orgUnitRecievalPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(
            transactionNumber, new OrgUnitRecievalPage(driver));

    String transactionNumberOnCard =
        orgUnitRecievalPage.getFirstTransactionNumber();

    // Verify PDF file
    double fileSize =
        getFileSize(directory + "/GenerateReport.pdf");

    Validations.verifyThat()
        .number(fileSize)
        .isGreaterThan(80);

    Validations.verifyThat()
        .file(directory, "GenerateReport.pdf")
        .content()
        .contains(transactionNumber);

    Validations.verifyThat()
        .file(directory, "GenerateReport.pdf")
        .content()
        .contains(transactionSubject);

    // Verify transaction exists in receiving department
    Validations.verifyThat()
        .object(transactionNumberOnCard)
        .isEqualTo(transactionNumber);

  }

}
