package pages.transactions;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.SystemAdminComponent;
import components.TransactionsNavigationPanelComponent;
import components.TransactionsOperationsComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ElementsOperations;
import utils.GeneralOperations;

public class TransactionsFollowupPage extends TransactionsNavigationPanelComponent {

  //======================General==========================
  private final By firstFollowupCardNumber = By.xpath(
      "(//div[contains(@class,'box-grid')]//div[contains(@class,'pr-1')])[1]");
  private final By numberOfAttachmentsAtFirstFollowup = By.xpath(
      "(//div[contains(@class,'ml-auto')]/div[@class='info']//span)[1]");
  private final By dateOnTheFirstCard = By.xpath(
      "(//div[contains(text(),'تاريخ المتابعة ')]/following-sibling::div[@class='result'])[1]");
  private final By addRemarkOnFirstCard = By.xpath("(//a[contains(@class,'color-blue')])[1]");
  private final By transactionRecord = By.xpath("(//a[contains(@class,'edit_trash')])[1]");
  private final By completedFollowupTab = By.id("CompleteFollowUpTab");

  //======================Remarks==========================
  private final By followupNotes = By.id("replyTxt");
  private final By addNoteButtonPopup = By.xpath("//button[contains(@class,'reply-button-popup')]");
  private final By uploadFollowUpAttachmentButton = By.id("Files");
  private final By confirmNoteButtonPopup = By.xpath(
      "(//div[contains(@class, 'actions-buttons')]/button[text()='نعم'])[1]");
  private final By closeNoteButtonPopup = By.xpath("//button[text()='إغلاق']");
  private final By displayRemarkCta = By.xpath("//td[@data-name='TransactionId']/a");
  private final By followUpRemarksModal = By.xpath(
      "//div[@class='modal-body']/h4[contains(@class,'title4')]");
  private final By attachmentsButton = By.xpath("//input[@value='المرفقات']");
  private final By attachmentsTable = By.id("grid-table-grdTaskAttachments");

  //========================================================
  private final By sentFollowupRequestsTab = By.id("SentFollowUpTab");

  //=============================TransactionFollowupRecord===================
  private final By followUpRecordsForm = By.xpath("//h2[contains(@class,'main-title')]");
  private final By followUpRecordsTab = By.xpath("//a[@href='#tab-9']");
  private final By lastFollowupNote = By.xpath("//td[@data-name='FollowupNotes']");

  //=================================EndFollowup==============================
  private final By endFollowUpOnFirstCard = By.xpath("(//a[contains(@class,'color-red')])[1]");
  private final By acceptEndTransactionFollowupPopup = By.xpath(
      "(//div[contains(@class, 'actions-buttons')]/button[text()='نعم'])[1]");


  @Getter
  private TransactionsOperationsComponent transactionsOperationsComponent;
  @Getter
  private SystemAdminComponent systemAdminComponent;
  @Getter
  private String followUpNoteText =
      "Followup_Notes: " + GeneralOperations.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");

  public TransactionsFollowupPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
    transactionsOperationsComponent = new TransactionsOperationsComponent(driver);
    systemAdminComponent = new SystemAdminComponent(driver);
  }

  @Step("الذهاب الى تبويب متابعات الادارة")
  public TransactionsFollowupPage navigateToSentFollowUpTab() {
    driver.element().click(sentFollowupRequestsTab);
    driver.browser().refreshCurrentPage();
    return this;
  }

  @Step("الذهاب الى تبويب المتابعات المنجزة")
  public TransactionsFollowupPage navigateToCompletedFollowUpTab() {
    driver.element().click(completedFollowupTab)
        .waitUntil(ExpectedConditions.attributeContains(completedFollowupTab, "class", "active"));
      //  .waitUntilAttributeContains(completedFollowupTab, "class", "active");
    return this;
  }

  @Step("الحصول على رقم المتابعة الأولى")
  public String getFirstTransactionNumber() {
    return driver.element().getText(firstFollowupCardNumber);
  }

  @Step("الحصول على عدد ملحقات المعاملة الأولى")
  public int getNumberOfAttachmentsOnFirstCard() {
    driver.element().scrollToElement(numberOfAttachmentsAtFirstFollowup);
    String trimmedNumber = driver.element().getText(numberOfAttachmentsAtFirstFollowup)
        .replaceAll("[^0-9]", "");
    return Integer.parseInt(trimmedNumber);
  }

  @Step("الحصول على تاريخ المتابعة الأولى")
  public String getFirstTransactionDate() {
    return driver.element().getText(dateOnTheFirstCard);
  }

  @Step("اضافة ملاحظة على المتابعة الأولى")
  public TransactionsFollowupPage addRemarkToFirstFollowupCard(String filePath) {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(addRemarkOnFirstCard))
        .click(addRemarkOnFirstCard)
        .waitUntil(ElementsOperations.waitForElementToBeReady(followupNotes))
        .type(followupNotes, followUpNoteText)
        .captureScreenshot(followupNotes)
        .typeFileLocationForUpload(uploadFollowUpAttachmentButton, filePath)
        .click(addNoteButtonPopup).waitUntil(ElementsOperations.waitForElementToBeReady(confirmNoteButtonPopup))
        .click(confirmNoteButtonPopup)
        .waitUntilNumberOfElementsToBe(confirmNoteButtonPopup, 1)
        .click(closeNoteButtonPopup);
    return this;
  }

  @Step("فتح سجل المعاملة")
  public TransactionsFollowupPage openTransactionRecords() {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(transactionRecord))
        .click(transactionRecord)
        .waitUntilNumberOfElementsToBe(followUpRecordsForm, 1);
    return this;
  }

  @Step("فتح سجل المتابعة")
  public TransactionsFollowupPage openFollowUpRecords() {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(followUpRecordsTab))
        .click(followUpRecordsTab)
        .waitUntilAttributeContains(followUpRecordsTab, "aria-expanded", "true");
    return this;
  }

  @Step("الحصول على وصف اخر ملاحظة على المتابعة")
  public String getLastFollowupNote() {
    return driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(lastFollowupNote))
        .getText(lastFollowupNote);
  }


  @Step("عرض أخر متابعة")
  public void displayRemarks() {
    driver.element().waitUntil(ElementsOperations.waitForElementToBeReady(displayRemarkCta))
        .click(displayRemarkCta)
        .waitUntil(ElementsOperations.waitForElementToBeReady(followUpRemarksModal));
  }

  @Step("التأكد من وجود المرفقات داخل المتابعة")
  public boolean confirmFollowupNoteAttachmentExistence() {
    displayRemarks();
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(attachmentsButton))
        .click(attachmentsButton);
    return driver.element().getElementsCount(attachmentsTable) > 0;
  }

  @Step("انهاء المتابعة")
  public TransactionsFollowupPage endTransactionFollowUp() {
    driver.element().waitUntil(ElementsOperations.waitForElementToBeReady(endFollowUpOnFirstCard))
        .click(endFollowUpOnFirstCard)
        .waitUntil(ElementsOperations.waitForElementToBeReady(
            acceptEndTransactionFollowupPopup))
            .click(acceptEndTransactionFollowupPopup);
    return this;
  }

}
