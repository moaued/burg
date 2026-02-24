package transactions.المتابعة;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.reports.FollowUpReportPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.TransactionsFollowupPage;

public class FollowUpCompleteTest extends TestBase {

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

  @Test(description = "[8.1] التحقق من وصول طلب المتابعة")
  @Description("[8.1] التحقق من وصول طلب المتابعة بتاريخ الارسال في السلة والتقرير")
  public void confirmFollowUpRequestDisplayInOregUnitAndReport() {
    SHAFT.TestData.JSON followupData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    SHAFT.TestData.JSON reportData = new SHAFT.TestData.JSON("followUpReports.json");
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

    FollowUpReportPage followUpReportPage = myTransactionsPage.getHMComponent()
        .navigateToReportsTab().navigateToFollowUpReport();

    followUpReportPage.generateReportForTransactionType(transactionFollowupDate, transactionFollowupDate,
        reportData.getTestData("followUpReport1.type"));
    Validations.verifyThat().object(followUpReportPage.isResultsGridDisplayed()).isTrue();
    Validations.verifyThat().number(followUpReportPage.getRowsCount()).isGreaterThanOrEquals(0);
    Validations.verifyThat().object(followUpReportPage.getDateRangeInReport())
        .contains(transactionFollowupDate + " - " + transactionFollowupDate);
    Validations.verifyThat().object(followUpReportPage.getTransactionTypeInReport())
        .contains(reportData.getTestData("followUpReport1.type"));
    Validations.verifyThat()
        .object(followUpReportPage.confirmTransactionNumberExistenceInReport(transactionNumber))
        .isTrue();
  }

}
