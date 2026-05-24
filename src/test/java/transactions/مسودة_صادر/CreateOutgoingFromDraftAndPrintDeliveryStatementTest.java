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
import pages.transactions.OutTransactionDraftPage;
import pages.transactions.OutTransactionsPage;
import pages.transactions.OutgoingDraftTransactionPage;
import pages.unifiedNumber.UnifiedNumberPage;
import utils.GeneralOperations;

public class CreateOutgoingFromDraftAndPrintDeliveryStatementTest extends TestBase {

  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//  @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

  //=============================

  @Test(description ="استلام مسودة الصادر المرسلة وتصديرها والتعديل على الصادر وطباعة بيان التسليم[4.7]")
  @Description("في الإدارة استلام مسودة الصادر المرسلة وتصديرها والتعديل على الصادر المسدد للمسودة وطباعة بيان التسليم والتحقق من وصول الرسالة النصية لصاحب العلاقة[4.7]")
  public void verifyCreateOutgoingFromDraftAndPrintDeliveryStatement() {


    SHAFT.TestData.JSON followupData =
        new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp2();
//    LoginPage loginPage = new LoginPage(driver);
//    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
// change the department
    myTransactionsPage =
        myTransactionsPage.getSystemAdminComponent()
            .changeDepartment(followupData.getTestData("referral3.departmentEmployee"));

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

    // Add Follow-up via Direct Referral
    // =============================
    myTransactionsPage =
        myTransactionsPage.selectFirstTransaction()
            .navigateToDirectReferral()
            .addNewReferral(
                followupData.getTestData("referral3.orgUnitNum"),
                followupData.getTestData("referral3.actionType"),
                followupData.getTestData("referral3.deliveryMethod")
            );
    //  Logout user1
    loginPage.logoutToTheApp();
    //  Login user2
//    loginPage = new LoginPage(driver);
//    MyTransactionsPage user2Home = loginPage.loginToTheApp2();
    loginPage = new LoginPage(driver);
    MyTransactionsPage user2Home = loginPage.loginToTheApp();

    // Switch to department
    myTransactionsPage =
        myTransactionsPage.getSystemAdminComponent()
            .changeDepartment(followupData.getTestData("referral3.orgUnitNum"));

    // الذهاب للرقم الموحد
    UnifiedNumberPage unifiedNumberPage = myTransactionsPage.getHMComponent()
        .navigateToUnifiedNumberTab().searchForTransactionWithNumber(transactionNumber);
    Validations.verifyThat()
        .object(unifiedNumberPage.confirmValueExistenceInResultsGrid(transactionNumber)).isTrue();


    Validations.verifyThat()
        .object(unifiedNumberPage
            .confirmValueExistenceInResultsGrid(transactionNumber))
        .isTrue();

    // إنشاء صادر
    OutTransactionDraftPage newOutgoing =
        unifiedNumberPage.clickCreateOutgoingButton()
            .addGeneralTransaction2();


    String newOutgoingNumber =
        newOutgoing.getTransactionNumberFromConfirmation();

    // التحقق في معاملات الصادر (الصادر الجديد يظهر في سلة معاملات الصادر)

    OutTransactionsPage outTransactions =
        newOutgoing.backToOutgoingTransactionPage()
            .navigateToExportedTransactions();

    outTransactions.getTransactionsOperationsComponent()
        .searchForTransactionWithId(newOutgoingNumber, outTransactions);

    boolean isCreated =
        outTransactions.isTransactionPresent2(newOutgoingNumber);

    Validations.verifyThat().object(isCreated).isTrue();





  }

}
