package unifiedNumber;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.orgUnitTransactions.OrgUnitRecievalPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.unifiedNumber.UnifiedNumberPage;

public class Deliverystatementverificationtest extends TestBase {

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

  @Test(description = "التحقق من صحة بيانات بيان التسليم مقابل سجل الإحالة في شاشة الرقم الموحد [9.5]")
  @Description("التحقق من ايقونة طباعة بيان التسليم وان البيان مطابق لسجل المعاملة[9.5]")
  public void verifyDeliveryStatementMatchesReferralRecordInUnifiedNumber() {

    SHAFT.TestData.JSON data = new SHAFT.TestData.JSON("internalTransactionData.json");

    // ── Step 1: Login ─────────────────────────────────────────────────────
    LoginPage loginPage = new LoginPage(driver);
    MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
    // ── Step 2: Create a new internal transaction ─────────────────────────
    InternalTransactionDraftPage internalTransactionDraftPage =
        myTransactionsPage.getTransactionsOperationsComponent()
            .addNewOutboundInternalTransaction()
            .addNewInternalTransaction();

    // Save transaction
    internalTransactionDraftPage.saveInternalTransaction();
    String transactionNumber =
        internalTransactionDraftPage.getTransactionNumberFromConfirmation();

    // Search for transaction
    myTransactionsPage =
        internalTransactionDraftPage.goBackToMyTransactionPage()
            .getTransactionsOperationsComponent()
            .searchForTransactionWithId(transactionNumber, myTransactionsPage);

    String transactionSubject =
        myTransactionsPage.getFirstTransactionDescription();

    // Edit transaction
    internalTransactionDraftPage =
        internalTransactionDraftPage.editFirstInTransaction();

    // Send & Print Delivery Statement

    /* سوف يتم استخدام الميثود الاساسيةوهي (sendAndPrintDeliveryStatementForModifiedInTransaction) عند حل مشكلة طباعة بيان التسليم */
    internalTransactionDraftPage
        .sendAndPrintDeliveryStatementForModifiedInTransaction1();


    // ── Step 7 + 8: Navigate to Unified Number and search ─────────────────
    UnifiedNumberPage unifiedNumberPage = myTransactionsPage.getHMComponent()
        .navigateToUnifiedNumberTab().searchForTransactionWithNumber(transactionNumber);
    Validations.verifyThat()
        .object(unifiedNumberPage.confirmValueExistenceInResultsGrid(transactionNumber)).isTrue();

/*    UnifiedNumberPage unifiedNumberPage =
        myTransactionsPage
            .getHMComponent()
            .navigateToUnifiedNumberTab()
            .searchForTransactionWithNumber(transactionNumber);*/

    // ── Step 9: Confirm the transaction appears in the results grid ────────
    Validations.verifyThat()
        .object(unifiedNumberPage.confirmValueExistenceInResultsGrid(transactionNumber))
        .isEqualTo(true)
        .withCustomReportMessage(
            "يجب أن يظهر رقم المعاملة في جدول نتائج الرقم الموحد بعد البحث"
        );

    // ── Steps 10 + 11: Print delivery statement → open referrals table ────
    //
    //   printDeliveryStatement() clicks both buttons in sequence and waits
    //   for the referral table's first row to be ready before returning.
    //
    unifiedNumberPage.printDeliveryStatement();

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
