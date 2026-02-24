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
import utils.GeneralOperations;

import static utils.GeneralOperations.getFileSize;

@Slf4j
public class PrintOutgoingBarcodeTest extends TestBase {

    String directory;

    @BeforeMethod
    public void beforeTest() {
        testData = new SHAFT.TestData.JSON("appData.json");
        GeneralOperations.createNewFolderWithDateTimeName("transactionBarcodeDownloadFolderPath");
        directory = GeneralOperations.getAbsolutePath();
        openBuragAppWithCustomCapabilities(directory);
    }

    @AfterMethod
    public void afterTest() {
        driver.quit();
    }

    //=============================
    @Test(description = "طباعة باركود [3.5]")
    @Description("طباعة باركود بانواعه (الكتروني + ملصق + A4) [3.5]")
    public void printBarcode() {
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        OutTransactionsPage outTransactionsPage = myTransactionsPage.navigateToOutTransactions();
        OutTransactionDraftPage outTransactionDraftPage = outTransactionsPage.navigateToExportedTransactions().tabOnEditFirstOutTransaction();

        outTransactionDraftPage.printBarcodeSticker();
        //Confirm that the barcode file was downloaded
        Validations.verifyThat().file(directory, "Barcode.pdf").exists();
        //& the file size is greater than 10KB (which is the average file size)
        double fileSize = getFileSize(directory+"/Barcode.pdf");
        Validations.verifyThat().number(fileSize).isGreaterThan(10);

        outTransactionDraftPage.printBarcode();
        //Confirm that the number of opened browser tabs are more than 1 to indicate the print dialogue was opened
        Validations.verifyThat().number(outTransactionDraftPage.getNumberOfBrowserWindows()).isGreaterThan(1);
        //Confirm also the contents of the print dialogue
        Validations.assertThat().object(outTransactionDraftPage.isPrintDialogueDisplayed()).isTrue();

    }
}
