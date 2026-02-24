package transactions.حقيبة_العرض;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.BriefcasePage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;

public class AddRemarkToTransactionToBriefcaseBasketTest extends TestBase {

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
  @Test(description = "[17.2] إضافة ملاحظة إلى معاملة الى سلة حقيبة العرض")
  @Description("[17.2] التحقق من إمكانية إضافة ملاحظة على معاملة داخل سلة حقيبة العرض")
  public void addOriginalTransactionToBriefcase() {
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionDestination().saveInTransaction();

    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage();

    BriefcasePage briefcasePage = myTransactionsPage.navigateToBriefcase()
        .searchForTransaction(transactionNumber);

    String transactionNoOnCard = briefcasePage.getTransactionNumberFromFirstCard();
    Validations.verifyThat().object(transactionNoOnCard).isEqualTo(transactionNumber);

    briefcasePage = briefcasePage.sendTransactionInBriefcase().searchForTransaction(transactionNumber);
    Validations.verifyThat().object(briefcasePage.confirmTransactionInBriefcase()).isTrue();
    Validations.verifyThat().object(briefcasePage.getTransactionRemarkOnCard())
        .contains(briefcasePage.getSendTransactionRemarkText());
  }

}
