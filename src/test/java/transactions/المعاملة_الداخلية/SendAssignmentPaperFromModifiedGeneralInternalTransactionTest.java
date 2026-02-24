package transactions.المعاملة_الداخلية;

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
import pages.transactions.TransactionsCopiesPage;
import pages.transactions.TransactionsFollowupPage;


public class SendAssignmentPaperFromModifiedGeneralInternalTransactionTest extends TestBase {

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

  //======================
  @Test(description = "[2.5] ارسال ورقة الاحالة من خلال تعديل معاملة")
  @Description("من ورقة الإحالة اضافة نسخ داخلية وخارجية بمرفقات مختلفة، مع التحقق من وصول النسخ بحسب المرفقات المحددة عند الارسال [2.5]")
  public void sendAssignmentPaperInternalTransaction() {

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
    internalTransactionDraftPage.addAttachment(
        attachmentsData.getTestData("attachment1.type"),
        attachmentsData.getTestData("attachment1.location"),
        attachmentsData.getTestData("attachment1.validity")
    );

    // Save transaction
    internalTransactionDraftPage.saveInternalTransaction();
    String transactionNumber =
        internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    // Search & edit transaction
    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));

    internalTransactionDraftPage = internalTransactionDraftPage.editFirstInTransaction();

    // Send Assignment Paper
    internalTransactionDraftPage
        .goToAssignmentPaper()
        .selectOrgUnitFromAssignmentPaper(
            attachmentsData.getTestData("assignmentPaper.orgUnitName"));

    internalTransactionDraftPage = internalTransactionDraftPage.sendAssignmentPaper();

    // Switch to receiving department
    myTransactionsPage = myTransactionsPage.getSystemAdminComponent()
        .changeDepartment(
            attachmentsData.getTestData("assignmentPaper.orgUnitName"));

    // Validate copy arrival
    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab()
        .getHMComponent()
        .receivinganddistributingimages();

    myTransactionsPage.printFirstTransaction();

    TransactionsCopiesPage transactionsCopiesPage =
        myTransactionsPage.navigateToTransactionsCopies();

    String transactionCopyNumber =
        transactionsCopiesPage.getTransactionsOperationsComponent()
            .searchForTransactionWithId(
                transactionNumber,
                new TransactionsCopiesPage(driver))
            .getFirstTransactionNumber();

    // Validation
    Validations.verifyThat()
        .object(transactionCopyNumber)
        .isEqualTo(transactionNumber);
  }

}
