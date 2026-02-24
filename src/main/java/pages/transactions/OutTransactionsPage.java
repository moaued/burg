package pages.transactions;

import com.shaft.driver.SHAFT;
import com.shaft.gui.internal.locator.Locator;
import components.HorizontalMenusComponent;
import components.TransactionsNavigationPanelComponent;
import components.TransactionsOperationsComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;

public class OutTransactionsPage extends TransactionsNavigationPanelComponent {


  private By exportedTransactionsButton = By.xpath("//a[contains(text(), 'معاملات تم تصديرها')]");
  private By addGeneralIncomingTransaction = By.xpath(
      "//a[@href='/MCS.UI.Tenants/User/OutboundExternal/Add']");
  private By editTransactionButton = By.xpath("//*[@class='icon-outline-edit']");
  private By firstEditTransactionButotn = Locator.hasTagName("i").containsClass("icon-outline-edit")
      .isFirst().build();
  private By firstTransactionNumber = Locator.hasTagName("div").containsClass("pr-1").isFirst()
      .build();
  private By firstTransactionDescription = Locator.hasTagName("div").containsClass("description")
      .isFirst().build();
  private By numberOfAttachmentsAtFirstTransaction = By.xpath(
      "(//div[contains(@class,'ml-auto')]/div[@class='info']/span[1])[1]");
  private By filterButton = By.className("icon-filter");
  private By filterTransactionId = By.id("TransId");
  private By filterSearchButton = By.xpath("//input[@type='button']");

  @Getter
  private TransactionsOperationsComponent transactionsOperationsComponent;


  public OutTransactionsPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
    transactionsOperationsComponent = new TransactionsOperationsComponent(driver);
  }

  @Step("الذهاب الى صفحة \"معاملات تم تصديرها\"")
  public OutTransactionsPage navigateToExportedTransactions() {
    driver.element().click(exportedTransactionsButton)
        .verifyThat(exportedTransactionsButton).attribute("aria-expanded").isTrue().perform();
    return this;
  }

  @Step("تعديل المعاملة الصادرة الأولى / الذهاب الى صفحة \"تعديل صادر\"")
  public OutTransactionDraftPage tabOnEditFirstOutTransaction() {
    driver.element().click(firstEditTransactionButotn);
    //.assertThat(firstEditTransactionButotn).doesNotExist();
    return new OutTransactionDraftPage(driver);
  }

  @Step("الحصول على رقم المعاملة الأولى")
  public String getFirstTransactionNumber() {
    return driver.element().getText(firstTransactionNumber);
  }

  @Step("الحصول على موضوع/وصف المعاملة الأولى")
  public String getFirstTransactionDescription() {
    return driver.element().getText(firstTransactionDescription);
  }

  @Step("الحصول على عدد ملحقات المعاملة الأولى")
  public int getNumberOfAttachmentsOnFirstCard() {
    driver.element().scrollToElement(numberOfAttachmentsAtFirstTransaction);
    return Integer.parseInt(driver.element().getText(numberOfAttachmentsAtFirstTransaction));
  }

  @Step("البحث عن معاملة باستخدام رقم المعاملة")
  public OutTransactionsPage searchForTransactionWithId(String transactionId) {
    driver.element().click(filterButton)
        .type(filterTransactionId, transactionId)
        .click(filterSearchButton).verifyThat(filterButton).isEnabled();
    driver.element().verifyThat(firstTransactionNumber).text().equals(transactionId);
    //driver.browser().captureScreenshot();
    return this;
  }
  @Step("التحقق من ان الصادر المنشأ من الرقم الموحد موجود في سلة الصادر")
  public boolean isTransactionPresent2(String transactionNumber) {

    By transactionCell =
        By.xpath("//*[contains(text(),'" + transactionNumber + "')]");

    int count = driver.element().getElementsCount(transactionCell);

    return count > 0;
  }

}
