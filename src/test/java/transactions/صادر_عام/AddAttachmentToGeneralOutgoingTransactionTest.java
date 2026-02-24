package transactions.صادر_عام;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.OutTransactionsPage;
import pages.transactions.OutTransactionDraftPage;
import pages.transactions.MyTransactionsPage;

@Slf4j
public class AddAttachmentToGeneralOutgoingTransactionTest extends TestBase {



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
    @Test(description = "اضافة ملحقات متعددة [3.3]")
    @Description("اضافة ملحقات متعددة على المعاملة والحفظ [3.3]")
    public void addAttachmentToTransaction() {
        SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("outTransactionDraftData.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        OutTransactionsPage outTransactionsPage = myTransactionsPage.navigateToOutTransactions();
        OutTransactionDraftPage outTransactionDraftPage = outTransactionsPage.navigateToExportedTransactions().tabOnEditFirstOutTransaction();
        String transactionDraftNumber = outTransactionDraftPage.getOutTransactionNumber();
        int numberOfAttachmentsBefore = outTransactionDraftPage.getNumberOfAttachmentsInGrid();

        outTransactionDraftPage.addAttachmentToTransaction(attachmentsData.getTestData("attachment1.type"), attachmentsData.getTestData("attachment1.location"));
        int numberOfAttachmentsAfterFirstAttachment = outTransactionDraftPage.getNumberOfAttachmentsInGrid();
        Validations.verifyThat().number(numberOfAttachmentsAfterFirstAttachment).isGreaterThan(numberOfAttachmentsBefore);

        outTransactionDraftPage.addAttachmentToTransaction(attachmentsData.getTestData("attachment2.type"), attachmentsData.getTestData("attachment2.location"));
        int numberOfAttachmentsAfterSecondAttachment = outTransactionDraftPage.getNumberOfAttachmentsInGrid();
        Validations.verifyThat().number(numberOfAttachmentsAfterSecondAttachment).isGreaterThan(numberOfAttachmentsAfterFirstAttachment);
        outTransactionDraftPage.saveModifiedTransaction();

        outTransactionsPage = outTransactionDraftPage.navigateToTransactionsPage().navigateToExportedTransactions().searchForTransactionWithId(transactionDraftNumber);
        int numberOfAttachmentOnCard = outTransactionsPage.getNumberOfAttachmentsOnFirstCard();

        Validations.assertThat().number(numberOfAttachmentOnCard).equals(numberOfAttachmentsAfterSecondAttachment);
    }
}