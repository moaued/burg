package pages.transactions;

import com.shaft.driver.SHAFT;
import com.shaft.driver.SHAFT.GUI.WebDriver;
import components.HorizontalMenusComponent;
import components.TransactionsNavigationPanelComponent;
import components.TransactionsOperationsComponent;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;


public class SentTransactionsPage extends TransactionsNavigationPanelComponent{

  private By revertButton = By.cssSelector("a[data-type='Revert']");
  private By SentManagementTab = By.xpath("//a[text()='مرسلة الادارة']");

  @Getter
  private TransactionsOperationsComponent transactionsOperationsComponent;

  public SentTransactionsPage(SHAFT.GUI.WebDriver driver) {
    super(driver, new HorizontalMenusComponent(driver));
    transactionsOperationsComponent = new TransactionsOperationsComponent(driver);

  }

  @Step("سحب المعاملة المرسلة ")
  public SentTransactionsPage withdrawTransaction() {
    driver.element().click(revertButton);
    return this;
  }
  @Step("الذهاب الى سلة المعاملات المرسلة تبويبة مرسلة الادارة")
  public SentTransactionsPage switchToSentManagementTab() {
    driver.element()
        .click(SentManagementTab);
    return this;
  }

  @Step("التحقق من وجود المعاملة رقم '{transactionNumber}' في المعاملات الصادرة")
  public boolean isTransactionPresent(String transactionNumber) {
    By transactionCell = By.xpath("//*[contains(text(),'" + transactionNumber + "')]");
    return driver.element().getElementsCount(transactionCell) > 0;
  }

}
