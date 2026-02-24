//package transactions.صادر_عام;
//
//import base.TestBase;
//import com.shaft.driver.SHAFT;
//import com.shaft.validation.Validations;
//import io.qameta.allure.Description;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//import pages.LoginPage;
//import pages.transactions.MyTransactionsPage;
//import pages.transactions.OutTransactionDraftPage;
//import pages.transactions.TransactionsCopiesPage;
//
//public class SendAssignmentPaperFromModifiedGeneralOutTransactionTest extends TestBase {
//
//  @BeforeMethod
//  public void beforeTest() {
//    testData = new SHAFT.TestData.JSON("appData.json");
//    openBuragApp();
//  }
//
//  @AfterMethod
////    public void afterTest() {
////        driver.quit();
////    }
//
//  //=============================
//  @Test(description = "[3.4] ارسال ورقة الاحالة من خلال تعديل معاملة صادر عام")
//  @Description("اضافة نسخ داخلية وخارجية، وتحديد مرفقات مختلفة للنسخ والتحقق من وصولها بحسب ما تم تحديده [3.4]")
//  public void sendAssignmentPaperOutgoingTransaction() {
//
//    SHAFT.TestData.JSON attachmentsData =
//        new SHAFT.TestData.JSON("outTransactionDraftData.json");
//
//    LoginPage loginPage = new LoginPage(driver);
//    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
//
//    // 1️⃣ إنشاء معاملة صادر عام
//    OutTransactionDraftPage outTransactionDraftPage =
//        myTransactionsPage.getTransactionsOperationsComponent().addNewGeneralTransaction();
//        outTransactionDraftPage.addGeneralTransaction();
//
//
//    // 2️⃣ إضافة مرفق
//    outTransactionDraftPage.addAttachmentToTransaction(
//        attachmentsData.getTestData("attachment1.type"),
//        attachmentsData.getTestData("attachment1.location")
//    );
//
//    // 3️⃣ حفظ المعاملة
//    outTransactionDraftPage.saveModifiedTransaction();
//    String transactionNumber =
//        outTransactionDraftPage.getTransactionNumberFromConfirmation();
//
//    // 4️⃣ البحث عن المعاملة وتعديلها
//    myTransactionsPage =
//        outTransactionDraftPage.backToOutgoingTransactionPage()
//            .searchForTransactionWithId(transactionDraftNumber);
//
//
//
//    outTransactionDraftPage =
//        OutTransactionsPage.tabOnEditFirstOutTransaction();
//
//    // 5️⃣ إرسال ورقة الإحالة
//    outTransactionDraftPage.goToAssignmentPaper()
//        .selectOrgUnitFromAssignmentPaper(
//            attachmentsData.getTestData("assignmentPaper.orgUnitName")
//        );
//
//    myTransactionsPage = outTransactionDraftPage.sendAssignmentPaper();
//
//    // 6️⃣ التحقق من النسخة في الجهة المستقبلة
//    myTransactionsPage =
//        myTransactionsPage.getSystemAdminComponent()
//            .changeDepartment(
//                attachmentsData.getTestData("assignmentPaper.orgUnitName")
//            );
//
//    myTransactionsPage.getHMComponent()
//        .navigateToOrgUnitTransactionsTab()
//        .getHMComponent()
//        .receivinganddistributingimages();
//
//    TransactionsCopiesPage transactionsCopiesPage =
//        myTransactionsPage.navigateToTransactionsCopies();
//
//    String transactionCopyNumber =
//        transactionsCopiesPage.getTransactionsOperationsComponent()
//            .searchForTransactionWithId(
//                transactionNumber,
//                new TransactionsCopiesPage(driver)
//            )
//            .getFirstTransactionNumber();
//
//    // 7️⃣ التحقق
//    Validations.verifyThat()
//        .object(transactionCopyNumber)
//        .isEqualTo(transactionNumber);
//  }
//
//}
