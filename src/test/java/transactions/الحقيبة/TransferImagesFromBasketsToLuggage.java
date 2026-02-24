package transactions.الحقيبة;

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
import pages.transactions.TransactionCopiesDetailsPage;
import pages.transactions.TransactionsCopiesPage;

public class TransferImagesFromBasketsToLuggage extends TestBase {

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

  // =========================================================
  @Test(description = "نقل صورة نسخة معاملة من سلة الصور إلى الحقيبة[5.2.1]")
  @Description("نقل صورة نسخة معاملة داخلية من سلة الصور إلى حقيبة الصور والتعاميم[5.2.1]")
  public void transferImageFromImagesBasketToLuggage() {


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

    // Capture attachment descriptions
    String copyType1 =
        internalTransactionDraftPage.getAttachmentCopyDescription().getFirst();


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

    internalTransactionDraftPage= internalTransactionDraftPage.sendImagesAndCircularsluggage()
        .navigateToImagesAndCircularsLuggage()
    .assertTransactionExistsInLuggage(transactionNumber);


  }

  // =========================================================

 @Test(description = "نقل صورة نسخة معاملة داخلية من سلة التعاميم إلى حقيبة الصور والتعاميم [5.2.2]")
  @Description("نقل صورة نسخة معاملة داخلية من سلة التعاميم إلى حقيبة الصور والتعاميم[5.2.2]")
  public void transferImageFromCircularsBasketToLuggage() {

   SHAFT.TestData.JSON data =
       new SHAFT.TestData.JSON("internalTransactionData.json");

   // Login
   LoginPage loginPage = new LoginPage(driver);
   MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

   // Create Internal Transaction
   InternalTransactionDraftPage internalTransactionDraftPage =
       myTransactionsPage.getTransactionsOperationsComponent()
           .addNewOutboundInternalTransaction()
           .addNewInternalTransaction2();

   // Add attachments
   internalTransactionDraftPage.expandAttachmentSection();
   internalTransactionDraftPage.addAttachment(
       data.getTestData("attachment1.type"),
       data.getTestData("attachment1.location"),
       data.getTestData("attachment1.validity")
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

   // Capture attachment descriptions
   String copyType1 =
       internalTransactionDraftPage.getAttachmentCopyDescription().getFirst();


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



/* found in TransactionsNavigationPanelComponent page, please change the method "navigateToCircularsBasket" from TransactionsCopiesPage page to  CircularsBasketPage
  when the last one is created   later*/

   TransactionsCopiesPage copiesPage =
       myTransactionsPage.navigateToCircularsBasket();

   copiesPage.getTransactionsOperationsComponent()
       .searchForTransactionWithId(transactionNumber, copiesPage);

   internalTransactionDraftPage= internalTransactionDraftPage.sendImagesAndCircularsluggage()
       .navigateToImagesAndCircularsLuggage()
       .assertTransactionExistsInLuggage(transactionNumber);

  }

}