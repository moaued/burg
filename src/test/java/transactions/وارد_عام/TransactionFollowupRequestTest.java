package transactions.وارد_عام;

import base.TestBase;
import com.shaft.driver.SHAFT;
import com.shaft.validation.Validations;
import io.qameta.allure.Description;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.transactions.*;

public class TransactionFollowupRequestTest extends TestBase {

    @BeforeMethod
    public void beforeTest() {
        testData = new SHAFT.TestData.JSON("appData.json");
        openBuragApp();
    }

    @AfterMethod
    public void afterTest() {
        driver.quit();
    }

    //======================
    @Test(description = "[1.6.1] اضافة طلب متابعة الى معاملة وارد جهات من خلال تعديل المعاملة")
    @Description("[1.6.1] اضافة طلب متابعة الى معاملة وارد جهات من خلال تعديل المعاملة")
    public void addFollowupRequestToInDestinationTransaction() {
        SHAFT.TestData.JSON followupData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
                .addNewIncomingTransaction().addInGeneralTransactionDestination();
        inTransactionDraftPage.expandAttachmentSection();
        inTransactionDraftPage.addAttachment(followupData.getTestData("attachment1.type")
                , followupData.getTestData("attachment1.location"), followupData.getTestData("attachment1.validity"));

        inTransactionDraftPage.saveInTransaction();
        String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();

        myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
                .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
        int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

        myTransactionsPage.editFirstInTransaction();
        myTransactionsPage = inTransactionDraftPage.addTransactionFollowup(
                followupData.getTestData("followup1.orgUnitNum")).saveModifiedTransaction().goBackToMyTransactionPage();
        myTransactionsPage.getSystemAdminComponent().changeDepartment(followupData.getTestData("followup1.orgUnitName"));
        myTransactionsPage.getHMComponent()
            .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
        myTransactionsPage.printFirstTransaction();
        TransactionsFollowupPage transactionsFollowupPage = myTransactionsPage.navigateToTransactionFollowup();
        transactionsFollowupPage = transactionsFollowupPage.navigateToSentFollowUpTab().getTransactionsOperationsComponent()
                .searchForTransactionWithId(transactionNumber, transactionsFollowupPage);

        String firstTransactionFollowupNumberOnCard = transactionsFollowupPage.getFirstTransactionNumber();
        int numberOfAttachmentsOnFollowupCard = transactionsFollowupPage.getNumberOfAttachmentsOnFirstCard();

//        Validations.verifyThat().object(firstTransactionFollowupNumberOnCard).isEqualTo(transactionNumber);
        Validations.verifyThat().number(numberOfAttachmentsOnFollowupCard).isEqualTo(numberOfAttachmentsOnCard);
    }
//
//    //======================
    @Test(description = "[1.6.2] اضافة طلب متابعة الى معاملة وارد أفراد من خلال تعديل المعاملة")
    @Description("[1.6.2] اضافة طلب متابعة الى معاملة وارد أفراد من خلال تعديل المعاملة")
    public void addFollowupRequestToInIndividualTransaction() {
        SHAFT.TestData.JSON followupData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
                .addNewIncomingTransaction().addInGeneralTransactionIndividual();
        inTransactionDraftPage.expandAttachmentSection();
        inTransactionDraftPage.addAttachment(followupData.getTestData("attachment1.type")
                , followupData.getTestData("attachment1.location"), followupData.getTestData("attachment1.validity"));

        inTransactionDraftPage.saveInTransaction();
        String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();

        myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
                .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
        int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

        myTransactionsPage.editFirstInTransaction();
        myTransactionsPage = inTransactionDraftPage.addTransactionFollowup(
                followupData.getTestData("followup1.orgUnitNum")).saveModifiedTransaction().goBackToMyTransactionPage();
        myTransactionsPage.getSystemAdminComponent().changeDepartment(followupData.getTestData("followup1.orgUnitName"));
        myTransactionsPage.getHMComponent()
            .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
        myTransactionsPage.printFirstTransaction();
        TransactionsFollowupPage transactionsFollowupPage = myTransactionsPage.navigateToTransactionFollowup();
        transactionsFollowupPage = transactionsFollowupPage.navigateToSentFollowUpTab().getTransactionsOperationsComponent()
            .searchForTransactionWithId(transactionNumber, transactionsFollowupPage);

        String firstTransactionFollowupNumberOnCard = transactionsFollowupPage.getFirstTransactionNumber();
        int numberOfAttachmentsOnFollowupCard = transactionsFollowupPage.getNumberOfAttachmentsOnFirstCard();

        Validations.verifyThat().object(firstTransactionFollowupNumberOnCard).isEqualTo(transactionNumber);
        Validations.verifyThat().number(numberOfAttachmentsOnFollowupCard).isEqualTo(numberOfAttachmentsOnCard);
    }

    //======================
    @Test(description = "[1.6.3] اضافة طلب متابعة الى معاملة وارد أفراد من ايقونة الاحالة المباشرة")
    @Description("[1.6.3] اضافة طلب متابعة الى معاملة وارد أفراد من ايقونة الاحالة المباشرة")
    public void addFollowupRequestFromIndividualTransactionCard() {
        SHAFT.TestData.JSON followupData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
                .addNewIncomingTransaction().addInGeneralTransactionIndividual();
        inTransactionDraftPage.expandAttachmentSection();
        inTransactionDraftPage.addAttachment(followupData.getTestData("attachment1.type")
                , followupData.getTestData("attachment1.location"), followupData.getTestData("attachment1.validity"));

        inTransactionDraftPage.saveInTransaction();
        String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
        myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
                .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
        int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

        myTransactionsPage = myTransactionsPage.selectFirstTransaction().navigateToDirectReferral()
                        .addNewReferral( followupData.getTestData("referral1.orgUnitNum"),
                                followupData.getTestData("referral1.actionType"),
                                followupData.getTestData("referral1.deliveryMethod"));
        myTransactionsPage.getSystemAdminComponent().changeDepartment(followupData.getTestData("referral1.followUpUnit"));
        myTransactionsPage.getHMComponent()
            .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
        myTransactionsPage.printFirstTransaction();
        TransactionsFollowupPage transactionsFollowupPage = myTransactionsPage.navigateToTransactionFollowup();
        transactionsFollowupPage = transactionsFollowupPage.navigateToSentFollowUpTab().getTransactionsOperationsComponent()
                .searchForTransactionWithId(transactionNumber, new TransactionsFollowupPage(driver));

        String firstTransactionFollowupNumberOnCard = transactionsFollowupPage.getFirstTransactionNumber();
        int numberOfAttachmentsOnFollowupCard = transactionsFollowupPage.getNumberOfAttachmentsOnFirstCard();

        Validations.verifyThat().object(firstTransactionFollowupNumberOnCard).equals(transactionNumber);
        Validations.verifyThat().number(numberOfAttachmentsOnFollowupCard).equals(numberOfAttachmentsOnCard);
    }

    //======================
    @Test(description = "[1.6.4] اضافة طلب متابعة الى معاملة وارد جهات من ايقونة الاحالة المباشرة")
    @Description("[1.6.4] اضافة طلب متابعة الى معاملة وارد جهات من ايقونة الاحالة المباشرة")
    public void addFollowupRequestFromDestinationTransactionCard() {
        SHAFT.TestData.JSON followupData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
                .addNewIncomingTransaction().addInGeneralTransactionDestination();
        inTransactionDraftPage.expandAttachmentSection();
        inTransactionDraftPage.addAttachment(followupData.getTestData("attachment2.type")
                , followupData.getTestData("attachment2.location"), followupData.getTestData("attachment2.validity"));

        inTransactionDraftPage.saveInTransaction();
        String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
        myTransactionsPage = inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
                .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver));
        int numberOfAttachmentsOnCard = myTransactionsPage.getNumberOfAttachmentsOnFirstCard();

        myTransactionsPage = myTransactionsPage.selectFirstTransaction().navigateToDirectReferral()
                .addNewReferral( followupData.getTestData("referral2.orgUnitNum"),
                        followupData.getTestData("referral2.actionType"),
                        followupData.getTestData("referral2.deliveryMethod"));
        myTransactionsPage.getSystemAdminComponent().changeDepartment(followupData.getTestData("referral2.followUpUnit"));
        myTransactionsPage.getHMComponent()
            .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
        myTransactionsPage.printFirstTransaction();
        TransactionsFollowupPage transactionsFollowupPage = myTransactionsPage.navigateToTransactionFollowup();
        transactionsFollowupPage = transactionsFollowupPage.navigateToSentFollowUpTab().getTransactionsOperationsComponent()
                .searchForTransactionWithId(transactionNumber, new TransactionsFollowupPage(driver));

        String firstTransactionFollowupNumberOnCard = transactionsFollowupPage.getFirstTransactionNumber();
        int numberOfAttachmentsOnFollowupCard = transactionsFollowupPage.getNumberOfAttachmentsOnFirstCard();

        Validations.verifyThat().object(firstTransactionFollowupNumberOnCard).equals(transactionNumber);
        Validations.verifyThat().number(numberOfAttachmentsOnFollowupCard).equals(numberOfAttachmentsOnCard);
    }
}
