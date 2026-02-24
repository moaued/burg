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

public class ModifyOutgoingDraftTransactionTest extends TestBase {


  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

  @AfterMethod
  public void afterTest() {
    driver.quit();
  }

  @Test(description = "[3.2] تعديل مسودة صادر")
  @Description("إنشاء مسودة صادر، ثم تعديل موضوع المعاملة والتحقق من التعديل[3.2]")
  public void modifyOutgoingDraftTransaction() {

    // Test Data
    SHAFT.TestData.JSON attachmentsData =
        new SHAFT.TestData.JSON("inTransactionDraftData.json");

    // 1️⃣ تسجيل الدخول
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // 2️⃣ إنشاء مسودة صادر
    OutgoingDraftTransactionPage outgoingDraftPage =
        myTransactionsPage.getTransactionsOperationsComponent()
            .addNewOutgoingDraftransaction()
            .addNewInternalTransaction()
            .addRelevantperson()
            .Returntobasicdata();

    // 3️⃣ إضافة مرفق
    outgoingDraftPage.expandAttachmentSection();
    outgoingDraftPage.addAttachment(
        attachmentsData.getTestData("attachment1.type"),
        attachmentsData.getTestData("attachment1.location"),
        attachmentsData.getTestData("attachment1.validity")
    );

    // 4️⃣ حفظ المسودة
    outgoingDraftPage.saveOutgoingDraftTransaction();
    String transactionNumber =
        outgoingDraftPage.getTransactionNumberFromConfirmation();

    // 5️⃣ البحث عن المعاملة
    myTransactionsPage = outgoingDraftPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));

    // 6️⃣ تعديل المعاملة
    outgoingDraftPage = myTransactionsPage
        .editFirstInTransaction3()
        .modifyInTransactionSubject();


    String modifiedSubject =
        outgoingDraftPage.getTransactionSubject();

    // 7️⃣ البحث مرة أخرى للتحقق
    myTransactionsPage = outgoingDraftPage.goBackToMyTransactionPage()
        .getTransactionsOperationsComponent()
        .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));

    // 8️⃣ التحقق
    String transactionNumberOnCard =
        myTransactionsPage.getFirstTransactionNumber();
    String transactionSubjectOnCard =
        myTransactionsPage.getFirstTransactionDescription();

    Validations.assertThat().object(transactionNumberOnCard)
        .equals(transactionNumber);

    Validations.assertThat().object(transactionSubjectOnCard)
        .equals(modifiedSubject);
  }

}
