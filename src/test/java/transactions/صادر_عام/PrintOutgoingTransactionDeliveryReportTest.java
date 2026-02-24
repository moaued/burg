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
import pages.transactions.OutTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

import static utils.GeneralOperations.getFileSize;

@Slf4j
public class PrintOutgoingTransactionDeliveryReportTest extends TestBase {

    String directory;

    @BeforeMethod
    public void beforeTest() {
        testData = new SHAFT.TestData.JSON("appData.json");
        GeneralOperations.createNewFolderWithDateTimeName("transactionDeliveryDownloadFolderPath");
        directory = GeneralOperations.getAbsolutePath();
        openBuragAppWithCustomCapabilities(directory);
    }

    @AfterMethod
    public void afterTest() {
        driver.quit();
    }

    //=============================
    @Test(description = "طباعة بين التسليم [3.6]")
    @Description("طباعة بين التسليم [3.6]")
    public void printTransactionDeliveryReport() {
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        OutTransactionDraftPage outTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
                .addNewGeneralTransaction().addGeneralTransaction();

        String transactionDraftNumber = outTransactionDraftPage.getTransactionNumberFromConfirmation();
        String transactionDraftSubject = outTransactionDraftPage.getTransactionDescription();
        outTransactionDraftPage.printTransactionDeliveryReportFromConfirmation();
        outTransactionDraftPage.backToOutgoingTransactionPage();
        double fileSize = getFileSize(directory + "/GenerateReport.pdf");
        //The created file size is greater than 80 KB which is the average file size
        Validations.verifyThat().number(fileSize).isGreaterThan(80);
        //Confirm the transaction number is the same between the transaction & the report
        Validations.verifyThat().file(directory, "GenerateReport.pdf").content().contains(transactionDraftNumber);
        //Confirm the transaction subject is the same between the transaction & the report
        Validations.verifyThat().file(directory, "GenerateReport.pdf").content().contains(transactionDraftSubject);
    }
}
