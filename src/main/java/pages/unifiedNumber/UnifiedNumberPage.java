package pages.unifiedNumber;

import com.shaft.driver.SHAFT.GUI.WebDriver;
import components.HorizontalMenusComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.OutTransactionDraftPage;
import utils.ElementsOperations;

public class UnifiedNumberPage extends HorizontalMenusComponent {

  private WebDriver driver;

  private By transactinNumberField = By.xpath("//input[contains(@id,'_TransactionLinkedNumber')]");
  private By transactionsResultsTable = By.xpath(
      "//table[contains(@id,'InboundOutboundLinkedNumber')]");
  private By addCopyIconInGrid = By.xpath("//i[contains(@class,'la-folder-open-o')]");
  private By createIncomingButton = By.xpath("//a[contains(@onclick,'CreateInternalOutbound') and i[contains(@class,'icon-outline-note-add')]]");
  private By convertToOutboundButton =
      By.xpath("//a[contains(@onclick,'ConvertDraftToOutbound') and i[contains(@class,'icon-outline-note-add')]]");
  private By printDeliveryStatementButton = By.cssSelector("a[data-original-title='طباعة بيان التسليم']");
  private By referralsButton = By.cssSelector("a[data-original-title='الاحالات']") ;

  private By transferButton = By.cssSelector("a[data-original-title='تحويل ']");

  private By printReviewerTicketIcon =
      By.xpath("//a[@data-original-title='طباعة تذكرة مراجعة']");

  public UnifiedNumberPage(WebDriver driver) {
    super(driver);
    this.driver = driver;
  }


  @Step("البحث عن معاملة باستخدام ؤقم المعاملة")
  public UnifiedNumberPage searchForTransactionWithNumber(String transactionNumber) {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(transactinNumberField))
        .type(transactinNumberField, transactionNumber + Keys.ENTER)
        .waitUntilNumberOfElementsToBe(transactionsResultsTable, 1);
    return this;
  }

  @Step("التأكد من وجود ؤقم المعاملة فى جدول النتائج")
  public boolean confirmValueExistenceInResultsGrid(String transactionNumber) {
    return ElementsOperations.confirmValueExistenceInTable(transactionNumber,
        transactionsResultsTable, driver);
  }

  @Step("الذهاب الى صفحة اضافة صور")
  public UnifiedNumberDetailsPage addCopiesToTransaction() {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(addCopyIconInGrid))
        .click(addCopyIconInGrid);
    return new UnifiedNumberDetailsPage(driver);
  }
  @Step("انشاء وارد من شاشة الرقم الموحد")
  public InTransactionDraftPage clickCreateIncomingButton(){
    driver.element()
        .click(createIncomingButton);
    return new InTransactionDraftPage(driver);
  }

  @Step("انشاء صادر من شاشة الرقم الموحد")
  public OutTransactionDraftPage clickCreateOutgoingButton(){
    driver.element()
        .click(convertToOutboundButton);
    return new OutTransactionDraftPage(driver);
  }
  @Step("طباعة بيان التسليم من شاشة الرقم الموحد")
  public UnifiedNumberPage printDeliveryStatement(){
    // طباعة بيان التسليم
    driver.element()
        .click(printDeliveryStatementButton);
    // (الاحالات)فتح سجل المعاملة
    driver.element().click(referralsButton);

    return this;
  }

  @Step("تعديل المعاملة من ايقونة التحويل في شاشة الرقم الموحد")
  public InTransactionDraftPage clickTransferIcon() {

    driver.element().click(transferButton);

    return new InTransactionDraftPage(driver);
  }


  // ── New locators: referral table cells (spec-supplied XPaths) ────────────
  private By referralFromOrgUnit =
      By.xpath("(//td[@data-name='FromOrgUnitName'])[1]");

  private By referralToOrgUnit =
      By.xpath("//tbody/tr[1]//td[@data-name='ToOrgUnitName']");

  private By referralAssignmentDate =
      By.xpath("//tbody/tr[1]/td[contains(@data-th,'تاريخ ووقت الإحالة')]");

  // ── Captured referral values exposed to the test layer via @Getter ────────
  @Getter
  private String capturedFromOrgUnit;

  @Getter
  private String capturedToOrgUnit;

  @Getter
  private String capturedAssignmentDate;


  @Step("قراءة بيانات الإحالة الأولى من جدول الإحالات")
  public UnifiedNumberPage captureReferralTableData() {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(referralFromOrgUnit));
    capturedFromOrgUnit    = driver.element().getText(referralFromOrgUnit);
    capturedToOrgUnit      = driver.element().getText(referralToOrgUnit);
    capturedAssignmentDate = driver.element().getText(referralAssignmentDate);
    return this;
  }

  @Step("التحقق من عدم فراغ خانة الجهة المُحيلة في جدول الإحالات")
  public boolean isReferralFromOrgUnitPresent() {
    String value = driver.element().getText(referralFromOrgUnit);
    return value != null && !value.isBlank();
  }

  @Step("التحقق من عدم فراغ خانة الجهة المُحال إليها في جدول الإحالات")
  public boolean isReferralToOrgUnitPresent() {
    String value = driver.element().getText(referralToOrgUnit);
    return value != null && !value.isBlank();
  }

  @Step("التحقق من عدم فراغ خانة تاريخ ووقت الإحالة في جدول الإحالات")
  public boolean isReferralAssignmentDatePresent() {
    String value = driver.element().getText(referralAssignmentDate);
    return value != null && !value.isBlank();
  }

  @Step("طباعة تذكرة مراجعة من شاشة الرقم الموحد")
  public UnifiedNumberPage clickPrintReviewerTicket(){
    driver.element()
        .click(printReviewerTicketIcon);

    // (الاحالات)فتح سجل المعاملة
    driver.element().click(referralsButton);
    return this;
  }


}
