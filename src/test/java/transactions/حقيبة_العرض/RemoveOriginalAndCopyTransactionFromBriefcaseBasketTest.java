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

public class RemoveOriginalAndCopyTransactionFromBriefcaseBasketTest extends TestBase {

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
  @Test(description = "[17.3.1] اخراج معاملة اصل من سلة حقيبة العرض")
  @Description("[17.3.1] التحقق من إمكانية اخراج معاملة اصل من سلة حقيبة العرض")
  public void removeOriginalTransactionToBriefcase() {
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionDestination().saveInTransaction();

    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
    myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage();
    BriefcasePage briefcasePage = myTransactionsPage.navigateToBriefcase();

    String transactionNoOnCard = briefcasePage.searchForTransaction(transactionNumber)
        .getTransactionNumberFromFirstCard();
    briefcasePage = briefcasePage.sendTransactionInBriefcase()
        .searchForTransaction(transactionNumber);

    Validations.verifyThat().object(transactionNoOnCard).isEqualTo(transactionNumber);
    Validations.verifyThat().object(briefcasePage.getTransactionTypeOnCard()).contains("اصل");
    Validations.verifyThat().object(briefcasePage.confirmTransactionInBriefcase()).isTrue();

    briefcasePage.searchForTransaction(transactionNumber)
        .sendTransactionOutBriefcase().searchForTransaction(transactionNumber);
    Validations.verifyThat().object(transactionNoOnCard).isEqualTo(transactionNumber);
    Validations.verifyThat().object(briefcasePage.getTransactionTypeOnCard()).contains("اصل");
    Validations.verifyThat().object(briefcasePage.confirmTransactionOutBriefcase()).isTrue();
  }

  //=============================
  @Test(description = "[17.3.2] اخراج نسخة معاملة من سلة حقيبة العرض")
  @Description("[17.3.2] التحقق من إمكانية اخراج نسخة معاملة موجودة في إدارة المستخدم من سلة حقيبة العرض")
  public void removeTransactionCopyToBriefcase() {
    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewIncomingTransaction().addInGeneralTransactionDestination();

    inTransactionDraftPage.expandAttachmentSection();
    inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
            , attachmentsData.getTestData("attachment1.location"),
            attachmentsData.getTestData("attachment1.validity"))
        .saveInTransaction();

    String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();

    inTransactionDraftPage = inTransactionDraftPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver))
        .editFirstInTransaction();
    inTransactionDraftPage.addInternalCopies(attachmentsData
        .getTestData("copy1.orgUnitNum"), attachmentsData.getTestData("copy1.copyReason"), 1);

    myTransactionsPage = inTransactionDraftPage.saveModifiedTransaction()
        .goBackToMyTransactionPage().getSystemAdminComponent()
        .changeDepartment(attachmentsData.getTestData("copy1.orgUnitName"));
    BriefcasePage briefcasePage = myTransactionsPage.navigateToBriefcase()
        .searchForTransaction(transactionNumber);

    String transactionNoOnCard = briefcasePage.getTransactionNumberFromFirstCard();
    briefcasePage = briefcasePage.sendTransactionInBriefcase()
        .searchForTransaction(transactionNumber);

    Validations.verifyThat().object(transactionNoOnCard).isEqualTo(transactionNumber);
    Validations.verifyThat().object(briefcasePage.getTransactionTypeOnCard()).contains("صورة");
    Validations.verifyThat().object(briefcasePage.confirmTransactionInBriefcase()).isTrue();

    briefcasePage.searchForTransaction(transactionNumber)
        .sendTransactionOutBriefcase().searchForTransaction(transactionNumber);
    Validations.verifyThat().object(transactionNoOnCard).isEqualTo(transactionNumber);
    Validations.verifyThat().object(briefcasePage.getTransactionTypeOnCard()).contains("صورة");
    Validations.verifyThat().object(briefcasePage.confirmTransactionOutBriefcase()).isTrue();
  }
}
