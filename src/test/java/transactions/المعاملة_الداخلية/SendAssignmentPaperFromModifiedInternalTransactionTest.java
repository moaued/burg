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

public class SendAssignmentPaperFromModifiedInternalTransactionTest extends TestBase {


  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }
  @AfterMethod
    public void afterTest() {
        driver.quit();
    }

  //=============================
  @Test(description = "[2.10] ارسال ورقة الاحالة من خلال تعديل معاملة داخلية")
  @Description("من التعديل على المعاملة يتم ارسال نسخ من المعاملة عبر ورقة الإحالة مع التحقق من وصول المرفقات بحسب ما تم تحديده عند الارسال[2.10]")
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

    // Add Attachment
    internalTransactionDraftPage.expandAttachmentSection();
    internalTransactionDraftPage.addAttachment(
        attachmentsData.getTestData("attachment1.type"),
        attachmentsData.getTestData("attachment1.location"),
        attachmentsData.getTestData("attachment1.validity")
    );

    // Save Transaction
    internalTransactionDraftPage.saveInternalTransaction();
    String transactionNumber =
        internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    // Search for transaction
    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));

    // Edit transaction
    internalTransactionDraftPage = internalTransactionDraftPage.editFirstInTransaction();

    // Assignment Paper
    internalTransactionDraftPage.goToAssignmentPaper()
        .selectOrgUnitFromAssignmentPaper(
            attachmentsData.getTestData("assignmentPaper.orgUnitName")
        );

    internalTransactionDraftPage = internalTransactionDraftPage.sendAssignmentPaper();

    // Validate the copy after switching department
    myTransactionsPage = myTransactionsPage.getSystemAdminComponent()
        .changeDepartment(
            attachmentsData.getTestData("assignmentPaper.orgUnitName")
        );

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
                new TransactionsCopiesPage(driver)
            )
            .getFirstTransactionNumber();

    // Validation
    Validations.verifyThat()
        .object(transactionCopyNumber)
        .isEqualTo(transactionNumber);
  }

}
