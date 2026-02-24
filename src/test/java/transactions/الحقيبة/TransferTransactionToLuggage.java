package transactions.الحقيبة;



import base.TestBase;
import com.shaft.driver.SHAFT;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;


@Slf4j
public class TransferTransactionToLuggage extends TestBase {

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
  @Test(description = "نقل اصل معاملة إلى الحقيبة [5.1]")
  @Description("نقل اصل معاملة إلى الحقيبة [5.1]")
  public void TransferTransactionToLuggage() {

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
  

  }


}
