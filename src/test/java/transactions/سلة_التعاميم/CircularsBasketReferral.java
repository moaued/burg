package transactions.سلة_التعاميم;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.TransactionCopiesDetailsPage;
import pages.transactions.TransactionsCopiesPage;

public class CircularsBasketReferral extends TestBase {


  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//    @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

// =========================
// =========================

  @Test(description = "إرسال نسخة جديدة من خلال ورقة الإحالة مع مرفقات مختلفة والتحقق من وصولها- سلة التعاميم[15.3]")
  @Description("بعد الدخول على نسخة التعاميم من أيقونة العرض، التحقق من إمكانية إرسال نسخة جديدة عبر ورقة الإحالة مع تحديد مرفقات مختلفة وإضافة ملاحظة، والتحقق من وصولها[15.3]")
  public void sendCircularCopyUsingReferralSheet() {

    // 1️⃣ تحميل بيانات الاختبار
    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");

    // 2️⃣ تسجيل الدخول
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // 3️⃣ إنشاء تعميم جديد
    InTransactionDraftPage inTransactionDraftPage =
        myTransactionsPage.getTransactionsOperationsComponent()
            .addNewIncomingTransaction()
            .addInGeneralTransactionIndividual2();

    // 4️⃣ إضافة المرفقات
    inTransactionDraftPage.expandAttachmentSection();
    inTransactionDraftPage.addAttachment(
        attachmentsData.getTestData("attachment1.type"),
        attachmentsData.getTestData("attachment1.location"),
        attachmentsData.getTestData("attachment1.validity")
    );
    inTransactionDraftPage.addAttachment(
        attachmentsData.getTestData("attachment2.type"),
        attachmentsData.getTestData("attachment2.location"),
        attachmentsData.getTestData("attachment2.validity")
    );
    inTransactionDraftPage.addAttachment(
        attachmentsData.getTestData("attachment3.type"),
        attachmentsData.getTestData("attachment3.location"),
        attachmentsData.getTestData("attachment3.validity")
    );

    // 5️⃣ حفظ التعميم وجلب رقمه
    inTransactionDraftPage.saveInTransaction();
    String circularNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();

    // 6️⃣ فتح التعميم للتعديل وإضافة النسخ الداخلية
    inTransactionDraftPage =
        inTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(circularNumber, new MyTransactionsPage(driver))
            .editFirstInTransaction();

    inTransactionDraftPage.addInternalCopies(
        attachmentsData.getTestData("copy1.orgUnitNum"),
        attachmentsData.getTestData("copy1.copyReason"),
        1
    );

    String copyType1 = inTransactionDraftPage.getAttachmentCopyDescription().getFirst();

    // 7️⃣ حفظ التعديلات والتحويل للإدارة المستقبلة للنسخة
    myTransactionsPage =
        inTransactionDraftPage.saveModifiedTransaction()
            .goBackToMyTransactionPage()
            .getSystemAdminComponent()
            .changeDepartment(attachmentsData.getTestData("copy1.orgUnitName"));

    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab()
        .getHMComponent()
        .receivinganddistributingimages();

    myTransactionsPage.printFirstTransaction();

    // 8️⃣ فتح سلة التعاميم
    TransactionsCopiesPage transactionsCopiesPage = myTransactionsPage.navigateToCircularsBasket();

    // 9️⃣ البحث عن النسخة والتحقق منها
    String circularCopyNumber
        = transactionsCopiesPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(circularNumber, new TransactionsCopiesPage(driver))
        .getFirstTransactionNumber();


    Validations.verifyThat().object(circularCopyNumber).isEqualTo(circularNumber);

    TransactionCopiesDetailsPage transactionCopiesDetailsPage = transactionsCopiesPage.goToTransactionCopyDetails();
    String transactionNumberFromCopy1 = transactionCopiesDetailsPage.getTransactionNumber();
    boolean isAttachmentTypePresent1 = transactionCopiesDetailsPage
        .confirmExistenceOfSpecificContentInTableOfCopies(copyType1);
//
    Validations.verifyThat().object(transactionNumberFromCopy1).isEqualTo(circularNumber);
    Validations.verifyThat().object(isAttachmentTypePresent1).isTrue();

    inTransactionDraftPage.selectOrgUnittosendcopy2(attachmentsData
        .getTestData("copy3.orgUnitNum"), attachmentsData.getTestData("copy3.copyReason")
    );

    inTransactionDraftPage.changeDepartmentafteraddInternalCopies(attachmentsData.getTestData("copy3.orgUnitName"));
    myTransactionsPage.getHMComponent()
        .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
    myTransactionsPage.printFirstTransaction();
    String transactionCopyNumber3 = myTransactionsPage.navigateToCircularsBasket().getTransactionsOperationsComponent()
        .searchForTransactionWithId(circularNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();
    Validations.verifyThat().object(transactionCopyNumber3).isEqualTo(circularNumber);


  }

}
