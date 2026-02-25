package pages.transactions;

import com.shaft.driver.SHAFT;
import com.shaft.gui.internal.locator.Locator;
import components.HorizontalMenusComponent;
import components.SystemAdminComponent;
import components.TransactionsNavigationPanelComponent;
import components.TransactionsOperationsComponent;
import io.qameta.allure.Step;
import java.util.List;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class MyTransactionsPage extends TransactionsNavigationPanelComponent{


  private By appLogo = By.className("main_logo");
  private By myTransactions = By.id("MyTransactions");
  private By mainLtitle = By.xpath("//h3[contains(text(), 'معاملاتي')]");
  private By firstEditTransactionButotn = Locator.hasTagName("i").containsClass("icon-outline-edit")
      .isFirst().build();
  private By firstTransactionNumber = Locator.hasTagName("div").containsClass("pr-1").isFirst()
      .build();
  private By firstTransactionDescription = Locator.hasTagName("div").containsClass("description")
      .isFirst().build();
  private By receiveButton =
      Locator.hasTagName("a")
          .containsAttribute("title", "استلام")
          .containsAttribute("data-type", "Assign")
          .isFirst()
          .build();
  private By copyInboxTitle = By.xpath(
      "//span[normalize-space()='استقبال الصور والتعاميم']"
  );
  private By filterButton = By.className("icon-filter");
  private By filterTransactionType = By.id("TransactionType");
  private By filterInTransactionType = By.xpath("//li[@data-value='254']");
  private By filterSearchButton = By.xpath("//input[@type='button']");
  private By firstGeneralInTransactionDestination = By.xpath(
      "(//div[contains(text(), ' وارد خارجي (جهات)')])[1]");
  private By firstEditInDestination = Locator.hasTagName("i").containsClass("icon-outline-edit")
      .relativeBy().below(firstGeneralInTransactionDestination);
  private By firstAttachmentsNumInDestination = Locator.hasAnyTagName().containsClass("span")
      .isFirst().relativeBy().below(firstGeneralInTransactionDestination);
  private By numberOfAttachmentsAtFirstTransaction = By.xpath(
      "(//div[contains(@class,'ml-auto')]/div[@class='info']/span)[1]");
  private By firstTransactionCheckbox = By.xpath(
      "(//div[contains(@class,'ml-auto')]/div[@class='checkbox'])[1]");
  private By multiOptionsSideBar = By.cssSelector(".sidebarMultiOptions.transparent-bg");
  private By sideBarReferral = By.xpath(
      "(//div[contains(@class,'sidebarMultiOptions')]//a[contains(@class,'boardered-circle-transparent')])[1]");

  private By moveToArchiveButton = By.xpath("//a[contains(@onclick,'openPendingExecution') and normalize-space()='نقل الى الحفظ']");
  private By archiveReasonInput = By.cssSelector("#remarks.form-control");
  private By confirmArchiveButton = By.xpath("(//input[@id='btnSend' and contains(@class,'btn-site')])[1]");
  private By yesButton = By.xpath("//button[normalize-space()='نعم']");
  private By transactionNumberCells = By.cssSelector("div.box-grid");
  private By sentTransactionsTab = By.xpath("//li[@class='nav-item']//span[normalize-space()='المعاملات المرسلة']/ancestor::a");
  private By myTransactionsMenu = By.id("MyTransactions");
//      By.xpath("//span[normalize-space()='معاملاتي']/ancestor::a");





  @Getter
  private TransactionsOperationsComponent transactionsOperationsComponent;
  @Getter
  private SystemAdminComponent systemAdminComponent;

  public MyTransactionsPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
    transactionsOperationsComponent = new TransactionsOperationsComponent(driver);
    systemAdminComponent = new SystemAdminComponent(driver);
  }

  @Step("تعديل المعاملة الأولى / الذهاب الى صفحة \"تعديل المعاملة\"")
  public InTransactionDraftPage editFirstInTransaction() {
    driver.element().click(firstEditTransactionButotn);
    return new InTransactionDraftPage(driver);
  }

  @Step("تعديل المعاملة الأولى / الذهاب الى صفحة \"تعديل المعاملة\"")
  public <T> T editFirstInTransaction2(T pageObject) {
    driver.element().click(firstEditTransactionButotn);
    return pageObject;
  }


  @Step("اختيار أول معاملة")
  public MyTransactionsPage selectFirstTransaction() {
    driver.element().click(firstTransactionCheckbox)
        .waitUntilNumberOfElementsToBe(multiOptionsSideBar, 1);
    return this;
  }


  /**
   * ToDo
   * This method needs to be modified, generalized & moved to the "TransactionsOperationsComponent.java" class
   * to be usable for all transaction pages instead of adding a separate method for each of the transaction pages
   *
   * @return
   */
  @Step("البحث عن وارد عام")
  public MyTransactionsPage searchForGeneralInTransaction() {
    driver.element().click(filterButton);
    driver.element().click(filterTransactionType).click(filterInTransactionType);
    driver.element().click(filterSearchButton).verifyThat(filterSearchButton).isEnabled();
    return this;
  }

  @Step("لبذهاب الى صفحة تعديل وارد عام(جهات)")
  public InTransactionDraftPage modifyFirstGeneralIncomingTransactionDestination() {
    driver.element().scrollToElement(firstGeneralInTransactionDestination);
    driver.element().click(firstEditInDestination);
    return new InTransactionDraftPage(driver);
  }

  @Step("عدد الملحقات فى المعاملة الأولى وارد عام(جهات)")
  public int getFirstTrxDestinationAttachmentsNumber() {
    driver.element().scrollToElement(firstGeneralInTransactionDestination);
    String trimmedNumber = driver.element().getText(firstAttachmentsNumInDestination)
        .replaceAll("[^0-9]", "");
    return Integer.parseInt(trimmedNumber);
  }

  @Step("الحصول على رقم المعاملة الأولى")
  public String getFirstTransactionNumber() {
    return driver.element().getText(firstTransactionNumber);
  }
  @Step("طباعة اول معاملة")
  public void printFirstTransaction() {
    driver.element().click(copyInboxTitle);
     driver.element().click(receiveButton);
  }

  @Step("الحصول على موضوع/وصف المعاملة الأولى")
  public String getFirstTransactionDescription() {
    return driver.element().getText(firstTransactionDescription);
  }

  @Step("الحصول على عدد ملحقات المعاملة الأولى")
  public int getNumberOfAttachmentsOnFirstCard() {
    driver.element().scrollToElement(numberOfAttachmentsAtFirstTransaction);
    String trimmedNumber = driver.element().getText(numberOfAttachmentsAtFirstTransaction)
        .replaceAll("[^0-9]", "");
    return Integer.parseInt(trimmedNumber);
  }

  @Step("الذهاب الى صفحة الاحالة المباشرة")
  public DirectReferralPage navigateToDirectReferral() {
    driver.element().click(sideBarReferral)
        .verifyThat(multiOptionsSideBar)
        .doesNotExist();
    return new DirectReferralPage(driver);
  }

  @Step("تعديل المعاملة الأولى / الذهاب الى صفحة \"تعديل المعاملة\"")
  public OutgoingDraftTransactionPage editFirstInTransaction3() {
    driver.element().click(firstEditTransactionButotn);
    return new OutgoingDraftTransactionPage(driver);
  }

  @Step("التحقق من ان الوارد المنشأ من الرقم الموحد موجود في سلة معاملاتي")
  public boolean isTransactionPresent(String transactionNumber) {

    By transactionCell =
        By.xpath("//*[contains(text(),'" + transactionNumber + "')]");

    int count = driver.element().getElementsCount(transactionCell);

    return count > 0;
  }


  @Step("الذهاب الى صفحة تعديل المعاملة الوارد")
  public InTransactionDraftPage editFirstInTransaction2() {
    driver.element().click(By.cssSelector("a[title='تعديل وارد']"));
    return new InTransactionDraftPage(driver);
  }

  @Step("الذهاب الى سلة معاملات الحفظ")
  public MyTransactionsPage moveTransactionToArchive() {
    // 1️⃣ تحديد أول معاملة (Checkbox)
    driver.element()
        .scrollToElement(firstTransactionCheckbox)
        .click(firstTransactionCheckbox);
    // 2️⃣ الضغط على زر نقل إلى سلة الحفظ
    driver.element()
        .click(moveToArchiveButton);

    driver.element()
        .type(archiveReasonInput, " نقل لاختبار الأتمتة");

    driver.element()
        .click(confirmArchiveButton);
    driver.element()
        .click(yesButton);
    return this;
  }

  @Step("التحقق من حفظ المعاملة وعدم وجودها في سلة معاملاتي")
  public boolean isTransactionNotPresent(String transactionNumber) {
    // استدعاء ميثود البحث من الكومبوننت
    transactionsOperationsComponent
        .searchForTransactionWithId(transactionNumber, this);

    List<WebElement> results =
        driver.getDriver().findElements(transactionNumberCells);

    return results.stream()
        .noneMatch(e -> e.getText().equals(transactionNumber));
  }

  @Step("الذهاب الى سلة المعاملات المرسلة")
  public SentTransactionsPage navigateToSentTransactions() {
    driver.element()
        .click(sentTransactionsTab);
    return new SentTransactionsPage(driver);
  }

  @Step("الذهاب الى سلة معاملاتي")
  public SentTransactionsPage navigateToMyTransaction() {
    driver.element().scrollToElement(myTransactionsMenu)
        .click(myTransactionsMenu);
    return new SentTransactionsPage(driver);
  }

  @Step("تعديل المعاملة الأولى / الذهاب الى صفحة \"تعديل المعاملة\"")
  public InternalTransactionDraftPage editFirstInTransaction4() {
    driver.element().click(firstEditTransactionButotn);
    return new InternalTransactionDraftPage(driver);
  }


}
