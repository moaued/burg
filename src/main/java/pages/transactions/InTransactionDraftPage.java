package pages.transactions;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ElementsOperations;
import utils.GeneralOperations;

public class InTransactionDraftPage {

  SHAFT.TestData.JSON testData = new SHAFT.TestData.JSON("inTransactionDraftData.json");
  private SHAFT.GUI.WebDriver driver;

  private By incomingTransactionNumber = By.xpath("//div[contains(@class, 'transaction-number')]");
  private By basicInfoTab = By.id("basicInfo");

  private By transactionTypeInput = By.cssSelector("#ddlInboundDocumentType.form-control");
  private By circularOption = By.xpath("//ul[contains(@class,'ui-autocomplete')]//li[normalize-space()='تعميم']");

  private By internalCopiesTab = By.xpath("//div[@id='electronicCopies']/a[@id='third' and @class='boardered-circle-transparent' and @data-divid='third-tab']/i[@class='uicon icon_folder4']");
  //=============================TransactionCopies==========================================
  private By transactionCopiesTab = By.id("transactionCopies");
  private By electronicCopiesText =
      By.xpath("(//div[@id='electronicCopies']//a[@data-id='third'])[last()]");

  private By orgnaisationalUnitNumber = By.xpath(
      "//input[@data-func='GetCopyUsersByOrgUnitId' and @onkeypress='return IsNumeric(event);']"
  );
  private By departmentNumberInput =
      By.xpath("//input[@class='form-control txtDepartmentNumber' and @data-func='GetCopyUsersByOrgUnitId' and @data-container='dvCopyDepartmentsTree' and @onkeypress='return IsNumeric(event);']");
//      By.cssSelector("input[data-func='GetAssignmentCopyUsersByOrgUnitId']");
private By departmentNumberInput2 =
    By.cssSelector("div.none_left input[data-func='GetAssignmentCopyUsersByOrgUnitId']");

  private By orgUnitAutoCompleteMenu = By.cssSelector("#divAutoComplateMenu");
  private By firstOrgChartAutoSuggestion = By.cssSelector(
      "#divAutoComplateMenu div:nth-of-type(1)");
  private By copyOrgUnitUsersInput =
      By.id("ddlCopyOrgUnitUsers");
  private By organisationalUnitNumber =
      By.xpath("//input[@data-func='GetCopyUsersByOrgUnitId' and @maxlength='20']");

  private By copyActionReason = By.id("ddlCopyActionId");
  private By copyActionReason2 = By.cssSelector("#ddlCopyActionId.form-control");

  private By checkbox2 = By.xpath(
      "//tr[.//text()[contains(.,'دراسة')]]\n"
          + "     //td[contains(@class,'fixed_col')]\n"
          + "     //input[@type='checkbox']"
  );
  private By addButton3 = By.id("btnShowCopiesAttDialog");

  private By lastActionInput =
      By.xpath("(//input[contains(@id,'__ActionId') and contains(@class,'DDLClass')])[last()]");

  private By explanationDiv = By.cssSelector("#explanation.form-control");
  private By firstAttachmentCheckBox = By.xpath(
      "(//input[contains(@id,'mainDocumentCheckbox')])[2]");
  private By printBarcodeStickerButton = By.xpath("//button[contains(@class, 'btn-site') and contains(@class, 'barcodeSticker-button-popup')]");
  private By printBarcodeButton = By.xpath("//button[contains(@class, 'barcode-button-popup')]");
  private By confirmationAndSuccessModal = By.xpath(
      "//div[contains(@class, 'jconfirm') and contains(@class, 'white')]");
  private By openUploadBtn = By.id("OpenUploadEdit");
  private By uploadInput =
      By.id("btnUploadFile");
  private By departmentDownArrow = By.xpath(
      "//a[contains(@class,'department-name')]//i[contains(@class,'la-angle-down')]");
  private By currentDepartmentName = By.cssSelector(".d-inline.manager");

  private String attachmentCheckBoxAtIndex = "(//input[contains(@id,'mainDocumentCheckbox')])[%s]";
  private String attachmentCheckBoxAtIndex2 = "(//input[contains(@id,'OpenUploadEdit')])[%s]";
  private String attachmentCopyTransactionDescriptionAtIndex = "(//td[@data-name='ArcivingTypeName'])[%s]";
  @Getter
  private List<String> attachmentCopyDescription = new ArrayList<>();
  private By addCopyButton = By.id("btnCopy");
  private By addButton =
      By.xpath("//div[contains(@class,'preview-mini-options')]//a[normalize-space()='إضافة']");
  private By addButton2 =
      By.cssSelector("#btnCopy.btn-site");

  private By lastIsCopyCheckbox =
      By.xpath("(//input[@type='checkbox' and contains(@id,'_IsCopy')])[last()]");


  private By lastIsCopyCheckbox2 = By.xpath(
      "//div[contains(@class,'TransactionAssignment')" +
          " and .//div[contains(@class,'ToOrgUnitName')]//label[normalize-space()='وكالة البيئة']]" +
          "//input[contains(@id,'__IsCopy') and @type='checkbox']"
  );

  private By copyRowInGrid = By.xpath(
      "//table[@id='grid-table-grdCopies']//tr[contains(@class,'grid-row')]");
  @Getter
  private int numberOfBrowserWindows;
  @Getter
  private boolean isPrintDialogueDisplayed;
  //===============================================================================================
  //================================TransactionAttachments==========================================
  private By attachmentsTab = By.id("anchorArchiving");
  private By attachmentType = By.id("ddlAttachmentType");
  private By attachmentVailidty = By.id("ddlDocumentValidityId");
  private By uploadAttachment = By.id("aTextEditor");
  private By addAttachmentButton = By.id("btnAddArchive");
  private By chooseAttachmentFile = By.id("TransactionFiles");
  private By uploadAttachmentContainer = By.id("uploadContainer");
  //===========================================================================
  //=========================================BasicInfo=========================
  private By inDataTypeEntities = By.id("Entities");
  private By inDataTypeIndividual = By.id("Individual");
  private By inDestinationName = By.xpath(
      "//input[contains(@class, 'InboundDepName') and contains(@class, 'txtDepartmentName')]");
  private By inboundDocumentNumber = By.id("InboundBasicInfo_InboundDocumentNumber");
  private By subjectTextField = By.id("txtAreaSubject");
  private By transactionSubjectTextField = By.id("InboundBasicInfoEdit_Remarks");
  private By inTransactionDateCalendar = By.id("HasInboundDateCal");
  private By inTransactionPriorityLevel = By.id("ddlPriorityLevel");
  private By saveModifiedTransactionButton = By.id("btnEditInbound");
  private By saveButton2 = By.id("btnSaveInbound");
  private By saveButton = By.id("btnSaveCopies");
  private By confirmYesButton =
      By.xpath("//div[contains(@class,'jconfirm')]//button[normalize-space()='نعم']");
  By closeButton =
      By.xpath("//div[contains(@class,'buttons')]//button[contains(normalize-space(),'إغلاق')]");



  private By saveInTransactionButton = By.id("btnSaveInbound");
  private By inDestinationFilter = By.xpath(
      "//span[contains(@class, 'input-group-text') and contains(@class, 'hyperlink')]");
  private By inDestinationModal = By.id("modalBody__dvDestinationTree");
  private By outDestinationEntry = By.xpath(
      "//div[contains(text(),\"" + testData.getTestData("incomingDestination")
          + "\") and contains(@data-name,'DestinationId')]");
  private By isLinkCheckBox = By.id("Islink");
  private By showNamesButton = By.id("btnShowNames");

  private By confidentialityLevelField = By.cssSelector("#ddlConfedentiality.form-control");
  private By confidentialityOption =By.xpath("//li[@data-value='27']");

  private By importanceLevelField = By.cssSelector("#ddlPriorityLevel.form-control");
  private By importanceOption = By.xpath("//li[@data-value='1']");
  //=================================================================================================
  //========================================TransactionLetter=========================================
  private By transactionLetterFrame = By.id("frameViewer");
  private By transactionLetterFrame2 = By.id("frameViewerAttachment");
  private By trasnactionLetterUploadButton = By.id("OpenUpload");
  private By transactionLetterUploadModal = By.xpath(
      "//div[@id='myModal' and contains(@style,'display')]");
  private By transactionLetterUploadArea = By.className("dz-message");
  private By transactionLetterUploadHiddenButton = By.xpath(
      "//input[@type='file' and @class='dz-hidden-input']");
  private By transactionLettersThumbnail = By.xpath("//input[contains(@class,'docThumbFocussed')]");
  private By transactionExitButton = By.xpath(
      "//div[@id='myModal']//button[@type='button' and contains(@class,'btn-st1')]");
  private By baseTransactionAttachmentsButton = By.id("btnShowAttachment");
  //==================================================================================================================
  //=======================================General/Generic_Fields=====================================================
  private By civilIdNumber = By.xpath(
      "//input[@id='TransactionName_CivilID' and @data-val='true']");
  private By remarks = By.id("InboundBasicInfoEdit_Remarks");
  private By confirmationCloseButton = By.xpath("//button[contains(@onclick,'dialog.close')]");
  private By firstName = By.xpath("//input[@id='TransactionName_FirstName' and @data-val='true']");
  private By sendSMSCheckBox = By.xpath(
      "//input[@id='TransactionName_SendSMS' and @data-val='true']");
  private By addNameButton = By.xpath("(//button[@id='btnNames'])[1]");
  private By loadingSpinner = By.id("loadingModal");
  private By namesGrid = By.xpath("//tr[contains(@class,'grid-row')]");
  //==================================================================================================================
  //====================================TransactionFollowup===========================================================
  private By followupTab = By.id("followUpCheck");
  private By followupOrgUnitNumber =  By.xpath("(//input[@data-func='GetFollowUpUsersByOrgUnitId'])[1]");
  private By departmentNameInput =  By.xpath("(//input[@data-func='GetFollowUpUsersByOrgUnitId' and contains(@class,'txtDepartmentName')])[1]");
  private  By firstAutoCompleteOption =
      By.xpath("(//div[@id='divAutoComplateMenu']//div)[1]");

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
  private By confirmationCancelButton = By.xpath("//button[Choose direction = 'الغاء']");
  private By confirmationAgreeButton = By.xpath("//button[normalize-space() = 'نعم']");
  private By transactionNumberFromConfirmationLabel = By.xpath(
      "//div[contains(@class,'col-xs-4')]/span[contains(@class,'indent-text')]");
  private By backToTheInboxButton = By.xpath("//button[contains(@class,'redirect-to-tray')]");
  private By sendAndPrintDeliveryStatement = By.xpath(
      "//button[contains(@class,'move-to-assignment')]");
  private By printQrPaperButton = By.xpath("//button[contains(@class, 'btn-site') and contains(@class, 'barcode-button-popup')]");;
  private By printAssignmentPaperButton = By.xpath("//button[contains(@class,'AssignmentPaper')]");
  private By printAssignmenPaperFromPreview = By.id("printdiv");
  private By printqrconfirm=By.xpath("//button[contains(@onclick,'PrintAllBarcode')]");
  private By printConfirmationModal = By.xpath(
      "//div[contains(@class, 'jconfirm') and contains(@class, 'white')]");

  //==================================================================================================================
  //=======================================Assignment_Paper==========================================================
  private By assignmentPaperCta = By.id("assignmentPaper");
  private By paperReferralText =
      By.xpath("(//div[@id='assigmentpaper']//a[@data-id='third'])[last()]");

  private By assignmentPaperAttachmentOverlay = By.cssSelector(".jconfirm.white");
  //First one after the original copy of the transaction
  private By firstAssignmentPaperAttachmentCheckBox = By.xpath(
      "(//input[contains(@class,' inp')])[2]");
  private By saveAttachmentButton = By.id("btnShowCopiesAttDialog");
  private By sendAssignmentButton = By.id("GetUserDelegationsById");
  private  By sendButton =
      By.xpath("//a[contains(@class,'btn-site') and contains(normalize-space(),'إرسال')]");


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
  //================================================================================================================

  public InTransactionDraftPage(SHAFT.GUI.WebDriver driver) {
    this.driver = driver;
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
  public InTransactionDraftPage addFileToTransactionLetter(String transactionAttachmentLocation) {
    driver.element().switchToIframe(transactionLetterFrame);
    driver.element().click(trasnactionLetterUploadButton).verifyThat(transactionLetterUploadModal)
        .isVisible();
    WebDriver wDriver = driver.getDriver();
    ((JavascriptExecutor) wDriver)
        .executeScript("arguments[0].classList.remove('hidden');",
            wDriver.findElement(transactionLetterUploadHiddenButton));
    driver.element().typeFileLocationForUpload(transactionLetterUploadHiddenButton,
        transactionAttachmentLocation);
    driver.element().waitUntilNumberOfElementsToBeMoreThan(transactionLettersThumbnail, 0);
    driver.element().switchToDefaultContent();
    scrollToAndClickSaveButton(saveButton2);
    return this;
  }
  @Step("اضافة ملف على أصل خطاب المعاملة2")
  public InTransactionDraftPage addFileToTransactionLetter2(String transactionAttachmentLocation) {
    driver.element().switchToIframe(transactionLetterFrame);
    driver.element().click(trasnactionLetterUploadButton).verifyThat(transactionLetterUploadModal)
        .isVisible();
    WebDriver wDriver = driver.getDriver();
    ((JavascriptExecutor) wDriver)
        .executeScript("arguments[0].classList.remove('hidden');",
            wDriver.findElement(transactionLetterUploadHiddenButton));
    driver.element().typeFileLocationForUpload(transactionLetterUploadHiddenButton,
        transactionAttachmentLocation);
    driver.element().waitUntilNumberOfElementsToBeMoreThan(transactionLettersThumbnail, 0);
    driver.element().switchToDefaultContent();
    scrollToAndClickSaveButton(saveButton2);
    return this;
  }
  @Step("عدد الملحقات")
  public int getNumberOfAttachmentsInGrid() {
    return driver.element().getElementsCount(attachmentsTableRows);
  }

  @Step("تعديل موضوع المعاملة و اعطائه القيمة الحالية للوقت و التاريخ")
  public InTransactionDraftPage modifyInTransactionSubject() {
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

  @Step("حفظ معاملة معدلة")
  public InTransactionDraftPage saveModifiedTransaction() {
    driver.element().scrollToElement(basicInfoTab).click(basicInfoTab);
    driver.element().verifyThat(inDestinationName).isVisible();
    driver.element().type(transactionSubjectTextField,
        "Transaction Description: " + modifiedTransactionDescription);
    scrollToAndClickSaveButton(saveModifiedTransactionButton);
    return this;
  }
  @Step("حفظ معاملة معدلة 2")
  public InTransactionDraftPage saveModifiedTransaction2() {
    driver.element().scrollToElement(basicInfoTab).click(basicInfoTab);
    driver.element().verifyThat(inDestinationName).isVisible();

    scrollToAndClickSaveButton(saveModifiedTransactionButton);
    return this;
  }

  @Step("العودة لصفحة معاملاتى")
  public MyTransactionsPage goBackToMyTransactionPage() {
    driver.element().click(backToTheInboxButton).verifyThat(backToTheInboxButton).doesNotExist();
    return new MyTransactionsPage(driver);
  }

  @Step("اضافة وارد عام - جهات")
  public InTransactionDraftPage addInGeneralTransactionDestination() {
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
  public InTransactionDraftPage addInGeneralTransactionIndividual() {
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
  public InTransactionDraftPage saveInTransaction() {
    By inputField = driver.element()
        .getElementsCount(saveInTransactionButton) > 0 ? saveInTransactionButton
        : saveModifiedTransactionButton;
    scrollToAndClickSaveButton(inputField);
    transactionNumberFromConfirmation = driver.element()
        .getText(transactionNumberFromConfirmationLabel);
    return this;
  }

  //Used when modifying a previously added transaction
  @Step("الذهاب الى تبويب الملحقات")
  public InTransactionDraftPage goToAttachmentsTab() {
    driver.element().click(attachmentsTab).verifyThat(attachmentType).isVisible();
    return this;
  }

  @Step("اضافة ملحقات")
  public InTransactionDraftPage addAttachment(String transactionAttachmentType,
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

  private void showSelectAttachment() {
    WebDriver wDriver = driver.getDriver();
    ((JavascriptExecutor) wDriver)
        .executeScript("arguments[0].classList.remove('hidden');",
            wDriver.findElement(chooseAttachmentFile));
  }


  @Step("الذهاب الى تبويب النسخ الداخلية الالكترونية")
  private InTransactionDraftPage goToTransactionCopiesTab() {
    driver.element().click(transactionCopiesTab).verifyThat(orgnaisationalUnitNumber).isVisible();
    return this;
  }
  @Step("الذهاب الى تبويب النسخ الداخلية الالكترونية")
  private InTransactionDraftPage goToTransactionCopiesTab2() {
    driver.element().click(electronicCopiesText).verifyThat(orgnaisationalUnitNumber).isVisible();
    return this;
  }

  @Step("اضافة نسخ داخلية")
  public InTransactionDraftPage addInternalCopies(String orgUnitNum, String copyReason,
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
  @Step("اختيار ادارة للارسال من خلال صورة المعاملة")
  public InTransactionDraftPage selectOrgUnittosendcopy(String orgUnitNum,String copyReason) {
    driver.element().click(departmentNumberInput).type(departmentNumberInput, orgUnitNum)
        .waitUntil(ElementsOperations.waitForElementToBeReady(orgUnitAutoCompleteMenu))
        .click(firstOrgChartAutoSuggestion);
    driver.element().click(addButton);
    driver.element().scrollToElement(lastIsCopyCheckbox).click(lastIsCopyCheckbox);
    driver.element().select(lastActionInput, copyReason);
    driver.element().scrollToElement(sendButton)
        .click(sendButton);
    return this;
  }

  @Step("اضافة نسخ داخلية")
  public InTransactionDraftPage addInternalCopies2(String orgUnitNum, String copyReason,
      String transactionAttachmentLocation) {
    goToTransactionCopiesTab2();
//    int numOfCopies = getNumberOfCopyRows();
//    By attachmentCheckBoxIndexElement = By.xpath(
//        String.format(attachmentCheckBoxAtIndex, attachmentIndex + 1));
//    By attachmentTypeAtIndex = By.xpath(
//        String.format(attachmentCopyTransactionDescriptionAtIndex, attachmentIndex + 1));
//    attachmentCopyDescription.add(driver.element().getText(attachmentTypeAtIndex));
    driver.element().click(orgnaisationalUnitNumber).type(orgnaisationalUnitNumber, orgUnitNum)
        .waitUntil(ElementsOperations.waitForElementToBeReady(orgUnitAutoCompleteMenu))
        .click(firstOrgChartAutoSuggestion);

//    driver.element().type(copyOrgUnitUsersInput, "مدير النظام");
//    driver.element().type(copyOrgUnitUsersInput, Keys.ARROW_DOWN);
//    driver.element().type(copyOrgUnitUsersInput, Keys.ENTER);
        driver.element().select(copyActionReason, copyReason);
    driver.element().click(addCopyButton);
    int framesCount =
        driver.element().getElementsCount(By.tagName("iframe"));

    System.out.println("Number of iframes = " + framesCount);

    for (int i = 0; i < framesCount; i++) {
      String frameId =
          driver.element()
              .getAttribute(By.xpath("(//iframe)[" + (i + 1) + "]"), "id");

      System.out.println("Frame " + i + " id = " + frameId);
    }

    driver.element().switchToIframe(transactionLetterFrame2);
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
    driver.element().click(saveButton);
    driver.element().click(confirmYesButton);
    driver.element().click(closeButton);

//    driver.element().click(attachmentCheckBoxIndexElement).click(addCopyButton);

    return this;
  }
  @Step("تغير القسم بعد اضافة لنسخ الداخلية")
  public InTransactionDraftPage changeDepartmentafteraddInternalCopies(String department) {

    By departmentName = By.xpath("//span[text()='" + department + "']");
    driver.element().click(departmentDownArrow)
        .scrollToElement(departmentName)
        .click(departmentName);
    return this;
  }

  @Step("عدد النسخ")
  public int getNumberOfCopyRows() {
    return driver.element().getElementsCount(copyRowInGrid);
  }

  @Step("حفظ المعاملة")
  private void scrollToAndClickSaveButton(By byButtonLocator) {
    driver.element().scrollToElement(byButtonLocator).click(byButtonLocator);
    driver.element().verifyThat(confirmationAndSuccessModal).isVisible();
    driver.element().click(confirmationAgreeButton)
        .waitUntil(ExpectedConditions.visibilityOfElementLocated(confirmationAndSuccessModal));
  }

  @Step("طباعة بيان التسليم اثناء حفظ المعاملة المعدلة")
  public MyTransactionsPage sendAndPrintDeliveryStatementForModifiedInTransaction() {
    ((JavascriptExecutor) driver.getDriver()).executeScript(
        "window.onerror = function(message, source, lineno, colno, error) {" +
            "  console.log('Suppressed JS error:', message);" +
            "  return true;" + // Prevents error propagation
            "};"
    );
    driver.element().type(transactionSubjectTextField,
        "Description: " + GeneralOperations.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));

    driver.element()
        .scrollToElement(saveModifiedTransactionButton)
        .waitUntil(ElementsOperations.waitForElementToBeReady(saveModifiedTransactionButton))
        .clickUsingJavascript(saveModifiedTransactionButton);



//    driver.element().scrollToElement(saveModifiedTransactionButton);
//    // Remove potential overlays via JS
//    ((JavascriptExecutor) driver.getDriver()).executeScript(
//        "document.querySelectorAll('.modal, .overlay').forEach(e => e.remove());"
//    );
//    WebElement element = driver.getDriver().findElement(saveModifiedTransactionButton);
//    ((JavascriptExecutor) driver.getDriver()).executeScript("arguments[0].click();", element);
//
//    Actions actions = new Actions(driver.getDriver());
//     if(!element.isDisplayed()) {
//      actions.moveToElement(element).pause(Duration.ofMillis(100)).click().perform();
//    }
//     driver.element()
//         .waitUntil(ExpectedConditions.invisibilityOfElementLocated(saveModifiedTransactionButton));

    driver.element().click(confirmationAgreeButton).verifyThat(confirmationAndSuccessModal)
        .isVisible().perform();
    driver.element().click(sendAndPrintDeliveryStatement)
        .waitUntil(ExpectedConditions.numberOfWindowsToBe(2))
        .waitUntil(ExpectedConditions.numberOfWindowsToBe(1));

    return new MyTransactionsPage(driver);
  }

  @Step("طباعة بيان التسليم اثناء حفظ المعاملة المضافة")
  public MyTransactionsPage printDeliveryStatementForAddedInTransaction() {
    driver.element().click(sendAndPrintDeliveryStatement).browser().waitUntilNumberOfWindowsToBe(2)
        .waitUntilNumberOfWindowsToBe(1);
    return new MyTransactionsPage(driver);
  }

  //Used when adding a new transaction
  @Step("توسيع قسم الملحقات")
  public InTransactionDraftPage expandAttachmentSection() {
    driver.element().scrollToElement(baseTransactionAttachmentsButton)
        .click(baseTransactionAttachmentsButton).verifyThat(attachmentType).isVisible();
    return this;
  }

  @Step("طباعة ورقة الاحالة")
  public InTransactionDraftPage printAssignmentPaperFromConfirmation() {
    driver.element().click(printAssignmentPaperButton)
        .waitUntil(ExpectedConditions.visibilityOfElementLocated(printAssignmenPaperFromPreview));
//        .verifyThat(printAssignmenPaperFromPreview)
//        .isVisible();
    driver.element().click(printAssignmenPaperFromPreview)
            .waitUntil(ExpectedConditions.visibilityOfElementLocated(loadingSpinner));
//        .verifyThat(loadingSpinner)
//        .isVisible();
    driver.element()
//        .waitUntil(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner))
        .waitUntil(ExpectedConditions.numberOfWindowsToBe(1));

    //driver.browser().waitUntilNumberOfWindowsToBe(1);
    return this;
  }
  @Step("طباعة لالباركود الكتروني")
  public InTransactionDraftPage printQrPaperFromConfirmation() {

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
  public InTransactionDraftPage printBarcodeSticker() {
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
  public InTransactionDraftPage goToFollowupTab() {
    driver.element().click(followupTab)
        .waitUntil(ElementsOperations.waitForElementToBeReady(followupOrgUnitNumber));
    return this;
  }

  @Step("الحصول على عدد طلبات المتابعة")
  public int getNumberOfFollowupRequests() {
    return driver.element().getElementsCount(transactionFollowupGridRow);
  }

  @Step("اضافة متابعة الى المعاملة")
  public InTransactionDraftPage addTransactionFollowup(String orgUnitNumber) {
    goToFollowupTab();
    int numberOfFollowupRequests = getNumberOfFollowupRequests();
    driver.element().click(followupOrgUnitNumber).clear(followupOrgUnitNumber)
        .type(followupOrgUnitNumber, orgUnitNumber);
    driver.element().waitUntil(ElementsOperations.waitForElementToBeReady(firstAutoCompleteOption)).click(firstAutoCompleteOption);
    driver.element() .type(followupEndDate, followupHijriDate)
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
  public InTransactionDraftPage goToAssignmentPaper() {
    driver.element().type(transactionSubjectTextField,
        "Transaction Description: " + modifiedTransactionDescription);
    driver.element().click(assignmentPaperCta);
    return this;
  }

  @Step("الذهاب الى ورقة الاحالة")
  public InTransactionDraftPage goToAssignmentPaper2() {

    driver.element().click(paperReferralText);
    return this;
  }



  @Step("اختيار ادارة للارسال من خلال ورقة الاحالة")
  public InTransactionDraftPage selectOrgUnitFromAssignmentPaper(String orgUnitName) {
    driver.element().click(transferRadioButtonForAssignmentPaperOrgUnit(orgUnitName))
        .click(imageCheckBoxForAssignmentPaperOrgUnit(orgUnitName))
        .waitUntilNumberOfElementsToBe(assignmentPaperAttachmentOverlay, 1);
    driver.element().click(firstAssignmentPaperAttachmentCheckBox).click(saveAttachmentButton)
        .verifyThat(assignmentPaperAttachmentOverlay).doesNotExist();
    return this;
  }

  @Step("ارسال ورقة الاحالة")
  public MyTransactionsPage sendAssignmentPaper() {
    driver.element().scrollToElement(sendAssignmentButton)
        .click(sendAssignmentButton);
//        .verifyThat(sendAssignmentButton).doesNotExist().wait(5000);
    return new MyTransactionsPage(driver);
  }

  @Step("اضافة معاملة وارد عام افراد جديدة- نوع خطابها تعميم")
  public InTransactionDraftPage addInGeneralTransactionIndividual2() {
    driver.element().click(inDataTypeIndividual);
    driver.element().click(isLinkCheckBox);
    driver.element().click(inDestinationFilter).verifyThat(inDestinationModal).isVisible();
    driver.element().click(outDestinationEntry)
        .verifyThat(inDestinationName).text().contains(testData.getTestData("incomingDestination"));
    driver.element().type(subjectTextField, modifiedTransactionDescription);

    // فتح الـ dropdown
    driver.element().click(transactionTypeInput);
    // اختيار "تعميم"
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(circularOption))
        .click(circularOption);

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

  @Step("فتح تبويبة النسخ الالكترونية الداخلية")
  public InTransactionDraftPage goToInternalCopiesTab() {
    driver.element().click(internalCopiesTab);
    return this;
  }


  @Step("اختيار ادارة للارسال من خلال صورة المعاملة- سلة التعاميم")
  public InTransactionDraftPage selectOrgUnittosendcopy2(String orgUnitNum,String copyReason) {
//    driver.element().click(departmentNumberInput2).type(departmentNumberInput2, orgUnitNum)
//        .waitUntil(ElementsOperations.waitForElementToBeReady(orgUnitAutoCompleteMenu))
//        .click(firstOrgChartAutoSuggestion);
//    driver.element().click(addButton);
    driver.element().scrollToElement(lastIsCopyCheckbox2).click(lastIsCopyCheckbox2);
//    driver.element().click(checkbox2);
    driver.element().click(addButton3);
//    driver.element().select(lastActionInput, copyReason);
    driver.element()
        .click(explanationDiv)
        .type(explanationDiv, "هذا نص ملاحظة تجريبية");

    driver.element().scrollToElement(sendButton).click(sendButton);

    return this;
  }

  @Step("اضافة وارد عام - جهات في شاشة الرقم الموحد")
  public InTransactionDraftPage addInGeneralTransactionDestination2() {
    driver.element().click(inDataTypeEntities);
    driver.element().click(isLinkCheckBox);
    driver.element().click(inDestinationFilter).verifyThat(inDestinationModal).isVisible();
    driver.element().click(outDestinationEntry)
        .verifyThat(inDestinationName).text().contains(testData.getTestData("incomingDestination"));
    driver.element().type(subjectTextField, modifiedTransactionDescription);
    driver.element().type(inboundDocumentNumber, testData.getTestData("inboundDocumentNumber"));
    driver.element().click(inTransactionDateCalendar)
        .type(inTransactionDateCalendar, GeneralOperations.getCurrentHijriDate() + Keys.ENTER);

    return this;
  }

  @Step("اضافة وارد عام - جهات في شاشة الرقم الموحد لمعاملات الصادر الخارجي ")
  public InTransactionDraftPage addInGeneralTransactionDestination3() {
    driver.element().click(inDataTypeEntities);
    driver.element().click(isLinkCheckBox);
    driver.element().click(inDestinationFilter).verifyThat(inDestinationModal).isVisible();
    driver.element().click(inDestinationModal);

    driver.element().click(outDestinationEntry)
        .verifyThat(inDestinationName).text().contains(testData.getTestData("incomingDestination"));
    driver.element().type(subjectTextField, modifiedTransactionDescription);
    driver.element().type(inboundDocumentNumber, testData.getTestData("inboundDocumentNumber"));
    driver.element().click(inTransactionDateCalendar)
        .type(inTransactionDateCalendar, GeneralOperations.getCurrentHijriDate() + Keys.ENTER);

    return this;
  }

  @Step("اضافة وارد عام - جهات في شاشة الرقم الموحد لمعاملات الوارد ")
  public InTransactionDraftPage addInGeneralTransactionDestination4() {
    driver.element().click(inDataTypeEntities);
    driver.element().click(isLinkCheckBox);
    driver.element().click(inDestinationFilter).verifyThat(inDestinationModal).isVisible();
    driver.element().click(inDestinationModal);

    driver.element().click(confidentialityLevelField)
        .click(confidentialityOption);
    driver.element().click(importanceLevelField)
        .click(importanceOption);

    driver.element().click(outDestinationEntry)
        .verifyThat(inDestinationName).text().contains(testData.getTestData("incomingDestination"));
    driver.element().type(subjectTextField, modifiedTransactionDescription);
    driver.element().type(inboundDocumentNumber, testData.getTestData("inboundDocumentNumber"));
    driver.element().click(inTransactionDateCalendar)
        .type(inTransactionDateCalendar, GeneralOperations.getCurrentHijriDate() + Keys.ENTER);

    return this;
  }

}