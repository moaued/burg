package components;

import com.shaft.driver.SHAFT;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.transactions.InTransactionDraftPage;
import pages.transactions.InternalTransactionDraftPage;
import pages.transactions.OutTransactionDraftPage;
import pages.transactions.OutgoingDraftTransactionPage;
import utils.ElementsOperations;

public class TransactionsOperationsComponent {

  protected SHAFT.GUI.WebDriver driver;

  private By addGeneralIncomingTransaction = By.xpath(
      "//a[@href='/MCS.UI.Tenants/User/OutboundExternal/Add']");
  private By addGeneralIncomingTransactionButton = By.xpath(
      "//a[@href='/MCS.UI.Tenants/User/Inbound/Add']");
  private By addOutboundInternalTransaction = By.xpath(
      "//a[@href='/MCS.UI.Tenants/User/OutboundInternal/Add']");
  private By addOutgoingDraftTransaction = By.xpath(
      "//a[contains(@href,'/OutboundExternal/Add') and contains(@href,'isDraft=True')]"
  );
  private By filterButton = By.className("icon-filter");
  private By filterTransactionId = By.id("TransId");
  private By filterTransactionNumber = By.id("TransNo");
  private By filterSearchButton = By.xpath("//input[@type='button']");
  private By loadingSpinner = By.id("loadingModal");


  public TransactionsOperationsComponent(SHAFT.GUI.WebDriver driver) {
    this.driver = driver;
  }

  @Step("البحث عن معاملة باستخدام رقم المعاملة")
  public <T> T searchForTransactionWithId(String transactionId, T pageObject) {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(filterButton))
        .click(filterButton)
        .waitUntil(ExpectedConditions.or(ElementsOperations.waitForElementToBeReady(filterTransactionNumber)
            , ElementsOperations.waitForElementToBeReady(filterTransactionId)));
    By inputField = driver.element().getElementsCount(filterTransactionId) > 0 ? filterTransactionId
        : filterTransactionNumber;
    driver.element().type(inputField, transactionId);
    driver.element()
        // .clickUsingJavascript(filterSearchButton)
        .click(filterSearchButton)
        .waitUntil(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner))
        .verifyThat(filterSearchButton).isEnabled();
    return pageObject;
  }

  @Step("إنشاء صادر عام")
  public OutTransactionDraftPage addNewGeneralTransaction() {
    driver.element().click(addGeneralIncomingTransaction);
    return new OutTransactionDraftPage(driver);
  }

  @Step("اضافة وارد جديد")
  public InTransactionDraftPage addNewIncomingTransaction() {
    driver.element().click(addGeneralIncomingTransactionButton);
    return new InTransactionDraftPage(driver);
  }

  @Step("انشاء معاملة داخلية جديدة")
  public InternalTransactionDraftPage addNewOutboundInternalTransaction() {
  driver.element().click(addOutboundInternalTransaction);
  return new InternalTransactionDraftPage(driver);
  }

  @Step("انشاء معاملة مسودة صادر جديدة")
  public OutgoingDraftTransactionPage addNewOutgoingDraftransaction() {
    driver.element().click(addOutgoingDraftTransaction);

    return new OutgoingDraftTransactionPage(driver);
  }

}
