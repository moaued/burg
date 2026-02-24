package transactions.وارد_عام;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.orgUnitTransactions.OrgUnitRecievalPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

import static utils.GeneralOperations.getFileSize;

public class PrintDeliveryStatementTest extends TestBase {


  String directory;

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    GeneralOperations.createNewFolderWithDateTimeName("deliveryStatementDownloadFolderPath");
    directory = GeneralOperations.getAbsolutePath();
    openBuragAppWithCustomCapabilities(directory);
  }

  @AfterMethod
//  public void afterTest() {
//    driver.quit();
//  }

  //=============================
//  @Test(description = "[1.11.1] حفظ معاملة وارد جهات وارسال وطباعة بيان التسليم")
//  @Description("[1.11.1] حفظ معاملة وارد جهات وارسالها من خلال ايقونة ارسال وطباعة بيان التسليم")
//  public void modifyIncomingTransactionAndSendDeliveryStatement() {
//    LoginPage loginPage = new LoginPage(driver);
//    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
//
//    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
//        .addNewIncomingTransaction().addInGeneralTransactionDestination();
//    inTransactionDraftPage.saveInTransaction();
//    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
//
//    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage()
//        .getTransactionsOperationsComponent()
//        .searchForTransactionWithId(transactionNumber, myTransactionsPage);
//    String transactionSubject = myTransactionsPage.getFirstTransactionDescription();
//    inTransactionDraftPage = myTransactionsPage.editFirstInTransaction();
//    inTransactionDraftPage.sendAndPrintDeliveryStatementForModifiedInTransaction();
//
//    OrgUnitRecievalPage orgUnitRecievalPage = inTransactionDraftPage.goBackToMyTransactionPage()
//        .getHMComponent()
//        .navigateToOrgUnitTransactionsTab();
//    orgUnitRecievalPage.getTransactionsOperationsComponent()
//        .searchForTransactionWithId(transactionNumber, new OrgUnitRecievalPage(driver));
//    String transactionNumberOnCard = orgUnitRecievalPage.getFirstTransactionNumber();
//
//    double fileSize = getFileSize(directory + "/GenerateReport.pdf");
//    //The created file size is greater than 80 KB which is the average file size
//    Validations.verifyThat().number(fileSize).isGreaterThan(80);
//    //Confirm the transaction number is the same between the transaction & the report
//    Validations.verifyThat()
//        .file(directory, "GenerateReport.pdf")
//        .exists()
//        .perform();
//    Validations.verifyThat()
//        .file(directory, "GenerateReport.pdf")
//        .content()
//        .contains(transactionNumber)
//        .perform();
//    //Confirm the transaction subject is the same between the transaction & the report
//    Validations.verifyThat()
//        .file(directory, "GenerateReport.pdf")
//        .content()
//        .contains("Description:")
//        .perform();
//    //Check for the transaction existence on the OrgUnitTransactions Page.
//  }
//
//  //=============================
  @Test(description = "[1.11.2] اضافة معاملة وارد جهات وارسال وطباعة بيان التسليم")
  @Description("[1.11.2] اضافة معاملة وارد جهات وارسال وطباعة بيان التسليم")
  public void addIncomingTransactionDestinationAndSendDeliveryStatement() {
    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionDestination();
    String transactionSubject = inTransactionDraftPage.getModifiedTransactionDescription();

    inTransactionDraftPage.expandAttachmentSection();
    int numberOfAttachmentsBefore = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
    inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
        , attachmentsData.getTestData("attachment1.location"),
        attachmentsData.getTestData("attachment1.validity"));
    int numberOfAttachmentsAfterFirstAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat().number(numberOfAttachmentsAfterFirstAttachment)
        .isGreaterThan(numberOfAttachmentsBefore);

    inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment2.type")
        , attachmentsData.getTestData("attachment2.location"),
        attachmentsData.getTestData("attachment2.validity"));
    int numberOfAttachmentsAfterSecondAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat().number(numberOfAttachmentsAfterSecondAttachment)
        .isGreaterThan(numberOfAttachmentsAfterFirstAttachment);

    inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment3.type")
        , attachmentsData.getTestData("attachment3.location"),
        attachmentsData.getTestData("attachment3.validity"));
    int numberOfAttachmentsAfterThirdAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat().number(numberOfAttachmentsAfterThirdAttachment)
        .isGreaterThan(numberOfAttachmentsAfterSecondAttachment);

    inTransactionDraftPage.addFileToTransactionLetter2(
        attachmentsData.getTestData("transactionLetter"));

    inTransactionDraftPage.saveInTransaction();
    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
    inTransactionDraftPage.printDeliveryStatementForAddedInTransaction();

    OrgUnitRecievalPage orgUnitRecievalPage = inTransactionDraftPage.goBackToMyTransactionPage()
        .getHMComponent().navigateToOrgUnitTransactionsTab();
    orgUnitRecievalPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new OrgUnitRecievalPage(driver));
    String transactionNumberOnCard = orgUnitRecievalPage.getFirstTransactionNumber();

    //This line helps not to delete thr created file after the test gets finished, no other targets
    myTransactionsPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
    double fileSize = getFileSize(directory + "/GenerateReport.pdf");
//    //The created file size is greater than 80 KB which is the average file size
    Validations.verifyThat().number(fileSize).isGreaterThan(80);
    //Confirm the transaction number is the same between the transaction & the report
    Validations.verifyThat()
        .file(directory, "GenerateReport.pdf")
        .exists()
        .perform();
    Validations.verifyThat()
        .file(directory, "GenerateReport.pdf")
        .content()
        .contains(transactionNumber)
        .perform();
    //Confirm the transaction subject is the same between the transaction & the report
    Validations.verifyThat()
        .file(directory, "GenerateReport.pdf")
        .content()
        .contains("Description:")
        .perform();
    Validations.verifyThat().object(transactionNumberOnCard).isEqualTo(transactionNumber);

  }
//
  //=============================
  @Test(description = "[1.11.3] اضافة معاملة وارد أفراد وارسال وطباعة بيان التسليم")
  @Description("[1.11.3] اضافة معاملة وارد أفراد وارسال وطباعة بيان التسليم")
  public void addIncomingTransactionIndividualAndSendDeliveryStatement() {
    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionIndividual();
    String transactionSubject = inTransactionDraftPage.getModifiedTransactionDescription();

    inTransactionDraftPage.expandAttachmentSection();
    int numberOfAttachmentsBefore = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
    inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
        , attachmentsData.getTestData("attachment1.location"),
        attachmentsData.getTestData("attachment1.validity"));
    int numberOfAttachmentsAfterFirstAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat().number(numberOfAttachmentsAfterFirstAttachment)
        .isGreaterThan(numberOfAttachmentsBefore);

    inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment2.type")
        , attachmentsData.getTestData("attachment2.location"),
        attachmentsData.getTestData("attachment2.validity"));
    int numberOfAttachmentsAfterSecondAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat().number(numberOfAttachmentsAfterSecondAttachment)
        .isGreaterThan(numberOfAttachmentsAfterFirstAttachment);

    inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment3.type")
        , attachmentsData.getTestData("attachment3.location"),
        attachmentsData.getTestData("attachment3.validity"));
    int numberOfAttachmentsAfterThirdAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat().number(numberOfAttachmentsAfterThirdAttachment)
        .isGreaterThan(numberOfAttachmentsAfterSecondAttachment);

    inTransactionDraftPage.addFileToTransactionLetter2(
        attachmentsData.getTestData("transactionLetter"));

    inTransactionDraftPage.saveInTransaction();
    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
    inTransactionDraftPage.printDeliveryStatementForAddedInTransaction();
    OrgUnitRecievalPage orgUnitRecievalPage = inTransactionDraftPage.goBackToMyTransactionPage()
        .getHMComponent()
        .navigateToOrgUnitTransactionsTab();
    orgUnitRecievalPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new OrgUnitRecievalPage(driver));
    String transactionNumberOnCard = orgUnitRecievalPage.getFirstTransactionNumber();

    //This line helps not to delete thr created file after the test gets finished, no other targets
    myTransactionsPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
    double fileSize = getFileSize(directory + "/GenerateReport.pdf");
    //The created file size is greater than 80 KB which is the average file size
    Validations.verifyThat().number(fileSize).isGreaterThan(80);
    //Confirm the transaction number is the same between the transaction & the report
    Validations.verifyThat().file(directory, "GenerateReport.pdf").content()
        .contains(transactionNumber);
    //Confirm the transaction subject is the same between the transaction & the report
    Validations.verifyThat().file(directory, "GenerateReport.pdf").content()
        .contains(transactionSubject);

    //Check for the transaction existence on the OrgUnitTransactions Page.
    Validations.verifyThat().object(transactionNumberOnCard).isEqualTo(transactionNumber);

  }
}
