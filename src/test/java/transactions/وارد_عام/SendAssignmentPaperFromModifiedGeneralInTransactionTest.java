package transactions.وارد_عام;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.MyTransactionsPage;
import pages.transactions.TransactionsCopiesPage;

public class SendAssignmentPaperFromModifiedGeneralInTransactionTest extends TestBase {

    @BeforeMethod
    public void beforeTest() {
        testData = new SHAFT.TestData.JSON("appData.json");
        openBuragApp();
    }

    @AfterMethod
//    public void afterTest() {
//        driver.quit();
//    }

    //=============================
    @Test(description = "[1.9] ارسال ورقة الاحالة من خلال تعديل معاملة")
    @Description("[1.9] من التعديل على الوارد يتم ارسال نسخ الكترونية داخلية وخارجية بمرفقات مختلفة عبر ايقونة ورقة الإحالة مع التحقق من وصول المرفقات بحسب ما تم تحديده عند الارسال")
    public void sendAssignmentPaperIncomingTransaction() {
        SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
                .addNewIncomingTransaction().addInGeneralTransactionDestination();
        inTransactionDraftPage.expandAttachmentSection();
        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
                , attachmentsData.getTestData("attachment1.location"), attachmentsData.getTestData("attachment1.validity"));

        inTransactionDraftPage.saveInTransaction();
        String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();

        myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
                .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
        inTransactionDraftPage = myTransactionsPage.editFirstInTransaction();


        inTransactionDraftPage.goToAssignmentPaper().selectOrgUnitFromAssignmentPaper(attachmentsData
                .getTestData("assignmentPaper.orgUnitName"));
        myTransactionsPage = inTransactionDraftPage.sendAssignmentPaper();

        //Validate the first copy after switching to its department
        myTransactionsPage = myTransactionsPage.getSystemAdminComponent()
                .changeDepartment(attachmentsData.getTestData("assignmentPaper.orgUnitName"));
        myTransactionsPage.getHMComponent()
            .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
        myTransactionsPage.printFirstTransaction();
        TransactionsCopiesPage transactionsCopiesPage = myTransactionsPage.navigateToTransactionsCopies();
        String transactionCopyNumber = transactionsCopiesPage.getTransactionsOperationsComponent()
                .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();

        Validations.verifyThat().object(transactionCopyNumber).isEqualTo(transactionNumber);


    }

}
