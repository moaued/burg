package unifiedNumber;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.TransactionCopiesDetailsPage;
import pages.transactions.TransactionsCopiesPage;
import pages.unifiedNumber.UnifiedNumberDetailsPage;
import pages.unifiedNumber.UnifiedNumberPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;

public class AddInternalCopiesFromUnifiedNumberTest extends TestBase {

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
  @Test(description = "[9.4] إضافة نسخ داخلية على المعاملة من شاشة الرقم الموحد")
  @Description("[9.4] التحقق من إمكانية إضافة نسخ داخلية على المعاملة من خلال ايقونة إضافة صورة وان النسخ تصل بحسب المرفقات المحددة من المستخدم عند الارسال")
  public void addOriginalTransactionToBriefcase() {
    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionDestination().expandAttachmentSection()
        .addAttachment(attachmentsData.getTestData("attachment1.type")
            , attachmentsData.getTestData("attachment1.location"),
            attachmentsData.getTestData("attachment1.validity")).saveInTransaction();

    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage();

    UnifiedNumberPage unifiedNumberPage = myTransactionsPage.getHMComponent()
        .navigateToUnifiedNumberTab().searchForTransactionWithNumber(transactionNumber);
    Validations.verifyThat()
        .object(unifiedNumberPage.confirmValueExistenceInResultsGrid(transactionNumber)).isTrue();
    UnifiedNumberDetailsPage unifiedNumberDetailsPage = unifiedNumberPage.addCopiesToTransaction()
        .selectOrgUnitForTransactionCopy(attachmentsData
            .getTestData("assignmentPaper.orgUnitName"));
    TransactionsCopiesPage transactionsCopiesPage = unifiedNumberDetailsPage.sendTransactionCopy();

    //Validate the first copy after switching to its department
    myTransactionsPage = transactionsCopiesPage.getSystemAdminComponent()
        .changeDepartment(attachmentsData.getTestData("assignmentPaper.orgUnitName"));

     transactionsCopiesPage = myTransactionsPage.navigateToTransactionsCopies();
    String transactionCopyNumber = transactionsCopiesPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();

    Validations.verifyThat().object(transactionCopyNumber).isEqualTo(transactionNumber);
    TransactionCopiesDetailsPage transactionCopiesDetailsPage = transactionsCopiesPage.goToTransactionCopyDetails();
    String transactionNumberFromCopy = transactionCopiesDetailsPage.getTransactionNumber();
    boolean isAttachmentTypePresent = transactionCopiesDetailsPage
        .confirmExistenceOfSpecificContentInTableOfCopies(attachmentsData.getTestData("attachment1.type"));

    Validations.verifyThat().object(transactionNumberFromCopy).isEqualTo(transactionNumber);
    Validations.verifyThat().object(isAttachmentTypePresent).isTrue();
  }

}
