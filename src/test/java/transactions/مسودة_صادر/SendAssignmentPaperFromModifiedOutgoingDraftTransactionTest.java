package transactions.مسودة_صادر;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.OutgoingDraftTransactionPage;
import pages.transactions.TransactionsCopiesPage;

public class SendAssignmentPaperFromModifiedOutgoingDraftTransactionTest extends TestBase {

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

  //=============================
  @Test(description = "[4.4] اضافة نسخ داخلية وخارجية")
  @Description("من تعديل مسودة صادر يتم ارسال نسخ الكترونية داخلية وخارجية مع مرفقات مختلفة والتحقق من وصولها")
  public void sendAssignmentPaperOutgoingDraftTransaction() {

    SHAFT.TestData.JSON attachmentsData =
        new SHAFT.TestData.JSON("outTransactionDraftData.json");

    // 1️⃣ تسجيل الدخول
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // 2️⃣ إنشاء مسودة صادر
    OutgoingDraftTransactionPage outgoingDraftTransactionPage =
        myTransactionsPage.getTransactionsOperationsComponent()
            .addNewOutgoingDraftransaction()
            .addNewInternalTransaction()
            .Returntobasicdata();


    outgoingDraftTransactionPage.expandAttachmentSection();
    outgoingDraftTransactionPage.addAttachment(attachmentsData.getTestData("attachment1.type")
        , attachmentsData.getTestData("attachment1.location"),
        attachmentsData.getTestData("attachment1.validity")).saveOutgoingDraftTransaction();

    // 3️⃣ إضافة مرفق
    outgoingDraftTransactionPage.expandAttachmentSection();
    outgoingDraftTransactionPage .addAttachment(
            attachmentsData.getTestData("attachment1.type"),
            attachmentsData.getTestData("attachment1.location"),
            attachmentsData.getTestData("attachment1.validity")
        );

    // 4️⃣ حفظ مسودة الصادر
    outgoingDraftTransactionPage.saveOutgoingDraftTransaction();
    String transactionNumber =
        outgoingDraftTransactionPage.getTransactionNumberFromConfirmation();

    // 5️⃣ الرجوع والبحث عن المعاملة وتعديلها
    myTransactionsPage = outgoingDraftTransactionPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent()
        .searchForTransactionWithId(
            transactionNumber,
            new MyTransactionsPage(driver)
        );

    outgoingDraftTransactionPage = myTransactionsPage.editFirstInTransaction3();

    // 6️⃣ ارسال ورقة الاحالة
    outgoingDraftTransactionPage.goToAssignmentPaper()
        .selectOrgUnitFromAssignmentPaper(
            attachmentsData.getTestData("assignmentPaper.orgUnitName")
        )
        .sendAssignmentPaper();

    // 7️⃣ التحقق من النسخة في الجهة المستقبلة
    myTransactionsPage = myTransactionsPage.getSystemAdminComponent()
        .changeDepartment(
            attachmentsData.getTestData("assignmentPaper.orgUnitName")
        );

    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab()
        .getHMComponent()
        .receivinganddistributingimages();

    TransactionsCopiesPage transactionsCopiesPage =
        myTransactionsPage.navigateToTransactionsCopies();

    String transactionCopyNumber =
        transactionsCopiesPage.getTransactionsOperationsComponent()
            .searchForTransactionWithId(
                transactionNumber,
                new TransactionsCopiesPage(driver)
            )
            .getFirstTransactionNumber();

    // 8️⃣ التحقق النهائي
    Validations.verifyThat()
        .object(transactionCopyNumber)
        .isEqualTo(transactionNumber);
  }

}
