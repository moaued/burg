package pages.transactions;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.SystemAdminComponent;
import components.TransactionsNavigationPanelComponent;
import components.TransactionsOperationsComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import utils.ElementsOperations;
import utils.GeneralOperations;

public class BriefcasePage extends TransactionsNavigationPanelComponent {

  //=====================BriefcaseOperations=========================
  private By transactionNumberField = By.id("TransNO");
  private By searchButton = By.cssSelector(".btn.btn-st1");

  //==========================TransactionCard=========================
  // private By transactionNumberOnCard = By.cssSelector(".pr-1");
  private By transactionCard = By.cssSelector(".box-grid");
  private By transactionNumberOnCard = By.xpath("(//div[contains(@class,'pr-1')])[1]");
  private By transactionNumberOnFirstCard = By.xpath("(//div[@class='pr-1'])[1]");
  //private By transactionTypeOnFirstCard = By.cssSelector(".treaty-name");
  private By transactionTypeOnFirstCard = By.xpath("(//div[contains(@class,'treaty-name')])[1]");
  private By remarkOnFirstTransactionCard = By.xpath(
      "(//div[contains(@class,'correspondence-b')]//div[contains(@class,'f-line')][3]/div[2])[1]");
  private By transactionInButtonOnCard = By.xpath("//a[@title='ادخال']");
  private By transactionOutButtonOnCard = By.xpath("//a[@title='اخراج']");
  private By transactionAddButtonOnCard = By.xpath("//a[@title='اضافة']");

  //========================RemarksModal===============================
  private By transactionRemark = By.id("rejectionReason");
  private By inTransactionButton = By.xpath(
      "//button[contains(@class,'remarks-button-popup') and normalize-space() = 'ادخال']");
  private By outTransactionButton = By.xpath(
      "//button[contains(@class,'remarks-button-popup') and normalize-space() = 'اخراج']");

  //======================General=====================================
  private By loadingSpinner = By.id("loadingModal");
  private By inAndOutModal = By.className(".jconfirm.white");


  @Getter
  private String sendTransactionRemarkText =
      "Remark: " + GeneralOperations.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");

  @Getter
  private TransactionsOperationsComponent transactionsOperationsComponent;
  @Getter
  private SystemAdminComponent systemAdminComponent;

  public BriefcasePage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
    transactionsOperationsComponent = new TransactionsOperationsComponent(driver);
    systemAdminComponent = new SystemAdminComponent(driver);
  }

  @Step("البحث عن معاملة")
  public BriefcasePage searchForTransaction(String transactionNo) {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(transactionNumberField))
        .type(transactionNumberField, transactionNo)
        .click(searchButton)
        .waitUntil(ElementsOperations.waitForElementToBeReady(searchButton));
    //.verifyThat(loadingSpinner).isHidden();
    // .verifyThat(loadingSpinner).isVisible();
    return this;
  }

  @Step("اعادة تحميل الصفحة")
  public BriefcasePage refreshPageBeforeSearch() {
    driver.browser().refreshCurrentPage();
    return this;
  }

  @Step("رقم المعاملة على كارت العرض")
  public String getTransactionNumberFromFirstCard() {
    return driver.element().getText(transactionNumberOnFirstCard);
  }

  @Step("عدد الكروت المسترجعة برقم المعاملة فى حقيبة العرض")
  public int getNumberOfRetrievedCards() {
    return driver.element().getElementsCount(transactionNumberOnCard);
  }

  @Step("الحصول على نوع المعاملة على كارت العرض")
  public String getTransactionTypeOnCard() {
    return driver.element().getText(transactionTypeOnFirstCard);
  }

  @Step("الحصول على ملاحظة المعاملة على كارت العرض")
  public String getTransactionRemarkOnCard() {
    return driver.element().getText(remarkOnFirstTransactionCard);
  }

  @Step("ادخال معاملة الى سلة حقيبة العرض")
  public BriefcasePage sendTransactionInBriefcase() {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(transactionInButtonOnCard))
        .click(transactionInButtonOnCard)
        .waitUntil(ElementsOperations.waitForElementToBeReady(transactionRemark))
        .type(transactionRemark, sendTransactionRemarkText)
        .click(inTransactionButton)
        .verifyThat(inAndOutModal).doesNotExist();
    return this;
  }

  @Step("التأكد من ادخال المعاملة الى سلة حقيبة العرض")
  public boolean confirmTransactionInBriefcase() {
    return driver.element().getElementsCount(transactionOutButtonOnCard) > 0
        && driver.element().getElementsCount(transactionAddButtonOnCard) > 0;
  }

  @Step("اخراج معاملة من سلة حقيبة العرض")
  public BriefcasePage sendTransactionOutBriefcase() {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(transactionOutButtonOnCard))
        .click(transactionOutButtonOnCard)
        .waitUntil(ElementsOperations.waitForElementToBeReady(transactionRemark))
        .type(transactionRemark, sendTransactionRemarkText)
        .click(outTransactionButton)
        .verifyThat(inAndOutModal).doesNotExist();;
    return this;
  }

  @Step("التأكد من اخراج المعاملة من سلة حقيبة العرض")
  public boolean confirmTransactionOutBriefcase() {
    return driver.element().getElementsCount(transactionInButtonOnCard) > 0;
  }

}
