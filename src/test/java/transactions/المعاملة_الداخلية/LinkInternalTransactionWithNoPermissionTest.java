package transactions.المعاملة_الداخلية;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;

public class LinkInternalTransactionWithNoPermissionTest extends TestBase {

  String restrictedTransactionNumber;

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//    @AfterMethod
//  public void afterTest() {
//    driver.quit();
//  }

//    =============================

  @Test(description = "إضافة ربط على معاملة داخلية برقم معاملة بدون صلاحية ثم الحفظ[2.7]")
  @Description("التحقق من إمكانية ربط معاملة داخلية برقم معاملة تابع لإدارة أخرى بدون صلاحية (أصل أو نسخ) مع حفظ المعاملة بنجاح")
  public void linkInternalTransactionWithNoPermissionTransaction() {

      // 👈 هنا نعرّف linkingData
      SHAFT.TestData.JSON linkingData =
          new SHAFT.TestData.JSON("internalTransactionData.json");


      // 1️⃣ تسجيل الدخول
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // 2️⃣ إنشاء معاملة داخلية جديدة

      InternalTransactionDraftPage internalTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
          .addNewOutboundInternalTransaction()
          .addNewInternalTransactionLink().addRelevantperson().Returntobasicdata();

    // 4️⃣ تفعيل خيار الربط وإدخال رقم معاملة بدون صلاحية
//    driver.element()
//        .click(internalTransactionDraftPage.getIsLinkedTransactionCheckBox())
//        .type(internalTransactionDraftPage.getLinkedTransactionNumberField(),
//            restrictedTransactionNumber);


      internalTransactionDraftPage
          .goToTransactionLinkingTab()
          .addLinkedTransaction(
              linkingData.getTestData("linking.year"),
              linkingData.getTestData("linking.transactionNumber"));

//      Validations.verifyThat()
//          .object(internalTransactionDraftPage.getLinkedTransactionsCount())
//          .isGreaterThan(0);


      // 5️⃣ حفظ المعاملة
      internalTransactionDraftPage.saveInternalTransaction();

    String createdTransactionNumber =
        internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    // 6️⃣ التحقق من نجاح الحفظ
    Validations.verifyThat()
        .object(createdTransactionNumber)
        .isNotNull()
        .perform();

    // 7️⃣ الرجوع إلى صفحة معاملاتي
    myTransactionsPage = internalTransactionDraftPage.goBackToMyTransactionPage();

    // 8️⃣ البحث عن المعاملة والتأكد من وجودها
    myTransactionsPage.getTransactionsOperationsComponent()
        .searchForTransactionWithId(createdTransactionNumber, myTransactionsPage);

    Validations.verifyThat()
        .object(myTransactionsPage.getFirstTransactionNumber())
        .isEqualTo(createdTransactionNumber)
        .perform();
  }

}
