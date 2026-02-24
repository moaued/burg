package pages.transactions;

import com.shaft.driver.SHAFT;
import com.shaft.driver.SHAFT.GUI.WebDriver;
import com.shaft.gui.internal.locator.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ElementsOperations;
import utils.GeneralOperations;

public class OutgoingDraftTransactionPage {

  SHAFT.TestData.JSON testData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
  private WebDriver driver;
  private By transactionNameTab = By.xpath("//li[contains(@id,'Names')]");
  private By basicInfor=By.xpath("//li[@id='basicInfor']//a[@href='#first-tab']\n");
  private By transactionSubject = By.id("txtAreaSubject");
  private By isLinkedTransactionCheckBox = By.id("Islink");
  private By basicInfoTab = By.id("basicInfo");
  private By transactionSubjectTextField = By.id("OutboundInternalBasicInfoEdit_Remarks");
  //=============================================================
  //==============================SaveTransaction================
  private By saveOutboundExternalTransactionButton = By.id("btnSaveOutboundExternal");
  private By saveTransactionConfirmationModal = By.xpath(
      "//div[contains(@class, 'jconfirm')][contains(@class, 'white')]");
  private By confirmationCancelButton = By.xpath("//button[Choose direction = 'الغاء']");
  private By confirmationAgreeButton = By.xpath("//button[normalize-space() = 'نعم']");
  private By transactionNumberFromConfirmationLabel = By.xpath(
      "//div[contains(@class,'col-xs-4')]/span[contains(@class,'indent-text')]");
  private By backToTheInboxButton =By.xpath("//button[contains(@class,'redirect-to-tray') and contains(normalize-space(),'الرجوع')]");
  private By confirmationAndSuccessModal = By.xpath(
      "//div[contains(@class, 'jconfirm') and contains(@class, 'white')]");
  private By saveModifiedTransactionButton = By.id("btnSaveOutboundInternal");
  private By sendAndPrintDeliveryStatement = By.xpath(
      "//button[contains(@class,'move-to-assignment')]");
  private By civilIdNumber = By.id("TransactionName_CivilID");
  private By firstName = By.id("TransactionName_FirstName");
  //===========================================================================
  //=============================TransactionCopies==========================================
  private By transactionCopiesTab = By.id("transactionCopies");
  private By orgnaisationalUnitNumber = By.xpath(
      "//input[@data-func='GetCopyUsersByOrgUnitId' and @onkeypress='return IsNumeric(event);']");
  private By orgUnitAutoCompleteMenu = By.cssSelector("#divAutoComplateMenu");
  private By firstOrgChartAutoSuggestion = By.cssSelector(
      "#divAutoComplateMenu div:nth-of-type(1)");
  private By copyActionReason = By.id("ddlCopyActionId");
  private By firstAttachmentCheckBox = By.xpath(
      "(//input[contains(@id,'mainDocumentCheckbox')])[2]");
  private String attachmentCheckBoxAtIndex = "(//input[contains(@id,'mainDocumentCheckbox')])[%s]";
  private String attachmentCopyTransactionDescriptionAtIndex = "(//td[@data-name='ArcivingTypeName'])[%s]";
  @Getter
  private List<String> attachmentCopyDescription = new ArrayList<>();
  private By addCopyButton = By.id("btnCopy");
  private By copyRowInGrid = By.xpath(
      "//table[@id='grid-table-grdCopies']//tr[contains(@class,'grid-row')]");
  //============================InternalCopies==========================
  private By electronicCopiesTab = By.id("electronicCopies");
  private By electronicCopiesOrgUnitName = By.xpath(
      "//input[@data-func='GetUsersByExternalPartyId' and contains(@class,'txtDepartmentName')]");
  private By elecCopiesOrgUnitSuggestionMenu = By.id("divAutoComplateMenu");
  private By orgUnitFirstSuggestion = By.xpath(
      "//div[@id='divAutoComplateMenu']/div[position()=1]");
  private By electronicCopiesOrgUnitNumber = By.xpath(
      "//input[@data-func='GetUsersByExternalPartyId' and contains(@class,'txtDepartmentNumber')]");
  private By electronicCopyreason = By.id("ddlExternalCopyActionId");
  private By electronicCopyAddButton = By.id("btnExternalCopy");
  private By numberOfElectronicCopies = By.xpath(
      "//table[@id='grid-table-grdExternalCopies']//tr[contains(@class,'grid-row')]");
  //======================================================================
  //===================================TransactionAttachments================
  private By baseTransactionAttachmentsButton = By.id("btnShowAttachment");
  private By attachmentsTab = By.id("anchorArchiving");
  private By attachmentType = By.id("ddlAttachmentType");
  private By attachmentVailidty = By.id("ddlDocumentValidityId");
  private By uploadAttachment = By.id("aTextEditor");
  private By addAttachmentButton = By.id("btnAddArchive");
  private By chooseAttachmentFile = By.id("TransactionFiles");
  private By uploadAttachmentContainer = By.id("uploadContainer");

  private By firstEditTransactionButotn = Locator.hasTagName("i").containsClass("icon-outline-edit")
      .isFirst().build();
  private By saveTransactionButton = By.id("btnNames");

  private By incomingTransactionNumber = By.xpath("//div[contains(@class, 'transaction-number')]");


  private By copyOrgUnitUsersInput =
      By.id("ddlCopyOrgUnitUsers");
  private By organisationalUnitNumber =
      By.xpath("//input[@data-func='GetCopyUsersByOrgUnitId' and @maxlength='20']");


  private By printBarcodeStickerButton = By.xpath("//button[contains(@class, 'btn-site') and contains(@class, 'barcodeSticker-button-popup')]");
  private By printBarcodeButton = By.xpath("//button[contains(@class, 'barcode-button-popup')]");

  @Getter
  private int numberOfBrowserWindows;
  @Getter
  private boolean isPrintDialogueDisplayed;
  //===============================================================================================
  //================================TransactionAttachments==========================================

  //===========================================================================
  //=========================================BasicInfo=========================
  private By inDataTypeEntities = By.id("Entities");
  private By inDataTypeIndividual = By.id("Individual");
  private By inDestinationName = By.xpath(
      "//input[contains(@class, 'InboundDepName') and contains(@class, 'txtDepartmentName')]");
  private By inboundDocumentNumber = By.id("InboundBasicInfo_InboundDocumentNumber");
  private By subjectTextField = By.id("txtAreaSubject");

  private By inTransactionDateCalendar = By.id("HasInboundDateCal");
  private By inTransactionPriorityLevel = By.id("ddlPriorityLevel");

  private By saveInTransactionButton = By.id("btnSaveInbound");
  private By inDestinationFilter = By.xpath(
      "//span[contains(@class, 'input-group-text') and contains(@class, 'hyperlink')]");
  private By inDestinationModal = By.id("modalBody__dvDestinationTree");
  private By outDestinationEntry = By.xpath(
      "//div[contains(text(),\"" + testData.getTestData("incomingDestination")
          + "\") and contains(@data-name,'DestinationId')]");
  private By isLinkCheckBox = By.id("Islink");
  private By showNamesButton = By.id("btnShowNames");
  //=================================================================================================
  //========================================TransactionLetter=========================================
  private By transactionLetterFrame = By.id("frameViewer");
  private By trasnactionLetterUploadButton = By.id("OpenUpload");
  private By transactionLetterUploadModal = By.xpath(
      "//div[@id='myModal' and contains(@style,'display')]");
  private By transactionLetterUploadArea = By.className("dz-message");
  private By transactionLetterUploadHiddenButton = By.xpath(
      "//input[@type='file' and @class='dz-hidden-input']");
  private By transactionLettersThumbnail = By.xpath("//input[contains(@class,'docThumbFocussed')]");
  private By transactionExitButton = By.xpath(
      "//div[@id='myModal']//button[@type='button' and contains(@class,'btn-st1')]");

  private By remarks = By.id("InboundBasicInfoEdit_Remarks");
  private By confirmationCloseButton = By.xpath("//button[contains(@onclick,'dialog.close')]");

  private By sendSMSCheckBox = By.xpath(
      "//input[@id='TransactionName_SendSMS' and @data-val='true']");
  private By addNameButton = By.xpath("(//button[@id='btnNames'])[1]");
  private By loadingSpinner = By.id("loadingModal");
  private By namesGrid = By.xpath("//tr[contains(@class,'grid-row')]");
  //==================================================================================================================
  //====================================TransactionFollowup===========================================================
  private By followupTab = By.id("followUpCheck");
  private By followupOrgUnitNumber = By.xpath(
      "(//input[@data-func='GetFollowUpUsersByOrgUnitId' and contains(@class,'txtDepartmentNumber')])[1]");
  private By followupOrgUnitAutoCompleteMenu = By.cssSelector("#divAutoComplateMenu");
  private By followupFirstOrgChartAutoSuggestion = By.cssSelector(
      "#divAutoComplateMenu div:nth-of-type(1)");
  private By followupEndDate = By.cssSelector("input#AssignPaperFollowUpDateCal.is-calendarsPicker[name='ummalqura']");





  private By followupDate = By.id("FollowUpDateCal");
  private By addTransactionFollowupButton = By.id("btnConfirmAddFollowUp");
  private By transactionFollowupRequestsGrid = By.id("grid-table-grdFollowUps");
  private By transactionFollowupGridRow = By.xpath(
      " //table[@id='grid-table-grdFollowUps']//tr[contains(@class,'grid-row')]");
  private By followupConfirmationAndSuccessModal = By.xpath(
      "//div[contains(@class, 'jconfirm') and contains(@class, 'white')]");
  private By confirmAddFollowup = By.xpath("//div[contains(@class,'actions-buttons')]/button[1]");
  //==================================================================================================================
  //====================================Confirmation_Popup============================================================
  private By attachmentsTableRows = By.xpath(
      "//table[@id='grid-table-grdArchiving']//tr[contains(@class,'grid-row')]");
  private By confirmationPrintButton = By.xpath("//button[contains(@onclick,'PrintAllBarcode')]");

  private By printQrPaperButton = By.xpath("//button[contains(@class, 'btn-site') and contains(@class, 'barcode-button-popup')]");;
  private By printAssignmentPaperButton = By.xpath("//button[contains(@class,'AssignmentPaper')]");
  private By printAssignmenPaperFromPreview = By.xpath("(//*[@id='printdiv'])[1]");
  //private By printAssignmenPaperFromPreview = By.xpath("//div[@id='printdiv' and not(contains(@style,'display: none'))]");
  private By printqrconfirm=By.xpath("//button[contains(@onclick,'PrintAllBarcode')]");
  private By printConfirmationModal = By.xpath(
      "//div[contains(@class, 'jconfirm') and contains(@class, 'white')]");

  //==================================================================================================================
  //=======================================Assignment_Paper==========================================================
  private By assignmentPaperCta = By.id("assignmentPaper");
  private By assignmentPaperAttachmentOverlay = By.cssSelector(".jconfirm.white");
  //First one after the original copy of the transaction
  private By firstAssignmentPaperAttachmentCheckBox = By.xpath(
      "(//input[contains(@class,' inp')])[2]");
  private By saveAttachmentButton = By.id("btnShowCopiesAttDialog");
  private By sendAssignmentButton = By.id("GetUserDelegationsById");

  private By incomDestinationNumber = By.xpath(
      "//input[contains(@class, 'InboundDepNum') and contains(@class, 'txtDepartmentNumber')]");
  private By outDestinationName = By.xpath(
      "//input[contains(@class, 'InboundDepName') and contains(@class, 'txtDepartmentName')]");
  private By outDestinationFilter = By.xpath(
      "//span[contains(@class, 'input-group-text') and contains(@class, 'hyperlink')]");
  private By outDestinationModal = By.id("modalBody__dvDestinationTree");

  private By uploadNewAttachmentButton = By.xpath(
      "//a[contains(@class,'btn-upload') and @href='#']");

  /*
Using normalize-space() instead of text() in order to handle the white spaces variations
 * */
  private By imageCheckBoxForAssignmentPaperOrgUnit(String orgUnitName) {
    return By.xpath(String.format(
        "(//div[label[normalize-space()='%s']]/following::div[@class='checkbox']/label/span[@class='cr'])[1]",
        orgUnitName));
  }

  private By transferRadioButtonForAssignmentPaperOrgUnit(String orgUnitName) {
    return By.xpath(String.format(
        "(//div[label[normalize-space()='%s']]/following::div[@class='radio']/label/span[@class='cr'])[1]",
        orgUnitName));
  }

  //============================================================================================
  @Getter
  private String transactionNumberFromConfirmation;
  @Getter
  private String modifiedTransactionDescription =
      "Description: " + GeneralOperations.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
  @Getter
  private String followupHijriDate = GeneralOperations.getCurrentHijriDate();
  @Getter
  private String transactionDescription;
  //==================================Variables======================================
  @Getter
  String transactionSubjectText =
      "Description: " + GeneralOperations.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");


  public OutgoingDraftTransactionPage(WebDriver driver) {
    this.driver = driver;
  }

  @Step("تغيير حالة ريط المعاملة")
  private void changeTransactionLinkStatus(boolean isLinkStatus) {
    boolean currentStatus = driver.getDriver().findElement(isLinkedTransactionCheckBox)
        .isSelected();
    if (currentStatus != isLinkStatus) {
      driver.element().click(isLinkedTransactionCheckBox);
    }
    driver.browser().captureScreenshot();
  }

  @Step("اضافة معاملة داخلية جديدة")
  public OutgoingDraftTransactionPage addNewInternalTransaction() {
    changeTransactionLinkStatus(false);
    //driver.element().click(isLinkedTransactionCheckBox);
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(transactionSubject))
        .type(transactionSubject, transactionSubjectText);


    return this;

  }
  @Step("اضافة بيانات صاحب العلاقة ")
  public OutgoingDraftTransactionPage addRelevantperson(){

    driver.element().click(transactionNameTab)
//To be un-commented when needed to send SMS
//                .click(sendSMSCheckBox)
        .type(civilIdNumber, testData.getTestData("civilId") + Keys.ENTER)
        .verifyThat(firstName).text().isNotNull();
    driver.element().scrollToElement(saveTransactionButton).click(saveTransactionButton);

    return this;
  }
  @Step("العوده الي تبويب البيانات الاساسية ")
  public OutgoingDraftTransactionPage Returntobasicdata(){

    driver.element().click(basicInfor);

    changeTransactionLinkStatus(false);
    driver.element().click(outDestinationEntry)
        .verifyThat(outDestinationName).text()
        .contains(testData.getTestData("outgoingDestination"));
    driver.element().type(subjectTextField,
        "Description: " + GeneralOperations.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
    transactionDescription = driver.element().getText(subjectTextField);

//To be un-commented when needed to send SMS
//                .click(sendSMSCheckBox)

    return this;
  }


  @Step("حفظ مسودة صادر")
  public OutgoingDraftTransactionPage saveOutgoingDraftTransaction() {
    driver.element().scrollToElement(saveOutboundExternalTransactionButton )
        .click(saveOutboundExternalTransactionButton)
        .waitUntilNumberOfElementsToBe(saveTransactionConfirmationModal, 1)
        .waitUntil(ElementsOperations.waitForElementToBeReady(confirmationAgreeButton))
        .click(confirmationAgreeButton);
    transactionNumberFromConfirmation = driver.element()
        .getText(transactionNumberFromConfirmationLabel);
    return this;
  }


  //Used when adding a new transaction
  @Step("توسيع قسم الملحقات")
  public OutgoingDraftTransactionPage expandAttachmentSection() {
    driver.element().scrollToElement(baseTransactionAttachmentsButton)
        .click(baseTransactionAttachmentsButton).verifyThat(attachmentType).isVisible();
    return this;
  }

  //Used when modifying a previously added transaction


  @Step("عدد النسخ")
  public int getNumberOfCopyRows() {
    return driver.element().getElementsCount(copyRowInGrid);
  }

  @Step("اضافة ملحقات")
  public OutgoingDraftTransactionPage addAttachment(String transactionAttachmentType,
      String transactionAttachmentLocation, String transactionAttachmentValidity) {
    driver.element().select(attachmentType, transactionAttachmentType);
    //.select(attachmentVailidty, transactionAttachmentVaildity);
    By validityOption = By.xpath("//li[text()='" + transactionAttachmentValidity + "']");
    driver.element().click(attachmentVailidty).click(validityOption);
    showSelectAttachment();
    driver.element()
        .click(uploadAttachment)
        .typeFileLocationForUpload(chooseAttachmentFile, transactionAttachmentLocation)
        .click(addAttachmentButton)
        .verifyThat(uploadAttachmentContainer).attribute("style").contains("none;");
    return this;
  }

  @Step("تعديل المعاملة الأولى / الذهاب الى صفحة \"تعديل المعاملة\"")
  public OutgoingDraftTransactionPage editFirstInTransaction() {
    driver.element().click(firstEditTransactionButotn);
    return new OutgoingDraftTransactionPage(driver);
  }





  @Step("الذهاب الى تبويب النسخ الالكترونية الخارجية")
  private OutgoingDraftTransactionPage goToElectronicCopiesTab() {
    driver.element().click(electronicCopiesTab).verifyThat(electronicCopiesOrgUnitName).isVisible();
    return this;
  }

  @Step("عدد النسخ")
  public int getNumberOfElectronicCopyRows() {
    return driver.element().getElementsCount(numberOfElectronicCopies);
  }

  @Step("اضافة نسخ خارجية")
  public OutgoingDraftTransactionPage addElectronicCopies(String orgUnitName, String orgUnitNumber,
      String copyReason) {
    goToElectronicCopiesTab();
    int numberOfElectronicCopiesRows = getNumberOfElectronicCopyRows();
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(electronicCopiesOrgUnitName))
        .type(electronicCopiesOrgUnitName, orgUnitName)
        .waitUntil(ElementsOperations.waitForElementToBeReady(elecCopiesOrgUnitSuggestionMenu))
        .click(orgUnitFirstSuggestion);
    driver.element().select(electronicCopyreason, copyReason).click(electronicCopyAddButton)
        .waitUntilNumberOfElementsToBeMoreThan(
            numberOfElectronicCopies, numberOfElectronicCopiesRows);
    return this;
  }

  @Step("حفظ المعاملة")
  private void scrollToAndClickSaveButton(By byButtonLocator) {
    driver.element().scrollToElement(byButtonLocator).click(byButtonLocator);
    driver.element().verifyThat(confirmationAndSuccessModal).isVisible();
    driver.element().click(confirmationAgreeButton).verifyThat(confirmationAndSuccessModal)
        .isVisible().perform();
  }

  @Step("حفظ معاملة معدلة")
  public OutgoingDraftTransactionPage saveModifiedTransaction() {
    driver.element().scrollToElement(basicInfoTab).click(basicInfoTab);
    driver.element().type(transactionSubjectTextField,
        "Transaction Description: " + modifiedTransactionDescription);
    scrollToAndClickSaveButton(saveModifiedTransactionButton);
    return this;
  }








  @Step("الحصول على رقم المعاملة من صفحة التعديل")
  public String getInTransactionNumber() {
    return driver.element().getText(incomingTransactionNumber);
  }

  @Step("العودة لصفحة معاملاتى")
  public MyTransactionsPage navigateToMyTransactionsPage() {
    driver.browser().navigateBack();
    return new MyTransactionsPage(driver);
  }

  @Step("الحصول على موضوع المعاملة من صفحة التعديل")
  public String getTransactionSubject() {
    return driver.element().getText(subjectTextField);
  }

  @Step("اضافة ملف على أصل خطاب المعاملة")
  public OutgoingDraftTransactionPage addFileToTransactionLetter(String transactionAttachmentLocation) {
    driver.element().switchToIframe(transactionLetterFrame);
    driver.element().click(trasnactionLetterUploadButton).verifyThat(transactionLetterUploadModal)
        .isVisible();
    org.openqa.selenium.WebDriver wDriver = driver.getDriver();
    ((JavascriptExecutor) wDriver)
        .executeScript("arguments[0].classList.remove('hidden');",
            wDriver.findElement(transactionLetterUploadHiddenButton));
    driver.element().typeFileLocationForUpload(transactionLetterUploadHiddenButton,
        transactionAttachmentLocation);
    driver.element().waitUntilNumberOfElementsToBeMoreThan(transactionLettersThumbnail, 0);
    driver.element().switchToDefaultContent();
    return this;
  }

  @Step("عدد الملحقات")
  public int getNumberOfAttachmentsInGrid() {
    return driver.element().getElementsCount(attachmentsTableRows);
  }

  @Step("تعديل موضوع المعاملة و اعطائه القيمة الحالية للوقت و التاريخ")
  public OutgoingDraftTransactionPage modifyInTransactionSubject() {
    driver.element().type(subjectTextField, modifiedTransactionDescription);
    driver.element().type(transactionSubjectTextField,
        "Transaction Description: " + modifiedTransactionDescription);
    driver.element().click(inTransactionDateCalendar)
        .type(inTransactionDateCalendar, GeneralOperations.getCurrentHijriDate() + Keys.ENTER);
    driver.element().click(inTransactionPriorityLevel)
        .type(inTransactionPriorityLevel, testData.getTestData("incomingPriority"))
        .click(inTransactionPriorityLevel);
    scrollToAndClickSaveButton(saveModifiedTransactionButton);
    return this;
  }


  @Step("حفظ معاملة معدلة 2")
  public OutgoingDraftTransactionPage saveModifiedTransaction2() {
    driver.element().scrollToElement(basicInfoTab).click(basicInfoTab);
//    driver.element().verifyThat(inDestinationName).isVisible();
//    driver.element()  .waitUntil(ElementsOperations.waitForElementToBeReady(saveModifiedTransactionButton));
    scrollToAndClickSaveButton(saveModifiedTransactionButton);
    return this;
  }

  @Step("العودة لصفحة معاملاتى")
  public MyTransactionsPage goBackToMyTransactionPage() {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(backToTheInboxButton));
    driver.element().click(backToTheInboxButton).verifyThat(backToTheInboxButton).doesNotExist();
    return new MyTransactionsPage(driver);
  }

  @Step("اضافة وارد عام - جهات")
  public OutgoingDraftTransactionPage addInGeneralTransactionDestination() {
    driver.element().click(inDataTypeEntities);
    driver.element().click(isLinkCheckBox);
    driver.element().click(inDestinationFilter).verifyThat(inDestinationModal).isVisible();
    driver.element().click(outDestinationEntry)
        .verifyThat(inDestinationName).text().contains(testData.getTestData("incomingDestination"));
    driver.element().type(subjectTextField, modifiedTransactionDescription);
    driver.element().type(inboundDocumentNumber, testData.getTestData("inboundDocumentNumber"));
    driver.element().click(inTransactionDateCalendar)
        .type(inTransactionDateCalendar, GeneralOperations.getCurrentHijriDate() + Keys.ENTER);
    driver.element().click(showNamesButton)
//To be un-commented when needed to send SMS
//                .click(sendSMSCheckBox)
        .type(civilIdNumber, testData.getTestData("civilId")+ Keys.ENTER)
        .verifyThat(firstName).text().isNotNull();
    driver.element().scrollToElement(addNameButton)
        .click(addNameButton).verifyThat(namesGrid).exists();
    return this;
  }

  @Step("اضافة وارد عام - أفراد")
  public OutgoingDraftTransactionPage addInGeneralTransactionIndividual() {
    driver.element().click(inDataTypeIndividual);
    driver.element().click(isLinkCheckBox);
    driver.element().click(inDestinationFilter).verifyThat(inDestinationModal).isVisible();
    driver.element().click(outDestinationEntry)
        .verifyThat(inDestinationName).text().contains(testData.getTestData("incomingDestination"));
    driver.element().type(subjectTextField, modifiedTransactionDescription);
    driver.element()
        //.click(inTransactionDateCalendar)
        .type(inTransactionDateCalendar, GeneralOperations.getCurrentHijriDate() + Keys.ENTER);
    driver.element().click(showNamesButton)
        .waitUntil(ElementsOperations.waitForElementToBeReady(civilIdNumber))
//To be un-commented when needed to send SMS
//                .click(sendSMSCheckBox)
        .type(civilIdNumber, testData.getTestData("civilId") + Keys.ENTER)
        .verifyThat(firstName).text().isNotNull();

    driver.element().scrollToElement(addNameButton)
        .click(addNameButton).verifyThat(namesGrid).exists();
    return this;
  }

  @Step("حفظ معاملة ")
  public OutgoingDraftTransactionPage saveInTransaction() {
    By inputField = driver.element()
        .getElementsCount(saveInTransactionButton) > 0 ? saveInTransactionButton
        : saveModifiedTransactionButton;
    scrollToAndClickSaveButton(inputField);
    transactionNumberFromConfirmation = driver.element()
        .getText(transactionNumberFromConfirmationLabel);
    return this;
  }



  private void showSelectAttachment() {
    org.openqa.selenium.WebDriver wDriver = driver.getDriver();
    ((JavascriptExecutor) wDriver)
        .executeScript("arguments[0].classList.remove('hidden');",
            wDriver.findElement(chooseAttachmentFile));
  }


  @Step("الذهاب الى تبويب النسخ الداخلية الالكترونية")
  private OutgoingDraftTransactionPage goToTransactionCopiesTab() {
    driver.element().click(transactionCopiesTab).verifyThat(orgnaisationalUnitNumber).isVisible();
    return this;
  }

  @Step("اضافة نسخ داخلية")
  public OutgoingDraftTransactionPage addInternalCopies(String orgUnitNum, String copyReason,
      int attachmentIndex) {
    goToTransactionCopiesTab();
    int numOfCopies = getNumberOfCopyRows();
    By attachmentCheckBoxIndexElement = By.xpath(
        String.format(attachmentCheckBoxAtIndex, attachmentIndex + 1));
    By attachmentTypeAtIndex = By.xpath(
        String.format(attachmentCopyTransactionDescriptionAtIndex, attachmentIndex + 1));
    attachmentCopyDescription.add(driver.element().getText(attachmentTypeAtIndex));
    driver.element().click(orgnaisationalUnitNumber).type(orgnaisationalUnitNumber, orgUnitNum)
        .waitUntil(ElementsOperations.waitForElementToBeReady(orgUnitAutoCompleteMenu))
        .click(firstOrgChartAutoSuggestion);

    driver.element().type(copyOrgUnitUsersInput, "مدير النظام");
    driver.element().type(copyOrgUnitUsersInput, Keys.ARROW_DOWN);
    driver.element().type(copyOrgUnitUsersInput, Keys.ENTER);
    driver.element().select(copyActionReason, copyReason);
    driver.element().click(attachmentCheckBoxIndexElement).click(addCopyButton);

    return this;
  }




  @Step("طباعة بيان التسليم اثناء حفظ المعاملة المعدلة")
  public OutgoingDraftTransactionPage sendAndPrintDeliveryStatementForModifiedInTransaction() {
    ((JavascriptExecutor) driver.getDriver()).executeScript(
        "window.onerror = function(message, source, lineno, colno, error) {" +
            "  console.log('Suppressed JS error:', message);" +
            "  return true;" + // Prevents error propagation
            "};"
    );
    driver.element().type(transactionSubjectTextField,
        "Description: " + GeneralOperations.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));

//    driver.element()
//        .scrollToElement(saveModifiedTransactionButton)
//        .waitUntil(ElementsOperations.waitForElementToBeReady(saveModifiedTransactionButton))
//        .click(saveModifiedTransactionButton);

    driver.element().scrollToElement(saveModifiedTransactionButton);
    // Remove potential overlays via JS
    ((JavascriptExecutor) driver.getDriver()).executeScript(
        "document.querySelectorAll('.modal, .overlay').forEach(e => e.remove());"
    );
    WebElement element = driver.getDriver().findElement(saveModifiedTransactionButton);
    ((JavascriptExecutor) driver.getDriver()).executeScript("arguments[0].click();", element);

    Actions actions = new Actions(driver.getDriver());
    if(!element.isDisplayed()) {
      actions.moveToElement(element).pause(Duration.ofMillis(100)).click().perform();
    }
    driver.element()
        .waitUntil(ExpectedConditions.invisibilityOfElementLocated(saveModifiedTransactionButton));

    driver.element().click(confirmationAgreeButton).verifyThat(confirmationAndSuccessModal)
        .isVisible().perform();
    driver.element().click(sendAndPrintDeliveryStatement)
        .waitUntil(ExpectedConditions.numberOfWindowsToBe(2))
        .waitUntil(ExpectedConditions.numberOfWindowsToBe(1));

    return new OutgoingDraftTransactionPage(driver);
  }

  @Step("طباعة بيان التسليم اثناء حفظ المعاملة المضافة")
  public OutgoingDraftTransactionPage printDeliveryStatementForAddedInTransaction() {
    driver.element().click(sendAndPrintDeliveryStatement).browser().waitUntilNumberOfWindowsToBe(2)
        .waitUntilNumberOfWindowsToBe(1);
    return new OutgoingDraftTransactionPage(driver);
  }

  //Used when adding a new transaction
  @Step("الذهاب الى تبويب الملحقات")
  public OutgoingDraftTransactionPage goToAttachmentsTab() {
    driver.element().click(attachmentsTab).verifyThat(attachmentType).isVisible();
    return this;
  }


  @Step("طباعة ورقة الاحالة")
  public OutgoingDraftTransactionPage printAssignmentPaperFromConfirmation() {

    driver.element().click(printAssignmentPaperButton);
//        .waitUntil(ExpectedConditions.visibilityOfElementLocated(printAssignmenPaperFromPreview));
//        .verifyThat(printAssignmenPaperFromPreview)
//        .isVisible();
    driver.element().click(printAssignmenPaperFromPreview);
//        .waitUntil(ExpectedConditions.visibilityOfElementLocated(loadingSpinner));
//        .verifyThat(loadingSpinner)
//        .isVisible();
    driver.element()
//        .waitUntil(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner))
        .waitUntil(ExpectedConditions.numberOfWindowsToBe(1));
    //driver.browser().waitUntilNumberOfWindowsToBe(1);
    return this;
  }
  @Step("طباعة لالباركود الكتروني")
  public OutgoingDraftTransactionPage printQrPaperFromConfirmation() {

    String originalWindowHandle = driver.getDriver().getWindowHandle();
    Allure.step("OriginalWindow: " + originalWindowHandle);
    driver.element().scrollToElement(printBarcodeButton)
        .click(printBarcodeButton);
    // driver.browser().captureScreenshot();
    driver.element().click(confirmationPrintButton)
        .waitUntil(ElementsOperations.waitForElementToBeReady(confirmationPrintButton));
    numberOfBrowserWindows = driver.browser().getWindowHandles().size();

    // Switch to the new window
    List<String> allWindows = driver.browser().getWindowHandles();
    for (String windowHandle : allWindows) {
      if (!windowHandle.equals(originalWindowHandle)) {
        driver.browser().switchToWindow(windowHandle);
        break;
      }
    }
    if (driver.browser().getCurrentURL().contains("print")
        || driver.browser().getCurrentWindowTitle().contains("Barcode")) {

      isPrintDialogueDisplayed = true;
    } else {
      isPrintDialogueDisplayed = false;
    }

    return this;



  }

  @Step("طباعة رمز التشفير - ملصق")
  public OutgoingDraftTransactionPage printBarcodeSticker() {
    driver.element().scrollToElement(printBarcodeStickerButton)
        .click(printBarcodeStickerButton);
    // driver.browser().captureScreenshot();
    driver.element().click(confirmationPrintButton)
        .waitUntil(ElementsOperations.waitForElementToBeReady(confirmationPrintButton));
    driver.element().click(confirmationCloseButton);
    // saveModifiedTransaction();
    return this;
  }

  @Step("الذهاب الى تبويب المتابعة")
  public OutgoingDraftTransactionPage goToFollowupTab() {
    driver.element().click(followupTab)
        .waitUntil(ElementsOperations.waitForElementToBeReady(followupOrgUnitNumber));
    return this;
  }

  @Step("الحصول على عدد طلبات المتابعة")
  public int getNumberOfFollowupRequests() {
    return driver.element().getElementsCount(transactionFollowupGridRow);
  }

  @Step("اضافة متابعة الى المعاملة")
  public OutgoingDraftTransactionPage addTransactionFollowup(String orgUnitNumber) {
    goToFollowupTab();
    int numberOfFollowupRequests = getNumberOfFollowupRequests();
    driver.element().click(followupOrgUnitNumber).clear(followupOrgUnitNumber)
        .type(followupOrgUnitNumber, orgUnitNumber)
        .type(followupEndDate, followupHijriDate)
        .type(remarks,"My remarks text")
    ;
    driver.element().click(addTransactionFollowupButton)
        .waitUntil(ElementsOperations.waitForElementToBeReady(followupConfirmationAndSuccessModal))
        .click(confirmAddFollowup)
        .waitUntilNumberOfElementsToBeMoreThan(transactionFollowupGridRow,
            numberOfFollowupRequests);
    return this;
  }

  @Step("الذهاب الى ورقة الاحالة")
  public OutgoingDraftTransactionPage goToAssignmentPaper() {
    driver.element().type(transactionSubjectTextField,
        "Transaction Description: " + modifiedTransactionDescription);
    driver.element().click(assignmentPaperCta);
    return this;
  }

  @Step("اختيار ادارة للارسال من خلال ورقة الاحالة")
  public OutgoingDraftTransactionPage selectOrgUnitFromAssignmentPaper(String orgUnitName) {
    driver.element().click(transferRadioButtonForAssignmentPaperOrgUnit(orgUnitName))
        .click(imageCheckBoxForAssignmentPaperOrgUnit(orgUnitName))
        .waitUntilNumberOfElementsToBe(assignmentPaperAttachmentOverlay, 1);
    driver.element().click(firstAssignmentPaperAttachmentCheckBox).click(saveAttachmentButton)
        .verifyThat(assignmentPaperAttachmentOverlay).doesNotExist();
    return this;
  }

  @Step("ارسال ورقة الاحالة")
  public OutgoingDraftTransactionPage sendAssignmentPaper() {
    driver.element().scrollToElement(sendAssignmentButton)
        .click(sendAssignmentButton);
//        .verifyThat(sendAssignmentButton).doesNotExist().wait(5000);
    return new OutgoingDraftTransactionPage(driver);
  }




}
