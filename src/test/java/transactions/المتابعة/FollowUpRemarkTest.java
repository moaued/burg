package transactions.المتابعة;

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
import pages.transactions.TransactionsFollowupPage;

public class FollowUpRemarkTest extends TestBase {

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//  @AfterMethod
//  public void afterTest() {
//    driver.quit();
//  }
  //=============================
//
//  @Test(description = "[8.2] اضافة ملاحظة مع مرفق على المتابعة")
//  @Description("[8.2] اضافة ملاحظة على المتابعة مع اضافة مرفق")
//  public void addRemarkAndAttachmentToFollowUpRequest() {
//    SHAFT.TestData.JSON followupData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
//    LoginPage loginPage = new LoginPage(driver);
//    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
//    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
//        .addNewIncomingTransaction().addInGeneralTransactionDestination();
//    //Add 1 Attachments
//    inTransactionDraftPage.expandAttachmentSection();
//    inTransactionDraftPage.addAttachment(followupData.getTestData("attachment1.type")
//        , followupData.getTestData("attachment1.location"),
//        followupData.getTestData("attachment1.validity"));
////
//    inTransactionDraftPage.saveInTransaction();
//    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
//
//    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage()
//        .getTransactionsOperationsComponent()
//        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
//    int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();
//    inTransactionDraftPage = myTransactionsPage.editFirstInTransaction();
//
//    inTransactionDraftPage.addTransactionFollowup(followupData.getTestData("followup.orgUnitNum"));
//    String transactionFollowupDate = inTransactionDraftPage.getFollowupHijriDate();
//
//    myTransactionsPage = inTransactionDraftPage.saveModifiedTransaction().goBackToMyTransactionPage();
//
//    myTransactionsPage.getSystemAdminComponent()
//        .changeDepartment(followupData.getTestData("followup.orgUnitName"));
//    myTransactionsPage.getHMComponent()
//        .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
//    myTransactionsPage.printFirstTransaction();
//
//    TransactionsFollowupPage transactionsFollowupPage = myTransactionsPage.navigateToTransactionFollowup();
//    transactionsFollowupPage = transactionsFollowupPage.navigateToSentFollowUpTab()
//        .getTransactionsOperationsComponent()
//        .searchForTransactionWithId(transactionNumber, new TransactionsFollowupPage(driver));
////
//    String firstTransactionFollowupNumberOnCard = transactionsFollowupPage.getFirstTransactionNumber();
//    String dateOnTheFirstTransactionCard = transactionsFollowupPage.getFirstTransactionDate();
//    int numberOfAttachmentsOnFollowupCard = transactionsFollowupPage.getNumberOfAttachmentsOnFirstCard();
//
//    Validations.verifyThat().object(firstTransactionFollowupNumberOnCard)
//        .isEqualTo(transactionNumber);
//    Validations.verifyThat().number(numberOfAttachmentsOnFollowupCard)
//        .isEqualTo(numberOfAttachmentsOnCard);
//    Validations.verifyThat().object(dateOnTheFirstTransactionCard)
//        .contains(transactionFollowupDate);
//    transactionsFollowupPage.addRemarkToFirstFollowupCard(followupData.getTestData("followupRemarkAttachmentLoc"));
//
//    firstTransactionFollowupNumberOnCard = transactionsFollowupPage.getFirstTransactionNumber();
//    Validations.verifyThat().object(firstTransactionFollowupNumberOnCard)
//        .isEqualTo(transactionNumber);
//
//  }

  //=============================

  @Test(description = "[8.3] التحقق من ظهور ملاحظة موظف والمرفق المضافة على المتابعة في سجل المتابعة")
  @Description("[8.3] التحقق من ظهور ملاحظة موظف والمرفق المضافة على المتابعة في سجل المتابعة")
  public void confirmRemarkAndAttachmentExistenceInFollowUpRequest() {
    SHAFT.TestData.JSON followupData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionDestination();
    //Add 1 Attachments
    inTransactionDraftPage.expandAttachmentSection();
    inTransactionDraftPage.addAttachment(followupData.getTestData("attachment1.type")
        , followupData.getTestData("attachment1.location"),
        followupData.getTestData("attachment1.validity"));

    inTransactionDraftPage.saveInTransaction();
    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();

    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
    int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();
    inTransactionDraftPage = myTransactionsPage.editFirstInTransaction();

    inTransactionDraftPage.addTransactionFollowup(followupData.getTestData("followup.orgUnitNum"));
    String transactionFollowupDate = inTransactionDraftPage.getFollowupHijriDate();

    myTransactionsPage = inTransactionDraftPage.saveModifiedTransaction()
        .goBackToMyTransactionPage();

    myTransactionsPage.getSystemAdminComponent()
        .changeDepartment(followupData.getTestData("followup.orgUnitName"));
    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
    myTransactionsPage.printFirstTransaction();
    TransactionsFollowupPage transactionsFollowupPage = myTransactionsPage.navigateToTransactionFollowup();
    transactionsFollowupPage = transactionsFollowupPage.navigateToSentFollowUpTab()
        .getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new TransactionsFollowupPage(driver));

    String firstTransactionFollowupNumberOnCard = transactionsFollowupPage.getFirstTransactionNumber();
    String dateOnTheFirstTransactionCard = transactionsFollowupPage.getFirstTransactionDate();
    int numberOfAttachmentsOnFollowupCard = transactionsFollowupPage.getNumberOfAttachmentsOnFirstCard();

    Validations.verifyThat().object(firstTransactionFollowupNumberOnCard)
        .isEqualTo(transactionNumber);
    Validations.verifyThat().number(numberOfAttachmentsOnFollowupCard)
        .isEqualTo(numberOfAttachmentsOnCard);
    Validations.verifyThat().object(dateOnTheFirstTransactionCard)
        .contains(transactionFollowupDate);
    transactionsFollowupPage.addRemarkToFirstFollowupCard(followupData.getTestData("followupRemarkAttachmentLoc"));
    transactionsFollowupPage.navigateToSentFollowUpTab().getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new TransactionsFollowupPage(driver))
        .openTransactionRecords().openFollowUpRecords();

    String followUpNoteText = transactionsFollowupPage.getFollowUpNoteText();
    String lastFollowUpNoteFromGrid = transactionsFollowupPage.getLastFollowupNote();
    Validations.verifyThat().object(lastFollowUpNoteFromGrid).isEqualTo(followUpNoteText);

    boolean attachmentsExisted = transactionsFollowupPage.confirmFollowupNoteAttachmentExistence();
    Validations.verifyThat().object(attachmentsExisted).isTrue();
  }

}
