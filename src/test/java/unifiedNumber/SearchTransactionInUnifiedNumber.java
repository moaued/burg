package unifiedNumber;

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
import pages.transactions.TransactionsCopiesPage;
import pages.unifiedNumber.UnifiedNumberDetailsPage;
import pages.unifiedNumber.UnifiedNumberPage;

public class SearchTransactionInUnifiedNumber extends TestBase {

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
  @Test(description = "التحقق من البحث عن معاملة في الرقم الموحد وإظهارها [9.1]")
  @Description(" تسجيل الدخول ثم البحث عن معاملة في تبويب الرقم الموحد باستخدام Enter والتحقق من ظهورها مظللة باللون الأخضر وظهور المعاملات المرتبطة بها إن وجدت[9.1]")
  public void searchTransactionInUnifiedNumber() {

    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionDestination().expandAttachmentSection()
        .addAttachment(attachmentsData.getTestData("attachment1.type")
            , attachmentsData.getTestData("attachment1.location"),
            attachmentsData.getTestData("attachment1.validity")).saveInTransaction();

    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage();

    UnifiedNumberPage unifiedNumberPage = myTransactionsPage.getHMComponent()
        .navigateToUnifiedNumberTab().searchForTransactionWithNumber(transactionNumber);
    Validations.verifyThat()
        .object(unifiedNumberPage.confirmValueExistenceInResultsGrid(transactionNumber)).isTrue();


  }

}
