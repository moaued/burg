package transactions.مسودة_صادر;

import base.TestBase;
import com.shaft.driver.SHAFT;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

import pages.transactions.MyTransactionsPage;
import pages.transactions.OutgoingDraftTransactionPage;

public class AddGeneralOutgoingDraftTransactionTest extends TestBase {


  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }
//
//  @AfterMethod
//  public void afterTest() {
//    driver.quit();
//  }

  // =============================
  @Test(description = "[4.1] اضافة مسودة صادر عام")
  @Description("انشاء صادر مسودة، واضافة بيانات صاحب علاقة وتحديد خيار ارسال رسالة نصية والتحقق من عدم وصول الرسالة الا بعد تصدير المعاملة[4.1]")
  public void addOutgoingDraftTransaction() {

    SHAFT.TestData.JSON attachmentsData =
        new SHAFT.TestData.JSON("inTransactionDraftData.json");

    // 1️⃣ تسجيل الدخول
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    OutgoingDraftTransactionPage outgoingDraftTransactionPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewOutgoingDraftransaction().addRelevantperson().Returntobasicdata();
    outgoingDraftTransactionPage.expandAttachmentSection();
    outgoingDraftTransactionPage.addAttachment(attachmentsData.getTestData("attachment1.type")
            , attachmentsData.getTestData("attachment1.location"),
            attachmentsData.getTestData("attachment1.validity")).saveOutgoingDraftTransaction();

    String transactionNumber = outgoingDraftTransactionPage.getTransactionNumberFromConfirmation();

    outgoingDraftTransactionPage = outgoingDraftTransactionPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver))
        .editFirstInTransaction2(new OutgoingDraftTransactionPage(driver));
  }

}
