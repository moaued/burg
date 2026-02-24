package unifiedNumber;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.OutTransactionDraftPage;
import pages.transactions.OutTransactionsPage;
import pages.unifiedNumber.UnifiedNumberDetailsPage;
import pages.unifiedNumber.UnifiedNumberPage;

public class CreateIncomingFromUnifiedNumber extends TestBase {

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

  @Test(description = "إنشاء وارد مرتبط بصادر خارجي من شاشة الرقم الموحد[9.2.1] ")
  @Description("التحقق من إمكانية إنشاء وارد جديد من الرقم الموحد مرتبط بصادر/وارد/داخلية[9.2.1]  ")
  public void createIncomingFromExternalOutgoing() {

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

    // إنشاء وارد
    InTransactionDraftPage incomingPage =
        unifiedNumberPage.clickCreateIncomingButton()
            .addInGeneralTransactionDestination3()
            .saveInTransaction();

    String newIncomingNumber =
        incomingPage.getTransactionNumberFromConfirmation();

    // التحقق في معاملاتي (الوارد الجديد يظهر في معاملاتي)
    MyTransactionsPage myTransactions =
        incomingPage.goBackToMyTransactionPage();

    boolean isCreated =
        myTransactions.getTransactionsOperationsComponent()
            .searchForTransactionWithId(newIncomingNumber,
                new MyTransactionsPage(driver))
            .isTransactionPresent(newIncomingNumber);

    Validations.verifyThat().object(isCreated).isTrue();

  }

  @Test(description = "إنشاء وارد مرتبط بمعاملة وارد من شاشة الرقم الموحد[9.2.2] ")
  @Description("التحقق من إمكانية إنشاء وارد جديد من الرقم الموحد مرتبط بصادر/وارد/داخلية[9.2.2]  ")
  public void createIncomingFromIncoming() {


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

    // إنشاء وارد
    InTransactionDraftPage incomingPage =
        unifiedNumberPage.clickCreateIncomingButton()
            .addInGeneralTransactionDestination4()
            .saveInTransaction();

    String newIncomingNumber =
        incomingPage.getTransactionNumberFromConfirmation();

    // التحقق في معاملاتي (الوارد الجديد يظهر في معاملاتي)
    MyTransactionsPage myTransactions =
        incomingPage.goBackToMyTransactionPage();

    boolean isCreated =
        myTransactions.getTransactionsOperationsComponent()
            .searchForTransactionWithId(newIncomingNumber,
                new MyTransactionsPage(driver))
            .isTransactionPresent(newIncomingNumber);

    Validations.verifyThat().object(isCreated).isTrue();


  }

  @Test(description = "إنشاء وارد مرتبط بمعاملة داخلية من شاشة الرقم الموحد[9.2.3] ")
  @Description("التحقق من إمكانية إنشاء وارد جديد من الرقم الموحد مرتبط بصادر/وارد/داخلية[9.2.3]  ")
  public void createIncomingFromInternal() {

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

    // إنشاء وارد
    InTransactionDraftPage incomingPage =
        unifiedNumberPage.clickCreateIncomingButton()
            .addInGeneralTransactionDestination2()
            .saveInTransaction();

    String newIncomingNumber =
        incomingPage.getTransactionNumberFromConfirmation();

    // التحقق في معاملاتي (الوارد الجديد يظهر في معاملاتي)
    MyTransactionsPage myTransactions =
        incomingPage.goBackToMyTransactionPage();

    boolean isCreated =
        myTransactions.getTransactionsOperationsComponent()
            .searchForTransactionWithId(newIncomingNumber,
                new MyTransactionsPage(driver))
            .isTransactionPresent(newIncomingNumber);

    Validations.verifyThat().object(isCreated).isTrue();


  }



}
