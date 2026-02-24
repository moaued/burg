package transactions.سلة_التعاميم;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.TransactionCopiesDetailsPage;
import pages.transactions.TransactionsCopiesPage;

public class CircularsBasket extends TestBase {


  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//    @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

// =========================
// =========================
  @Test(description = "التحقق من ان النسخ الواردة للإدارة ومرفقاتها مطابقة لما تم ارسالة من قبل المستخدم من ايقونة المرفقات ومن تبويبة محلقات اصل المعاملة بعد الدخول على النسخة من خلال ايقونة العرض[15.1]")
  @Description("التحقق من ان النسخ الواردة للإدارة ومرفقاتها مطابقة لما تم ارسالة من قبل المستخدم من ايقونة المرفقات ومن تبويبة محلقات اصل المعاملة بعد الدخول على النسخة من خلال ايقونة العرض - سلة التعاميم[15.1]")
  public void verifyCircularCopiesAttachments() {

    // Test Data
    SHAFT.TestData.JSON attachmentsData =
        new SHAFT.TestData.JSON("inTransactionDraftData.json");

    // Login
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage =
        loginPage.loginToTheApp();

    // Create Incoming Transaction
    InTransactionDraftPage inTransactionDraftPage =
        myTransactionsPage.getTransactionsOperationsComponent()
            .addNewIncomingTransaction()
            .addInGeneralTransactionIndividual2();

    // Add Attachments
    inTransactionDraftPage.expandAttachmentSection();

    inTransactionDraftPage.addAttachment(
        attachmentsData.getTestData("attachment1.type"),
        attachmentsData.getTestData("attachment1.location"),
        attachmentsData.getTestData("attachment1.validity")
    );

    inTransactionDraftPage.addAttachment(
        attachmentsData.getTestData("attachment2.type"),
        attachmentsData.getTestData("attachment2.location"),
        attachmentsData.getTestData("attachment2.validity")
    );

    inTransactionDraftPage.addAttachment(
        attachmentsData.getTestData("attachment3.type"),
        attachmentsData.getTestData("attachment3.location"),
        attachmentsData.getTestData("attachment3.validity")
    );

    // Save Transaction
    inTransactionDraftPage.saveInTransaction();
    String transactionNumber =
        inTransactionDraftPage.getTransactionNumberFromConfirmation();

    // Edit Transaction & Add Copies
    inTransactionDraftPage =
        inTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(
                transactionNumber,
                new MyTransactionsPage(driver)
            )
            .editFirstInTransaction();

    inTransactionDraftPage.addInternalCopies(
        attachmentsData.getTestData("copy1.orgUnitNum"),
        attachmentsData.getTestData("copy1.copyReason"),
        1
    );

    inTransactionDraftPage.addInternalCopies(
        attachmentsData.getTestData("copy2.orgUnitNum"),
        attachmentsData.getTestData("copy2.copyReason"),
        2
    );

    inTransactionDraftPage.addInternalCopies(
        attachmentsData.getTestData("copy3.orgUnitNum"),
        attachmentsData.getTestData("copy3.copyReason"),
        3
    );

    // Capture Attachment Descriptions
    String copyType1 =
        inTransactionDraftPage.getAttachmentCopyDescription().getFirst();
    String copyType2 =
        inTransactionDraftPage.getAttachmentCopyDescription().get(1);
    String copyType3 =
        inTransactionDraftPage.getAttachmentCopyDescription().getLast();

    // Save modifications
    myTransactionsPage =
        inTransactionDraftPage.saveModifiedTransaction()
            .goBackToMyTransactionPage();

    // Validate First Circular Copy
    validateCircularCopy(
        myTransactionsPage,
        attachmentsData.getTestData("copy1.orgUnitName"),
        transactionNumber,
        copyType1
    );

//    // Validate Second Circular Copy
//    validateCircularCopy(
//        myTransactionsPage,
//        attachmentsData.getTestData("copy2.orgUnitName"),
//        transactionNumber,
//        copyType2
//    );
//
//    // =====================================================
//    // Validate Third Circular Copy
//    // =====================================================
//    validateCircularCopy(
//        myTransactionsPage,
//        attachmentsData.getTestData("copy3.orgUnitName"),
//        transactionNumber,
//        copyType3
//    );
  }

  // Helper Method – Validate Circular Copy
  private void validateCircularCopy(
      MyTransactionsPage myTransactionsPage,
      String orgUnitName,
      String transactionNumber,
      String expectedAttachmentType
  ) {

    // Switch Department
    myTransactionsPage =
        myTransactionsPage.getSystemAdminComponent()
            .changeDepartment(orgUnitName);



    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab()
        .getHMComponent()
        .receivinganddistributingimages();
    myTransactionsPage.printFirstTransaction();

// Navigate to Circulars Basket
    TransactionsCopiesPage transactionsCopiesPage =
        myTransactionsPage.navigateToCircularsBasket();

    // Search & Open Circular
//    copiesPage.getTransactionsOperationsComponent()
//        .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();



    String transactionCopyNumber = transactionsCopiesPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();


    Validations.verifyThat().object(transactionCopyNumber).isEqualTo(transactionNumber);

    TransactionCopiesDetailsPage transactionCopiesDetailsPage = transactionsCopiesPage.goToTransactionCopyDetails();
    String transactionNumberFromCopy1 = transactionCopiesDetailsPage.getTransactionNumber();
    boolean isAttachmentTypePresent1 = transactionCopiesDetailsPage
        .confirmExistenceOfSpecificContentInTableOfCopies(expectedAttachmentType);
//
    Validations.verifyThat().object(transactionNumberFromCopy1).isEqualTo(transactionNumber);
    Validations.verifyThat().object(isAttachmentTypePresent1).isTrue();

//        .searchForTransactionWithId(transactionNumber, copiesPage);

//
//    // Search & Open Circular
//    CircularDetailsPage circularDetailsPage =
//        circularsBasketPage
//            .searchForTransaction(transactionNumber)
//            .openCircularDetails();
//
//    // Assertions
//    circularDetailsPage.assertTransactionNumber(transactionNumber);
//    circularDetailsPage.assertAttachmentExists(expectedAttachmentType);

  }

}
