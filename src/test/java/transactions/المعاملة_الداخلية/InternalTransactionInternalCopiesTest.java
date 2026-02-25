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
import pages.transactions.TransactionCopiesDetailsPage;
import pages.transactions.TransactionsCopiesPage;

public class InternalTransactionInternalCopiesTest extends TestBase {


  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod
    public void afterTest() {
        driver.quit();
    }

  //======================
  @Test(description = "[2.8] اضافة نسخ داخلية الى معاملة داخلية")
  @Description("في تبويبة النسخ الالكترونية الداخلية يتم اضافة نسخ داخلية مع تحديد مرفقات مختلفة في ارسال النسخ الالكترونية الداخلية والتحقق من وصولها بحسب ما تم تحديده عند الارسال[2.8]")
  public void addInternalCopiesToInternalTransaction() {

    SHAFT.TestData.JSON data =
        new SHAFT.TestData.JSON("internalTransactionData.json");

    // Login
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // Create Internal Transaction
    InternalTransactionDraftPage internalTransactionDraftPage =
        myTransactionsPage.getTransactionsOperationsComponent()
            .addNewOutboundInternalTransaction()
            .addNewInternalTransaction();

    // Add attachments
    internalTransactionDraftPage.expandAttachmentSection();
    internalTransactionDraftPage.addAttachment(
        data.getTestData("attachment1.type"),
        data.getTestData("attachment1.location"),
        data.getTestData("attachment1.validity")
    );
    internalTransactionDraftPage.addAttachment(
        data.getTestData("attachment2.type"),
        data.getTestData("attachment2.location"),
        data.getTestData("attachment2.validity")
    );
    internalTransactionDraftPage.addAttachment(
        data.getTestData("attachment3.type"),
        data.getTestData("attachment3.location"),
        data.getTestData("attachment3.validity")
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


    // Add internal copies
    internalTransactionDraftPage.addInternalCopies(
        data.getTestData("copy1.orgUnitNum"),
        data.getTestData("copy1.copyReason"), 1
    );
    internalTransactionDraftPage.addInternalCopies(
        data.getTestData("copy2.orgUnitNum"),
        data.getTestData("copy2.copyReason"), 2
    );
    internalTransactionDraftPage.addInternalCopies(
        data.getTestData("copy3.orgUnitNum"),
        data.getTestData("copy3.copyReason"), 3
    );

    // Capture attachment descriptions
    String copyType1 =
        internalTransactionDraftPage.getAttachmentCopyDescription().getFirst();
    String copyType2 =
        internalTransactionDraftPage.getAttachmentCopyDescription().get(1);
    String copyType3 =
        internalTransactionDraftPage.getAttachmentCopyDescription().getLast();

    // Save modifications
    myTransactionsPage =
        internalTransactionDraftPage.saveModifiedTransaction()
            .goBackToMyTransactionPage();

    // =======================
    // Validate first copy
    myTransactionsPage =
        myTransactionsPage.getSystemAdminComponent()
            .changeDepartment(data.getTestData("copy1.orgUnitName"));

    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab()
        .getHMComponent()
        .receivinganddistributingimages();
    myTransactionsPage.printFirstTransaction();




    TransactionsCopiesPage copiesPage =
        myTransactionsPage.navigateToTransactionsCopies();

    copiesPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, copiesPage);

    TransactionCopiesDetailsPage detailsPage =
        copiesPage.goToTransactionCopyDetails();

    Validations.verifyThat()
        .object(detailsPage.getTransactionNumber())
        .isEqualTo(transactionNumber);

    Validations.verifyThat()
        .object(detailsPage.confirmExistenceOfSpecificContentInTableOfCopies(copyType1))
        .isTrue();

    // =======================
    // Validate second copy
    myTransactionsPage =
        copiesPage.getSystemAdminComponent()
            .changeDepartment(data.getTestData("copy2.orgUnitName"));

    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab()
        .getHMComponent()
        .receivinganddistributingimages();
    myTransactionsPage.printFirstTransaction();


    copiesPage = myTransactionsPage.navigateToTransactionsCopies();
    detailsPage = copiesPage.goToTransactionCopyDetails();

    Validations.verifyThat()
        .object(detailsPage.confirmExistenceOfSpecificContentInTableOfCopies(copyType2))
        .isTrue();

    // =======================
    // Validate third copy
    myTransactionsPage =
        copiesPage.getSystemAdminComponent()
            .changeDepartment(data.getTestData("copy3.orgUnitName"));

    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab()
        .getHMComponent()
        .receivinganddistributingimages();
    myTransactionsPage.printFirstTransaction();

    copiesPage = myTransactionsPage.navigateToTransactionsCopies();
    detailsPage = copiesPage.goToTransactionCopyDetails();

    Validations.verifyThat()
        .object(detailsPage.confirmExistenceOfSpecificContentInTableOfCopies(copyType3))
        .isTrue();
  }



}
