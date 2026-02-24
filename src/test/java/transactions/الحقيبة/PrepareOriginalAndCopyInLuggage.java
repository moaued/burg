package transactions.الحقيبة;

import base.TestBase;
import com.shaft.driver.SHAFT;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.Luggages.ImageAndCircularLuggagePage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.TransactionsCopiesPage;
import utils.GeneralOperations;
import pages.Luggages.LuggagePage;

@Slf4j
public class PrepareOriginalAndCopyInLuggage extends TestBase {

  String directory;

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    GeneralOperations.createNewFolderWithDateTimeName("assignmentPaperDownloadFolderPath");
    directory = GeneralOperations.getAbsolutePath();
    openBuragAppWithCustomCapabilities(directory);
  }

  //
//    @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

  //=============================

  // ===============================================
  // TC 1: إعداد أصل المعاملة في سلة الحقيبة
  // ===============================================
  @Test(description = "إعداد أصل معاملة داخل سلة الحقيبة – تبويب قيد الإجراء[5.3.1]")
  @Description("إنشاء معاملة داخلية ثم إرسال الأصل للحقيبة وإعداده من تبويب قيد الإجراء[5.3.1]")
  public void prepareOriginalTransactionInLuggage() {

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

    internalTransactionDraftPage.saveInternalTransaction();
    String transactionNumber =
        internalTransactionDraftPage.getTransactionNumberFromConfirmation();


    // Search for transaction
    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
    internalTransactionDraftPage= internalTransactionDraftPage.sendluggage().assertTransactionDisplayed(transactionNumber);


    // Assert transaction exists in "Under Process"
    LuggagePage luggagePage = new LuggagePage(driver);

    luggagePage
        .searchForTransaction(transactionNumber)
        .assertTransactionExists(transactionNumber);

    // Prepare Original
    luggagePage
        .clickPrepareButton(transactionNumber)
        .selectAction()
        .setupOriginalTransaction()
        .selectViewLevel("للاطلاع")
        .saveSetup();

    // Assertion after save
    luggagePage.assertSetupSavedSuccessfully();

  }

  // ===============================================
  // TC 2: إعداد صورة المعاملة في حقيبة الصور والتعاميم
  // ==============================================
  @Test(description = "[5.3.2]إعداد صورة معاملة داخل حقيبة الصور والتعاميم – تبويب قيد الإجراء")
  @Description("إنشاء معاملة داخلية وإضافة نسخة ثم إعداد الصورة من حقيبة الصور والتعاميم[5.3.2]")
  public void prepareCopyTransactionInImageAndCircularLuggage() {


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



    // Assert transaction exists in "Under Process"
    ImageAndCircularLuggagePage imageAndCircularLuggagePage = new ImageAndCircularLuggagePage(driver);

    imageAndCircularLuggagePage
        .searchForTransaction2(transactionNumber)
        .assertTransactionExists(transactionNumber);

    // Prepare Original
    imageAndCircularLuggagePage
        .clickPrepareButton(transactionNumber)
        .selectAction()
        .setupCopyTransaction()
        .selectViewLevel()
        .saveSetup();
//        .selectViewLevel("للاطلاع")

    // Assertion after save
    imageAndCircularLuggagePage.assertSetupSavedSuccessfully();
  }


//
//    // Login
//    LoginPage loginPage = new LoginPage(driver);
//    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
//
//    // Create Internal Transaction
//    InternalTransactionDraftPage internalDraft =
//        myTransactionsPage.getTransactionsOperationsComponent()
//            .addNewOutboundInternalTransaction()
//            .addNewInternalTransaction()
//            .saveInternalTransaction();
//
//    String transactionNumber =
//        internalDraft.getTransactionNumberFromConfirmation();
//
//    // Add Internal Copy
//    internalDraft
//        .expandAttachmentSection()
//        .addInternalCopies(
//            testData.getTestData("copyOrgUnitNumber"),
//            testData.getTestData("copyReason"),
//            1)
//        .saveModifiedTransaction();
//
//    // Navigate to Image & Circular Luggage
//    ImageAndCircularLuggagePage imageLuggagePage =
//        internalDraft.goBackToMyTransactionPage()
//            .getHMComponent()
//            .navigateToImageAndCircularLuggage();
//
//    // Assert transaction exists in "Under Process"
//    imageLuggagePage
//        .goToUnderProcessTab()
//        .searchForTransaction(transactionNumber)
//        .assertTransactionExists(transactionNumber);
//
//    // Prepare Copy
//    imageLuggagePage
//        .clickPrepareButton(transactionNumber)
//        .selectTargetOrgUnits(testData.getTestData("prepareOrgUnit"))
//        .selectActionIfNeeded(testData.getTestData("prepareAction"))
//        .selectDocumentAccessLevel("للتوقيع")
//        .clickSave();
//
//    // Assertion after save
//    imageLuggagePage.assertPrepareSavedSuccessfully(transactionNumber);
//  }

}
