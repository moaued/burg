package pages.transactions;

import com.shaft.driver.SHAFT;
import components.HorizontalMenusComponent;
import components.TransactionsNavigationPanelComponent;
import components.TransactionsOperationsComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ElementsOperations;

public class AdminActionsPage extends TransactionsNavigationPanelComponent {

  // =========================================================
  // ======================== Locators =======================
  // =========================================================

  private By loadingSpinner = By.id("loadingModal");

  private By withdrawAndEditButton = By.xpath(
      "//a[@title='سحب وتعديل' and contains(@onclick,'Open')]");

  private By transactionNumberCells = By.xpath(
      "//*[contains(@class,'transaction-number') or contains(@class,'pr-1')]");

  // =========================================================
  // ====================== Components =======================
  // =========================================================

  @Getter
  private TransactionsOperationsComponent transactionsOperationsComponent;

  // =========================================================
  // ====================== Constructor ======================
  // =========================================================

  public AdminActionsPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
    transactionsOperationsComponent = new TransactionsOperationsComponent(driver);
  }

  // =========================================================
  // ======================== Actions ========================
  // =========================================================

  @Step("سحب المعاملة الصادرة وفتحها للتعديل")
  public OutTransactionDraftPage withdrawAndEditTransaction() {
    driver.element()
        .waitUntil(ElementsOperations.waitForElementToBeReady(withdrawAndEditButton))
        .click(withdrawAndEditButton)
        .waitUntil(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
    return new OutTransactionDraftPage(driver);
  }

  // =========================================================
  // ======================= Getters =========================
  // =========================================================

  @Step("التحقق من وجود المعاملة رقم '{transactionNumber}' في القائمة")
  public boolean isTransactionPresent(String transactionNumber) {
    By transactionCell = By.xpath("//*[contains(text(),'" + transactionNumber + "')]");
    return driver.element().getElementsCount(transactionCell) > 0;
  }


}
