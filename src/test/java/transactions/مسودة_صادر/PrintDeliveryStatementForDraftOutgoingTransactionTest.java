package transactions.مسودة_صادر;

import static utils.GeneralOperations.getFileSize;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.OutgoingDraftTransactionPage;
import pages.transactions.TransactionsCopiesPage;
import utils.GeneralOperations;

public class PrintDeliveryStatementForDraftOutgoingTransactionTest extends TestBase {

  String directory;

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    GeneralOperations.createNewFolderWithDateTimeName("deliveryStatementDownloadFolderPath");
    directory = GeneralOperations.getAbsolutePath();
    openBuragAppWithCustomCapabilities(directory);
  }

//    @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

  //=============================
  @Test(description = "[4.6] ارسال المسودة إلى إدارة أخرى للتصدير وطباعة بيان التسليم")
  @Description("حفظ مسودة صادر عام ثم ارسالها من خلال ايقونة الارسال وطباعة بيان التسليم والتحقق من النسخ المرسلة[4.6]")
  public void modifyDraftOutgoingTransactionAndSendDeliveryStatement() {

    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // 1️⃣ إنشاء مسودة صادر عام
    OutgoingDraftTransactionPage OutgoingDraftTransactionPage =
        myTransactionsPage.getTransactionsOperationsComponent()
            .addNewOutgoingDraftransaction()
            .Returntobasicdata();

    // 2️⃣ حفظ المسودة
    OutgoingDraftTransactionPage.saveModifiedTransaction();
    String transactionNumber =
        OutgoingDraftTransactionPage.getTransactionNumberFromConfirmation();

    // 3️⃣ البحث عن المعاملة
    myTransactionsPage =
        OutgoingDraftTransactionPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(transactionNumber, myTransactionsPage);

    String transactionSubject =
        myTransactionsPage.getFirstTransactionDescription();

    // 4️⃣ تعديل المعاملة وارسالها مع طباعة بيان التسليم
    OutgoingDraftTransactionPage =
        myTransactionsPage.editFirstInTransaction3();

    OutgoingDraftTransactionPage.sendAndPrintDeliveryStatementForModifiedInTransaction();

    // 5️⃣ التحقق من ملف بيان التسليم
    double fileSize =
        getFileSize(directory + "/GenerateReport.pdf");

    Validations.verifyThat()
        .number(fileSize)
        .isGreaterThan(80);

    Validations.verifyThat()
        .file(directory, "GenerateReport.pdf")
        .content()
        .contains(transactionNumber);

    Validations.verifyThat()
        .file(directory, "GenerateReport.pdf")
        .content()
        .contains(transactionSubject);

    // 6️⃣ التحقق من النسخ المرسلة
    TransactionsCopiesPage transactionsCopiesPage =
        OutgoingDraftTransactionPage.goBackToMyTransactionPage()
            .navigateToTransactionsCopies();

    String transactionCopyNumber =
        transactionsCopiesPage.getTransactionsOperationsComponent()
            .searchForTransactionWithId(
                transactionNumber,
                new TransactionsCopiesPage(driver)
            )
            .getFirstTransactionNumber();

    Validations.verifyThat()
        .object(transactionCopyNumber)
        .isEqualTo(transactionNumber);
  }

}
