package unifiedNumber;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.OutTransactionDraftPage;
import pages.transactions.OutTransactionsPage;
import pages.unifiedNumber.UnifiedNumberPage;

public class CreateOutgoingTransactionFromUnifiedNumber extends TestBase {

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//  @AfterMethod
//  public void afterTest() {
//    driver.quit();
//  }
  // =============================

  @Test(description = "إنشاء صادر مرتبط بصادر خارجي من شاشة الرقم الموحد[9.3.1] ")
  @Description("التحقق من إمكانية إنشاء صادر جديد من الرقم الموحد مرتبط بصادر/وارد/داخلية[9.3.1]  ")
  public void createOutgoingFromExternalOutgoing() {

    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    // إنشاء صادر خارجي
    OutTransactionDraftPage outTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewGeneralTransaction()
        .addGeneralTransaction();

    String transactionNumber = outTransactionDraftPage.getTransactionNumberFromConfirmation();
    OutTransactionsPage outTransactionsPage = outTransactionDraftPage.backToOutgoingTransactionPage().navigateToExportedTransactions();

    // الذهاب للرقم الموحد
    UnifiedNumberPage unifiedNumberPage = myTransactionsPage.getHMComponent()
        .navigateToUnifiedNumberTab().searchForTransactionWithNumber(transactionNumber);
    Validations.verifyThat()
        .object(unifiedNumberPage.confirmValueExistenceInResultsGrid(transactionNumber)).isTrue();


    Validations.verifyThat()
        .object(unifiedNumberPage
            .confirmValueExistenceInResultsGrid(transactionNumber))
        .isTrue();

    // إنشاء صادر
    OutTransactionDraftPage newOutgoing =
        unifiedNumberPage.clickCreateOutgoingButton()
            .addGeneralTransaction2();


    String newOutgoingNumber =
        newOutgoing.getTransactionNumberFromConfirmation();

    // التحقق في معاملات الصادر (الصادر الجديد يظهر في سلة معاملات الصادر)

    OutTransactionsPage outTransactions =
        newOutgoing.backToOutgoingTransactionPage()
            .navigateToExportedTransactions();

    outTransactionsPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(newOutgoingNumber, outTransactionsPage);

    boolean isCreated =
        outTransactionsPage.isTransactionPresent2(newOutgoingNumber);

    Validations.verifyThat().object(isCreated).isTrue();

  }


  @Test(description = "إنشاء صادر مرتبط بمعاملة وارد من شاشة الرقم الموحد[9.3.2] ")
  @Description("التحقق من إمكانية إنشاء صادر جديد من الرقم الموحد مرتبط بصادر/وارد/داخلية[9.3.2]  ")
  public void createOutgoingFromIncoming() {


    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // إنشاء معاملة وارد
    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction()
        .addInGeneralTransactionIndividual()
        .saveInTransaction();

    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage();

    // الذهاب للرقم الموحد
    UnifiedNumberPage unifiedNumberPage = myTransactionsPage.getHMComponent()
        .navigateToUnifiedNumberTab().searchForTransactionWithNumber(transactionNumber);
    Validations.verifyThat()
        .object(unifiedNumberPage.confirmValueExistenceInResultsGrid(transactionNumber)).isTrue();


    Validations.verifyThat()
        .object(unifiedNumberPage
            .confirmValueExistenceInResultsGrid(transactionNumber))
        .isTrue();


    // إنشاء صادر
    OutTransactionDraftPage newOutgoing =
        unifiedNumberPage.clickCreateOutgoingButton()
            .addGeneralTransaction2();


    String newOutgoingNumber =
        newOutgoing.getTransactionNumberFromConfirmation();

    // التحقق في معاملات الصادر (الصادر الجديد يظهر في سلة معاملات الصادر)

    OutTransactionsPage outTransactions =
        newOutgoing.backToOutgoingTransactionPage()
            .navigateToExportedTransactions();

    outTransactions.getTransactionsOperationsComponent()
        .searchForTransactionWithId(newOutgoingNumber, outTransactions);

    boolean isCreated =
        outTransactions.isTransactionPresent2(newOutgoingNumber);

    Validations.verifyThat().object(isCreated).isTrue();


  }

  @Test(description = "إنشاء صادر مرتبط بمعاملة داخلية من شاشة الرقم الموحد[9.3.3] ")
  @Description("التحقق من إمكانية إنشاء صادر جديد من الرقم الموحد مرتبط بصادر/وارد/داخلية[9.3.3]  ")
  public void createOutgoingFromInternal() {

    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // إنشاء معاملة داخلية
    InternalTransactionDraftPage internalTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewOutboundInternalTransaction()
        .addNewInternalTransaction().Returntobasicdata()
        .saveInternalTransaction();

    String transactionNumber = internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage();

    // الذهاب للرقم الموحد
    UnifiedNumberPage unifiedNumberPage = myTransactionsPage.getHMComponent()
        .navigateToUnifiedNumberTab().searchForTransactionWithNumber(transactionNumber);
    Validations.verifyThat()
        .object(unifiedNumberPage.confirmValueExistenceInResultsGrid(transactionNumber)).isTrue();


    Validations.verifyThat()
        .object(unifiedNumberPage
            .confirmValueExistenceInResultsGrid(transactionNumber))
        .isTrue();

    // إنشاء صادر
    OutTransactionDraftPage newOutgoing =
        unifiedNumberPage.clickCreateOutgoingButton()
            .addGeneralTransaction2();


    String newOutgoingNumber =
        newOutgoing.getTransactionNumberFromConfirmation();

    // التحقق في معاملات الصادر (الصادر الجديد يظهر في سلة معاملات الصادر)

    OutTransactionsPage outTransactions =
        newOutgoing.backToOutgoingTransactionPage()
            .navigateToExportedTransactions();

    outTransactions.getTransactionsOperationsComponent()
        .searchForTransactionWithId(newOutgoingNumber, outTransactions);

    boolean isCreated =
        outTransactions.isTransactionPresent2(newOutgoingNumber);

    Validations.verifyThat().object(isCreated).isTrue();
  }

}
