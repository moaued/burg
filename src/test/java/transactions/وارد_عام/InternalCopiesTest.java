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
import pages.transactions.TransactionCopiesDetailsPage;
import pages.transactions.TransactionsCopiesPage;

public class InternalCopiesTest extends TestBase {

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
   //This test is disabled because we can't find a way till the moment to find the target transaction type directly
//     @Test(description = "[1.7.1] اضافة نسخ داخلية الى معاملة وارد جهات")
//    @Description("[1.7.1] اضافة نسخ داخلية الى معاملة وارد جهات")
//    public void addInternalCopiesToInTransaction() {
//        SHAFT.TestData.JSON inTransactionData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
//        InTransactionDraftPage inTransactionDraftPage;
//        LoginPage loginPage = new LoginPage(driver);
//        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
//        int numberOfAttachmentsAtFirstTransaction = myTransactionsPage.searchForGeneralInTransaction().getFirstTrxDestinationAttachmentsNumber();
//
//        //if the transaction doesn't contain attachment, add 3 attachments & save it
//        if (numberOfAttachmentsAtFirstTransaction < 3) {
//            inTransactionDraftPage = myTransactionsPage.modifyFirstGeneralIncomingTransactionDestination().goToAttachmentsTab();
//
//            String transactionDraftNumber = inTransactionDraftPage.getInTransactionNumber();
//            int numberOfAttachmentsBefore = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
//
//            inTransactionDraftPage.addAttachment(inTransactionData.getTestData("attachment1.type")
//                    , inTransactionData.getTestData("attachment1.location"), inTransactionData.getTestData("attachment1.validity"));
//            int numberOfAttachmentsAfterFirstAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
//            Validations.verifyThat().number(numberOfAttachmentsAfterFirstAttachment).isGreaterThan(numberOfAttachmentsBefore);
//
//            inTransactionDraftPage.addAttachment(inTransactionData.getTestData("attachment2.type")
//                    , inTransactionData.getTestData("attachment2.location"), inTransactionData.getTestData("attachment2.validity"));
//            int numberOfAttachmentsAfterSecondAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
//            Validations.verifyThat().number(numberOfAttachmentsAfterSecondAttachment).isGreaterThan(numberOfAttachmentsAfterFirstAttachment);
//
//            inTransactionDraftPage.addAttachment(inTransactionData.getTestData("attachment3.type")
//                    , inTransactionData.getTestData("attachment3.location"), inTransactionData.getTestData("attachment3.validity"));
//            int numberOfAttachmentsAfterThirdAttachment = inTransactionDraftPage.getNumberOfAttachmentsInGrid();
//            Validations.verifyThat().number(numberOfAttachmentsAfterThirdAttachment).isGreaterThan(numberOfAttachmentsAfterSecondAttachment);
//
//            // inTransactionDraftPage.saveModifiedTransaction().printAssignmentPaperFromConfirmation();
//            inTransactionDraftPage.saveModifiedTransaction();
//            inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
//                    .searchForTransactionWithId(transactionDraftNumber, new MyTransactionsPage(driver));
//        }
//        inTransactionDraftPage = myTransactionsPage.editFirstInTransaction();
//        String transactionNumber = inTransactionDraftPage.getInTransactionNumber();
//        inTransactionDraftPage.addInternalCopies(inTransactionData.getTestData("copy1.orgUnitNum"), inTransactionData.getTestData("copy1.copyReason"), 1);
//            myTransactionsPage = inTransactionDraftPage.saveModifiedTransaction().goBackToMyTransactionPage();
//       myTransactionsPage =  myTransactionsPage.getSystemAdminComponent()
//               .changeDepartment(inTransactionData.getTestData("copy1.orgUnitName"));
//
//    }
//
    //======================
//    @Test(description = "[1.7.1] اضافة نسخ داخلية الى معاملة وارد جهات")
//    @Description("[1.7.1] اضافة نسخ داخلية الى معاملة وارد جهات")
//    public void addInternalCopiesToInDestinationTransaction() {
//        SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
//        LoginPage loginPage = new LoginPage(driver);
//        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
//        InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
//                .addNewIncomingTransaction().addInGeneralTransactionDestination();
//    //Add 3 Attachments
//        inTransactionDraftPage.expandAttachmentSection();
//        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
//                , attachmentsData.getTestData("attachment1.location") , attachmentsData.getTestData("attachment1.validity"));
//        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment2.type")
//                , attachmentsData.getTestData("attachment2.location") , attachmentsData.getTestData("attachment2.validity"));
//        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment3.type")
//                , attachmentsData.getTestData("attachment3.location") , attachmentsData.getTestData("attachment3.validity"));
//
//        inTransactionDraftPage.saveInTransaction();
//        String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
//
//       inTransactionDraftPage = inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
//               .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver)).editFirstInTransaction();
//
//        inTransactionDraftPage.addInternalCopies(attachmentsData
//                .getTestData("copy1.orgUnitNum"), attachmentsData.getTestData("copy1.copyReason"), 1);
//        inTransactionDraftPage.addInternalCopies(attachmentsData
//                .getTestData("copy2.orgUnitNum"), attachmentsData.getTestData("copy2.copyReason"), 2);
//        inTransactionDraftPage.addInternalCopies(attachmentsData
//                .getTestData("copy3.orgUnitNum"), attachmentsData.getTestData("copy3.copyReason"), 3);
//
//        String copyType1 = inTransactionDraftPage.getAttachmentCopyDescription().getFirst();
//        String copyType2 = inTransactionDraftPage.getAttachmentCopyDescription().get(1);
//        String copyType3 = inTransactionDraftPage.getAttachmentCopyDescription().getLast();
//
////        Validate the first copy after switching to its department
//        myTransactionsPage = inTransactionDraftPage.saveModifiedTransaction().goBackToMyTransactionPage().getSystemAdminComponent()
//                .changeDepartment(attachmentsData.getTestData("copy1.orgUnitName"));
//      myTransactionsPage.getHMComponent()
//          .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
//      myTransactionsPage.printFirstTransaction();
//        TransactionsCopiesPage transactionsCopiesPage = myTransactionsPage.navigateToTransactionsCopies();
//        String transactionCopyNumber = transactionsCopiesPage.getTransactionsOperationsComponent()
//                .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();
//        Validations.verifyThat().object(transactionCopyNumber).isEqualTo(transactionNumber);
////
//        TransactionCopiesDetailsPage transactionCopiesDetailsPage = transactionsCopiesPage.goToTransactionCopyDetails();
//        String transactionNumberFromCopy1 = transactionCopiesDetailsPage.getTransactionNumber();
//        boolean isAttachmentTypePresent1 = transactionCopiesDetailsPage
//                .confirmExistenceOfSpecificContentInTableOfCopies(copyType1);

//        Validations.verifyThat().object(transactionNumberFromCopy1).isEqualTo(transactionNumber);
//        Validations.verifyThat().object(isAttachmentTypePresent1).isTrue();
//
//        //Validate the second copy after switching to its department
//        myTransactionsPage = transactionsCopiesPage.getSystemAdminComponent()
//                .changeDepartment(attachmentsData.getTestData("copy2.orgUnitName"));
//      myTransactionsPage.getHMComponent()
//          .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
//      myTransactionsPage.printFirstTransaction();
//        transactionsCopiesPage =  myTransactionsPage.navigateToTransactionsCopies();
//        String transactionCopyNumber2 =transactionsCopiesPage.getTransactionsOperationsComponent()
//                .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();
//        Validations.verifyThat().object(transactionCopyNumber2).isEqualTo(transactionNumber);
//
//        transactionCopiesDetailsPage = transactionsCopiesPage.goToTransactionCopyDetails();
//        String transactionNumberFromCopy2 = transactionCopiesDetailsPage.getTransactionNumber();
//        boolean isAttachmentTypePresent2 = transactionCopiesDetailsPage
//                .confirmExistenceOfSpecificContentInTableOfCopies(copyType2);
//
//        Validations.verifyThat().object(transactionNumberFromCopy2).isEqualTo(transactionNumber);
//        Validations.verifyThat().object(isAttachmentTypePresent2).isTrue();
//
//        //Validate the third copy after switching to its department
//        myTransactionsPage = transactionsCopiesPage.getSystemAdminComponent()
//                .changeDepartment(attachmentsData.getTestData("copy3.orgUnitName"));
//      myTransactionsPage.getHMComponent()
//          .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
//      myTransactionsPage.printFirstTransaction();
//        String transactionCopyNumber3 = myTransactionsPage.navigateToTransactionsCopies().getTransactionsOperationsComponent()
//                .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();
//        Validations.verifyThat().object(transactionCopyNumber3).isEqualTo(transactionNumber);
//
//        transactionCopiesDetailsPage = transactionsCopiesPage.goToTransactionCopyDetails();
//        String transactionNumberFromCopy3 = transactionCopiesDetailsPage.getTransactionNumber();
//        boolean isAttachmentTypePresent3 = transactionCopiesDetailsPage
//                .confirmExistenceOfSpecificContentInTableOfCopies(copyType3);
//
//        Validations.verifyThat().object(transactionNumberFromCopy3).isEqualTo(transactionNumber);
//        Validations.verifyThat().object(isAttachmentTypePresent3).isTrue();
//    }
//
    //======================
    @Test(description = "[1.7.2] اضافة نسخ داخلية الى معاملة وارد أفراد")
    @Description("[1.7.2] اضافة نسخ داخلية الى معاملة وارد أفراد")
    public void addInternalCopiesToInIndividualTransaction() {
        SHAFT.TestData.JSON attachmentsData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
        LoginPage loginPage = new LoginPage(driver);
        MyTransactionsPage myTransactionsPage = loginPage.loginToTheApp();
        InTransactionDraftPage inTransactionDraftPage = myTransactionsPage.getTransactionsOperationsComponent()
                .addNewIncomingTransaction().addInGeneralTransactionIndividual();
        //Add 3 Attachments
        inTransactionDraftPage.expandAttachmentSection();
        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment1.type")
                , attachmentsData.getTestData("attachment1.location") , attachmentsData.getTestData("attachment1.validity"));
        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment2.type")
                , attachmentsData.getTestData("attachment2.location") , attachmentsData.getTestData("attachment2.validity"));
        inTransactionDraftPage.addAttachment(attachmentsData.getTestData("attachment3.type")
                , attachmentsData.getTestData("attachment3.location") , attachmentsData.getTestData("attachment3.validity"));

        inTransactionDraftPage.saveInTransaction();
        String transactionNumber = inTransactionDraftPage.getTransactionNumberFromConfirmation();
        inTransactionDraftPage = inTransactionDraftPage.goBackToMyTransactionPage().getTransactionsOperationsComponent()
                .searchForTransactionWithId(transactionNumber, new MyTransactionsPage(driver)).editFirstInTransaction();

        inTransactionDraftPage.addInternalCopies(attachmentsData
                .getTestData("copy1.orgUnitNum"), attachmentsData.getTestData("copy1.copyReason"), 1);
        inTransactionDraftPage.addInternalCopies(attachmentsData
                .getTestData("copy2.orgUnitNum"), attachmentsData.getTestData("copy2.copyReason"), 2);
        inTransactionDraftPage.addInternalCopies(attachmentsData
                .getTestData("copy3.orgUnitNum"), attachmentsData.getTestData("copy3.copyReason"), 3);

        String copyType1 = inTransactionDraftPage.getAttachmentCopyDescription().getFirst();
        String copyType2 = inTransactionDraftPage.getAttachmentCopyDescription().get(1);
        String copyType3 = inTransactionDraftPage.getAttachmentCopyDescription().getLast();

//        //Validate the first copy after switching to its department
//        myTransactionsPage = inTransactionDraftPage.saveModifiedTransaction().goBackToMyTransactionPage().getSystemAdminComponent()
//                .changeDepartment(attachmentsData.getTestData("copy1.orgUnitName"));
//              myTransactionsPage.getHMComponent()
//          .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
//      myTransactionsPage.printFirstTransaction();
//        TransactionsCopiesPage transactionsCopiesPage = myTransactionsPage.navigateToTransactionsCopies();
//        String transactionCopyNumber = transactionsCopiesPage.getTransactionsOperationsComponent()
//                .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();
//
//        Validations.verifyThat().object(transactionCopyNumber).isEqualTo(transactionNumber);

//        TransactionCopiesDetailsPage transactionCopiesDetailsPage = transactionsCopiesPage.goToTransactionCopyDetails();
//        String transactionNumberFromCopy1 = transactionCopiesDetailsPage.getTransactionNumber();
//        boolean isAttachmentTypePresent1 = transactionCopiesDetailsPage
//                .confirmExistenceOfSpecificContentInTableOfCopies(copyType1);
////
//        Validations.verifyThat().object(transactionNumberFromCopy1).isEqualTo(transactionNumber);
//        Validations.verifyThat().object(isAttachmentTypePresent1).isTrue();
//
//        //Validate the second copy after switching to its department
//        myTransactionsPage = transactionsCopiesPage.getSystemAdminComponent()
//                .changeDepartment(attachmentsData.getTestData("copy2.orgUnitName"));
//        myTransactionsPage.getHMComponent()
//            .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
//      myTransactionsPage.printFirstTransaction();
//        transactionsCopiesPage =  myTransactionsPage.navigateToTransactionsCopies();
//        String transactionCopyNumber2 =transactionsCopiesPage.getTransactionsOperationsComponent()
//                .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();
//        Validations.verifyThat().object(transactionCopyNumber2).isEqualTo(transactionNumber);
//
//        transactionCopiesDetailsPage = transactionsCopiesPage.goToTransactionCopyDetails();
//        String transactionNumberFromCopy2 = transactionCopiesDetailsPage.getTransactionNumber();
//        boolean isAttachmentTypePresent2 = transactionCopiesDetailsPage
//                .confirmExistenceOfSpecificContentInTableOfCopies(copyType2);
//
//        Validations.verifyThat().object(transactionNumberFromCopy2).isEqualTo(transactionNumber);
//        Validations.verifyThat().object(isAttachmentTypePresent2).isTrue();
//
//        //Validate the third copy after switching to its department
//        myTransactionsPage = transactionsCopiesPage.getSystemAdminComponent()
//                .changeDepartment(attachmentsData.getTestData("copy3.orgUnitName"));
//   myTransactionsPage.getHMComponent()
//            .navigateToOrgUnitTransactionsTab().getHMComponent().receivinganddistributingimages();
//      myTransactionsPage.printFirstTransaction();
//        String transactionCopyNumber3 = myTransactionsPage.navigateToTransactionsCopies().getTransactionsOperationsComponent()
//                .searchForTransactionWithId(transactionNumber, new TransactionsCopiesPage(driver)).getFirstTransactionNumber();
//        Validations.verifyThat().object(transactionCopyNumber3).isEqualTo(transactionNumber);
//
//        transactionCopiesDetailsPage = transactionsCopiesPage.goToTransactionCopyDetails();
//        String transactionNumberFromCopy3 = transactionCopiesDetailsPage.getTransactionNumber();
//        boolean isAttachmentTypePresent3 = transactionCopiesDetailsPage
//                .confirmExistenceOfSpecificContentInTableOfCopies(copyType3);
//
//        Validations.verifyThat().object(transactionNumberFromCopy3).isEqualTo(transactionNumber);
//        Validations.verifyThat().object(isAttachmentTypePresent3).isTrue();
   }
}
