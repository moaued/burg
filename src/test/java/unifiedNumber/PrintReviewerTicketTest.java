package unifiedNumber;

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
import pages.unifiedNumber.UnifiedNumberPage;

public class PrintReviewerTicketTest extends TestBase {


  @BeforeMethod
  public void beforeTest() {
    testData = new SHAFT.TestData.JSON("appData.json");
    openBuragApp();
  }

//  @AfterMethod
//  public void afterTest() {
//    driver.quit();
//  }
  //=============================

  @Test(description = "التحقق من طباعة تذكرة مراجعة ومطابقتها مع سجل المعاملة")
  @Description("إنشاء معاملة داخلية ثم البحث عنها في الرقم الموحد والتحقق من عمل زر طباعة تذكرة مراجعة وظهور بيانات الإحالة")
  public void verifyPrintReviewerTicketMatchesTransactionRecord() {


    SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();

    // إنشاء معاملة داخلية
    InternalTransactionDraftPage internalTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
        .addNewOutboundInternalTransaction()
        .addNewInternalTransaction().Returntobasicdata()
        .saveInternalTransaction();

    String transactionNumber = internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage();

    // الذهاب للرقم الموحد
    UnifiedNumberPage unifiedNumberPage = myTransactionsPage.getHMComponent()
        .navigateToUnifiedNumberTab().searchForTransactionWithNumber(transactionNumber);
    Validations.verifyThat()
        .object(unifiedNumberPage.confirmValueExistenceInResultsGrid(transactionNumber)).isTrue();


    Validations.verifyThat()
        .object(unifiedNumberPage
            .confirmValueExistenceInResultsGrid(transactionNumber))
        .isTrue()
        .withCustomReportMessage("المعاملة غير موجودة في نتائج الرقم الموحد")
        .perform();

    unifiedNumberPage.clickPrintReviewerTicket();


    // ── Step 12: Capture referral row data ────────────────────────────────
    unifiedNumberPage.captureReferralTableData();

    String fromOrgUnit    = unifiedNumberPage.getCapturedFromOrgUnit();
    String toOrgUnit      = unifiedNumberPage.getCapturedToOrgUnit();
    String assignmentDate = unifiedNumberPage.getCapturedAssignmentDate();

    // ── Assertions: each referral cell must be non-null and non-blank ──────
    Validations.verifyThat()
        .object(unifiedNumberPage.isReferralFromOrgUnitPresent())
        .isEqualTo(true)
        .withCustomReportMessage(
            "عمود الجهة المُحيلة يجب ألا يكون فارغاً في جدول الإحالات"
        );

    Validations.verifyThat()
        .object(unifiedNumberPage.isReferralToOrgUnitPresent())
        .isEqualTo(true)
        .withCustomReportMessage(
            "عمود الجهة المُحال إليها يجب ألا يكون فارغاً في جدول الإحالات"
        );

    Validations.verifyThat()
        .object(unifiedNumberPage.isReferralAssignmentDatePresent())
        .isEqualTo(true)
        .withCustomReportMessage(
            "عمود تاريخ ووقت الإحالة يجب ألا يكون فارغاً في جدول الإحالات"
        );

    // ── Optional: log captured values to Allure report ────────────────────
    Validations.verifyThat()
        .object(fromOrgUnit)
        .isNotNull()
        .withCustomReportMessage("الجهة المُحيلة: " + fromOrgUnit);

    Validations.verifyThat()
        .object(toOrgUnit)
        .isNotNull()
        .withCustomReportMessage("الجهة المُحال إليها: " + toOrgUnit);

    Validations.verifyThat()
        .object(assignmentDate)
        .isNotNull()
        .withCustomReportMessage("تاريخ ووقت الإحالة: " + assignmentDate);





  }


}
