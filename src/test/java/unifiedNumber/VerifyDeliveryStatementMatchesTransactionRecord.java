package unifiedNumber;

import static utils.GeneralOperations.getFileSize;

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
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.unifiedNumber.UnifiedNumberPage;

public class VerifyDeliveryStatementMatchesTransactionRecord extends TestBase {

  String directory;

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

  @Test(description = "التحقق من ايقونة طباعة بيان التسليم وان البيان مطابق لسجل المعاملة[9.5]")
  @Description("التحقق من ايقونة طباعة بيان التسليم وان البيان مطابق لسجل المعاملة في شاشة الرقم الموحد [9.5]")
  public void testDeliveryStatementMatchesTransactionRecord() {

    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionDestination();
    inTransactionDraftPage.saveInTransaction();
    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();

    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, myTransactionsPage);
    String transactionSubject = myTransactionsPage.getFirstTransactionDescription();
    inTransactionDraftPage = myTransactionsPage.editFirstInTransaction2().saveModifiedTransaction();
    inTransactionDraftPage.printDeliveryStatementForAddedInTransaction();

    // الذهاب للرقم الموحد
    UnifiedNumberPage unifiedNumberPage = myTransactionsPage.getHMComponent()
        .navigateToUnifiedNumberTab2().searchForTransactionWithNumber(transactionNumber);
    Validations.verifyThat()
        .object(unifiedNumberPage.confirmValueExistenceInResultsGrid(transactionNumber)).isTrue();

    Validations.verifyThat()
        .object(unifiedNumberPage
            .confirmValueExistenceInResultsGrid(transactionNumber))
        .isTrue();

    //طباعة بيان التسليم ثم فتح سجل الاحالات
        unifiedNumberPage.printDeliveryStatement();




   /* OrgUnitRecievalPage orgUnitRecievalPage = inTransactionDraftPage.goBackToMyTransactionPage()
        .getHMComponent()
        .navigateToOrgUnitTransactionsTab();
    orgUnitRecievalPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new OrgUnitRecievalPage(driver));
    String transactionNumberOnCard = orgUnitRecievalPage.getFirstTransactionNumber();

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
    Validations.verifyThat().object(transactionNumberOnCard).isEqualTo(transactionNumber);*/


  }

}
