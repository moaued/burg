package transactions.المعاملة_الداخلية;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.qameta.allure.Description;




public class AddInternalTransactionTest extends TestBase {


  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod
  public void afterTest() {
    driver.quit();
  }

//    =============================

  @Test(description = "[2.1] إضافة معاملة داخلية")
  @Description("انشاء معاملة داخلية مع اضافة 3 مرفقات بصلاحيات مختلفة مع إضافة ملف على اصل خطاب المعاملة، واضافة بيانات صاحب علاقة وتحديد خيار ارسال رسالة نصية والتحقق من وصول الرسالة")
  public void addInternalTransaction() {


    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    InternalTransactionDraftPage internalTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewOutboundInternalTransaction()
        .addNewInternalTransaction().addRelevantperson().Returntobasicdata();
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


  }






@Test(description = "[2.2] تعديل معاملة داخلية")
@Description("تعديل معاملة داخلية")
public void editInternalTransaction() {


  SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
  LoginPage loginPage = new LoginPage(driver);
  MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
  InternalTransactionDraftPage internalTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
      .addNewOutboundInternalTransaction()
      .addNewInternalTransaction().addRelevantperson().Returntobasicdata();
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

}

}