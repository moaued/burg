package transactions.مسودة_صادر;

import static utils.GeneralOperations.getFileSize;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.OutgoingDraftTransactionPage;
import utils.GeneralOperations;

@Slf4j
public class AddAttachmentToOutgoingDraftTransactionTest extends TestBase {

  String directory;

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    GeneralOperations.createNewFolderWithDateTimeName("assignmentPaperDownloadFolderPath");
    directory = GeneralOperations.getAbsolutePath();
    openBuragAppWithCustomCapabilities(directory);
  }
    @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

  //=============================

  @Test(description = "اضافة ملحقات متعددة و طباعة ورقة الاحالة - مسودة صادر[4.3]")
  @Description("اضافة عدة ملحقات على مسودة الصادر ثم حفظها وطباعة ورقة الإحالة[4.3]")
  public void addAttachmentToOutgoingDraftTransaction() {

    SHAFT.TestData.JSON attachmentsData =
        new SHAFT.TestData.JSON("inTransactionDraftData.json");

    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // 1️⃣ إنشاء مسودة صادر
      OutgoingDraftTransactionPage outgoingDraftPage =
          myTransactionsPage.getTransactionsOperationsComponent()
              .addNewOutgoingDraftransaction()
              .addNewInternalTransaction()
              .addRelevantperson()
              .Returntobasicdata();

    // 2️⃣ حفظ المسودة
    outgoingDraftPage.saveOutgoingDraftTransaction();
    String transactionNumber =
        outgoingDraftPage.getTransactionNumberFromConfirmation();

    // 3️⃣ البحث عن المعاملة
    myTransactionsPage = outgoingDraftPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber,
            new MyTransactionsPage(driver));

    // 4️⃣ تعديل المعاملة والذهاب للملحقات
    outgoingDraftPage =
        myTransactionsPage.editFirstInTransaction3()
            .goToAttachmentsTab();

    int attachmentsBefore =
        outgoingDraftPage.getNumberOfAttachmentsInGrid();

    // 5️⃣ إضافة الملحق الأول
    outgoingDraftPage.addAttachment(
        attachmentsData.getTestData("attachment1.type"),
        attachmentsData.getTestData("attachment1.location"),
        attachmentsData.getTestData("attachment1.validity")
    );

    int afterFirst =
        outgoingDraftPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat()
        .number(afterFirst).isGreaterThan(attachmentsBefore);

    // 6️⃣ إضافة الملحق الثاني
    outgoingDraftPage.addAttachment(
        attachmentsData.getTestData("attachment2.type"),
        attachmentsData.getTestData("attachment2.location"),
        attachmentsData.getTestData("attachment2.validity")
    );

    int afterSecond =
        outgoingDraftPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat()
        .number(afterSecond).isGreaterThan(afterFirst);

    // 7️⃣ إضافة الملحق الثالث
    outgoingDraftPage.addAttachment(
        attachmentsData.getTestData("attachment3.type"),
        attachmentsData.getTestData("attachment3.location"),
        attachmentsData.getTestData("attachment3.validity")
    );

    int afterThird =
        outgoingDraftPage.getNumberOfAttachmentsInGrid();
    Validations.verifyThat()
        .number(afterThird).isGreaterThan(afterSecond);

    // 8️⃣ حفظ التعديل + طباعة ورقة الإحالة
    outgoingDraftPage
        .saveModifiedTransaction()
        .printAssignmentPaperFromConfirmation();

    // 9️⃣ حفظ نهائي
    outgoingDraftPage.saveModifiedTransaction2();

    // 🔟 التحقق من عدد الملحقات في كرت المعاملة
    myTransactionsPage = outgoingDraftPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber,
            new MyTransactionsPage(driver));

    int attachmentsOnCard =
        myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

    Validations.assertThat()
        .number(attachmentsOnCard)
        .isEqualTo(afterThird);

    // ⓫ التحقق من ملف ورقة الإحالة
    double fileSize =
        getFileSize(directory + "/Download.pdf");

    Validations.verifyThat()
        .number(fileSize)
        .isGreaterThan(150);
  }

}
