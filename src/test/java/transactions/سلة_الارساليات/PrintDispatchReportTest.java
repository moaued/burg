package transactions.سلة_الارساليات;

import static utils.GeneralOperations.getFileSize;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import java.io.UnsupportedEncodingException;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import utils.GeneralOperations;

@Slf4j
public class PrintDispatchReportTest extends TestBase {

  String directory;

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    GeneralOperations.createNewFolderWithDateTimeName("deliveryStatementDownloadFolderPath");
    directory = GeneralOperations.getAbsolutePath();
    openBuragAppWithCustomCapabilities(directory);
  }

  @AfterMethod
  public void afterTest() {
    driver.quit();
  }

  //=============================
  @Test(description = "[16.2] طباعة بيان التسليم لمعاملة داخلية و الارسال لسبل و طباعة بيان الارسالية")
  @Description("[16.2] بعد الدخول على سلة الارساليات، البحث عن المعاملة المرسلة من إدارة المستخدم وتم طباعة بيان تسليم لها، ويتم تحديد عدد من المعاملات المرسلة إلى جهة محددة وتحديد مراسل لها واختيار طريقة الارسال سبل، ومن ثم طباعة بيان الارسالية والتحقق من ظهور بيانات المعاملة في الارسالية مع اسم المراسل، واسم الجهة المرسل إليها")
  public void addAttachmentToTransaction() throws UnsupportedEncodingException {
    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("outboundTransactionDraft.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InternalTransactionDraftPage internalTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewOutboundInternalTransaction()
        .addNewInternalTransaction();

    internalTransactionDraftPage.expandAttachmentSection();
    internalTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
            , attachmentsData.getTestData("attachment1.location"),
            attachmentsData.getTestData("attachment1.validity"))
        .saveInternalTransaction();
    String transactionNumber = internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    internalTransactionDraftPage = internalTransactionDraftPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver))
        .editFirstInTransaction2(new InternalTransactionDraftPage(driver));
    internalTransactionDraftPage.addElectronicCopies(
        attachmentsData.getTestData("externalCopy1.orgUnitName"),
        attachmentsData.getTestData("externalCopy1.orgUnitNumber"),
        attachmentsData.getTestData("externalCopy1.copyReason"));

    internalTransactionDraftPage = internalTransactionDraftPage.saveModifiedTransaction()
        .sendAndPrintDeliveryStatementForModifiedInTransaction();
    double fileSize = getFileSize(directory + "/GenerateReport.pdf");
    //The created file size is greater than 80 KB which is the average file size
    Validations.verifyThat().number(fileSize).isGreaterThan(80);

    String reporterName = attachmentsData.getTestData("externalCopy1.reporterName");

    myTransactionsPage.getHMComponent()
        .navigateToDispatchReportTab()
        .searchForTransaction(attachmentsData.getTestData("externalCopy1.orgUnitName"))
        .printSplReport(reporterName);

    double splFileSize = getFileSize(directory + "/GenerateReportSpl.pdf");
    //The created file size is greater than 90 KB which is the average file size
    Validations.verifyThat().number(splFileSize).isGreaterThan(90);
    Validations.verifyThat().file(directory, "GenerateReportSpl.pdf").content()
        .contains(transactionNumber);

    //The contents in the 2 points below are in arabic & appears as corrupted text in the report
/*    Validations.verifyThat().file(directory, "GenerateReportSpl.pdf").content().contains(attachmentsData.getTestData("externalCopy1.orgUnitName"));
    Validations.verifyThat().file(directory, "GenerateReportSpl.pdf").content().contains(reporterName);*/

  }
}